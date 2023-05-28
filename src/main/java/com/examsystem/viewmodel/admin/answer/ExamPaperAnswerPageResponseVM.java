package com.examsystem.viewmodel.admin.answer;

import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 答卷列表响应数据
 * @DATE: 2023/5/22 22:42
 */
@Data
public class ExamPaperAnswerPageResponseVM {
    //主键ID
    private Integer id;

    //提交时间
    private String createTime;

    //用户得分
    private String userScore;

    //学科名称
    private String subjectName;

    //学科Id
    private Integer subjectId;

    //题目数量
    private Integer questionCount;

    //正确题目数
    private Integer questionCorrect;

    //试卷总分
    private String paperScore;

    //耗时
    private String doTime;

    //试卷类型
    private Integer paperType;

    //自动批改得分
    private String systemScore;

    //答卷状态
    private Integer status;

    //试卷名称
    private String paperName;

    //用户名
    private String userName;
}
