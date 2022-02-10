package com.study.rabbitmq.nine;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMQFactory;

/**
 * @program: RabbitMQ-Hello
 * @ClassName Consumer
 * @description:
 * @author: huJie
 * @create: 2022-02-10 09:46
 **/
public class Consumer {
    public static final String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception{

        Channel channel = RabbitMQFactory.getChannel();
        channel.basicConsume(QUEUE_NAME, false, (tag, msg)->{
            System.out.println("接收信息：" + new String(msg.getBody()));
        }, (msg)->{});

    }
}
