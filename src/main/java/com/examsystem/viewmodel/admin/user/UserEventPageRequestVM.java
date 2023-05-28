package com.examsystem.viewmodel.admin.user;

import com.examsystem.common.BasePage;
import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户日志分页
 * @DATE: 2023/5/11 21:11
 */
@Data
public class UserEventPageRequestVM extends BasePage {
    //用户ID
    private Integer userId;

    //用户名
    private String userName;
}
