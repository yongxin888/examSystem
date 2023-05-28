package com.examsystem.viewmodel.admin.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户个人信息
 * @DATE: 2023/5/24 13:41
 */
@Data
public class UserResponseVM {
    //主键ID
    private Integer id;

    //用户uuid
    private String userUuid;

    //用户名
    private String userName;

    //真实姓名
    private String realName;

    //年龄
    private Integer age;

    //角色
    private Integer role;

    //性别
    private Integer sex;

    //出生日期
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date birthDay;

    //手机号
    private String phone;

    //最后活动时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastActiveTime;

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    //修改时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyTime;

    //状态
    private Integer status;

    //年级
    private Integer userLevel;

    //头像
    private String imagePath;
}
