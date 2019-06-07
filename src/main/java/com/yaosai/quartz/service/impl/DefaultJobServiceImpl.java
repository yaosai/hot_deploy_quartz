package com.yaosai.quartz.service.impl;

import com.yaosai.quartz.entity.MyJob;
import com.yaosai.quartz.service.DefaultJobService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * 具体任务处理，所有的任务都会走到这里，根据Myjob的参数进行处理
 *
 * @author YaoS
 * @date 19/6/7 16:51
 */
@Service
public class DefaultJobServiceImpl implements DefaultJobService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultJobServiceImpl.class);

    @Override
    public void doJob(MyJob job) {
        if (null != job && StringUtils.isNotBlank(job.getJobClassName())) {
            try {
                Class<?> c = Class.forName(job.getRealJobClassName());
                Method m = c.getMethod(job.getRealJobMethodName());
                m.invoke(c.newInstance());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

}