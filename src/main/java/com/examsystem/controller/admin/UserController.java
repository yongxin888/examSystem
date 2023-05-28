package com.examsystem.controller.admin;

import com.examsystem.common.RestResponse;
import com.examsystem.entity.User;
import com.examsystem.entity.UserEventLog;
import com.examsystem.entity.enums.UserStatusEnum;
import com.examsystem.entity.other.KeyValue;
import com.examsystem.service.UserEventLogService;
import com.examsystem.service.UserService;
import com.examsystem.utility.*;
import com.examsystem.viewmodel.admin.user.UserCreateVM;
import com.examsystem.viewmodel.admin.user.UserEventPageRequestVM;
import com.examsystem.viewmodel.admin.user.UserPageRequestVM;
import com.examsystem.viewmodel.admin.user.UserResponseVM;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户管理
 * @DATE: 2023/5/8 16:51
 */
@RestController
@RequestMapping("/api/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserEventLogService userEventLogService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 学生列表
     * @param userPageRequest role 角色 pageIndex 起始页 pageSize 每页显示条数
     * @return 学生列表
     */
    @PostMapping("/page/list")
    public RestResponse<PageInfo<User>> pageList(@RequestBody UserPageRequestVM userPageRequest) {
        //通过角色查询
        PageInfo<User> userPageInfo = userService.userPage(userPageRequest);
        return RestResponse.ok(userPageInfo);
    }

    /**
     * 添加学生
     * @Valid 表单验证
     * @return
     */
    @PostMapping("/edit")
    public RestResponse<UserCreateVM> edit(@RequestBody @Valid UserCreateVM userCreate) {
        if (userCreate.getId() == null) {
            //根据用户名查询用户
            User user = userService.getUserByUserName(userCreate.getUserName());
            if (user != null) {
                return new RestResponse<>(2, "用户已存在");
            }

            if (StringUtils.isBlank(userCreate.getPassword())) {
                return new RestResponse<>(3, "密码不能为空");
            }
        }

        //如果生日为空，则设为null
        if (StringUtils.isBlank(userCreate.getBirthDay())) {
            userCreate.setBirthDay(null);
        }

        if (userCreate.getId() == null) {
            //生成UUID
            String uuid = CommunityUtil.generateUUID();
            String password = userCreate.getPassword();

            //密码加密
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encode = passwordEncoder.encode(password);

            //将密码进行MD5加密
            //String pass = CommunityUtil.md5(password + CommunityUtil.passwordSubString(uuid));
            userCreate.setUserUuid(uuid);
            userCreate.setPassword(encode);
            userCreate.setLastActiveTime(new Date());
            userCreate.setCreateTime(new Date());
            userCreate.setDeleted(false);
            userService.insertUser(userCreate);
        }
        return RestResponse.ok(userCreate);
    }

    /**
     * 修改学生状态
     * @param id 学生id
     * @return
     */
    @PostMapping("/changeStatus/{id}")
    public RestResponse<Integer> changeStatus(@PathVariable Integer id) {
        //根据id查询学生信息
        User user = userService.getUserById(id);

        //修改学生状态
        Integer status = user.getStatus();
        Integer newStatus = status == UserStatusEnum.Enable.getCode() ? UserStatusEnum.Disable.getCode() : UserStatusEnum.Enable.getCode();

        //设置学生信息
        user.setStatus(newStatus);
        user.setModifyTime(new Date());

        //更新学生信息
        userService.updateByIdStatus(user);

        return RestResponse.ok(newStatus);
    }

    /**
     * 根据id查询用户信息
     * @param id 用户id
     * @return
     */
    @PostMapping("/select/{id}")
    public RestResponse<User> select(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        user.setPassword("");
        return RestResponse.ok(user);
    }

    /**
     * 分页查询用户日志
     * @param userEventPageRequest 请求数据
     * @return
     */
    @PostMapping("/event/page/list")
    public RestResponse<PageInfo<UserEventLog>> eventPageList(@RequestBody UserEventPageRequestVM userEventPageRequest) {
        //根据用户id以及用户名查询日志
        PageInfo<UserEventLog> page = userEventLogService.page(userEventPageRequest);
        return RestResponse.ok(page);
    }

    /**
     * 删除用户，修改用户delete状态，并不是真正的删除
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        user.setDeleted(true);
        userService.deleteByIdUser(user);
        return RestResponse.ok();
    }

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return
     */
    @PostMapping("/selectByUserName")
    public RestResponse<List<KeyValue>> selectByUserName(@RequestBody String userName) {
        List<KeyValue> keyValues = userService.selectByUserName(userName);
        return RestResponse.ok(keyValues);
    }

    /**
     * 个人信息
     * @return
     */
    @PostMapping("/current")
    public RestResponse<UserResponseVM> current(HttpServletRequest request) {
//        //获取cookie里的uuid
//        Cookie[] cookies = request.getCookies();
//        String uuid = null;
//        //循环遍历
//        for (Cookie cookie : cookies) {
//            //拿到ticket中的值
//            if (cookie.getName().equalsIgnoreCase("ticket")) {
//                uuid = cookie.getValue();
//            }
//        }
        //获取cookie里的uuid
        String token = CookieUtil.getCookie(request, RedisKeyUtil.getTicketKey());

        //解析token
        Claims claims = JWTUtil.parseJwt(token);
        String userId = claims.getSubject();

        //从redis获取用户信息
        String userKey = RedisKeyUtil.getUserKey(userId);
        String str = (String) redisTemplate.opsForValue().get(userKey);

        //反序列化将JSON数据转User对象
        User user = JsonUtil.jsonToPojo(str, User.class);

        UserResponseVM map = ModelMapperSingle.Instance().map(user, UserResponseVM.class);

        return RestResponse.ok(map);
    }
}
