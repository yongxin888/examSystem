package com.examsystem.viewmodel.admin.question;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 题目选项数据
 * @DATE: 2023/5/12 21:05
 */
@Data
public class QuestionEditItemVM {
    //选项
    @NotBlank
    private String prefix;

    //内容
    @NotBlank
    private String content;

    //选项分数
    private String score;

    //选项标识
    private String itemUuid;
}
