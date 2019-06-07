package com.yaosai.quartz;

import org.springframework.stereotype.Component;

/**
 * @author YaoS
 * @date 2019-06-07 17:12
 */
@Component
public class HelloWorld {
    public void run() {
        System.out.println("定时任务执行成功");
    }
}
