package com.git.common;

import com.git.entity.Mail;
import com.git.exception.ServiceException;
import com.git.util.BaseConsumer;
import com.git.util.MessageHelper;
import com.git.util.SendMailUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Mr ● Li
 * @version 1.0
 * @date 2021/4/27 11:08
 */
@Component
@Slf4j
public class MailConsumer implements BaseConsumer {

    @Autowired
    private SendMailUtil sendMailUtil;
    @Override
    public void consume(Message message, Channel channel) throws IOException {
        Mail mail = MessageHelper.msgToObj(message, Mail.class);
        log.info("收到消息： {}", mail.toString());

        boolean success = sendMailUtil.send(mail);
        if(!success){
            throw new ServiceException("send mail error!!");
        }
    }
}
