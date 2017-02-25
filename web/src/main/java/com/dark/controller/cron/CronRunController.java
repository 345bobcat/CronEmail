package com.dark.controller.cron;

import com.dark.model.ScheduleJob;
import com.dark.model.base.DtParam;
import com.dark.model.base.DtResponse;
import com.dark.service.base.JsonResult;
import com.dark.service.cron.ScheduleJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by jinwenzhang on 17/2/10.
 */
@Controller
@RequestMapping("cronRun")
public class CronRunController {
    private Logger logger = LoggerFactory.getLogger(CronRunController.class);

    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * cronRun页面
     * @param view
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model view) {
        List<ScheduleJob> executingJobList = scheduleJobService.queryExecutingJobList();
        view.addAttribute("executingJobList", executingJobList);
        return "/cron/crList";
    }
}
