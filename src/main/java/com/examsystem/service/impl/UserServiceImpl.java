package com.examsystem.service.impl;

import com.examsystem.common.RestResponse;
import com.examsystem.entity.User;
import com.examsystem.entity.UserEventLog;
import com.examsystem.entity.enums.RoleEnum;
import com.examsystem.entity.login.LoginTicket;
import com.examsystem.entity.login.LoginUser;
import com.examsystem.entity.other.KeyValue;
import com.examsystem.mapper.UserMapper;
import com.examsystem.service.UserEventLogService;
import com.examsystem.service.UserService;
import com.examsystem.utility.*;
import com.examsystem.viewmodel.admin.user.UserCreateVM;
import com.examsystem.viewmodel.admin.user.UserPageRequestVM;
import com.examsystem.viewmodel.admin.user.UserResponseVM;
import com.examsystem.viewmodel.login.LoginRequestVM;
import com.examsystem.viewmodel.login.LoginResponseVm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 用户相关
 * @DATE: 2023/5/9 20:44
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserEventLogService userEventLogService;

    /**
     * 根据条件查询用户并分页
     * @param requestVM userName 用户名  role 角色
     * @return
     */
    @Override
    public PageInfo<User> userPage(UserPageRequestVM requestVM) {
        //PageHelper.startPage开启分页,通过拦截MySQL的方式,把查询语句拦截下来加limit 根据ID进行降序
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(
                () -> userMapper.userPage(requestVM));
    }

    /**
     * 根据用户名查询
     * @param username 用户名
     * @return
     */
    @Override
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

    /**
     * 新增用户
     * @param user 用户数据
     * @return
     */
    @Override
    public int insertUser(UserCreateVM user) {
        return userMapper.insertUser(user);
    }

    /**
     * 根据ID查询用户
     * @param id id
     * @return
     */
    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    /**
     * 根据用户状态
     * @param user 用户数据
     * @return
     */
    @Override
    public int updateByIdStatus(User user) {
        return userMapper.updateByIdStatus(user);
    }

    /**
     * 删除用户
     * @param user 请求数据
     * @return
     */
    @Override
    public int deleteByIdUser(User user) {
        return userMapper.deleteByIdUser(user);
    }

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return
     */
    @Override
    public List<KeyValue> selectByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }

    /**
     * 根据ID批量查询用户
     * @param ids id
     * @return
     */
    @Override
    public List<User> selectByIds(List<Integer> ids) {
        return userMapper.selectByIds(ids);
    }

    /**
     * 用户登录
     * @param user 登录请求数据
     * @return
     */
    @Override
    public Map<String, Object> login(User user) {
//        Map<String, Object> map = new HashMap<>();
//
//        if (StringUtils.isBlank(loginRequest.getPassword())) {
//            map.put("passwordMsg", "密码不能为空");
//            return map;
//        }
//
//        //验证账号
//        User user = userMapper.getUserByUserName(loginRequest.getUserName());
//        if (user == null || StringUtils.isBlank(loginRequest.getUserName())) {
//            map.put("usernameMsg", "该账户不存在");
//            return map;
//        }
//
//        // 验证状态
//        if (user.getStatus() == 2) {
//            // 账号被禁用
//            map.put("usernameNotStar", "该账号已被禁用");
//            return map;
//        }
//
//        //验证密码是否正确
//        String password = CommunityUtil.md5(loginRequest.getPassword() + CommunityUtil.passwordSubString(user.getUserUuid()));
//        if (!user.getPassword().equals(password)) {
//            map.put("passwordMsg", "密码错误");
//            return map;
//        }
//
//        //数据拷贝,去掉一些无用数据
//        UserResponseVM userResponse = ModelMapperSingle.Instance().map(user, UserResponseVM.class);
//
//        //如果用户名密码都正确，则生成该用户登录凭证
//        LoginTicket loginTicket = new LoginTicket();
//        loginTicket.setUserId(user.getId()); //用户ID
//        loginTicket.setStatus(0); // 设置凭证状态为有效（当用户登出的时候，设置凭证状态为无效）
//        loginTicket.setTicket(CommunityUtil.generateUUID()); //生成随机凭证
//        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000)); //凭证到期时间
//
//        //将凭证存入redis
//        redisTemplate.opsForValue().set(RedisKeyUtil.getUserKey(loginTicket.getTicket()), JsonUtil.objectToJson(userResponse), 1, TimeUnit.DAYS);
//
//        map.put("ticket", loginTicket.getTicket());
//        map.put("userName", user.getUserName());
//        return map;

        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        //根据用户名查询用户
        User byUserName = getUserByUserName(user.getUserName());
        //获取用户权限
        Integer role = byUserName.getRole();
        //如果用户没权限，则抛出异常
        if (role == 1) {
            throw new RuntimeException("您没有权限");
        }

        //如果认证通过, 通过userid生成一个jwt，jwt存入LoginResponseVm返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //获取用户ID
        String userId = loginUser.getUser().getId().toString();
        String jwt = JWTUtil.createJwt(userId);

        //把完整的用户信息存入redis。userID作为key,一天后过期
        redisTemplate.opsForValue().set((RedisKeyUtil.getUserKey(userId)), JsonUtil.objectToJson(loginUser.getUser()), 1, TimeUnit.DAYS);

//        //设置返回数据
//        LoginResponseVm loginResponseVm = new LoginResponseVm();
//        loginResponseVm.setUserName(loginUser.getUsername());
//        loginResponseVm.setToken(jwt);

        //配置日志信息
        UserEventLog userEventLog = new UserEventLog();
        userEventLog.setUserId(loginUser.getUser().getId());    //用户ID
        userEventLog.setUserName(user.getUserName());   //用户名
        userEventLog.setRealName(byUserName.getRealName()); //真实姓名
        userEventLog.setContent(user.getUserName() + "登录了学之思开源考试系统");  //文本信息
        userEventLog.setCreateTime(new Date());

        //添加日志
        userEventLogService.insertLog(userEventLog);

        Map<String, Object> map = new HashMap<>();
        map.put(RedisKeyUtil.getTicketKey(), jwt);
        map.put("userName", loginUser.getUsername());

        return map;
    }

    /**
     * 登出
     * @return
     */
    @Override
    public RestResponse loginOut() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User loginUser = (User) authentication.getPrincipal();
        String id = loginUser.getId().toString();

        //删除Redis中的值
        redisTemplate.delete(RedisKeyUtil.getUserKey(id));
        return RestResponse.ok();
    }

    /**
     * 获取用户权限
     * @param userId
     * @return
     */
    @Override
    public List<GrantedAuthority> getAuthorities(int userId) {
//        //根据ID查询用户
        User user = this.getUserById(userId);
        //权限集合
        List<GrantedAuthority> list = new ArrayList<>();
        //将权限存入security
        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                //判断用户权限
                switch (user.getRole()) {
                    case 2:
                        return RoleEnum.TEACHER.getName();
                    case 3:
                        return RoleEnum.ADMIN.getName();
                    default:
                        return RoleEnum.STUDENT.getName();
                }
            }
        });

        return list;
    }

    /**
     * 根据ticket查询用户信息
     * @param ticket ticket
     * @return
     */
    @Override
    public LoginTicket findLoginTicket(HttpServletRequest request, String ticket) {
        //从cookie中获取凭证token
        String token = CookieUtil.getCookie(request, RedisKeyUtil.getTicketKey());

        //解析token
        Claims claims = JWTUtil.parseJwt(token);
        String userId = claims.getSubject();

        //从redis获取用户信息
        String userKey = RedisKeyUtil.getUserKey(userId);
        String str = (String) redisTemplate.opsForValue().get(userKey);

        //反序列化将JSON数据转User对象
        User user = JsonUtil.jsonToPojo(str, User.class);

        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(token);

        return loginTicket;
    }
}
