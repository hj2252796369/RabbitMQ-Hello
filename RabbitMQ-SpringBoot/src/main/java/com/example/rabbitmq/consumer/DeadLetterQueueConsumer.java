package com.example.rabbitmq.consumer;

import com.example.rabbitmq.config.TtlRabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @program: RabbitMQ-Hello
 * @ClassName DeadLetterQueueConsumer
 * @description:
 * @author: huJie
 * @create: 2022-02-08 16:24
 **/
@Component
@Slf4j
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = TtlRabbitMQConfig.QD_DEAD_LETTER_QUEUE)
    public void receiveD(Message message, Channel channel){
        log.info("当前时间为：{}，接受消息：{}", new Date().toString(), new String(message.getBody(), StandardCharsets.UTF_8));
    }

}
