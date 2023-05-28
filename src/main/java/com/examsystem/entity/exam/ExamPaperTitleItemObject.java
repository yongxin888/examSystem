package com.examsystem.entity.exam;

import lombok.Data;

import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 试卷试题题干对象
 * @DATE: 2023/5/13 16:49
 */
@Data
public class ExamPaperTitleItemObject {
    //标题
    private String name;

    //题目数据
    private List<ExamPaperQuestionItemObject> questionItems;
}
