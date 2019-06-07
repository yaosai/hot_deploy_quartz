package com.yaosai.quartz.job;

import com.alibaba.fastjson.JSON;
import com.yaosai.quartz.constants.JobConstants;
import com.yaosai.quartz.entity.MyJob;
import com.yaosai.quartz.factory.DefaultJobFactory;
import com.yaosai.quartz.factory.DisallowConcurrentJobFactory;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 任务调度管理器, 实现任务的动态操作
 *
 * @author YaoS
 * @date 19/6/7 16:22
 */
public class JobManager {

    /**
     * 调度管理器注入工厂bean
     **/
    private SchedulerFactory schedulerFactory;

    public SchedulerFactory getSchedulerFactory() {
        return schedulerFactory;
    }

    public void setSchedulerFactory(SchedulerFactory schedulerFactory) {
        this.schedulerFactory = schedulerFactory;
    }


    /**
     * 添加任务
     *
     * @param myJob myJob
     * @author YaoS
     * @date 19/6/7 18:22
     */
    public void addJob(MyJob myJob) throws SchedulerException, ClassNotFoundException {
        if (myJob == null || StringUtils.isBlank(myJob.getJobId())) {
            return;
        }
        if (StringUtils.isBlank(myJob.getCronExpression()) && null == myJob.getSimpleExpression()) {
            return;
        }
        if (StringUtils.isBlank(myJob.getJobName())) {
            myJob.setJobName(JobConstants.JOB_DEFAULT_NAME);
        }
        if (null == myJob.getSimpleExpression()) {
            addCronJob(myJob);
        } else {
            addSimpleJob(myJob);
        }
    }

    /**
     * 更新任务
     *
     * @param myJob MyJob
     * @author YaoS
     * @date 19/6/7 18:21
     */
    public void updateJob(MyJob myJob) throws SchedulerException, ClassNotFoundException {
        if (myJob == null || StringUtils.isBlank(myJob.getJobId())) {
            return;
        }
        if (StringUtils.isBlank(myJob.getCronExpression()) && null == myJob.getSimpleExpression()) {
            return;
        }
        if (StringUtils.isBlank(myJob.getJobName())) {
            myJob.setJobName(JobConstants.JOB_DEFAULT_NAME);
        }
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobKey jobKey = JobKey.jobKey(myJob.getJobId(), myJob.getJobGroup());
        // 判断是否存在任务
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
        if (null == myJob.getSimpleExpression()) {
            addCronJob(myJob);
        } else {
            addSimpleJob(myJob);
        }
    }

