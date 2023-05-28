package com.examsystem.viewmodel.admin.education;

import com.examsystem.common.BasePage;
import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 学科分页请求数据模型
 * @DATE: 2023/5/22 19:47
 */
@Data
public class SubjectPageRequestVM extends BasePage {
    //id
    private Integer id;

    //年级（小学、初中、高中）
    private Integer level;
}
