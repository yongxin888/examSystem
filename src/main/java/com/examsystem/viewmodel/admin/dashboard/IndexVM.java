package com.examsystem.viewmodel.admin.dashboard;

import lombok.Data;

import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 主页视图模型
 * @DATE: 2023/5/8 17:26
 */
@Data
public class IndexVM {
    //试卷总数
    private Integer examPaperCount;
    //题目总数
    private Integer questionCount;
    //答卷总数
    private Integer doExamPaperCount;
    //答题总数
    private Integer doQuestionCount;
    //用户活跃度
    private List<Integer> mothDayUserActionValue;
    //题目月数量
    private List<Integer> mothDayDoExamQuestionValue;
    //封存日期
    private List<String> mothDayText;
}