    /**
     * 更新job时间表达式
     *
     * @param myJob myJob
     * @author YaoS
     * @date 19/6/7 18:22
     */
    public void updateJobCron(MyJob myJob) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(
                myJob.getJobId(), myJob.getJobGroup());
        CronTrigger trigger = (CronTrigger) schedulerFactory.getScheduler().getTrigger(triggerKey);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(myJob.getCronExpression())).build();
        schedulerFactory.getScheduler().rescheduleJob(triggerKey, trigger);
    }

    /**
     * 更新job时间表达式
     *
     * @param myJob myJob
     * @author YaoS
     * @date 19/6/7 18:23
     */
    public void updateJobSimple(MyJob myJob) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(
                myJob.getJobId(), myJob.getJobGroup());
        SimpleTrigger trigger = (SimpleTrigger) schedulerFactory.getScheduler().getTrigger(triggerKey);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .startAt(myJob.getSimpleExpression()).build();
        schedulerFactory.getScheduler().rescheduleJob(triggerKey, trigger);
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return List<MyJob>
     * @author YaoS
     * @date 19/6/7 18:35
     */
    public List<MyJob> getAllJob() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = schedulerFactory.getScheduler().getJobKeys(matcher);
        List<MyJob> myJobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = schedulerFactory.getScheduler().getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                MyJob myJob = new MyJob();
                myJob.setJobId(jobKey.getName());
                myJob.setJobGroup(jobKey.getGroup());
                Trigger.TriggerState triggerState = schedulerFactory.getScheduler().getTriggerState(trigger.getKey());
                myJob.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    myJob.setCronExpression(cronExpression);
                }
                myJobList.add(myJob);
            }
        }
        return myJobList;
    }

    /**
     * 获取所有正在运行的job
     *
     * @author YaoS
     * @date 19/6/7 18:33
     */
    public List<MyJob> getRunningJob() throws SchedulerException {
        List<JobExecutionContext> executingJobs = schedulerFactory.getScheduler().getCurrentlyExecutingJobs();
        List<MyJob> myJobList = new ArrayList<>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            MyJob myJob = new MyJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            myJob.setJobName(jobKey.getName());
            myJob.setJobGroup(jobKey.getGroup());
            Trigger.TriggerState triggerState = schedulerFactory.getScheduler().getTriggerState(trigger.getKey());
            myJob.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                myJob.setCronExpression(cronExpression);
            }
            myJobList.add(myJob);
        }
        return myJobList;
    }

    /**
     * 暂停job
     *
     * @param myJob MyJob
     * @author YaoS
     * @date 19/6/7 18:34
     */
    public void pauseJob(MyJob myJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(myJob.getJobId(), myJob.getJobGroup());
        schedulerFactory.getScheduler().pauseJob(jobKey);
    }

    /**
     * 恢复job
     *
     * @param myJob MyJob
     * @author YaoS
     * @date 19/6/7 18:34
     */
    public void resumeJob(MyJob myJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(myJob.getJobId(), myJob.getJobGroup());
        schedulerFactory.getScheduler().resumeJob(jobKey);
    }

    /**
     * 删除job
     *
     * @param scheduleMyJob
     * @throws SchedulerException
     */
    public void deleteJob(MyJob scheduleMyJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleMyJob.getJobId(), scheduleMyJob.getJobGroup());
        schedulerFactory.getScheduler().deleteJob(jobKey);

    }

    /**
     * 立即执行job
     *
     * @param myJob
     * @throws SchedulerException
     */
    public void triggerJob(MyJob myJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(myJob.getJobId(), myJob.getJobGroup());
        schedulerFactory.getScheduler().triggerJob(jobKey);
    }

    /**
     * 启动所有定时任务
     *
     * @author YaoS
     * @date 19/6/7 18:35
     */
    public void startJobs() throws SchedulerException {
        if (!schedulerFactory.getScheduler().isShutdown()) {
            schedulerFactory.getScheduler().start();
        }
    }

    /**
     * 关闭所有定时任务
     *
     * @author YaoS
     * @date 19/6/7 18:36
     */
    public void shutdownJobs() throws SchedulerException {
        if (!schedulerFactory.getScheduler().isShutdown()) {
            schedulerFactory.getScheduler().shutdown();
        }
    }

    /**
     * 调度任务是否已开启
     *
     * @author YaoS
     * @date 19/6/7 18:36
     */
    public boolean isStarted() throws SchedulerException {
        return schedulerFactory.getScheduler().isStarted();
    }

    /**
     * 添加 cron表达式任务
     *
     * @param myJob MyJob
     * @author YaoS
     * @date 19/6/7 18:36
     */
    private void addCronJob(MyJob myJob) throws SchedulerException, ClassNotFoundException {
        //根据任务id和任务组Id创建触发器key
        TriggerKey triggerKey = TriggerKey.triggerKey(myJob.getJobId(), myJob.getJobGroup());
        //获取触发器对象
        CronTrigger trigger = (CronTrigger) schedulerFactory.getScheduler().getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            JobDetail jobDetail = JobBuilder.newJob(myJob.isConcurrent() ? DefaultJobFactory.class : DisallowConcurrentJobFactory.class)
                    .withIdentity(myJob.getJobId(), myJob.getJobGroup()).build();
            jobDetail.getJobDataMap().put(JobConstants.SCHEDULER_NAME, JSON.toJSONString(myJob));
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(
                            myJob.getCronExpression())).build();
            schedulerFactory.getScheduler().scheduleJob(jobDetail, trigger);
            // 启动  
            if (!schedulerFactory.getScheduler().isShutdown()) {
                schedulerFactory.getScheduler().start();
            }
        } else {
            updateJobCron(myJob);
        }
    }

    /**
     * 添加简单时间 表达式任务
     *
     * @param myJob MyJob
     * @author YaoS
     * @date 19/6/7 18:36
     */
    private void addSimpleJob(MyJob myJob) throws SchedulerException {
        //根据任务id和任务组Id创建触发器key
        TriggerKey triggerKey = TriggerKey.triggerKey(myJob.getJobId(), myJob.getJobGroup());
        //获取触发器对象
        SimpleTrigger trigger = (SimpleTrigger) schedulerFactory.getScheduler().getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            JobDetail jobDetail = JobBuilder.newJob(myJob.isConcurrent() ? DefaultJobFactory.class : DisallowConcurrentJobFactory.class)
                    .withIdentity(myJob.getJobId(), myJob.getJobGroup()).build();
            jobDetail.getJobDataMap().put(JobConstants.SCHEDULER_NAME, JSON.toJSONString(myJob));
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                    .startAt(myJob.getSimpleExpression()).build();
            schedulerFactory.getScheduler().scheduleJob(jobDetail, trigger);
            // 启动  
            if (!schedulerFactory.getScheduler().isShutdown()) {
                schedulerFactory.getScheduler().start();
            }
        } else {
            updateJobSimple(myJob);
        }
    }
}