package com.examsystem.mapper;

import com.examsystem.entity.ExamPaper;
import com.examsystem.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 试卷相关
 * @DATE: 2023/5/8 19:17
 */
@Mapper
public interface ExamPaperMapper {
    //查询试卷总数
    int selectAllCount();

    //根据条件查询试卷列表
    List<ExamPaper> page(ExamPaperPageRequestVM requestVM);

    //添加试卷
    int insertExamPaper(ExamPaper examPaper);

    //根据ID查询试卷
    ExamPaper selectExamPaperById(Integer id);

    //根据ID更新试卷表
    int updateExamPaperById(ExamPaper examPaper);

    //任务管理根据条件查询试卷
    List<ExamPaper> taskExamPage(ExamPaperPageRequestVM requestVM);

    //更新试卷任务ID
    int updateTaskPaper(@Param("taskId") Integer taskId, @Param("paperIds") List<Integer> paperIds);

    //清空试卷的任务ID
    int clearTaskPaper(@Param("paperIds") List<Integer> paperIds);
}
