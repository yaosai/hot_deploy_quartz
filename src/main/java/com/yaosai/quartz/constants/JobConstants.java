package com.yaosai.quartz.constants;

/**
 * 功能描述: 任务静态属性
 *
 * @author YaoS
 * @date 19/6/7 15:39
 */
public final class JobConstants {
    /**
     * 调度名称
     **/
    public static final String SCHEDULER_NAME = "scheduler";
    /**
     * 任务状态：开启
     **/
    public static final int STATUS_RUN = 1;
    /**
     * 任务状态：停用
     **/
    public static final int STATUS_STOP = 2;
    /**
     * 任务状态：更新
     **/
    public static final int STATUS_UPDATE = 3;
    /**
     * 默认执行方法名
     **/
    public static final String DEFAULT_JOB_METHOD = "doJob";

    /**
     * 默认的任务名
     */
    public static final String JOB_DEFAULT_NAME = "job_default_name";

    /**
     * 默认的任务组ID
     */
    public static final String JOB_DEFAULT_GROUP_ID = "job_default_group_id";

    /**
     * 默认的任务组名
     */
    public static final String JOB_DEFAULT_GROUP_NAME = "job_default_group_name";


}