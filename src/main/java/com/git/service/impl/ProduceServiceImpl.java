package com.git.service.impl;

import com.git.common.ResponseCode;
import com.git.common.ServletResponse;
import com.git.config.RabbitConfig;
import com.git.entity.Mail;
import com.git.entity.MsgLog;
import com.git.mapper.MsgLogMapper;
import com.git.service.ProduceService;
import com.git.util.MessageHelper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Mr ● Li
 * @version 1.0
 * @date 2021/4/26 11:18
 */
@Service
public class ProduceServiceImpl implements ProduceService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MsgLogMapper msgLogMapper;

    @Override
    public ServletResponse send(Mail mail) {
        //创建UUID
        String msgId = UUID.randomUUID().toString().replaceAll("-","");
        mail.setMsgId(msgId);
        //添加日志,保存到数据库
        MsgLog msgLog = new MsgLog(msgId,mail, RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME);
        msgLogMapper.insert(msgLog);

        //发送消息到rabbitMQ
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME, MessageHelper.objToMsg(mail), correlationData);
        return ServletResponse.success(ResponseCode.SUCCESS.getMsg());
    }
}
