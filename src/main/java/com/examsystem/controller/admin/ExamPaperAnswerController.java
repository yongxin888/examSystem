package com.examsystem.controller.admin;

import com.examsystem.common.RestResponse;
import com.examsystem.entity.ExamPaperAnswer;
import com.examsystem.entity.Subject;
import com.examsystem.entity.User;
import com.examsystem.service.ExamPaperAnswerService;
import com.examsystem.service.SubjectService;
import com.examsystem.service.UserService;
import com.examsystem.utility.ExamUtil;
import com.examsystem.utility.ModelMapperSingle;
import com.examsystem.utility.PageInfoHelperUtil;
import com.examsystem.viewmodel.admin.answer.ExamPaperAnswerPageRequestVM;
import com.examsystem.viewmodel.admin.answer.ExamPaperAnswerPageResponseVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 成绩管理
 * @DATE: 2023/5/22 22:34
 */
@RestController
@RequestMapping(value = "/api/admin/examPaperAnswer")
public class ExamPaperAnswerController {

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    /**
     * 分页查询答卷列表
     * @param examPaperAnswerPageRequest 请求数据
     * @return
     */
    @PostMapping("/page")
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> pageAnswer(@RequestBody ExamPaperAnswerPageRequestVM examPaperAnswerPageRequest) {
        //分页查询答卷列表
        PageInfo<ExamPaperAnswer> pageInfo = examPaperAnswerService.page(examPaperAnswerPageRequest);

        //pageInfo替换
        PageInfo<ExamPaperAnswerPageResponseVM> page = PageInfoHelperUtil.copyMap(pageInfo, examPaperAnswer -> {
            //数据拷贝
            ExamPaperAnswerPageResponseVM vm = ModelMapperSingle.Instance().map(examPaperAnswer, ExamPaperAnswerPageResponseVM.class);
            //根据ID查询学科
            Subject subject = subjectService.selectById(vm.getSubjectId());
            //根据总秒数计算答题时间
            vm.setDoTime(ExamUtil.secondToVM(examPaperAnswer.getDoTime()));
            //系统得分转换成百分制
            vm.setSystemScore(ExamUtil.scoreToVM(examPaperAnswer.getSystemScore()));
            //最终得分转换成百分制
            vm.setUserScore(ExamUtil.scoreToVM(examPaperAnswer.getUserScore()));
            //试卷总分转换成百分制
            vm.setPaperScore(ExamUtil.scoreToVM(examPaperAnswer.getPaperScore()));
            //设置学科名
            vm.setSubjectName(subject.getName());

            //查询用户
            User user = userService.getUserById(examPaperAnswer.getCreateUser());
            //设置用户名
            vm.setUserName(user.getUserName());
            return vm;
        });
        return RestResponse.ok(page);
    }
}
