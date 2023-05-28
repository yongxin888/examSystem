package com.examsystem.mapper;

import com.examsystem.entity.UserEventLog;
import com.examsystem.entity.other.KeyValue;
import com.examsystem.viewmodel.admin.user.UserEventPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 用户日志相关
 */
@Mapper
public interface UserEventLogMapper {
    //根据时间段查询用户活跃度（每天） key 当天时间 value 行为次数
    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    //根据用户ID查询日志
    List<UserEventLog> page(UserEventPageRequestVM requestVM);

    //添加日志信息
    void insertLog(UserEventLog userEventLog);
}
