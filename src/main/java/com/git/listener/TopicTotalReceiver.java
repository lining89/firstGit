package com.git.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Mr ● Li
 * @version 1.0
 * @date 2021/4/25 15:06
 */
@Component
@RabbitListener(queues = "topic.woman")
public class TopicTotalReceiver {

    @RabbitHandler
    public void process(Map messageMap){
        System.out.println("TopicTotalReceiver消费者收到消息  : " + messageMap.toString());
    }
}
