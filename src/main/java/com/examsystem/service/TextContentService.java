package com.examsystem.service;

import com.examsystem.entity.TextContent;

/**
 * 文本相关
 */
public interface TextContentService {
    //根据ID查询文本信息
    TextContent selectById(Integer id);

    //新增试卷题目信息
    int insertTextContent(TextContent textContent);

    //根据ID更新文本数据
    int updateTextContentById(TextContent textContent);
}
