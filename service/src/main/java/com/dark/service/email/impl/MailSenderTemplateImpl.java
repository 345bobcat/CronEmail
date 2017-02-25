package com.dark.service.email.impl;

import com.dark.model.EmailVO;
import com.dark.service.email.MailSenderTemplate;
import com.dark.service.email.strategy.MailStrategy;
import com.dark.service.email.strategy.impl.TextStrategy;
import com.dark.service.email.type.EmailType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjinwen on 2016/12/3.
 */
@Service("mailSenderTemplate")
public class MailSenderTemplateImpl implements MailSenderTemplate {

    private Logger logger = LoggerFactory.getLogger(MailSenderTemplateImpl.class);

    protected static final EmailType DEFAULT_EMAIL_TYPE = EmailType.TEXT;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private MailStrategy strategy;

    public MailSenderTemplateImpl setStrategy(MailStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public void sendMail(EmailVO vo) throws MessagingException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // 第二个参数表示这个是mulipart类型的
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "GBK");
            boolean isHtmlText = true;

            if (this.strategy instanceof TextStrategy) {
                isHtmlText = false;
            }

            this.emailMessage(helper, vo);

            // true表示发送的是html消息
            helper.setText(this.sendContent(vo), isHtmlText);

            Map<String, String> path = new HashMap<>();
/*            path.put("timg","/Users/jinwenzhang/JavaSrc/cronEmail/timg.jpeg");
            vo.setImgPaths(path);*/
            // 显示是内置图片等
            if (vo.getImgPaths()!=null && vo.getImgPaths().size() > 0) {
                for (String key : vo.getImgPaths().keySet()) {
                    File file =  new File(vo.getImgPaths().get(key));
                    FileSystemResource res = new FileSystemResource(file);//从本地文件夹中获取所需图片
                    helper.addInline(key, res);
                }
            }

            // 附件
            if (vo.getAttachFile() != null && vo.getAttachFile().length > 0) {
                for (File file : vo.getAttachFile()) {
                    FileSystemResource resource = new FileSystemResource(file);
                    helper.addAttachment(file.getName(), resource);
                }
            }

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("============e:"+e);
        }

    }

    public void sendMailSync(final EmailVO vo) throws MessagingException {
        final MailSenderTemplate mailSenderTemplate = new MailSenderTemplateImpl();
        threadPoolTaskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    mailSenderTemplate.sendMail(vo);
                } catch (MessagingException e) {
                    logger.error("[failed because of ]" + e);
                    System.out.println("Failed .....");
                }
            }
        });
    }

    private void emailMessage(MimeMessageHelper helper, EmailVO vo) throws MessagingException {
        if (vo.getCc()!=null && vo.getCc().length > 0) {
            helper.setCc(vo.getCc());
        }
        if (vo.getBcc()!=null && vo.getBcc().length > 0) {
            helper.setBcc(vo.getBcc());
        }

        helper.setFrom(vo.getSender());
        helper.setTo(vo.getReceivers());
        helper.setSubject(vo.getSubject());
        helper.setSentDate(new Date());
    }

    private String sendContent(EmailVO vo) {
        String content = null;
        try {
            content = this.strategy.message(vo).toString();
        } catch (Exception e) {
            logger.error("邮件内容格式化失败，because of :"+e);
        }
        return content;
    }
}
