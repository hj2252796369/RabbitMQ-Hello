package com.study.rabbitmq.eight;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.nio.charset.StandardCharsets;

/**
 * @program: RabbitMQ-Hello
 * @ClassName Customer01
 * @description:
 * @author: huJie
 * @create: 2022-02-08 10:02
 **/
public class Customer02 {
    private static final String DEAD_EXCHANGE = "dead_exchange";

    private static final String DEAD_QUEUE = "dead_queue";

    private static final String DEAD_ROUTING_KEY = "lisi";

    public static void main(String[] args) throws Exception{

        Channel channel = RabbitMQFactory.getChannel();

        System.out.println("等待接受消息......");

        channel.basicConsume(DEAD_QUEUE, false, (( consumerTag,  message)->{
            System.out.println("C2 死信队列 收到消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
        }), (consumerTag -> {}));

    }
}
