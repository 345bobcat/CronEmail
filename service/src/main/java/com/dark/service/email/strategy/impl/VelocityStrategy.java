package com.dark.service.email.strategy.impl;

import com.dark.model.EmailVO;
import com.dark.service.email.strategy.MailStrategy;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * 优化：让使用者更加方便，传入VelocityEngine
 */
public class VelocityStrategy implements MailStrategy {
    private static final Logger logger = LoggerFactory.getLogger(VelocityStrategy.class);

    private VelocityEngine velocityEngine;
    private VelocityContext velocityContext;
    private String templateName;

    public VelocityStrategy setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
        return this;
    }

    // 不指定模板名称
    public VelocityStrategy(VelocityContext velocityContext) {
        this.velocityContext = velocityContext;
    }

    // 两种注入方式
    public VelocityStrategy(VelocityContext velocityContext, String templateName) {
        this.velocityContext = velocityContext;
        this.templateName = templateName;
    }



    public VelocityStrategy setVelocityContext(VelocityContext velocityContext) {
        this.velocityContext = velocityContext;
        return this;
    }

    public String message(EmailVO vo) throws Exception {
        StringWriter sw = new StringWriter();

        try {
            if(StringUtils.isEmpty(templateName)){
                String rawContent = vo.getEmailContent();
                StringReader sr = new StringReader(rawContent);

                Velocity.evaluate(velocityContext, sw, "velocityContent", sr);
                sr.close();
            }else {
                Template template = this.velocityEngine.getTemplate(this.templateName);
                template.merge(velocityContext, sw);
            }
            sw.close();
        } catch (Exception e) {
            logger.error("==========e:"+e);
        }
        return sw.toString();
    }
}
