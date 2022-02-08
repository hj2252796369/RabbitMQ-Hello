package com.example.rabbitmq.consumer;

import com.example.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @program: RabbitMQ-Hello
 * @ClassName DelayedQueueConsumer
 * @description:
 * @author: huJie
 * @create: 2022-02-08 17:57
 **/
@Component
@Slf4j
public class DelayedQueueConsumer {

    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE)
    public void receiveDelayedQueue(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("当前时间：{},DelayedQueueConsumer 收到延时队列的消息：{}", new Date().toString(), msg);
    }

}
