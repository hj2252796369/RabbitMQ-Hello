package com.example.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: RabbitMQ-Hello
 * @ClassName ConfirmConfig
 * @description:
 * @author: huJie
 * @create: 2022-02-09 17:34
 **/
@Slf4j
@Component
public class ConfirmConfig {
    public static final String CONFIRM_EXCHANGE = "confirm.exchange";
    public static final String CONFIRM_QUEUE = "confirm.queue";
    public static final String CONFIRM_ROUTING_KEY = "key1";

    public static final String BACKUP_EXCHANGE = "backup.exchange";
    public static final String BACKUP_QUEUE = "backup.queue";
    public static final String WARNING_QUEUE = "warning.queue";

    @Bean
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE).alternate(BACKUP_EXCHANGE).build();
    }

    @Bean
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean
    public Binding confirmQueueToExchange(@Qualifier("confirmExchange") DirectExchange directExchange, @Qualifier("confirmQueue") Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean
    public FanoutExchange backupExchange(){
        return ExchangeBuilder.fanoutExchange(BACKUP_EXCHANGE).build();
    }

    @Bean
    public Queue backupQueue(){
        return QueueBuilder.durable(BACKUP_QUEUE).build();
    }

    @Bean
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE).build();
    }

    @Bean
    public Binding backupQueueToExchange(@Qualifier("backupExchange") FanoutExchange fanoutExchange, @Qualifier("backupQueue") Queue queue){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding warningQueueToExchange(@Qualifier("backupExchange") FanoutExchange fanoutExchange, @Qualifier("warningQueue") Queue queue){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
}
