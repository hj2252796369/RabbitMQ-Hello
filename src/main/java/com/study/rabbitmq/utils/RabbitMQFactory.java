package com.study.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitMQ-Hello
 * @ClassName RabbitMQFactory
 * @description:
 * @author: huJie
 * @create: 2022-01-19 11:39
 **/
public class RabbitMQFactory {
    public static Channel getChannel() throws IOException, TimeoutException {
//        创建一个连接工程
        ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();
//        设置MQ的连接信息  ip  端口号  y、用户名  密码
        connectionFactory.setHost("192.168.56.104");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

        // 创建一个连接
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        return channel;
    }
}
