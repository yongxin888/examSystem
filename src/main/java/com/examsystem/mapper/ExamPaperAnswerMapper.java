package com.examsystem.mapper;

import com.examsystem.entity.ExamPaperAnswer;
import com.examsystem.viewmodel.admin.answer.ExamPaperAnswerPageRequestVM;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 试卷答案相关
 */
@Mapper
public interface ExamPaperAnswerMapper {
    //查询答卷总数
    int selectAllCount();

    //分页查询列表
    PageInfo<ExamPaperAnswer> page(ExamPaperAnswerPageRequestVM examPaperAnswerPageRequest);
}
