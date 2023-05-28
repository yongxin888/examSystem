package com.examsystem.utility;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 获取cookie
 * @DATE: 2023/5/24 15:40
 */
public class CookieUtil {

    public static String getCookie(HttpServletRequest request, String name) {
        //获取cookie里的uuid
        Cookie[] cookies = request.getCookies();
        String uuid = null;
        //循环遍历
        for (Cookie cookie : cookies) {
            //拿到ticket中的值
            if (cookie.getName().equalsIgnoreCase(name)) {
                uuid = cookie.getValue();
            }
        }

        return uuid;
    }
}
