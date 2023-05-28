package com.examsystem.viewmodel.admin.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 消息列表响应数据
 * @DATE: 2023/5/23 11:56
 */
@Data
public class MessageResponseVM {
    //ID
    private Integer id;

    //消息标题
    private String title;

    //消息内容
    private String content;

    //发送人用户名
    private String sendUserName;

    //接收人用户名
    private String receives;

    //接收人数量
    private Integer receiveUserCount;

    //已读数量
    private Integer readCount;

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
