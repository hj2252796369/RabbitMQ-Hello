package com.study.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.nio.charset.StandardCharsets;

/**
 * @program: RabbitMQ-Hello
 * @ClassName ReceiveLogs01
 * @description:
 * @author: huJie
 * @create: 2022-01-21 14:51
 **/
public class ReceiveLogs02 {
    private final static String EXCHANGE_NAME = "logs";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQFactory.getChannel();
        /**
         * 获取临时队列
         */
        String queueName = channel.queueDeclare().getQueue();

        // 绑定队列和交换机
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        // 接收信息
        channel.basicConsume(queueName, true, (String consumerTag, Delivery message)->{
            System.out.println("ReceiveLogs02 === > 接受的信息：" + new String(message.getBody(), StandardCharsets.UTF_8));
        }, (String consumerTag)->{});
    }
}
