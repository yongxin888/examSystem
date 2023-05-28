package com.examsystem.utility;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 生成UUID以及MD5加密
 * @DATE: 2023/5/11 18:25
 */
public class CommunityUtil {
    /**
     * 生成随机字符串
     * @return
     */
    public static String generateUUID() {
        // 去除生成的随机字符串中的 ”-“
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * md5 加密
     * @param key 要加密的字符串
     * @return
     */
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    /**
     * 截取UUID前五个数
     * @param password
     * @return
     */
    public static String passwordSubString(String password) {
        return password.substring(0, 5);
    }
}
