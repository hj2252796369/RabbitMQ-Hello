package com.study.rabbitmq.four;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @program: RabbitMQ-Hello
 * @ClassName Confirm
 * @description:
 * @author: huJie
 * @create: 2022-01-20 15:18
 **/
public class ConfirmMessage {

    private static final Integer MAX_SEND = 1000;

    public static void main(String[] args) throws Exception {

//      pushMessageSingleConfirm();   // 消耗时间：3263   3354   3201

//      pushMessageBatchConfirm();   // 消耗时间：159   155   143

        pushMessageAsyn();          // 消耗时间： 51  70  59

    }

    /**
     * 单个消息发布确认
     *
     * @throws Exception
     */
    public static void pushMessageSingleConfirm() throws Exception {
        String queueName = UUID.randomUUID().toString();

        Channel channel = RabbitMQFactory.getChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.confirmSelect();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX_SEND; i++) {
            String message = "消息" + i;
            var s = "消息" + i;
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            boolean b = channel.waitForConfirms();
            if (b) {
                System.out.println("发送消息成功，并收到消息确认");
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("消耗时间：" + (end - start));
    }

    /**
     * 批量发布确认
     *
     * @throws Exception
     */
    public static void pushMessageBatchConfirm() throws Exception {
        String queueName = UUID.randomUUID().toString();
        Channel channel = RabbitMQFactory.getChannel();

        channel.queueDeclare(queueName, true, false, false, null);
        channel.confirmSelect();

        long start = System.currentTimeMillis();
        for (int i = 1; i <= MAX_SEND; i++) {
            String message = "消息" + i;
            var s = "消息" + i;
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("发送消息成功，并收到消息确认");
            if (i % 200 == 0) {
                channel.waitForConfirms();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("消耗时间：" + (end - start));
    }

    /**
     * 异步发布确认
     * @throws Exception
     */
    public static void pushMessageAsyn() throws Exception {
        Channel channel = RabbitMQFactory.getChannel();

        String queueName = UUID.randomUUID().toString();

        channel.confirmSelect();
        channel.queueDeclare(queueName, true, false, false, null);

        ConcurrentSkipListMap<Long, String> linkedQueue = new ConcurrentSkipListMap<Long, String>();

        /**
         * 参数说明：
         *  deliveryTag：消息所在的队列号
         *  multiple： 是否批量确认
         */
        // 消息确认成功  回调函数
        ConfirmCallback ackCallback = (long deliveryTag, boolean multiple) -> {
            if(multiple){
                ConcurrentNavigableMap<Long, String> longStringConcurrentNavigableMap = linkedQueue.headMap(deliveryTag);
                longStringConcurrentNavigableMap.clear();
            }else{
                linkedQueue.remove(deliveryTag);
            }
            System.out.println("传输成功消息==>：" + deliveryTag);
        };

        // 消息确认失败 回调函数
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            System.out.println("传输失败消息：" + deliveryTag);
        };

        // 准备消息的监听器，监听那些消息成功与失败  // 准备异步监听
        /**
         * 1、监听消息成功
         * 2、监听消息失败
         */
        channel.addConfirmListener(ackCallback, nackCallback);

        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_SEND; i++) {
            String message = "消息" + i;
            var s = "消息" + i;
            linkedQueue.put(channel.getNextPublishSeqNo(), message);
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        }

        long end = System.currentTimeMillis();
        System.out.println("消耗时间：" + (end - start));
    }

}
