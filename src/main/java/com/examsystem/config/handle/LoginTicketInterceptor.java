//package com.examsystem.config.handle;
//
//import com.examsystem.entity.User;
//import com.examsystem.entity.login.LoginTicket;
//import com.examsystem.service.UserService;
//import com.examsystem.utility.CookieUtil;
//import com.examsystem.utility.RedisKeyUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Date;
//
///**
// * @PROJECT_NAME: examSystem
// * @DESCRIPTION: 检查登录凭证拦截器
// * @DATE: 2023/5/25 18:26
// */
//@Component
//public class LoginTicketInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private UserService userService;
//
//    /**
//     * 在 Controller 执行之前被调用
//     * 检查用户是否登录
//     * @param request 请求数据
//     * @param response 响应数据
//     * @param handler
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //从cookie中获取凭证
//        String ticket = CookieUtil.getCookie(request, RedisKeyUtil.getTicketKey());
//
//        //判断凭证是否为空
//        if (ticket != null) {
//            //根据凭证查询Redis用户信息是否存在
//            LoginTicket loginTicket = userService.findLoginTicket(ticket);
//
//            //检查凭证是否有效或过期
//            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
//                //根据用户ID查询用户是否存在
//                User user = userService.getUserById(loginTicket.getUserId());
//
//                // 构建用户认证的结果，并存入 SecurityContext, 以便于 Spring Security 进行授权
//                Authentication authentication = new UsernamePasswordAuthenticationToken(
//                        user, user.getPassword(), userService.getAuthorities(user.getId())
//                );
//                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
//            }
//        }
//        return true;
//    }
//}
