package com.dark.service.email;



import com.dark.model.EmailVO;
import com.dark.service.email.strategy.MailStrategy;
import com.dark.service.email.impl.MailSenderTemplateImpl;

import javax.mail.MessagingException;

/**
 * Created by zhangjinwen on 2016/12/3.
 */
public interface MailSenderTemplate {

    /**
     * 发送邮件
     *
     * @param vo
     */
    void sendMail(EmailVO vo) throws MessagingException;

    /**
     * 异步发送邮件
     * @param vo
     * @throws MessagingException
     */
    void sendMailSync(EmailVO vo) throws MessagingException;

    /**
     * 设置邮件发送策略
     *
     * @param strategy
     * @return
     */
    public MailSenderTemplateImpl setStrategy(MailStrategy strategy);
}
