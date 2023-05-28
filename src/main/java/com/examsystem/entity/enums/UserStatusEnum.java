package com.examsystem.entity.enums;


/**
 * 用户状态
 */
public enum UserStatusEnum {
    Enable(1, "启用"),
    Disable(2, "禁用");

    //状态码
    int code;
    //状态信息
    String name;

    UserStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
