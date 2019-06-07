package com.yaosai.quartz.controller;

import com.yaosai.quartz.entity.TaskDef;
import com.yaosai.quartz.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务控制器
 *
 * @author YaoS
 * @date 19/6/7 16:09
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * /task/save 添加定时任务
     *
     * @param {"taskName":"测试定时任务2","taskDesc":"测试定时任务2","taskUrl":"http://127.0.0.1:8989/datagovern-api/task/mytask2","cronExp":"0/5 * * * * ?","taskStatus":1}
     * @return { "code": 0, "msg": "成功", "data": 2 }
     * @author YaoS
     * @date 12:22 19/1/5
     **/
    @PostMapping(value = "/save")
    public Object addTaskInfo(@RequestBody TaskDef taskDef) {
        if (StringUtils.isEmpty(taskDef)) {
            return "Entity is Null";
        }
        return taskService.save(taskDef);
    }

    /**
     * /task/findOneTask 根据任务Id获取单条任务详情信息
     *
     * @param id 定时任务id
     * @return {"code":0,"msg":"成功","data":{"id":1,"taskName":"测试定时任务","taskDesc":"测试定时任务","taskUrl":"http://127.0.0.1:8989/datagovern-api/task/mytask","cronExp":"0/5 * * * * ?","taskStatus":1}}
     * @author YaoS
     * @date 12:29 19/1/5
     **/
    @PostMapping(value = "/findOneTask")
    public TaskDef findOneTask(long id) {
        return taskService.findOneTask(id);
    }

    /**
     * /task/update 更新任务信息
     *
     * @param taskDef TaskDef
     * @return {"code":0,"msg":"成功","data":1}
     * @author YaoS
     * @date 12:30 19/1/5
     **/
    @PostMapping(value = "/update")
    public Object updateTaskDef(@RequestBody TaskDef taskDef) {
        if (StringUtils.isEmpty(taskDef)) {
            return "Entity is Null";
        }
        return taskService.update(taskDef);
    }

    /**
     * /task/updateTaskStatus 是否启用定时任务状态
     *
     * @param id         定时任务id
     * @param taskStatus 是否启用定时任务(1:启用、2:停用)
     * @return {"code":0,"msg":"成功","data":6}
     * @author YaoS
     * @date 13:01 19/1/5
     **/
    @PostMapping(value = "/updateTaskStatus")
    public Object updateTaskStatus(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "taskStatus") int taskStatus) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(taskStatus)) {
            return "Entity is Null";
        }
        return taskService.updateTaskStatus(id, taskStatus);

    }

    /**
     * /task/delete 删除任务
     *
     * @param id 定时任务id
     * @return
     * @author YaoS
     * @date 13:09 19/1/5
     **/
    @PostMapping(value = "/delete")
    public Object delete(Long id) {
        if (StringUtils.isEmpty(id)) {
            return "Entity is Null";
        }
        boolean b = taskService.delete(id);
        return b ? "删除成功" : "删除失败！";
    }

    /**
     * /task/findAll 获取所有定时任务
     *
     * @param
     * @return
     * @author YaoS
     * @date 11:41 19/1/5
     **/
    @PostMapping("/findAll")
    public Object findAll() {
        return taskService.findAll();
    }

}
