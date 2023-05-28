package com.examsystem.utility;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 对象映射拷贝工具类
 * @DATE: 2023/5/12 17:16
 */
public class ModelMapperSingle {

    public static ModelMapper Instance() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

}
