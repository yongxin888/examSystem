package com.examsystem.viewmodel.admin.education;

import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 学科响应数据模型
 * @DATE: 2023/5/22 19:49
 */
@Data
public class SubjectResponseVM {
    //主键ID
    private Integer id;

    //学科名
    private String name;

    //年级（小学、初中、高中）
    private Integer level;

    //年级数
    private String levelName;
}
