package com.git.listener;

import com.git.common.MailConsumer;
import com.git.config.RabbitConfig;
import com.git.service.MsgLogService;
import com.git.util.BaseConsumer;
import com.git.util.BaseConsumerProxy;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Mr ● Li
 * @version 1.0
 * @date 2021/4/26 12:08
 */
@Component
@Slf4j
public class ConsumerMailListener {

    @Autowired
    private MailConsumer mailConsumer;

    @Autowired
    private MsgLogService msgLogService;

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE_NAME)
    public void consume(Message message, Channel channel)throws IOException{
        BaseConsumerProxy baseConsumerProxy = new BaseConsumerProxy(mailConsumer, msgLogService);
        BaseConsumer consumer = (BaseConsumer) baseConsumerProxy.getProxy();
        if(null != consumer){
            consumer.consume(message, channel);
        }
    }
}
