package com.study.rabbitmq.six;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.nio.charset.StandardCharsets;

/**
 * @program: RabbitMQ-Hello
 * @ClassName ReceiveLogsDirect01
 * @description:
 * @author: huJie
 * @create: 2022-01-21 17:21
 **/
public class ReceiveLogsDirect02 {

    private final static String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQFactory.getChannel();

        channel.queueDeclare("disk", false, false, false, null);
        channel.queueBind("disk", EXCHANGE_NAME, "error");

        // 接收信息
        channel.basicConsume("disk", true, (String consumerTag, Delivery message)->{
            System.out.println("ReceiveLogsDirect02 === > 接受的信息：" + new String(message.getBody(), StandardCharsets.UTF_8));
        }, (String consumerTag)->{});

    }
}
