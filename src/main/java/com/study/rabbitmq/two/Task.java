package com.study.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitMQ-Hello
 * @ClassName Task
 * @description:
 * @author: huJie
 * @create: 2022-01-19 18:31
 **/
public class Task {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQFactory.getChannel();
        /**
         * 参数说明：
         *  queue：队列名称
         *  durable：队列是否持久化
         *  exclusive：是否是独占队列，队列只有一个消费者
         *  autoDelete：是否自动删除
         *  arguments： 其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            channel.basicPublish("", QUEUE_NAME, null, s.getBytes());
            System.out.println("发送消息：" + s);
        }
    }

}
