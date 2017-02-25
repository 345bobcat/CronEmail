package com.dark.service;

import com.dark.model.EmailVO;
import com.dark.service.email.MailSenderTemplate;
import com.dark.service.email.strategy.impl.VelocityStrategy;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * Created by jinwenzhang on 17/1/3.
 */
public class EmailSenderTest extends BaseJunit{
    @Resource
    private MailSenderTemplate mailSenderTemplate;

    @Autowired
    private VelocityEngine velocityEngine;

    protected EmailVO vo;

    @Before
    public void setUp() {
        vo = new EmailVO();
        vo.setCc(new String[]{});
        vo.setBcc(new String[]{});
        vo.setSubject("[主题][致亲爱的一封邮件]");
        vo.setEmailContent("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"content\">\n" +
                "    <h3>亲爱的,$name</h3>\n" +
                "    <ul>\n" +
                "        <li>I love You !!</li>\n" +
                "    </ul>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
        vo.setReceivers(new String[]{"854338217@qq.com"});
        vo.setSender("3173579082@qq.com");
    }

    /**
     * 如果邮件测试报错，替换jdk1.8版本的jar包
     * jar包见 http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
     * @throws Exception
     */
    @Test
    public void testSendVelocity() throws Exception {
        VelocityContext ctx = new VelocityContext();
        ctx.put("name", "Jack");
//        mailSenderTemplate.setStrategy(new VelocityStrategy(ctx, "/velocity/velocityTemplate.vm").setVelocityEngine(velocityEngine)).sendMail(vo);
        mailSenderTemplate.setStrategy(new VelocityStrategy(ctx).setVelocityEngine(velocityEngine)).sendMail(vo);
    }
}
