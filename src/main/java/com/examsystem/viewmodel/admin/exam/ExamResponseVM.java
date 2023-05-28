package com.examsystem.viewmodel.admin.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 任务管理试卷响应数据
 * @DATE: 2023/5/16 20:39
 */
@Data
public class ExamResponseVM {

    //主键
    private Integer id;

    //试卷名称
    private String name;

    //题目总数
    private Integer questionCount;

    //试卷分数
    private Integer score;

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    //创建人
    private Integer createUser;

    //创建人
    private Integer subjectId;

    //试卷类型
    private Integer paperType;

    //试卷内容
    private Integer frameTextContentId;
}
