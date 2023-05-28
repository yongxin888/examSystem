package com.examsystem.common;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 状态码信息枚举
 * @DATE: 2023/5/8 17:10
 */
public enum SystemCode {

    OK(1, "成功"),
    ParameterValidError(501, "参数验证错误"),
    UNAUTHORIZED(401, "用户未登录"),
    NotUserMessage(402, "用户不存在"),
    AccessDenied(403, "用户没有权限访问"),
    AuthError(406, "用户名或密码错误"),
    LoginNot(405, "登录失败"),
    DataAlreadyExists(502, "已有该年级数据，请重新添加");

    //状态码
    int code;
    //响应信息
    String message;

    SystemCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
}
