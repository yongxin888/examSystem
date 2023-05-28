package com.examsystem.entity.login;

import lombok.Data;

import java.util.Date;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 登录凭证
 * @DATE: 2023/5/23 20:45
 */
@Data
public class LoginTicket {

    private int id;

    //用户ID
    private int userId;

    //凭证
    private String ticket;

    //状态（是否有效）
    private int status;

    //过期时间
    private Date expired;
}
