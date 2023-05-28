package com.examsystem.viewmodel.admin.exam;

import com.examsystem.viewmodel.admin.question.QuestionEditRequestVM;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 试题题干数据
 * @DATE: 2023/5/12 21:03
 */
@Data
public class ExamPaperTitleItemVM {

    //标题
    @NotBlank(message = "标题内容不能为空")
    private String name;

    //题目数据
    @Size(min = 1,message = "请添加题目")
    @Valid
    private List<QuestionEditRequestVM> questionItems;
}
