package com.git.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Mr ● Li
 * @version 1.0
 * @date 2021/4/25 16:03
 */
@Component
@RabbitListener(queues = "fanout.B")
public class FanoutReceiverB {

    @RabbitHandler
    public void process(Map messageMap){
        System.out.println("FanoutReceiverB消费者收到消息  : " + messageMap.toString());
    }
}
