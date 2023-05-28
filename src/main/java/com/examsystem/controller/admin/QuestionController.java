package com.examsystem.controller.admin;


import com.examsystem.common.RestResponse;
import com.examsystem.common.SystemCode;
import com.examsystem.entity.Question;
import com.examsystem.entity.TextContent;
import com.examsystem.entity.enums.QuestionTypeEnum;
import com.examsystem.entity.question.QuestionObject;
import com.examsystem.service.QuestionService;
import com.examsystem.service.TextContentService;
import com.examsystem.utility.*;
import com.examsystem.viewmodel.admin.question.QuestionEditRequestVM;
import com.examsystem.viewmodel.admin.question.QuestionPageRequestVM;
import com.examsystem.viewmodel.admin.question.QuestionResponseVM;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 题目管理
 * @DATE: 2023/5/12 16:16
 */
@RestController
@RequestMapping("/api/admin/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TextContentService textContentService;

    /**
     * 分页查询题目信息
     * @param questionPageRequest 请求数据
     * @return
     */
    @PostMapping("/page")
    public RestResponse<PageInfo<QuestionResponseVM>> pageList(@RequestBody QuestionPageRequestVM questionPageRequest) {
        PageInfo<Question> pageInfo = questionService.page(questionPageRequest);
        PageInfo<QuestionResponseVM> page = PageInfoHelperUtil.copyMap(pageInfo, q -> {
            //对象映射拷贝
            QuestionResponseVM responseVM = ModelMapperSingle.Instance().map(q, QuestionResponseVM.class);
            responseVM.setScore(ExamUtil.scoreToVM(q.getScore()));

            //根据ID查询文本
            TextContent textContent = textContentService.selectById(q.getInfoTextContentId());

            //将文本转换为对象
            QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);
            String clearHtml = HtmlUtil.clear(questionObject.getTitleContent());
            responseVM.setShortTitle(clearHtml);

            return responseVM;
        });
        return RestResponse.ok(page);
    }

    /**
     * 查询题目信息数据
     * @param id
     * @return
     */
    @PostMapping("/select/{id}")
    public RestResponse<QuestionEditRequestVM> select(@PathVariable Integer id) {
        QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(id);
        return RestResponse.ok(questionEditRequestVM);
    }

    /**
     * 更新/添加题目
     * @param questionEditRequest 请求数据
     * @return
     */
    @PostMapping("/edit")
    public RestResponse edit(@RequestBody @Valid QuestionEditRequestVM questionEditRequest) {
        //表单数据校验
        RestResponse validQuestionEditRequestResult = validQuestionEditRequestVM(questionEditRequest);

        //判断校验是否成功
        if (validQuestionEditRequestResult.getCode() != SystemCode.OK.getCode()) {
            //如果不成功则返回校验错误信息
            return validQuestionEditRequestResult;
        }

        //判断请求数据是否有ID，如果有，则是更新，否则添加
        if (questionEditRequest.getId() == null) {
            //添加题目数据
            questionService.insertQuestion(questionEditRequest);
        }else {
            //更新题目数据
            questionService.updateQuestion(questionEditRequest);
        }
        return RestResponse.ok();
    }

    /**
     * 前端表单校验
     * @param questionEditRequest 请求数据
     * @return
     */
    private RestResponse validQuestionEditRequestVM(QuestionEditRequestVM questionEditRequest) {
        //获取题目题型
        int questionType = questionEditRequest.getQuestionType().intValue();

        //判断题型是否是单选题或者判断题
        if (questionType == QuestionTypeEnum.SingleChoice.getCode() || questionType == QuestionTypeEnum.TrueFalse.getCode()) {
            //如果是，则判断答案是否为空
            if (StringUtils.isBlank(questionEditRequest.getCorrect())) {
                //如果为空，则抛出异常提示信息
                String errorMsg = ErrorUtil.parameterErrorFormat("correct", "不能为空");
                return new RestResponse<>(SystemCode.ParameterValidError.getCode(), errorMsg);
            }
        }

        //判断题型是否是填空题
        if (questionType == QuestionTypeEnum.GapFilling.getCode()) {
            //遍历填空题选项中的分数并相加
            Integer scoreSum = questionEditRequest.getItems().stream().mapToInt(d -> ExamUtil.scoreFromVM(d.getScore())).sum();
            //分数乘十
            Integer questionScore = ExamUtil.scoreFromVM(questionEditRequest.getScore());
            //判断题目总分和选项中的分数并相加是否相等
            if (!scoreSum.equals(questionScore)) {
                //如果不相等则抛出异常提示信息
                String errorMsg = ErrorUtil.parameterErrorFormat("score", "空分数和与题目总分不相等");
                return new RestResponse<>(SystemCode.ParameterValidError.getCode(), errorMsg);
            }
        }
        return RestResponse.ok();
    }

    /**
     * 删除题目
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Integer id) {
        Question question = questionService.selectById(id);
        question.setDeleted(true);
        questionService.deleteQuestionById(question);
        return RestResponse.ok();
    }
}
