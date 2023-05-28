package com.examsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 试卷表
 * @DATE: 2023/5/12 15:07
 */
@Data
public class ExamPaper implements Serializable {

    private static final long serialVersionUID = 8509645224550501395L;

    //主键
    private Integer id;

    //试卷名称
    private String name;

    //学科
    private Integer subjectId;

    //试卷类型( 1固定试卷 4.时段试卷 6.任务试卷)
    private Integer paperType;

    //年级
    private Integer gradeLevel;

    //试卷总分(千分制)
    private Integer score;

    //题目数量
    private Integer questionCount;

    //建议时长(分钟)
    private Integer suggestTime;

    //时段试卷 开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date limitStartTime;

    //时段试卷 结束时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date limitEndTime;

    //试卷框架 内容为JSON（题目数据）
    private Integer frameTextContentId;

    //添加的用户
    private Integer createUser;

    //提交时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    //是否删除
    private Boolean deleted;

    //任务ID
    private Integer taskExamId;
}
