package com.examsystem.viewmodel.admin.question;

import com.examsystem.common.BasePage;
import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 题目请求数据
 * @DATE: 2023/5/12 18:26
 */
@Data
public class QuestionPageRequestVM extends BasePage {
    //主键
    private Integer id;

    //年级
    private Integer level;

    //学科ID
    private Integer subjectId;

    //题目类型
    private Integer questionType;

    //题目信息
    private String content;
}
