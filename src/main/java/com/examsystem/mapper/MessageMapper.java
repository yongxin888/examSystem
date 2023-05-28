package com.examsystem.mapper;

import com.examsystem.entity.Message;
import com.examsystem.viewmodel.admin.message.MessagePageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 消息相关
 */
@Mapper
public interface MessageMapper {
    //根据条件查询消息
    List<Message> page(MessagePageRequestVM requestVM);

    //新增消息
    int insertMessage(Message message);
}
