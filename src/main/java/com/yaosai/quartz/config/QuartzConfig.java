package com.yaosai.quartz.config;

import com.yaosai.quartz.job.JobManager;
import com.yaosai.quartz.job.JobScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QuartzConfig
 *
 * @author YaoS
 * @date 19/6/7 17:09
 */
@Configuration
public class QuartzConfig {

    /**
     * 定义quartz调度工厂
     *
     * @return StdSchedulerFactory
     * @author YaoS
     * @date 18:03 18/12/25
     **/
    @Bean(name = "schedulerFactory")
    public StdSchedulerFactory schedulerFactory() {
        return new StdSchedulerFactory();
    }

    /**
     * 定义定时任务管理类jobManager
     *
     * @param stdSchedulerFactory StdSchedulerFactory
     * @return JobManager
     * @author YaoS
     * @date 18:02 18/12/25
     **/
    @Bean(name = "jobManager")
    public JobManager jobManagerBean(StdSchedulerFactory stdSchedulerFactory) {
        JobManager bean = new JobManager();
        bean.setSchedulerFactory(stdSchedulerFactory);
        return bean;
    }

    /**
     * 定义jobScheduler初始化
     *
     * @return JobScheduler
     * @author YaoS
     * @date 18:03 18/12/25
     **/
    @Bean(name = "jobScheduler", initMethod = "initJob")
    public JobScheduler createJobScheduler() {
        JobScheduler bean = new JobScheduler();
        return bean;
    }
}
