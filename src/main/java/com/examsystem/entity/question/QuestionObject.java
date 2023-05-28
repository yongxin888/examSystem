package com.examsystem.entity.question;

import lombok.Data;

import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 文本内容题目列表对象
 * @DATE: 2023/5/12 19:49
 */
@Data
public class QuestionObject {

    //题干
    private String titleContent;

    //解析
    private String analyze;

    //题目选项
    private List<QuestionItemObject> questionItemObjects;

    //正确选项
    private String correct;
}
