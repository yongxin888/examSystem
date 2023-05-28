package com.examsystem.viewmodel.login;

import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 登录请求数据
 * @DATE: 2023/5/23 20:20
 */
@Data
public class LoginRequestVM {

    //用户名
    private String userName;

    //密码
    private String password;

    //是否记住密码
    private boolean remember;
}
