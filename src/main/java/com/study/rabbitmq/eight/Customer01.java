package com.study.rabbitmq.eight;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMQFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: RabbitMQ-Hello
 * @ClassName Customer01
 * @description:
 * @author: huJie
 * @create: 2022-02-08 10:02
 **/
public class Customer01 {
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    private static final String DEAD_EXCHANGE = "dead_exchange";


    private static final String NORMAL_QUEUE = "normal_queue";
    private static final String DEAD_QUEUE = "dead_queue";

    private static final String NORMAL_ROUTING_KEY = "zhangsan";
    private static final String DEAD_ROUTING_KEY = "lisi";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQFactory.getChannel();


        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 声明死性队列
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        // 声明正常交换机  队列  以及绑定关系
        Map<String, Object> argsParam = new HashMap<>();
        argsParam.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        argsParam.put("x-dead-letter-routing-key", DEAD_ROUTING_KEY);
        argsParam.put("x-dead-letter-routing-key", DEAD_ROUTING_KEY);
        // 设置队列最大长度
        argsParam.put("x-max-length", 6);
//        argsParam.put("x-message-ttl", 100000);

        channel.queueDeclare(NORMAL_QUEUE, false, false, false, argsParam);
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, NORMAL_ROUTING_KEY);


        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, DEAD_ROUTING_KEY);

        System.out.println("等待接受消息......");


        channel.basicConsume(NORMAL_QUEUE, false, ((consumerTag, message) -> {

            String s = new String(message.getBody(), StandardCharsets.UTF_8);

            // 消费者拒绝接受信息
            if ("info5".equals(s)) {
                System.out.println("C1 普通队列 拒绝 收到消息：" +s);
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            }else{
                System.out.println("C1 普通队列 收到消息：" +s);
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }


        }), (consumerTag -> {
        }));

    }
}
