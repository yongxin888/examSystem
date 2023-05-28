package com.examsystem.controller;

import com.examsystem.common.RestResponse;
import com.examsystem.common.SystemCode;
import com.examsystem.entity.User;
import com.examsystem.service.UserService;
import com.examsystem.utility.RedisKeyUtil;
import com.examsystem.viewmodel.login.LoginRequestVM;
import com.examsystem.viewmodel.login.LoginResponseVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户登录/登出
 * @DATE: 2023/5/23 20:09
 */
@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param user 请求数据
     * @return
     */
    @PostMapping("/login")
    public RestResponse login(@RequestBody User user, HttpServletResponse response) {
//        //设置cookie过期时间
//        int expiredSeconds = loginRequest.isRemember() ? 3600 * 12 : 3600 * 24 * 30;
//
//        //验证用户名和密码
//        Map<String, Object> login = userService.login(loginRequest, expiredSeconds);
//
//        //判断用户名是否存在
//        if (login.containsKey("usernameMsg")) {
//            return RestResponse.fail(SystemCode.NotUserMessage.getCode(), SystemCode.NotUserMessage.getMessage());
//        }
//
//        //判断密码是否正确
//        if (login.containsKey("passwordMsg")) {
//            return RestResponse.fail(SystemCode.AuthError.getCode(), SystemCode.AuthError.getMessage());
//        }
//
//        //判断密码是否被禁用
//        if (login.containsKey("usernameNotStar")) {
//            return RestResponse.fail(SystemCode.AccessDenied.getCode(), SystemCode.AccessDenied.getMessage());
//        }
//
//        String ticketKey = RedisKeyUtil.getTicketKey();
//
//        //判断ticket在map中是否存在
//        if (login.containsKey(ticketKey)) {
//            //账号和密码都正确，则服务端会生成ticket，浏览器通过 cookie 存储 ticket
//            Cookie cookie = new Cookie(ticketKey, login.get(ticketKey).toString());
//            //cookie有效期
//            cookie.setMaxAge(expiredSeconds);
//            //访问路径
//            cookie.setPath("/");
//            //添加cookie
//            response.addCookie(cookie);
//
//            //返回用户名
//            LoginResponseVm loginResponseVm = new LoginResponseVm();
//            String userName = login.get("userName").toString();
//            loginResponseVm.setUserName(userName);
//
//            return RestResponse.ok(loginResponseVm);
//        }else {
//            //否则登录失败
//            return RestResponse.fail(SystemCode.LoginNot.getCode(), SystemCode.LoginNot.getMessage());
//        }
        Map<String, Object> map = userService.login(user);

        String ticketKey = RedisKeyUtil.getTicketKey();
        //账号和密码都正确，则服务端会生成ticket，浏览器通过 cookie 存储 ticket
        Cookie cookie = new Cookie(ticketKey, map.get(ticketKey).toString());
        //cookie有效期
        cookie.setMaxAge(3600 * 12);
        //访问路径
        cookie.setPath("/");
        //添加cookie
        response.addCookie(cookie);

        return RestResponse.ok(map);
    }

    /**
     * 登出
     * @return
     */
    @PostMapping("/logout")
    public RestResponse loginOut() {
        return userService.loginOut();
    }
}
