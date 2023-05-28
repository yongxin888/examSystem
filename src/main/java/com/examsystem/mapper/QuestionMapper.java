package com.examsystem.mapper;

import com.examsystem.entity.Question;
import com.examsystem.viewmodel.admin.question.QuestionPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题目相关
 */
@Mapper
public interface QuestionMapper {
    //查询题目总数
    int selectAllCount();

    //根据条件查询题目
    List<Question> page(QuestionPageRequestVM requestVM);

    //根据ID查询题目信息
    Question selectQuestionById(Integer id);

    //根据ID查询多条题目信息
    List<Question> selectByIds(@Param("ids") List<Integer> ids);

    //新增题目信息
    int insertQuestion(Question question);

    //更新题目信息
    int updateQuestion(Question question);
}
