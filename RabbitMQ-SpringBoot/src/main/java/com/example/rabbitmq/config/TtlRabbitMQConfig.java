package com.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: RabbitMQ-Hello
 * @ClassName TtlRabbitMQConfig
 * @description:
 * @author: huJie
 * @create: 2022-02-08 16:00
 **/
@Component
public class TtlRabbitMQConfig {

    // 正常交换机X
    public final static String X_EXCHANGE = "X";
    // 死信交换机Y
    public final static String Y_DEAD_LETTER_EXCHANGE = "Y";

    // 队列 QA
    public final static String QA_QUEUE = "QA";

    // 队列QB
    public final static String QB_QUEUE = "QB";

    public final static String QC_QUEUE = "QC";

    // 死信队列QD
    public final static String QD_DEAD_LETTER_QUEUE = "QD";

    // 路由
    public final static String XA_ROUTING = "XA";
    public final static String XB_ROUTING = "XB";
    public final static String XC_ROUTING = "XC";
    public final static String YD_ROUTING = "YD";

    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }


    @Bean("queueA")
    public Queue queueA(){
        return QueueBuilder.durable(QA_QUEUE).ttl(10000).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey(YD_ROUTING).build();
    }

    @Bean
    public Binding queueABindingX(@Qualifier("xExchange")DirectExchange directExchange, @Qualifier("queueA") Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with(XA_ROUTING);
    }


    @Bean("queueB")
    public Queue queueB(){
        return QueueBuilder.durable(QB_QUEUE).ttl(40000).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey(YD_ROUTING).build();
    }

    @Bean
    public Binding queueBBindingX(@Qualifier("xExchange")DirectExchange directExchange, @Qualifier("queueB") Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with(XB_ROUTING);
    }

    @Bean("queueC")
    public Queue queueC(){
        return QueueBuilder.durable(QC_QUEUE).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey(YD_ROUTING).build();
    }

    @Bean
    public Binding queueCBindingX(@Qualifier("xExchange")DirectExchange directExchange, @Qualifier("queueC") Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with(XC_ROUTING);
    }



    @Bean("queueD")
    public Queue queueD(){
        return QueueBuilder.durable(QD_DEAD_LETTER_QUEUE).build();
    }
    @Bean
    public Binding queueDBindingY(@Qualifier("yExchange")DirectExchange directExchange, @Qualifier("queueD") Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with(YD_ROUTING);
    }


}
