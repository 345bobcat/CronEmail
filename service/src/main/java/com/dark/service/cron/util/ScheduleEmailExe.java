package com.dark.service.cron.util;

import com.alibaba.fastjson.JSON;
import com.dark.engine.data.ReportDataSource;
import com.dark.engine.query.MySqlQueryer;
import com.dark.model.CronMetaDatasource;
import com.dark.model.EmailConfig;
import com.dark.model.EmailVO;
import com.dark.model.TemplateConfig;
import com.dark.service.cron.exception.ScheduleException;
import com.dark.service.dbSource.CronSourceService;
import com.dark.service.email.MailSenderTemplate;
import com.dark.service.email.strategy.impl.VelocityStrategy;
import com.dark.service.template.EmailConfigService;
import com.dark.service.template.TemplateConfigService;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jinwenzhang on 17/2/8.
 */
@Service("scheduleEmailExe")
public class ScheduleEmailExe {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleEmailExe.class);

    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired
    private EmailConfigService emailConfigService;
    @Autowired
    private TemplateConfigService templateConfigService;
    @Autowired
    private MailSenderTemplate mailSenderTemplate;
    @Autowired
    private CronSourceService cronSourceService;

    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    public void generateTemplateEmail(Integer ecId, String uuid){
        try {
            pool.execute(new TemplateEmailRunnable(ecId, uuid));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class TemplateEmailRunnable implements Runnable {
        private Integer ecId;

        private String uuid;

        public TemplateEmailRunnable(Integer ecId, String uuid) {
            this.ecId = ecId;
            this.uuid = uuid;
        }

        @Override
        public void run() {
            try {
                EmailConfig ec = emailConfigService.queryById(ecId);
                if(ec==null){
                    logger.error("没有查询到uuid:{}对应的邮件模板信息", new Object[]{uuid});
                    return;
                }
                TemplateConfig tc = templateConfigService.queryById(ec.getTcId());
                if(tc==null){
                    logger.error("没有查询到ec:{}对应的模板信息", new Object[]{ec.getEcId()});
                    return;
                }

                EmailVO vo = configToVO(ec);
                vo.setEmailContent(tc.getContent());

                VelocityContext ctx = new VelocityContext();
                if(tc.getSqlSourceId()!=null){
                    setVelocityContext(ctx, tc);
                }
//                mailSenderTemplate.setStrategy(new VelocityStrategy(ctx, "/velocity/velocityTemplate.vm").setVelocityEngine(velocityEngine)).sendMail(vo);
                mailSenderTemplate.setStrategy(new VelocityStrategy(ctx).setVelocityEngine(velocityEngine)).sendMail(vo);
            } catch (Exception e) {
                logger.error("===============e:",e);
                throw new ScheduleException(e.getMessage());
            }
        }

        private EmailVO configToVO(EmailConfig ec){
            EmailVO vo = new EmailVO();
            vo.setSubject(ec.getSubject());
            vo.setSender(ec.getSender());
            vo.setReceivers(ec.getReceivers().split(";"));
            return vo;
        }

        //往velocityContext中设置模板变量
        private void setVelocityContext(VelocityContext ctx, TemplateConfig tc) throws Exception{
            CronMetaDatasource rs = cronSourceService.queryById(tc.getSqlSourceId());
            ReportDataSource rds = new ReportDataSource();
            BeanUtils.copyProperties(rs, rds, new String[] { "options" });
            rds.setDbPoolClass(rs.getPoolClass());
            rds.setOptions(JSON.parseObject(rs.getOptions()));

            String[] sentences = tc.getSqlSentence().split(";");
            int i=1;
            for(String sentence: sentences){
                MySqlQueryer msq = new MySqlQueryer(rds, sentence);
                List<List<Object>> rowDatas = msq.getQueryData();
                ctx.put("table"+i,rowDatas);
                i++;
            }
        }
    }

    @PreDestroy
    public void destroy(){
        logger.info("=======pool shutting down=====");
        pool.shutdown();
    }
}


