package com.git.util;

import com.git.entity.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Mr ● Li
 * @version 1.0
 * @date 2021/4/26 11:08
 */
@Component
@Slf4j
public class SendMailUtil {

    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单的邮件
     */
    public boolean send(Mail mail){
        String to = mail.getTo();//目标邮箱
        String title = mail.getTitle();//邮件标题
        String content = mail.getContent();//邮件正文

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);

        try {
            mailSender.send(message);
            log.info("邮件发送成功");
            return true;
        }catch (MailException e){
            log.error("邮件发送失败！！！ 目标邮箱： " + to +" , 邮件标题： " + title);
            e.printStackTrace();
            return false;
        }
    }
}
