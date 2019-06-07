package com.yaosai.quartz.service.impl;

import com.yaosai.quartz.entity.TaskDef;
import com.yaosai.quartz.job.JobScheduler;
import com.yaosai.quartz.repository.TaskRepository;
import com.yaosai.quartz.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YaoS
 * @date 19/6/7 16:45
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JobScheduler jobScheduler;

    @Override
    public List<TaskDef> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public TaskDef findOneTask(Long id) {
        return taskRepository.getOne(id);
    }

    @Override
    public Long save(TaskDef taskDef) {
        TaskDef saved = taskRepository.save(taskDef);
        jobScheduler.setJobDetail(saved);
        return saved.getId();
    }

    @Override
    public Long update(TaskDef taskDef) {
        Long l = taskRepository.save(taskDef).getId();
        if (l != null && l > 0) {
            jobScheduler.setJobDetail(taskDef);
        }
        return l;
    }

    @Override
    public Long updateTaskStatus(Long id, int taskStatus) {

        TaskDef taskDef = this.findOneTask(id);
        taskDef.setTaskStatus(taskStatus);
        TaskDef updated = taskRepository.save(taskDef);
        if (null != updated) {
            // 发送任务信息
            jobScheduler.setJobDetail(updated);
        }
        return updated.getId();
    }

    @Override
    public boolean delete(Long deleteId) {
        //定时任务内存中删除定时任务
        String msg = jobScheduler.deleteJobDetail(taskRepository.getOne(deleteId));
        if (StringUtils.isBlank(msg)) {
            taskRepository.deleteById(deleteId);
            return true;
        } else {
            return false;
        }
    }
}
