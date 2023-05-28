package com.examsystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 学科表
 * @DATE: 2023/5/12 15:31
 */
@Data
public class Subject implements Serializable {

    private static final long serialVersionUID = 8058095034457106501L;

    //主键
    private Integer id;

    //语文 数学 英语 等
    private String name;

    //年级 (1-12) 小学 初中
    private Integer level;

    //一年级、二年级等
    private String levelName;

    //排序
    private Integer itemOrder;

    //是否删除
    private Boolean deleted;
}
