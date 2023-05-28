package com.examsystem.entity.other;

import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 键值对类
 * @DATE: 2023/5/9 14:59
 */
@Data
public class KeyValue {
    //键
    private String name;

    //值
    private Integer value;
}
