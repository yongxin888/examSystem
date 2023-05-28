package com.examsystem.entity.enums;

/**
 * 试卷类型枚举
 */
public enum ExamPaperTypeEnum {
    Fixed(1, "固定试卷"),
    TimeLimit(4, "时段试卷"),
    Task(6, "任务试卷");

    int code;
    String name;

    ExamPaperTypeEnum(int code, String name) {
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
