package com.examsystem.viewmodel.admin.education;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 添加学科请求数据
 * @DATE: 2023/5/22 20:16
 */
@Data
public class SubjectEditRequestVM {
    //ID
    private Integer id;

    //学科名
    @NotBlank
    private String name;

    //年级（小学、初中、高中）
    @NotNull
    private Integer level;

    //年级数
    @NotBlank
    private String levelName;
}
