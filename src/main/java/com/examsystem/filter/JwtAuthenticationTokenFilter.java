package com.examsystem.filter;

import com.examsystem.entity.User;
import com.examsystem.entity.login.LoginUser;
import com.examsystem.service.UserService;
import com.examsystem.utility.CookieUtil;
import com.examsystem.utility.JWTUtil;
import com.examsystem.utility.JsonUtil;
import com.examsystem.utility.RedisKeyUtil;
import com.examsystem.viewmodel.admin.user.UserResponseVM;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 请求过滤器
 * @DATE: 2023/5/26 14:30
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从cookie中获取凭证token
        String token = CookieUtil.getCookie(request, RedisKeyUtil.getTicketKey());

        //获取token
        //String token = request.getHeader(RedisKeyUtil.getTicketKey());

        //判断token是否为空
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }

        //解析token
        Claims claims = JWTUtil.parseJwt(token);
        String userId = claims.getSubject();

        //从redis获取用户信息
        String userKey = RedisKeyUtil.getUserKey(userId);
        String str = (String) redisTemplate.opsForValue().get(userKey);

        //判断str是否为空
        if (Objects.isNull(str)) {
            Cookie newCookie = new Cookie(RedisKeyUtil.getTicketKey(), "");
            newCookie.setMaxAge(0); //立即删除型
            newCookie.setPath("/");
            response.addCookie(newCookie);
            throw new RuntimeException("用户未登录.....");
        }

        //反序列化将JSON数据转User对象
        User user = JsonUtil.jsonToPojo(str, User.class);

        //List<GrantedAuthority> authorities = userService.getAuthorities(user.getRole());

        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null,  null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //SecurityContextHolder.setContext(new SecurityContextImpl(authenticationToken));

        //放行
        filterChain.doFilter(request, response);

    }
}
