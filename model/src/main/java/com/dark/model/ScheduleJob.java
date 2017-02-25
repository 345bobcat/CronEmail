package com.dark.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "SCHEDULE_JOB")
public class ScheduleJob implements Serializable {
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    @Id
    @Column(name = "schedule_job_id")
    @GeneratedValue(generator = "JDBC")
    private Long scheduleJobId;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_group")
    private String jobGroup;

    private String status;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "is_sync")
    private Boolean isSync;

    @Column(name = "ec_id")
    private Integer ecId;

    private String description;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_modify")
    private Date gmtModify;

    private static final long serialVersionUID = 7281587404238158164L;

    public ScheduleJob(Long scheduleJobId, String jobName, String jobGroup, String status, String cronExpression, Boolean isSync, Integer ecId, String description, Date gmtCreate, Date gmtModify) {
        this.scheduleJobId = scheduleJobId;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.status = status;
        this.cronExpression = cronExpression;
        this.isSync = isSync;
        this.ecId = ecId;
        this.description = description;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
    }

    public ScheduleJob() {
        super();
    }

    /**
     * @return schedule_job_id
     */
    public Long getScheduleJobId() {
        return scheduleJobId;
    }

    /**
     * @param scheduleJobId
     */
    public void setScheduleJobId(Long scheduleJobId) {
        this.scheduleJobId = scheduleJobId;
    }

    /**
     * @return job_name
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName
     */
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    /**
     * @return job_group
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * @param jobGroup
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return cron_expression
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * @param cronExpression
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression == null ? null : cronExpression.trim();
    }

    /**
     * @return is_sync
     */
    public Boolean getIsSync() {
        return isSync;
    }

    /**
     * @param isSync
     */
    public void setIsSync(Boolean isSync) {
        this.isSync = isSync;
    }

    /**
     * @return ec_id
     */
    public Integer getEcId() {
        return ecId;
    }

    /**
     * @param ecId
     */
    public void setEcId(Integer ecId) {
        this.ecId = ecId;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return gmt_create
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return gmt_modify
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * @param gmtModify
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", scheduleJobId=").append(scheduleJobId);
        sb.append(", jobName=").append(jobName);
        sb.append(", jobGroup=").append(jobGroup);
        sb.append(", status=").append(status);
        sb.append(", cronExpression=").append(cronExpression);
        sb.append(", isSync=").append(isSync);
        sb.append(", ecId=").append(ecId);
        sb.append(", description=").append(description);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModify=").append(gmtModify);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}