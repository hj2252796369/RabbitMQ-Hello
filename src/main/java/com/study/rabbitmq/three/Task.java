package com.study.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitMQ-Hello
 * @ClassName Task
 * @description:
 * @author: huJie
 * @create: 2022-01-19 21:40
 **/
public class Task {
    private final static String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQFactory.getChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        Scanner c = new Scanner(System.in);
        while (c.hasNextLine()) {
            String s = c.nextLine();
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, s.getBytes());
            System.out.println("发送消息：" + s);
        }
    }
}
