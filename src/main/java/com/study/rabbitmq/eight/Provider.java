package com.study.rabbitmq.eight;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMQFactory;

/**
 * @program: RabbitMQ-Hello
 * @ClassName Provider
 * @description:
 * @author: huJie
 * @create: 2022-02-08 14:20
 **/
public class Provider {

    private static final String NORMAL_EXCHANGE = "normal_exchange";
    private static final String NORMAL_QUEUE = "normal_queue";
    private static final String NORMAL_ROUTING_KEY = "zhangsan";


    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQFactory.getChannel();


        // 设置TTL时间  time to live
//        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().build();

        for (int i = 0; i < 10; i++) {
            String msg = "info" + i;
            System.out.println("发送消息.." + msg);
            channel.basicPublish(NORMAL_EXCHANGE, NORMAL_ROUTING_KEY, properties, msg.getBytes());
        }
    }
}
