package com.examsystem.viewmodel.admin.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 题目响应数据
 * @DATE: 2023/5/12 17:28
 */
@Data
public class QuestionResponseVM {
    //主键
    private Integer id;

    //题型
    private Integer questionType;

    //文本ID
    private Integer textContentId;

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    //学科
    private Integer subjectId;

    //创建人
    private Integer createUser;

    //得分
    private String score;

    //状态
    private Integer status;

    //正确答案
    private String correct;

    //文本解析ID
    private Integer analyzeTextContentId;

    //难度
    private Integer difficult;

    //题干
    private String shortTitle;
}
