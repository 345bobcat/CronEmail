package com.dark.service.email.strategy.impl;


import com.dark.model.EmailVO;
import com.dark.service.email.strategy.MailStrategy;

//@Component
public class TextStrategy implements MailStrategy {

    public String message(EmailVO vo) {
        return vo.getEmailContent();
    }
}
