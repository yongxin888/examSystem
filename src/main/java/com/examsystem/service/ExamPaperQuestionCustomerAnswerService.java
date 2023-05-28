package com.examsystem.service;

import java.util.List;

/**
 * 试卷题目答案表
 */
public interface ExamPaperQuestionCustomerAnswerService {
    //查询答题总数
    int selectAllCount();

    //计算每月答题总数
    List<Integer> selectMothCount();
}
