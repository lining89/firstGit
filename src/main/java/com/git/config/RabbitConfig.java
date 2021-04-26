package com.git.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr ● Li
 * @version 1.0
 * @date 2021/4/25 16:15
 */
@Configuration
@Slf4j
public class RabbitConfig {

    //发送邮件
    public static final String MAIL_QUEUE_NAME = "mail.queue";
    public static final String MAIL_EXCHANGE_NAME = "mail.exchange";
    public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        //消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if(ack){
                log.info("消息成功发送到Exchange");
            }else{
                log.info("消息发送到Exchange失败, {}, cause: {}", correlationData, cause);
            }
        });
        //触发setReturnCallback回调必须设置mandatory=true,否则Exchange没有找到QUEUE就会丢弃掉消息，而不会触发回调
        rabbitTemplate.setMandatory(true);
        //消息是否从Exchange路由到QUEUE，注意：这是一个失败回调，只有消息从Exchange路由到QUEUE失败才会回调这个方法
        rabbitTemplate.setReturnsCallback((message) -> {
            log.info("消息从Exchange路由到Queue失败: " + message);
        });

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){

        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue mailQueue(){
        return new Queue(MAIL_QUEUE_NAME);
    }

    @Bean
    public DirectExchange mailExchange(){
        return new DirectExchange(MAIL_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding mailBinding(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MAIL_ROUTING_KEY_NAME);
    }
    /*@Bean
    public RabbitTemplate createRabbitmqTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String str) {
                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+b);
                System.out.println("ConfirmCallback:     "+"原因："+str);
            }
        });
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println("ReturnCallback:     "+"消息："+returnedMessage);
            }
        });
        return rabbitTemplate;
    }*/
}
