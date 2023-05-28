package com.examsystem.viewmodel.admin.task;

import com.examsystem.common.BasePage;
import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 任务列表请求数据
 * @DATE: 2023/5/15 20:19
 */
@Data
public class TaskPageRequestVM extends BasePage {
    //年级
    private Integer gradeLevel;
}
