package com.examsystem.mapper;

import com.examsystem.entity.other.KeyValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 试卷答案相关
 */
@Mapper
public interface ExamPaperQuestionCustomerAnswerMapper {
    //查询答题总数
    int selectAllCount();

    //根据时间段查询答题数
    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
