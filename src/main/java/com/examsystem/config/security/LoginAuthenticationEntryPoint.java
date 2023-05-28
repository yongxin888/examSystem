package com.examsystem.config.security;

import com.examsystem.common.SystemCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户未登录情况
 * @DATE: 2023/5/23 15:37
 */
@Component
public class LoginAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    //如果用户未登录，则跳转到登录页面
//    public LoginAuthenticationEntryPoint() {
//        super();
//    }
//

    public LoginAuthenticationEntryPoint() {
        super("/login");
    }

    /**
     * 开启认证方案
     * @param request 认证异常的用户请求
     * @param response 将要返回给用户的响应
     * @param authException 请求过程中遇见的认证异常
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        RestUtil.response(response, SystemCode.UNAUTHORIZED);
    }
}
