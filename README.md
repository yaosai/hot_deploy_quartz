# SpringBoot整合quartz热部署

## 说明：
该项目基于SpringBoot实现Quartz热部署，目前通过将任务的类和方法信息记录在mysql中，利用反射调用执行定时任务
在`com.yaosai.quartz.controller.TaskController`类提供了restAPI,使其支持实时修改

## 如何实现跨服务调用
如果要实现跨服务调用，只需要对`com.yaosai.quartz.service.impl.DefaultJobServiceImpl`类下的`doJob`方法进行简单的改造
同时使Myjob和TaskDef增加restURL相关参数使其将任务地址暴露为restAPI。即可实现跨服务调用

## 如何部署
修改application.properties文件中的数据库配置，同时在数据库中执行项目根路径下的task_def.sql脚本,然后启动项目即可
