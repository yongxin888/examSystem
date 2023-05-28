package com.examsystem.controller.admin;

import com.examsystem.common.RestResponse;
import com.examsystem.entity.ExamPaper;
import com.examsystem.service.ExamPaperService;
import com.examsystem.utility.ModelMapperSingle;
import com.examsystem.utility.PageInfoHelperUtil;
import com.examsystem.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.examsystem.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.examsystem.viewmodel.admin.exam.ExamResponseVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 卷题管理
 * @DATE: 2023/5/12 15:04
 */
@RestController
@RequestMapping("/api/admin/exam/paper")
public class ExamPaperController {

    @Autowired
    private ExamPaperService examPaperService;

    /**
     * 分页查询试卷列表
     * @param examPaperPageRequest 请求数据
     * @return
     */
    @PostMapping("/page")
    public RestResponse<PageInfo<ExamPaper>> pageList(@RequestBody ExamPaperPageRequestVM examPaperPageRequest) {
        PageInfo<ExamPaper> examPaperPage = examPaperService.page(examPaperPageRequest);
        return RestResponse.ok(examPaperPage);
    }

    /**
     * 添加试卷
     * @param examPaperEditRequest 请求数据
     * @return
     */
    @PostMapping("/edit")
    public RestResponse<ExamPaper> edit(@RequestBody @Valid ExamPaperEditRequestVM examPaperEditRequest, HttpServletRequest request) {
        ExamPaper examPaper = examPaperService.saveExamPaper(examPaperEditRequest, request);
        return RestResponse.ok(examPaper);
    }

    /**
     * 根据ID查询试卷
     * @param id
     * @return
     */
    @PostMapping("/select/{id}")
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM examPaperEditRequestVM = examPaperService.examPaperToId(id);
        return RestResponse.ok(examPaperEditRequestVM);
    }

    /**
     * 删除试卷
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Integer id) {
        ExamPaper examPaper = examPaperService.selectById(id);
        examPaper.setDeleted(true);
        examPaperService.updateExamPaperById(examPaper);
        return RestResponse.ok();
    }

    /**
     * 任务管理试卷分页查询
     * @param examPaperPageRequest 请求数据
     * @return
     */
    @PostMapping("/taskExamPage")
    public RestResponse<PageInfo<ExamResponseVM>> taskExamPageList(@RequestBody ExamPaperPageRequestVM examPaperPageRequest){
        //分页查询试卷
        PageInfo<ExamPaper> pageInfo = examPaperService.taskExamPage(examPaperPageRequest);
        //PageInfo转换
        PageInfo<ExamResponseVM> examResponseVM = PageInfoHelperUtil.copyMap(pageInfo, e -> {
            //数据拷贝
            ExamResponseVM examResponse = ModelMapperSingle.Instance().map(e, ExamResponseVM.class);
            return examResponse;
        });
        return RestResponse.ok(examResponseVM);
    }
}
