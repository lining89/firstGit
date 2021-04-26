package com.git.listener;

import com.git.config.RabbitConfig;
import com.git.entity.Mail;
import com.git.util.JsonUtil;
import com.git.util.SendMailUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
    private SendMailUtil sendMailUtil;

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE_NAME)
    public void consume(Message message, Channel channel)throws IOException{
        //将消息转化为对象
        String str = new String(message.getBody());
        Mail mail = JsonUtil.strToObj(str, Mail.class);
        log.info("收到消息: {}", mail.toString());
        MessageProperties properties = message.getMessageProperties();
        long tag = properties.getDeliveryTag();

        boolean sucess = sendMailUtil.send(mail);
        if(sucess){
            channel.basicAck(tag, false);//消费确认
        }else{
            channel.basicNack(tag, false, true);
        }
    }
}
