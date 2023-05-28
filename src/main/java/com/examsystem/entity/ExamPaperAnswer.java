package com.examsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 试卷答案表
 * @DATE: 2023/5/22 22:36
 */
@Data
public class ExamPaperAnswer implements Serializable {

    private static final long serialVersionUID = -2143539181805283910L;

    //主键ID
    private Integer id;

    //试卷ID
    private Integer examPaperId;

    //试卷名称
    private String paperName;

    //试卷类型( 1固定试卷 4.时段试卷 6.任务试卷)
    private Integer paperType;

    //学科
    private Integer subjectId;

    //系统判定得分
    private Integer systemScore;

    //最终得分(千分制)
    private Integer userScore;

    //试卷总分
    private Integer paperScore;

    //做对题目数量
    private Integer questionCorrect;

    //题目总数量
    private Integer questionCount;

    //做题时间(秒)
    private Integer doTime;

    //试卷状态(1待判分 2完成)
    private Integer status;

    //学生
    private Integer createUser;

    //提交时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    //任务ID
    private Integer taskExamId;
}
