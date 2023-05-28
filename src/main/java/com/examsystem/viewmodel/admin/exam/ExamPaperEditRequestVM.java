package com.examsystem.viewmodel.admin.exam;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 添加试卷请求数据
 * @DATE: 2023/5/12 21:00
 */
@Data
public class ExamPaperEditRequestVM {
    //主键ID
    private Integer id;

    //年级
    @NotNull
    private Integer level;

    //学科ID
    @NotNull
    private Integer subjectId;

    //试卷类型
    @NotNull
    private Integer paperType;

    //试卷名称
    @NotBlank
    private String name;

    //考试时长
    @NotNull
    private Integer suggestTime;

    //限时
    private List<String> limitDateTime;

    //试题题干数据
    @Size(min = 1,message = "请添加试卷标题")
    @Valid
    private List<ExamPaperTitleItemVM> titleItems;

    //试卷总分
    private String score;

}
