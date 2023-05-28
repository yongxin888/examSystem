package com.examsystem.viewmodel.admin.user;

import com.examsystem.common.BasePage;
import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户分页请求数据类型
 * @DATE: 2023/5/9 20:40
 */
@Data
public class UserPageRequestVM extends BasePage {
    //用户名
    private String userName;
    //角色 1.学生 2.老师 3.管理员
    private Integer role;
}
