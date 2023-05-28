package com.examsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户日志表
 * @DATE: 2023/5/11 21:06
 */
@Data
public class UserEventLog implements Serializable {

    private static final long serialVersionUID = -3951198127152024633L;

    //主键
    private Integer id;

    //用户id
    private Integer userId;

    //用户名
    private String userName;

    //真实姓名
    private String realName;

    //内容
    private String content;

    //时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
