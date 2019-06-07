package com.yaosai.quartz.factory;

import com.yaosai.quartz.common.SpringContextHolder;
import com.yaosai.quartz.entity.MyJob;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * 任务执行抽象类
 *
 * @author Leegern
 * @date 2018年5月7日
 */
abstract class AbstractJobFactory {
    /**
     * 执行方法
     *
     * @param job
     * @author YaoS
     * @date 19/6/7 16:19
     */
    void invoke(MyJob job) {
        if (StringUtils.isNotBlank(job.getJobClassName())) {
            try {
                Class<?> clazz = Class.forName(job.getJobClassName());
                // 从spring容器获取bean,否则无法注入
                Object obj = SpringContextHolder.getBean(clazz);
                // 反射方法
                Method method = null;
                if (null != obj) {
                    method = obj.getClass().getDeclaredMethod(job.getJobMethodName(), MyJob.class);
                } else {
                    obj = clazz.newInstance();
                    if (null != obj) {
                        method = obj.getClass().getDeclaredMethod(job.getJobMethodName(), MyJob.class);
                    }
                }
                if (null == method) {
                    System.out.println("DefaultJobService's doJob method not found, please check the config!");
                }
                method.invoke(obj, job);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}