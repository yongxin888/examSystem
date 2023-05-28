package com.examsystem.viewmodel.admin.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 添加学生类
 * @DATE: 2023/5/11 17:01
 */
@Data
public class UserCreateVM {
    //主键
    private Integer id;

    //uuid
    private String userUuid;

    //用户名
    @NotBlank   //不能为空 需要配合@Valid一起使用
    private String userName;

    //密码
    private String password;

    //真实姓名
    @NotBlank
    private String realName;

    //年龄
    private String age;

    //状态
    private Integer status;

    //性别
    private Integer sex;

    //生日
    private String birthDay;

    //电话
    private String phone;

    //角色
    private Integer role;

    //年级
    private Integer userLevel;

    //提交时间
    private Date createTime;

    //上次登录时间
    private Date lastActiveTime;

    //是否删除
    private Boolean deleted;
}
