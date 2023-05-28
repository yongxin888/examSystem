package com.examsystem.service;

import com.examsystem.entity.UserEventLog;
import com.examsystem.viewmodel.admin.user.UserEventPageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 用户日志表
 */
public interface UserEventLogService {
    //查询指定日期的用户行为
    List<Integer> selectMothCount();

    //根据用户ID查询日志
    PageInfo<UserEventLog> page(UserEventPageRequestVM requestVM);

    //添加日志信息
    void insertLog(UserEventLog userEventLog);
}
