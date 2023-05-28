package com.examsystem.service;

import com.examsystem.entity.ExamPaperAnswer;
import com.examsystem.viewmodel.admin.answer.ExamPaperAnswerPageRequestVM;
import com.github.pagehelper.PageInfo;

/**
 *  试卷答案相关
 */
public interface ExamPaperAnswerService {
    //查询答卷总数
    int selectAllCount();

    //分页查询列表
    PageInfo<ExamPaperAnswer> page(ExamPaperAnswerPageRequestVM examPaperAnswerPageRequest);
}
