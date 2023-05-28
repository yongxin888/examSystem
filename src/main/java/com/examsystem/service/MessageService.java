package com.examsystem.service;

import com.examsystem.entity.Message;
import com.examsystem.entity.MessageUser;
import com.examsystem.viewmodel.admin.message.MessagePageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 消息相关
 * @DATE: 2023/5/23 12:01
 */
public interface MessageService {
    //分页查询消息
    PageInfo<Message> page(MessagePageRequestVM messagePageRequest);

    //根据消息ID查询用户消息
    List<MessageUser> selectByMessageIds(List<Integer> ids);

    //发送消息
    void sendMessage(Message message, List<MessageUser> messageUsers);
}
