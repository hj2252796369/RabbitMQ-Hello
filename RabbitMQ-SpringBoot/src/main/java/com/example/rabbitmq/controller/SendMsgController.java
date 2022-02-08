package com.example.rabbitmq.controller;

import com.example.rabbitmq.config.DelayedQueueConfig;
import com.example.rabbitmq.config.TtlRabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program: RabbitMQ-Hello
 * @ClassName SendMsgController
 * @description:
 * @author: huJie
 * @create: 2022-02-08 16:16
 **/
@RestController
@RequestMapping("/ttl")
@Slf4j
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{msg}")
    public void sendMsg(@PathVariable String msg){
    log.info("当前时间{}, 发送消息内容为：{}", new Date().toString(), msg);

    rabbitTemplate.convertAndSend(TtlRabbitMQConfig.X_EXCHANGE, TtlRabbitMQConfig.XA_ROUTING, "消息来自 ttl 为 10S 的队列:"+msg);
    rabbitTemplate.convertAndSend(TtlRabbitMQConfig.X_EXCHANGE, TtlRabbitMQConfig.XB_ROUTING, "消息来自 ttl 为 40S 的队列:"+msg);
    }

    @GetMapping("/sendMsg/{msg}/{ttlTime}")
    public void sendMsg(@PathVariable String msg, @PathVariable String ttlTime){
        log.info("当前时间{}, 发送消息内容为：{},延迟时间为：{}", new Date().toString(), msg, ttlTime);

        rabbitTemplate.convertAndSend(TtlRabbitMQConfig.X_EXCHANGE, TtlRabbitMQConfig.XC_ROUTING, "消息来自 ttl 为 10S 的队列:"+msg, (message -> {
            message.getMessageProperties().setExpiration(ttlTime);
            return message;
        }));
    }


    @GetMapping("/sendDelayedMsg/{msg}/{ttlTime}")
    public void sendDelayedMsg(@PathVariable String msg, @PathVariable Integer ttlTime){
        log.info("当前时间{},发送一条延迟 {} 毫秒的信息给队列 delayed.queue:{}", new Date().toString(), ttlTime, msg);

        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE, DelayedQueueConfig.DELAYED_ROUTING_KEY, "消息来自 ttl 为 "+ttlTime+" 的队列:"+msg, (correlationData -> {
            correlationData.getMessageProperties().setDelay(ttlTime);
            return correlationData;
        }));
    }

}
