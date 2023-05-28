package com.examsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户表
 * @DATE: 2023/5/9 16:14
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -7797183521247423117L;

    private Integer id;

    //uuid
    private String userUuid;

    //用户名
    private String userName;

    //密码
    private String password;

    //真实姓名
    private String realName;

    //年龄
    private Integer age;

    //1.男 2女
    private Integer sex;

    //生日
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date birthDay;

    //学生年级(1-12)
    private Integer userLevel;

    //电话
    private String phone;

    //1.学生 2.老师 3.管理员
    private Integer role;

    //1.启用 2禁用
    private Integer status;

    //头像地址
    private String imagePath;

    //提交时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    //修改时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyTime;

    //上次登录时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastActiveTime;

    //是否删除
    private Boolean deleted;

    //微信openId
    private String wxOpenId;
}
