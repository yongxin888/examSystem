package com.examsystem.mapper;

import com.examsystem.entity.TextContent;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文本相关
 */
@Mapper
public interface TextContentMapper {
    //根据ID查询文本
    TextContent selectById(Integer id);

    //新增试卷题目信息
    int insertTextContent(TextContent textContent);

    //根据ID更新文本数据
    int updateTextContentById(TextContent textContent);
}
