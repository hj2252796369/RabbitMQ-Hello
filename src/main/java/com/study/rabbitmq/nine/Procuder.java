package com.study.rabbitmq.nine;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: RabbitMQ-Hello
 * @ClassName Procuder
 * @description:
 * @author: huJie
 * @create: 2022-02-10 09:41
 **/
public class Procuder {

    public static final String QUEUE_NAME = "mirrior_hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQFactory.getChannel();


        Map<String, Object> params = new HashMap<>();
        params.put("x-max-priority", 10);
        channel.queueDeclare(QUEUE_NAME, true, false, false, params);

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().priority(5).build();

        for (int i = 0; i < 10; i++) {
            String msg = "info" + i;

            if (i == 5) {
                channel.basicPublish("", QUEUE_NAME, properties, msg.getBytes());
            } else {
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            }

        }
    }
}
