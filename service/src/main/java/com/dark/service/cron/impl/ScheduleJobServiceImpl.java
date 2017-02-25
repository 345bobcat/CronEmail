package com.dark.service.cron.impl;

import com.dark.model.ScheduleJob;
import com.dark.service.base.impl.BaseService;
import com.dark.service.cron.ScheduleJobService;
import com.dark.service.cron.util.ScheduleUtils;
import com.github.pagehelper.PageInfo;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * author : zhangjinwen
 * description : 定时任务服务实现
 */
@Service
public class ScheduleJobServiceImpl extends BaseService<ScheduleJob> implements ScheduleJobService {

    /** 调度工厂Bean */
    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void initScheduleJob() {
        List<ScheduleJob> scheduleJobList = this.queryAll();
        if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }
        for (ScheduleJob scheduleJob : scheduleJobList) {

            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());

            //不存在，创建一个
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Transactional
    public Integer insertJob(ScheduleJob scheduleJob) {
        Integer count = this.save(scheduleJob);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        return count;
    }

    public void updateJob(ScheduleJob scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        this.updateSelective(scheduleJob);
    }

    public void delUpdate(ScheduleJob scheduleJob) {
        //先删除
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //再创建
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        //数据库直接更新即可
        this.updateSelective(scheduleJob);
    }

    public void delete(Long scheduleJobId) {
        ScheduleJob scheduleJob = this.get(scheduleJobId);
        //删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //删除数据
        this.deleteById(scheduleJobId);
    }

    public void runOnce(Long scheduleJobId) {
        ScheduleJob scheduleJob = this.get(scheduleJobId);
        ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    public void pauseJob(Long scheduleJobId) {
        ScheduleJob scheduleJob = this.get(scheduleJobId);
        ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //演示数据库就不更新了
    }

    public void resumeJob(Long scheduleJobId) {
        ScheduleJob scheduleJob = this.queryById(scheduleJobId);
        ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //演示数据库就不更新了
    }

    public ScheduleJob get(Long scheduleJobId) {
        ScheduleJob scheduleJob = this.queryById(scheduleJobId);
        return scheduleJob;
    }

    public PageInfo<ScheduleJob> queryList(int page, int rows, ScheduleJob scheduleJob) {

        PageInfo<ScheduleJob> scheduleJobs = this.queryPageListByWhere(page, rows, new ScheduleJob());

        try {
            for (ScheduleJob vo : scheduleJobs.getList()) {

                JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                if (CollectionUtils.isEmpty(triggers)) {
                    continue;
                }

                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
                Trigger trigger = triggers.iterator().next();

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                vo.setStatus(triggerState.name());

                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    vo.setCronExpression(cronExpression);
                }
            }
        } catch (SchedulerException e) {
            //演示用，就不处理了
        }
        return scheduleJobs;
    }

    /**
     * 获取运行中的job列表
     * @return
     */
    public List<ScheduleJob> queryExecutingJobList() {
        try {
            // 存放结果集
            List<ScheduleJob> jobList = new ArrayList<>();

            // 获取scheduler中的JobGroupName
            for (String group:scheduler.getJobGroupNames()){
                // 获取JobKey 循环遍历JobKey
                for(JobKey jobKey:scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group))){
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    ScheduleJob scheduleJob = (ScheduleJob)jobDataMap.get(ScheduleJob.JOB_PARAM_KEY);
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    Trigger trigger = triggers.iterator().next();
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    scheduleJob.setStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        scheduleJob.setCronExpression(cronExpression);
                    }
                    // 获取正常运行的任务列表
                    if(triggerState.name().equals("NORMAL")){
                        jobList.add(scheduleJob);
                    }
                }
            }

            return jobList;
        } catch (SchedulerException e) {
            return null;
        }

    }
}
