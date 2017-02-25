package com.dark.controller.cron;

import com.dark.model.ScheduleJob;
import com.dark.model.base.DtParam;
import com.dark.model.base.DtResponse;
import com.dark.service.base.JsonResult;
import com.dark.service.cron.ScheduleJobService;
import com.dark.service.template.EmailConfigService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("cronShow")
public class CronShowController {
    private Logger logger = LoggerFactory.getLogger(CronShowController.class);

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private EmailConfigService emailConfigService;

    private static String CRON_RUN = "1";
    private static String CRON_STOP = "0";

    /**
     * 数据源页面
     *
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String list() {
        return "cron/csList";
    }

    /**
     * 数据源信息
     *
     * @return
     */
    @RequestMapping(value = "sourceData", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sourceData(DtParam dtParam) {
        PageInfo<ScheduleJob> results = null;
        try {
            int rows = dtParam.getLength();
            int page = dtParam.getStart()/rows + 1;//页数
            results = scheduleJobService.queryList(page, rows, new ScheduleJob());
        } catch (Exception e) {
            logger.error("======error======e:",e);
        }
        return new JsonResult(DtResponse.of(results, dtParam));
    }

    /**
     * 新增或者编辑
     *
     * @return
     */
    @RequestMapping(value = "openInfo",method = RequestMethod.POST)
    public String openInfo(Long id, Model view) {
        try {
            if(id!=null){
                ScheduleJob rs = scheduleJobService.queryById(id);
                view.addAttribute("scheduleJob", rs);
            }
            //后面会变更，只能查看个人用户
            view.addAttribute("ecList", emailConfigService.queryAll());
        } catch (Exception e) {
            logger.error("======error======e:", e);
        }
        return "cron/csEdit";
    }

    /**
     * 根据id删除信息
     *
     * @return
     */
    @RequestMapping(value = "delById", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delById(Long id) {
        try {
            scheduleJobService.delete(id);
        } catch (Exception e) {
            logger.error("======error======e:", e);
            return new JsonResult(false,"");
        }
        return new JsonResult();
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping(value = "saveEdit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveEdit(ScheduleJob sj, String keyword) {
        try {
            if (sj.getScheduleJobId() == null) {
                scheduleJobService.insertJob(sj);
            } else if (StringUtils.equalsIgnoreCase(keyword,"delUpdate")){
                //直接拿keywords存一下，就不另外重新弄了
                scheduleJobService.delUpdate(sj);
            }else {
                scheduleJobService.updateJob(sj);
            }
            if(sj.getStatus().equals(CRON_STOP)){
                scheduleJobService.pauseJob(sj.getScheduleJobId());
            }
        } catch (Exception e) {
            logger.error("======error======e:", e);
            return new JsonResult(false, "");
        }
        return new JsonResult();
    }

    /**
     * 运行一次
     *
     * @return
     */
    @RequestMapping(value = "runOnce", method = RequestMethod.GET)
    public String runOnce(Long scheduleJobId) {
        scheduleJobService.runOnce(scheduleJobId);
        return "redirect:list";
    }

    /**
     * 暂停
     *
     * @return
     */
    @RequestMapping(value = "pause", method = RequestMethod.GET)
    public String pauseScheduleJob(Long scheduleJobId) {
        scheduleJobService.pauseJob(scheduleJobId);
        return "redirect:list";
    }

    /**
     * 恢复
     *
     * @return
     */
    @RequestMapping(value = "resume", method = RequestMethod.GET)
    public String resumeScheduleJob(Long scheduleJobId) {
        scheduleJobService.resumeJob(scheduleJobId);
        return "redirect:list";
    }
}
