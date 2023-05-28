package com.examsystem.viewmodel.admin.answer;

import com.examsystem.common.BasePage;
import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 答卷列表请求数据
 * @DATE: 2023/5/22 22:47
 */
@Data
public class ExamPaperAnswerPageRequestVM extends BasePage {
    //学科
    private Integer subjectId;
}
