package com.examsystem.entity.enums;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 题目状态枚举
 * @DATE: 2023/5/14 21:03
 */
public enum QuestionStatusEnum {

    OK(1, "正常"),
    Publish(2, "发布");

    int code;
    String name;

    QuestionStatusEnum(int code, String name) {
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
