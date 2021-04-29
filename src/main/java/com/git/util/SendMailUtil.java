package com.git.util;

import com.git.entity.Mail;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

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

    /**
     * 发送附件邮件
     */
    public boolean sendAttachment(Mail mail){
        String to = mail.getTo();
        String title = mail.getTitle();
        String content = mail.getContent();
        File file = mail.getFile();
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content);
            FileSystemResource resource = new FileSystemResource(file);
            String fileName = file.getName();
            helper.addAttachment(fileName, resource);
            mailSender.send(message);
            log.info("附件邮件发送成功");
            return true;
        }catch(Exception e){
            log.info("附件邮件发送失败", to, title, e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 发送模板邮件
     */
    public boolean sendTemplateMail(Mail mail){
        String to = mail.getTo();
        String title = mail.getTitle();
        String info = mail.getInfo();
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            //封装模板使用的数据
            Map<String,Object> model = new HashMap<>();
            model.put("username","小红");
            //得到模板
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(info);
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
            helper.setText(content, true);
            mailSender.send(message);
            log.info("附件邮件发送成功");
            return true;
        }catch(Exception e){
            log.info("附件邮件发送失败", to, title, e);
            e.printStackTrace();
            return false;
        }
    }
}
