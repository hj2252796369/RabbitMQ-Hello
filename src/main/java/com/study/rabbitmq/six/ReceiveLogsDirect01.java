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
public class ReceiveLogsDirect01 {

    private final static String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQFactory.getChannel();

        channel.queueDeclare("console", false, false, false, null);
        channel.queueBind("console", EXCHANGE_NAME, "info");
        channel.queueBind("console", EXCHANGE_NAME, "warning");

        // 接收信息
        channel.basicConsume("console", true, (String consumerTag, Delivery message)->{
            System.out.println("ReceiveLogsDirect01 === > 接受的信息：" + new String(message.getBody(), StandardCharsets.UTF_8));
        }, (String consumerTag)->{});

    }
}
