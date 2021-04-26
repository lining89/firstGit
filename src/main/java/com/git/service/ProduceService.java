package com.git.service;

import com.git.entity.Mail;

/**
 * @author Mr ● Li
 * @version 1.0
 * @date 2021/4/26 11:20
 */
public interface ProduceService {
    public boolean send(Mail mail);
}
