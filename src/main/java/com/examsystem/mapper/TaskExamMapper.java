package com.examsystem.mapper;

import com.examsystem.entity.TaskExam;
import com.examsystem.viewmodel.admin.task.TaskPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 任务相关
 */
@Mapper
public interface TaskExamMapper {
    //根据条件查询任务列表
    List<TaskExam> page(TaskPageRequestVM requestVM);

    //添加任务
    int insertTaskExam(TaskExam taskExam);

    //根据ID查询任务
    TaskExam selectTaskExamById(Integer id);

    //根据ID更新任务
    int updateTaskExamById(TaskExam taskExam);
}
