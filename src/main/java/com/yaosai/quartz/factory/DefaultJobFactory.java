package com.yaosai.quartz.factory;

import com.alibaba.fastjson.JSON;
import com.yaosai.quartz.constants.JobConstants;
import com.yaosai.quartz.entity.MyJob;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 默认的任务执行类
 *
 * @author YaoS
 * @date 19/6/7 15:58
 */
public class DefaultJobFactory extends AbstractJobFactory implements org.quartz.Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String scheduleJob = (String) context.getMergedJobDataMap().get(JobConstants.SCHEDULER_NAME);
        if (StringUtils.isNotBlank(scheduleJob)) {
            MyJob job = JSON.parseObject(scheduleJob, MyJob.class);
            invoke(job);
        }
    }
}