package com.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: RabbitMQ-Hello
 * @ClassName DelayedQueueConfig
 * @description:
 * @author: huJie
 * @create: 2022-02-08 17:44
 **/
@Configuration
public class DelayedQueueConfig {

    public static final String DELAYED_EXCHANGE = "delayed.exchange";

    public static final String DELAYED_QUEUE = "delayed.queue";

    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";


    @Bean
    public Queue delayedQueue() {
        return new Queue(DELAYED_QUEUE);
    }

    @Bean("delayedExchange")
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", ExchangeTypes.DIRECT);
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding bindingDelayedQueue(@Qualifier("delayedQueue") Queue queue, @Qualifier("delayedExchange") CustomExchange customExchange) {
        return BindingBuilder.bind(queue).to(customExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

}
