package com.examsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 任务表
 * @DATE: 2023/5/16 20:02
 */
@Data
public class TaskExam implements Serializable {

    private static final long serialVersionUID = -7014704644631536195L;

    //主键
    private Integer id;

    //任务标题
    private String title;

    //年级
    private Integer gradeLevel;

    //任务框架 内容为JSON
    private Integer frameTextContentId;

    //创建者
    private Integer createUser;

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    //是否删除
    private Boolean deleted;

    //创建人用户名
    private String createUserName;
}
