package com.examsystem.service;

import com.examsystem.entity.ExamPaper;
import com.examsystem.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.examsystem.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 试卷相关
 */
public interface ExamPaperService {
    //查询试卷总数
    int selectAllCount();

    //分页查询试卷列表
    PageInfo<ExamPaper> page(ExamPaperPageRequestVM requestVM);

    //添加试卷
    ExamPaper saveExamPaper(ExamPaperEditRequestVM requestVM, HttpServletRequest request);

    //根据ID查询试卷并查询题目信息
    ExamPaperEditRequestVM examPaperToId(Integer id);

    //根据ID查询试卷表
    ExamPaper selectById(Integer id);

    //根据ID更新试卷表
    void updateExamPaperById(ExamPaper examPaper);

    //任务管理分页查询试卷
    PageInfo<ExamPaper> taskExamPage(ExamPaperPageRequestVM requestVM);

    //更新试卷任务ID
    int updateTaskPaper(@Param("taskId") Integer taskId, @Param("paperIds") List<Integer> paperIds);
}
