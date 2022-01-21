package com.study.rabbitmq.four;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitMQ-Hello
 * @ClassName WorkerOne
 * @description:
 * @author: huJie
 * @create: 2022-01-19 21:44
 **/
public class WorkerTwo {

    private final static String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitMQFactory.getChannel();

        System.out.println("C2接收信息，接收信息时间长");

        channel.basicQos(3);
        channel.basicConsume(QUEUE_NAME, false, (consumerTag, message)->{
            try {
                System.out.println("C2接收到的信息");
                TimeUnit.SECONDS.sleep(30);
                System.out.println("C2接收信息 ===> " + new String(message.getBody(), "UTF-8"));
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, (consumerTag)->{
            System.out.println("C2取消接收信息 ===> " + consumerTag.toString());
        });

    }
}
