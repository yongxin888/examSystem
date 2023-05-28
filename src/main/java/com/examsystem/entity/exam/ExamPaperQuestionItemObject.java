package com.examsystem.entity.exam;

import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 题目列表对象
 * @DATE: 2023/5/13 16:50
 */
@Data
public class ExamPaperQuestionItemObject {
    //ID
    private Integer id;

    //题序
    private Integer itemOrder;
}
