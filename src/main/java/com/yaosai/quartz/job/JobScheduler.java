package com.yaosai.quartz.job;

import com.yaosai.quartz.constants.JobConstants;
import com.yaosai.quartz.service.impl.DefaultJobServiceImpl;
import com.yaosai.quartz.entity.MyJob;
import com.yaosai.quartz.entity.TaskDef;
import com.yaosai.quartz.service.TaskService;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 任务调度器
 *
 * @author YaoS
 * @date 19/6/7 18:29
 */
public class JobScheduler {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);
    @Autowired
    private TaskService taskService;
    @Autowired
    private JobManager jobManager;


    /**
     * 初始化任务
     *
     * @author YaoS
     * @date 19/6/7 18:29
     */
    public void initJob() {
        // 获取所有任务数据
        List<TaskDef> datas = taskService.findAll();
        if (!CollectionUtils.isEmpty(datas)) {
            for (TaskDef taskDef : datas) {
                if (null != taskDef) {
                    // 设置任务
                    this.setJobDetail(taskDef);
                }
            }
            // 启动所有定时任务
            try {
                jobManager.startJobs();
            } catch (SchedulerException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 设置任务
     *
     * @param taskDef 任务实体
     * @author YaoS
     * @date 19/6/7 18:30
     */
    public void setJobDetail(TaskDef taskDef) {
        if (null != taskDef && CronExpression.isValidExpression(taskDef.getCronExp())) {
            try {
                MyJob myJob = this.populateEntity(taskDef);
                // 更新
                if (JobConstants.STATUS_UPDATE == taskDef.getTaskStatus()) {
                    // 更新表达式
                    jobManager.updateJob(myJob);
                }
                // 开启
                else if (JobConstants.STATUS_RUN == taskDef.getTaskStatus()) {
                    // 添加任务
                    jobManager.addJob(myJob);
                }
                // 停用
                else if (JobConstants.STATUS_STOP == taskDef.getTaskStatus()) {
                    // 暂停任务
                    jobManager.pauseJob(myJob);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 删除任务
     *
     * @param taskDef 任务实体
     * @return String
     * @author YaoS
     * @date 19/6/7 18:30
     */
    public String deleteJobDetail(TaskDef taskDef) {
        String msg = "";
        try {
            MyJob myJob = this.populateEntity(taskDef);
            // 删除
            jobManager.deleteJob(myJob);
        } catch (Exception e) {
            msg = e.getMessage();
            logger.error(msg);
        }
        return msg;
    }

    /**
     * 组装任务实体
     *
     * @param taskDef 业务定义的task
     * @return MyJob
     * @author YaoS
     * @date 19/6/7 16:43
     */
    private MyJob populateEntity(TaskDef taskDef) {
        MyJob myJob = new MyJob();
        myJob.setJobId(String.valueOf(taskDef.getId()));
        myJob.setJobName(taskDef.getTaskName());
        myJob.setDescription(taskDef.getTaskDesc());
        myJob.setCronExpression(taskDef.getCronExp());
        myJob.setJobClassName(DefaultJobServiceImpl.class.getName());
        myJob.setJobMethodName(JobConstants.DEFAULT_JOB_METHOD);
        myJob.setRealJobClassName(taskDef.getJobClassName());
        myJob.setRealJobMethodName(taskDef.getJobMethodName());
        return myJob;
    }
}