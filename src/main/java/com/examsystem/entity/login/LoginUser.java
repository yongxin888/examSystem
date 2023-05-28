package com.examsystem.entity.login;

import com.examsystem.entity.User;
import com.examsystem.entity.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 封装用户对应的信息，包括权限、状态等
 * @NoArgsConstructor 空参构造
 * @AllArgsConstructor 有参构造
 * @DATE: 2023/5/26 12:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

//    private List<GrantedAuthority> permissions;
//
//    public LoginUser(User user, List<GrantedAuthority> list){
//        this.user = user;
//        this.permissions = list;
//    }

    /**
     * 权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        //根据ID查询用户
//        //User user = this.getUserById(userId);
//        //权限集合
//        List<GrantedAuthority> list = new ArrayList<>();
//        //将权限存入security
//        list.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                //判断用户权限
//                switch (user.getRole()) {
//                    case 2:
//                        return RoleEnum.TEACHER.getName();
//                    case 3:
//                        return RoleEnum.ADMIN.getName();
//                    default:
//                        return RoleEnum.STUDENT.getName();
//                }
//            }
//        });
        return null;
    }

    /**
     * 密码
     * @return
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 用户名
     * @return
     */
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 用户没过期返回true，反之则false
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户没锁定返回true，反之则false
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户凭据(通常为密码)没过期返回true，反之则false
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是启用状态返回true，反之则false
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
