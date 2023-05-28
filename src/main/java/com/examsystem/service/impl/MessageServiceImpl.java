package com.examsystem.service.impl;

import com.examsystem.entity.Message;
import com.examsystem.entity.MessageUser;
import com.examsystem.mapper.MessageMapper;
import com.examsystem.mapper.MessageUserMapper;
import com.examsystem.service.MessageService;
import com.examsystem.viewmodel.admin.message.MessagePageRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 消息相关
 * @DATE: 2023/5/23 12:02
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageUserMapper messageUserMapper;

    /**
     * 分页查询消息数据
     * @param messagePageRequest 请求数据
     * @return
     */
    @Override
    public PageInfo<Message> page(MessagePageRequestVM messagePageRequest) {
        //开启分页
        return PageHelper.startPage(messagePageRequest.getPageIndex(), messagePageRequest.getPageSize(),
                "id desc").doSelectPageInfo(() -> messageMapper.page(messagePageRequest)
        );
    }

    /**
     * 根据消息ID查询用户消息
     * @param ids 消息ID
     * @return
     */
    @Override
    public List<MessageUser> selectByMessageIds(List<Integer> ids) {
        return messageUserMapper.selectByMessageIds(ids);
    }

    /**
     * 发送消息
     * @param message 消息发送方数据
     * @param messageUsers 消息接收方数据
     */
    @Override
    @Transactional
    public void sendMessage(Message message, List<MessageUser> messageUsers) {
        //新增消息
        messageMapper.insertMessage(message);
        //获取刚新增的消息ID,并赋值给用户消息中的消息内容ID
        messageUsers.forEach(messageUser -> messageUser.setMessageId(message.getId()));
        //新增用户消息
        messageUserMapper.insertMessageUsers(messageUsers);
    }
}
