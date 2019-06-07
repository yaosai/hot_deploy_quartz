package com.yaosai.quartz.service;

import com.yaosai.quartz.entity.TaskDef;

import java.util.List;

/**
 * @author YaoS
 * @date 2019-06-07 16:12
 */
public interface TaskService {
    /**
     * 获取所有任务列表
     *
     * @return List<TaskDef> 所有任务列表
     * @author YaoS
     * @date 18:12 18/12/25
     **/
    List<TaskDef> findAll();

    /**
     * 新增定时任务
     *
     * @param taskDef 定时任务实体
     * @return Long 定时任务实体id
     * @author YaoS
     * @date 11:32 18/12/28
     **/
    Long save(TaskDef taskDef);

    /**
     * 更新定时任务
     *
     * @param taskDef 定时任务实体
     * @return Long 定时任务实体id
     * @author YaoS
     * @date 11:33 18/12/28
     **/
    Long update(TaskDef taskDef);

    /**
     * 根据id查询定时任务
     *
     * @param id 定时任务id
     * @return taskDef 定时任务实体
     * @author YaoS
     * @date 11:33 18/12/28
     **/
    TaskDef findOneTask(Long id);

    /**
     * 更改定时任务状态
     *
     * @param id         定时任务id
     * @param taskStatus 定时任务状态
     * @return Long 定时任务id
     * @author YaoS
     * @date 11:33 18/12/28
     **/
    Long updateTaskStatus(Long id, int taskStatus);

    /**
     * 删除定时任务
     *
     * @param deleteId 定时任务id
     * @return boolean
     * @author YaoS
     * @date 11:49 18/12/28
     **/
    boolean delete(Long deleteId);
}
