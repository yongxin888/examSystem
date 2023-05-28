package com.examsystem.common;

import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 分页通用类
 * @DATE: 2023/5/9 17:01
 */
@Data
public class BasePage {
    //起始页
    private Integer pageIndex;
    //每页显示条数
    private Integer pageSize;
}
