package com.example.rabbitmq.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: RabbitMQ-Hello
 * @ClassName ConfirmCallBack
 * @description:
 * @author: huJie
 * @create: 2022-02-09 17:45
 **/
@Slf4j
@Component
public class CustomCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }
    /**
     * 交换机不管是否收到消息的一个回调方法
     * CorrelationData
     * 消息相关数据
     * ack
     * 交换机是否收到消息
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id=correlationData!=null?correlationData.getId():"";
        if(ack){
            // 交换机接受成功
            log.info("交换机已经收到 id 为:{}的消息",id);
        }else{
            // 交换机接收不到信息
            log.info("交换机还未收到 id 为:{}消息,由于原因:{}",id,cause);
        }
    }
    //当消息无法路由的时候的回调方法
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error(" 消 息 {}, 被 交 换 机 {} 退 回 ， 退 回 原 因 :{}, 路 由 key:{}",new
        String(returned.getMessage().getBody()),returned.getExchange(),returned.getReplyText(),returned.getRoutingKey());
    }
}
