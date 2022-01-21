package com.study.rabbitmq.five;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.util.Scanner;

/**
 * @program: RabbitMQ-Hello
 * @ClassName EmitLog
 * @description:
 * @author: huJie
 * @create: 2022-01-21 14:44
 **/
public class EmitLog {

    private final static String EXCHANGE_NAME = "logs";
    private final static Integer SEND_COUNT = 1000;

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQFactory.getChannel();

        // 声明一个交换机，类型为FANOUT
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            // 向交换机推送消息，不指定队列则是发送所有的队列信息
            channel.basicPublish(EXCHANGE_NAME, "", null, s.getBytes());
            System.out.println("发送消息：" + s);
        }

    }
}
