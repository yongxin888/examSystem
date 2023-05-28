package com.examsystem.service.impl;

import com.examsystem.entity.ExamPaperAnswer;
import com.examsystem.mapper.ExamPaperAnswerMapper;
import com.examsystem.service.ExamPaperAnswerService;
import com.examsystem.viewmodel.admin.answer.ExamPaperAnswerPageRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 试卷答案相关
 * @DATE: 2023/5/8 19:20
 */
@Service
public class ExamPaperAnswerServiceImpl implements ExamPaperAnswerService {

    @Autowired
    private ExamPaperAnswerMapper examPaperAnswerMapper;

    /**
     * 查询答卷总数
     * @return
     */
    @Override
    public int selectAllCount() {
        return examPaperAnswerMapper.selectAllCount();
    }

    /**
     * 分页查询答卷列表数据
     * @param examPaperAnswerPageRequest 请求数据
     * @return
     */
    @Override
    public PageInfo<ExamPaperAnswer> page(ExamPaperAnswerPageRequestVM examPaperAnswerPageRequest) {
        //开启分页
        return PageHelper.startPage(examPaperAnswerPageRequest.getPageIndex(), examPaperAnswerPageRequest.getPageSize(),
                "id desc").doSelectPageInfo(() -> examPaperAnswerMapper.page(examPaperAnswerPageRequest));
    }
}
