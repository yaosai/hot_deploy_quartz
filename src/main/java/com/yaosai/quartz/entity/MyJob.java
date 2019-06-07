package com.yaosai.quartz.entity;

import com.yaosai.quartz.constants.JobConstants;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务实体
 *
 * @author YaoS
 * @date 19/6/7 15:50
 */
public final class MyJob implements Serializable {

    private static final long serialVersionUID = -1815426304568478999L;

    /**
     * 任务ID
     */
    private String jobId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务组id
     */
    private String jobGroupId = JobConstants.JOB_DEFAULT_GROUP_ID;
    /**
     * 任务分组
     */
    private String jobGroup = JobConstants.JOB_DEFAULT_GROUP_NAME;
    /**
     * 任务状态
     */
    private String jobStatus;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 简单时间表达式
     */
    private Date simpleExpression;
    /**
     * 任务执行时调用的包名+类名,此处为默认值DefaultJobDetail
     */
    private String jobClassName;
    /**
     * 任务调用的方法名，此处为默认值doJob
     */
    private String jobMethodName;
    /**
     * 任务执行时调用的包名+类名,据业务需求自定义
     */
    private String realJobClassName;
    /**
     * 任务调用的方法名,根据业务需求自定义
     */
    private String realJobMethodName;
    /**
     * 任务是否有状态(即并发执行同一个任务)
     */
    private boolean isConcurrent = true;
    /**
     * 描述
     */
    private String description;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(String jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Date getSimpleExpression() {
        return simpleExpression;
    }

    public void setSimpleExpression(Date simpleExpression) {
        this.simpleExpression = simpleExpression;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobMethodName() {
        return jobMethodName;
    }

    public void setJobMethodName(String jobMethodName) {
        this.jobMethodName = jobMethodName;
    }

    public String getRealJobClassName() {
        return realJobClassName;
    }

    public void setRealJobClassName(String realJobClassName) {
        this.realJobClassName = realJobClassName;
    }

    public String getRealJobMethodName() {
        return realJobMethodName;
    }

    public void setRealJobMethodName(String realJobMethodName) {
        this.realJobMethodName = realJobMethodName;
    }

    public boolean isConcurrent() {
        return isConcurrent;
    }

    public void setConcurrent(boolean concurrent) {
        isConcurrent = concurrent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}