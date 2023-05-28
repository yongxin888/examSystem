package com.examsystem.service;

import com.examsystem.common.RestResponse;
import com.examsystem.entity.User;
import com.examsystem.entity.login.LoginTicket;
import com.examsystem.entity.other.KeyValue;
import com.examsystem.viewmodel.admin.user.UserCreateVM;
import com.examsystem.viewmodel.admin.user.UserPageRequestVM;
import com.examsystem.viewmodel.login.LoginRequestVM;
import com.examsystem.viewmodel.login.LoginResponseVm;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户相关
 */
public interface UserService {
    //根据条件查询用户
    PageInfo<User> userPage(UserPageRequestVM requestVM);

    //根据用户名查询
    User getUserByUserName(String username);

    //新增用户
    int insertUser(UserCreateVM user);

    //根据ID查询用户
    User getUserById(Integer id);

    //更新用户状态
    int updateByIdStatus(User user);

    //删除用户
    int deleteByIdUser(User user);

    //根据用户名查询用户
    List<KeyValue> selectByUserName(String userName);

    //根据ID批量查询用户
    List<User> selectByIds(List<Integer> ids);

    //用户登录
    Map<String, Object> login(User user);

    //用户登出
    RestResponse loginOut();

    //获取某个用户的权限
    List<GrantedAuthority> getAuthorities(int userId);

    //根据ticket查询用户信息
    LoginTicket findLoginTicket(HttpServletRequest request, String ticket);
}
