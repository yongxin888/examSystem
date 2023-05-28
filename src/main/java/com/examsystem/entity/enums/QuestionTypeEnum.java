package com.examsystem.entity.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 标题枚举
 */
public enum QuestionTypeEnum {
    SingleChoice(1, "单选题"),
    MultipleChoice(2, "多选题"),
    TrueFalse(3, "判断题"),
    GapFilling(4, "填空题"),
    ShortAnswer(5, "简答题");

    int code;
    String name;

    private static final Map<Integer, QuestionTypeEnum> keyMap = new HashMap<>();

    //将枚举类信息存到Map集合中
    static {
        for (QuestionTypeEnum item : QuestionTypeEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    //根据code获取值
    public static QuestionTypeEnum fromCode(Integer code) {
        return keyMap.get(code);
    }

    QuestionTypeEnum(int code, String name) {
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
