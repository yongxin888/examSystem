package com.examsystem.entity;

import com.examsystem.entity.enums.QuestionTypeEnum;
import com.examsystem.utility.ExamUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 题目表
 * @DATE: 2023/5/12 18:34
 */
@Data
public class Question implements Serializable {

    private static final long serialVersionUID = 8826266720383164363L;

    //主键
    private Integer id;

    //1.单选题 2.多选题 3.判断题 4.填空题 5.简答题
    private Integer questionType;

    //学科
    private Integer subjectId;

    //题目总分(千分制)
    private Integer score;

    //级别
    private Integer gradeLevel;

    //题目难度
    private Integer difficult;

    //正确答案
    private String correct;

    //题目 填空、 题干、解析、答案等信息
    private Integer infoTextContentId;

    //创建人
    private Integer createUser;

    //1.正常
    private Integer status;

    //创建时间
    private Date createTime;

    //是否删除
    private Boolean deleted;

    public void setCorrectFromVM(String correct, List<String> correctArray) {
        //获取当前题目类型
        int qType = this.getQuestionType();

        //判断是否是多选题
        if (qType == QuestionTypeEnum.MultipleChoice.getCode()) {
            //如果是，则将答案集合转字符串 用，分隔
            String correctJoin = ExamUtil.contentToString(correctArray);
            this.setCorrect(correctJoin);
        } else {
            this.setCorrect(correct);
        }
    }
}
