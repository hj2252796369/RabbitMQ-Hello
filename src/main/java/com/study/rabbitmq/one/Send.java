package com.study.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
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
        /**
         * 参数说明：
         *  queue：队列名称
         *  durable：队列是否持久化
         *  exclusive：是否是独占队列，队列只有一个消费者
         *  autoDelete：是否自动删除
         *  arguments： 其他参数
         */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        String message = "Hello World!";
        /**
         * 参数说明：
         *  exchange：推送到的交换机上
         *  routingKey： 交换机与队列绑定的路由key信息，即到哪个队列中
         *  props：其他参数
         *  body: 二进制信息
         *
         *
         */
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

        System.out.println("[Send ===> ]" + message);
    }
}
