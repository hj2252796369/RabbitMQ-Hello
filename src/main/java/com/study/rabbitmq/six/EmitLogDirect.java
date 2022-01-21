package com.study.rabbitmq.six;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.util.Scanner;

/**
 * @program: RabbitMQ-Hello
 * @ClassName EmitLogDirect
 * @description:
 * @author: huJie
 * @create: 2022-01-21 15:18
 **/
public class EmitLogDirect {


    private final static String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQFactory.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            // 向交换机推送消息，不指定队列则是发送所有的队列信息
            channel.basicPublish(EXCHANGE_NAME, "error", null, s.getBytes());
            System.out.println("发送消息：" + s);
        }

    }
}
