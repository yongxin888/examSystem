package com.examsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户消息表
 * @DATE: 2023/5/23 12:29
 */
@Data
public class MessageUser implements Serializable {

    private static final long serialVersionUID = -4042932811802896498L;

    //主键ID
    private Integer id;

    //消息内容ID
    private Integer messageId;

    //接收人ID
    private Integer receiveUserId;

    //接收人用户名
    private String receiveUserName;

    //接收人真实姓名
    private String receiveRealName;

    //是否已读
    private Boolean readed;

    //提交时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    //阅读时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date readTime;
}
