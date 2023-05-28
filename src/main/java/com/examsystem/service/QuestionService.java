package com.examsystem.service;

import com.examsystem.entity.Question;
import com.examsystem.viewmodel.admin.question.QuestionEditRequestVM;
import com.examsystem.viewmodel.admin.question.QuestionPageRequestVM;
import com.github.pagehelper.PageInfo;

/**
 * 题目相关
 */
public interface QuestionService {
    //查询题目总数
    int selectAllCount();

    //根据条件分页查询题目
    PageInfo<Question> page(QuestionPageRequestVM requestVM);

    //根据ID查询题目信息数据
    QuestionEditRequestVM getQuestionEditRequestVM(Integer questionId);

    //将题目表中的数据映射到 QuestionEditRequestVM 题目具体信息数据中
    QuestionEditRequestVM getQuestionEditRequestVM(Question question);

    //添加题目
    Question insertQuestion(QuestionEditRequestVM questionEditRequest);

    //更新题目
    Question updateQuestion(QuestionEditRequestVM questionEditRequest);

    //根据ID查询题目
    Question selectById(Integer id);

    //更新题目（删除题目）
    int deleteQuestionById(Question question);
}
