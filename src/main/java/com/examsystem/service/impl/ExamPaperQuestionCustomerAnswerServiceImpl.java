package com.examsystem.service.impl;

import com.examsystem.entity.other.KeyValue;
import com.examsystem.mapper.ExamPaperQuestionCustomerAnswerMapper;
import com.examsystem.service.ExamPaperQuestionCustomerAnswerService;
import com.examsystem.utility.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 试卷题目答案表
 * @DATE: 2023/5/8 19:27
 */
@Service
public class ExamPaperQuestionCustomerAnswerServiceImpl implements ExamPaperQuestionCustomerAnswerService {

    @Autowired
    private ExamPaperQuestionCustomerAnswerMapper examPaperQuestionCustomerAnswerMapper;

    /**
     * 查询答题总数
     * @return
     */
    @Override
    public int selectAllCount() {
        return examPaperQuestionCustomerAnswerMapper.selectAllCount();
    }

    /**
     * 计算每月答题数
     * @return
     */
    @Override
    public List<Integer> selectMothCount() {
        //开始时间
        Date startTime = DateTimeUtil.getMonthStartDay();
        //结束时间
        Date endTime = DateTimeUtil.getMonthEndDay();
        //获取开始时间到现在的答题数
        List<KeyValue> mouthCount = examPaperQuestionCustomerAnswerMapper.selectCountByDate(startTime, endTime);
        //格式化日期
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        return mothStartToNowFormat.stream().map(md -> {
            //stream.filter查找对应用户名条件的集合，findAny如果找到任意的一个，则返回，orElse如果没有找到，则返回null
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }
}
