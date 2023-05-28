package com.examsystem.config.security;

import com.examsystem.common.RestResponse;
import com.examsystem.common.SystemCode;
import com.examsystem.utility.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 响应工具类
 * @DATE: 2023/5/23 15:55
 */
public class RestUtil {

    private static final Logger logger = LoggerFactory.getLogger(RestUtil.class);

    /**
     *
     * @param response 响应数据
     * @param systemCode 状态码
     */
    public static void response(HttpServletResponse response, SystemCode systemCode) {
        response(response, systemCode.getCode(), systemCode.getMessage());
    }

    public static void response(HttpServletResponse response, int systemCode, String msg) {
        response(response, systemCode, msg, null);
    }

    public static void response(HttpServletResponse response, int systemCode, String msg, Object content) {
        try {
            //生成响应类数据
            RestResponse res = new RestResponse<>(systemCode, msg, content);
            //将JSON数据转换为String
            String resStr = JsonUtil.toJsonStr(res);
            //将内容指定为JSON格式，以UTF-8字符编码进行编码
            response.setContentType("application/json;charset=utf-8");
            //向前台页面显示一段信息
            response.getWriter().write(resStr);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
