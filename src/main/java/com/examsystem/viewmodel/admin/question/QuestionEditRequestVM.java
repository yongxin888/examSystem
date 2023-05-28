package com.examsystem.viewmodel.admin.question;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 题目信息数据
 * @DATE: 2023/5/12 21:04
 */
@Data
public class QuestionEditRequestVM {

    //主键
    private Integer id;

    //题型
    @NotNull
    private Integer questionType;

    //学科
    @NotNull
    private Integer subjectId;

    //题干
    @NotBlank
    private String title;

    //年级
    private Integer gradeLevel;

    //选项
    @Valid
    private List<QuestionEditItemVM> items;

    //解析
    @NotBlank
    private String analyze;

    //正确答案
    private List<String> correctArray;

    //正确答案
    private String correct;

    //题目分数
    @NotBlank
    private String score;

    //难度
    @Range(min = 1, max = 5, message = "请选择题目难度")
    private Integer difficult;

    //题序
    private Integer itemOrder;
}
