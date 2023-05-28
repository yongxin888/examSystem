package com.examsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 文本表
 * @DATE: 2023/5/12 19:11
 */
@Data
public class TextContent implements Serializable {

    private static final long serialVersionUID = -1279530310964668131L;

    //主键
    private Integer id;

    //内容(Json)
    private String content;

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
