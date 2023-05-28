package com.examsystem.viewmodel.admin.task;

import com.examsystem.viewmodel.admin.exam.ExamResponseVM;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 添加任务请求数据
 * @DATE: 2023/5/18 18:47
 */
@Data
public class TaskRequestVM {

    //主键
    private Integer id;

    //年级
    @NotNull
    private Integer gradeLevel;

    //标题
    @NotNull
    private String title;

    //试卷
    @Size(min = 1, message = "请添加试卷")
    @Valid
    private List<ExamResponseVM> paperItems;
}
