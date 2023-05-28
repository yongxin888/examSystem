package com.examsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 消息表
 * @DATE: 2023/5/23 12:10
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = -3510265139403747341L;

    //主键ID
    private Integer id;

    //标题
    private String title;

    //内容
    private String content;

    //提交时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    //发送者用户ID
    private Integer sendUserId;

    //发送者用户名
    private String sendUserName;

    //发送者真实姓名
    private String sendRealName;

    //接收人数
    private Integer receiveUserCount;

    //已读人数
    private Integer readCount;
}
