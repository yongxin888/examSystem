package com.examsystem.service.impl;

import com.examsystem.entity.UserEventLog;
import com.examsystem.entity.other.KeyValue;
import com.examsystem.mapper.UserEventLogMapper;
import com.examsystem.service.UserEventLogService;
import com.examsystem.utility.DateTimeUtil;
import com.examsystem.viewmodel.admin.user.UserEventPageRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户日志相关
 * @DATE: 2023/5/8 19:45
 */
@Service
public class UserEventLogServiceImpl implements UserEventLogService {

    @Autowired
    private UserEventLogMapper userEventLogMapper;

    /**
     * 查询指定日期的用户行为
     * @return
     */
    @Override
    public List<Integer> selectMothCount() {
        //开始日期
        Date startTime = DateTimeUtil.getMonthStartDay();
        //结束日期
        Date endTime = DateTimeUtil.getMonthEndDay();
        //根据时间段获取用户行为 name 时间 value 行为数
        List<KeyValue> mouthCount = userEventLogMapper.selectCountByDate(startTime, endTime);
        //格式化日期
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        return mothStartToNowFormat.stream().map(md -> {
            //stream.filter查找对应时间条件的集合，findAny如果找到任意的一个，则返回，orElse如果没有找到，则返回null
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }

    /**
     * 根据用户ID查询日志
     * @param requestVM 请求数据
     * @return
     */
    @Override
    public PageInfo<UserEventLog> page(UserEventPageRequestVM requestVM) {
        //开启分页,通过拦截MySQL的方式,把查询语句拦截下来加limit 根据ID进行降序
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(
                () -> userEventLogMapper.page(requestVM)
        );
    }

    /**
     * 添加日志
     * @param userEventLog
     */
    @Override
    public void insertLog(UserEventLog userEventLog) {
        userEventLogMapper.insertLog(userEventLog);
    }
}
