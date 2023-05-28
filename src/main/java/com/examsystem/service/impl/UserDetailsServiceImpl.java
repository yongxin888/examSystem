package com.examsystem.service.impl;

import com.examsystem.entity.User;
import com.examsystem.entity.login.LoginUser;
import com.examsystem.mapper.UserMapper;
import com.examsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 自定义security用户校验类
 * @DATE: 2023/5/26 12:42
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        User user = userMapper.getUserByUserName(username);

        //如果没有查询到用户就抛出异常
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误");
        }

        //权限
        //Collection<? extends GrantedAuthority> authorities = userService.getAuthorities(user.getId());

        //把数据封装成UserDetails返回
        return new LoginUser(user);
    }
}
