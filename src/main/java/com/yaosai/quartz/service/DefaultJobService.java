package com.yaosai.quartz.service;

import com.yaosai.quartz.entity.MyJob;

/**
 * 任务明细接口
 * @author YaoS
 * @date 19/6/7 16:36
 */
public interface DefaultJobService {

	/**
	 * 任务执行
	 * @param job 任务实体
	 */
	void doJob(MyJob job);
}
