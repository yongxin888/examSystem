package com.examsystem.service.impl;

import com.examsystem.entity.TextContent;
import com.examsystem.mapper.TextContentMapper;
import com.examsystem.service.TextContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 文本相关
 * @DATE: 2023/5/12 19:10
 */
@Service
public class TextContentServiceImpl implements TextContentService {

    @Autowired
    private TextContentMapper textContentMapper;

    /**
     * 根据ID查询文本
     * @param id id
     * @return
     */
    @Override
    public TextContent selectById(Integer id) {
        return textContentMapper.selectById(id);
    }

    /**
     * 新增试卷题目信息
     * @param textContent
     * @return
     */
    @Override
    public int insertTextContent(TextContent textContent) {
        return textContentMapper.insertTextContent(textContent);
    }

    /**
     * 根据ID更新文本数据
     * @param textContent 文本数据
     * @return
     */
    @Override
    public int updateTextContentById(TextContent textContent) {
        return textContentMapper.updateTextContentById(textContent);
    }
}
