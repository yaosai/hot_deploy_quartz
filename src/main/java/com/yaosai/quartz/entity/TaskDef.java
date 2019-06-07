package com.yaosai.quartz.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

/**
 * 任务详情实体
 *
 * @author YaoS
 * @date 19/6/7 16:31
 */
@Entity
@Table
public class TaskDef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * 任务名称
     **/
    private String taskName;
    /**
     * 任务描述
     **/
    private String taskDesc;
    /**
     * 任务调用地址
     **/
    private String jobClassName;
    /**
     * 任务调用的方法名
     */
    private String jobMethodName;
    /**
     * CRON表达式
     **/
    private String cronExp;
    /**
     * 任务状态，1：开启，2：停用
     **/
    private int taskStatus;
    /**
     * 删除标识，1：否，2：是
     **/
    private int delFlag;
    /**
     * 更新标识，1：否，2：是
     **/
    private int updFlag;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
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

    public String getCronExp() {
        return cronExp;
    }

    public void setCronExp(String cronExp) {
        this.cronExp = cronExp;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getUpdFlag() {
        return updFlag;
    }

    public void setUpdFlag(int updFlag) {
        this.updFlag = updFlag;
    }
}