package com.examsystem.controller.admin;

import com.examsystem.common.RestResponse;
import com.examsystem.service.*;
import com.examsystem.viewmodel.admin.dashboard.IndexVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 主页
 * @DATE: 2023/5/8 16:50
 */
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserEventLogService userEventLogService;

    @PostMapping("/index")
    public RestResponse<IndexVM> index() {
        IndexVM indexVM = new IndexVM();

        //试卷总数
        Integer examPaperCount = examPaperService.selectAllCount();
        indexVM.setExamPaperCount(examPaperCount);

        //题目总数
        Integer questionCount = questionService.selectAllCount();
        indexVM.setQuestionCount(questionCount);

        //答卷总数
        Integer doExamPaperCount = examPaperAnswerService.selectAllCount();
        indexVM.setDoExamPaperCount(doExamPaperCount);

        //答题总数
        Integer doQuestionCount = examPaperQuestionCustomerAnswerService.selectAllCount();
        indexVM.setDoQuestionCount(doQuestionCount);

        //用户活跃度
        List<Integer> mothDayUserActionValue = userEventLogService.selectMothCount();
        indexVM.setMothDayUserActionValue(mothDayUserActionValue);

        //月答题数
        List<Integer> mothDayDoExamQuestionValue = examPaperQuestionCustomerAnswerService.selectMothCount();
        indexVM.setMothDayDoExamQuestionValue(mothDayDoExamQuestionValue);

        return RestResponse.ok(indexVM);
    }

}
