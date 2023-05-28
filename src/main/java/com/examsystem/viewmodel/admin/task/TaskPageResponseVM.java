package com.examsystem.viewmodel.admin.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 任务列表响应数据
 * @DATE: 2023/5/15 20:17
 */
@Data
public class TaskPageResponseVM {

    //主键
    private Integer id;

    //任务标题
    private String title;

    //年级
    private Integer gradeLevel;

    //创建人用户名
    private String createUserName;

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    //是否删除
    private Boolean deleted;
}
