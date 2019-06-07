package com.yaosai.quartz.repository;

import com.yaosai.quartz.entity.TaskDef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 定时任务管理Repository
 *
 * @author YaoS
 * @date 17:58 18/12/25
 **/
public interface TaskRepository extends JpaRepository<TaskDef, Long>,
        JpaSpecificationExecutor<TaskDef> {
}
