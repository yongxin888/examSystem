package com.examsystem.utility;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 异常工具类
 * @DATE: 2023/5/14 19:19
 */
public class ErrorUtil {

    /**
     * 异常信息拼接
     * @param field 异常字段
     * @param msg 异常信息
     * @return
     */
    public static String parameterErrorFormat(String field, String msg) {
        return "【" + field + " : " + msg + "】";
    }

}
