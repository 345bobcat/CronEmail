package com.dark.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jinwenzhang on 17/1/21.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping(method = RequestMethod.GET)
    public String resumeScheduleJob(Long scheduleJobId) {
        return "redirect:cronRun/list";
    }
}
