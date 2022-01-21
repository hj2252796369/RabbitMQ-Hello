package com.study.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();;
        connectionFactory.setHost("192.168.56.104");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();


        String arg = args[0];
        System.out.println(arg);
        /**
         * 参数说明：
         *  queue：队列名称
         *  autoAck：是否自动接受
         *  deliverCallback：成功接受处理
         *  cancelCallback：失败处理
         *
         */
        channel.basicConsume(QUEUE_NAME, false, (consumerTag, message)->{
            System.out.println( new String(message.getBody(), "UTF-8"));
        }, (consumerTag)->{
            System.out.println("消息接收失败");
        });

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    }
}
