package com.examsystem.viewmodel.admin.exam;

import com.examsystem.common.BasePage;
import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 试卷列表请求数据
 * @DATE: 2023/5/12 15:13
 */
@Data
public class ExamPaperPageRequestVM extends BasePage {
    //主键ID
    private Integer id;

    //学科
    private Integer subjectId;

    //年级
    private Integer level;

    //试卷类型
    private Integer paperType;

    //任务ID
    private Integer taskExamId;
}
