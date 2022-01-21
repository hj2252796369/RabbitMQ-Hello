package com.study.rabbitmq.seven;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.nio.charset.StandardCharsets;

/**
 * @program: RabbitMQ-Hello
 * @ClassName ReceiveLogsTopic01
 * @description:
 * @author: huJie
 * @create: 2022-01-21 18:02
 **/
public class ReceiveLogsTopic02 {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQFactory.getChannel();

        channel.queueDeclare("Q2", false, false, false, null);
        channel.queueBind("Q2", EXCHANGE_NAME, "*.*.rabbit");

        channel.basicConsume("Q2", false, ((consumerTag, message) -> {
            System.out.println("Q2收到消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
        }), (consumerTag -> {}));

    }
}
