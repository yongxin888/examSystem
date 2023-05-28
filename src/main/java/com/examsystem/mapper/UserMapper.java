package com.examsystem.mapper;

import com.examsystem.entity.User;
import com.examsystem.entity.other.KeyValue;
import com.examsystem.viewmodel.admin.user.UserCreateVM;
import com.examsystem.viewmodel.admin.user.UserPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户相关
 */
@Mapper
public interface UserMapper {
    //根据条件查询用户
    List<User> userPage(UserPageRequestVM requestVM);

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

    //根据用户名模糊查询用户
    List<KeyValue> selectByUserName(String userName);

    //根据ID批量查询用户
    List<User> selectByIds(List<Integer> ids);
}
