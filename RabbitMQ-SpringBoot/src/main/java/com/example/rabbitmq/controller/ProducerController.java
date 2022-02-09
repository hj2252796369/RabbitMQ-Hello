package com.example.rabbitmq.controller;

import com.example.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: RabbitMQ-Hello
 * @ClassName ProducerController
 * @description:
 * @author: huJie
 * @create: 2022-02-09 17:40
 **/
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message) {
//指定消息 id 为 1
        CorrelationData correlationData1 = new CorrelationData("1");
        String routingKey = "key1";
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE, routingKey, message + "-" + routingKey, correlationData1);
        CorrelationData correlationData2 = new CorrelationData("2");
        routingKey = "key2";
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE, routingKey, message + "-" + routingKey, correlationData2);
        log.info("发送消息内容:{}", message);
    }
}
