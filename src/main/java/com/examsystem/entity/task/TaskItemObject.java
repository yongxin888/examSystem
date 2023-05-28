package com.examsystem.entity.task;

import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 任务对象
 * @DATE: 2023/5/18 19:36
 */
@Data
public class TaskItemObject {

    //试卷ID
    private Integer examPaperId;

    //试卷名称
    private String examPaperName;

    //题序
    private Integer itemOrder;
}
