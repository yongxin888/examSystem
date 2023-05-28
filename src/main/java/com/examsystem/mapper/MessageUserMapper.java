package com.examsystem.mapper;

import com.examsystem.entity.MessageUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户消息相关
 */
@Mapper
public interface MessageUserMapper {
    //根据消息ID查询用户名信息
    List<MessageUser> selectByMessageIds(List<Integer> ids);

    //新增用户消息
    void insertMessageUsers(List<MessageUser> list);
}
