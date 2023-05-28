package com.examsystem.entity.question;

import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 题目选项内容
 * @DATE: 2023/5/12 19:53
 */
@Data
public class QuestionItemObject {

    //选项
    private String prefix;

    //内容
    private String content;

    //选项分数
    private Integer score;

    //选项标识
    private String itemUuid;
}
