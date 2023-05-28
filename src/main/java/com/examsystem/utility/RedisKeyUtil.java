package com.examsystem.utility;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 缓存中的key
 * @DATE: 2023/5/25 18:38
 */
public class RedisKeyUtil {
    private static final String SPLIT = "/";    //连接符
    private static final String USER = "user";   //用户凭证
    private static final String TICKET = "TOKEN"; //登录后凭证

    /**
     * 用户凭证
     * @param uuid 随机凭证
     * @return
     */
    public static String getUserKey(String uuid) {
        return USER + SPLIT + uuid;
    }

    /**
     * 登录后凭证
     * @return
     */
    public static String getTicketKey() {
        return TICKET;
    }
}
