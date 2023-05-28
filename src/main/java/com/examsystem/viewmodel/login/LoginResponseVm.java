package com.examsystem.viewmodel.login;

import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户登录响应数据
 * @DATE: 2023/5/23 20:26
 */
@Data
public class LoginResponseVm {
    //用户名
    private String userName;

    //token
    private String token;
}
