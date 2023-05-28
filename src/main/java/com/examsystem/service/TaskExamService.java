package com.examsystem.service;

import com.examsystem.entity.TaskExam;
import com.examsystem.viewmodel.admin.task.TaskPageRequestVM;
import com.examsystem.viewmodel.admin.task.TaskRequestVM;
import com.github.pagehelper.PageInfo;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 任务相关
 * @DATE: 2023/5/16 20:11
 */
public interface TaskExamService {
    //分页查询任务列表
    PageInfo<TaskExam> page(TaskPageRequestVM requestVM);

    //添加任务
    void edit(TaskRequestVM taskRequest);

    //编辑任务
    TaskRequestVM taskExamToVM(Integer id);

    //根据ID查询任务
    TaskExam selectTaskExamById(Integer id);

    //根据ID更新任务
    void updateTaskExamById(TaskExam taskExam);
}
