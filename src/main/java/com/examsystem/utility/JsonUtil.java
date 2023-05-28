package com.examsystem.utility;

import com.examsystem.entity.TextContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: Json工具类
 * @DATE: 2023/5/12 19:06
 */
public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    static {
        //设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将JSON字符串转反序列化为对象
     * @param json
     * @param valueType
     * @return
     * @param <T>
     */
    public static <T> T toJsonObject(String json, Class<T> valueType) {
        try {
            return MAPPER.<T>readValue(json, valueType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将JSON数据转换为String
     * @param o
     * @return
     * @param <T>
     */
    public static <T> String toJsonStr(T o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将Json数据转换成集合对象
     * @param json Json字符串
     * @param valueType 转换后的对象
     * @return
     * @param <T>
     */
    public static <T> List<T> toJsonListObject(String json, Class<T> valueType) {
        try {
            JavaType getCollectionType = MAPPER.getTypeFactory().constructParametricType(List.class, valueType);
            List<T> list = MAPPER.readValue(json, getCollectionType);
            return list;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 创建一个TextContent，将内容转化为json，回写到content中
     * @param list 需要转换的集合对象
     * @param now 时间
     * @param mapper
     * @return
     * @param <T>
     * @param <R>
     */
    public static <T, R> TextContent jsonConvertInsert(List<T> list, Date now, Function<? super T, ? extends R> mapper) {
        String frameTextContent = null;
        if (null == mapper) {
            frameTextContent = toJsonStr(list);
        } else {
            //获取mapper中的字段，转成一个新的list
            List<R> mapList = list.stream().map(mapper).collect(Collectors.toList());
            frameTextContent = toJsonStr(mapList);
        }
        TextContent textContent = new TextContent();
        textContent.setContent(frameTextContent);
        textContent.setCreateTime(now);
        return textContent;
    }

    /**
     * 修改一个TextContent，将内容转化为json，回写到content中
     * @param textContent
     * @param list
     * @param mapper
     * @return
     * @param <T>
     * @param <R>
     */
    public static <T, R> TextContent jsonConvertUpdate(TextContent textContent, List<T> list, Function<? super T, ? extends R> mapper) {
        String frameTextContent = null;
        if (null == mapper) {
            frameTextContent = toJsonStr(list);
        } else {
            List<R> mapList = list.stream().map(mapper).collect(Collectors.toList());
            frameTextContent = toJsonStr(mapList);
        }
        textContent.setContent(frameTextContent);
        return textContent;
    }

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
