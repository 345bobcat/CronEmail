package com.dark.service.email.strategy;


import com.dark.model.EmailVO;

public interface MailStrategy {

    String message(EmailVO vo) throws Exception;
}
