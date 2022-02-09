package com.study.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitMQ-Hello
 * @ClassName Worker
 * @description:
 * @author: huJie
 * @create: 2022-01-19 18:29
 **/
public class Worker {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQFactory.getChannel();

        String name = args[0];

        System.out.println(name + " 准备接受消息......");

        /**
         * 参数说明：
         *  queue：队列名称
         *  autoAck：是否自动接受
         *  deliverCallback：成功接受处理
         *  cancelCallback：失败处理
         *
         */
        channel.basicConsume(QUEUE_NAME, true, (consumerTag, message)->{
            System.out.println( new String(message.getBody(), "UTF-8"));
        }, (consumerTag)->{
            System.out.println("消息接收失败");
        });
    }
}
