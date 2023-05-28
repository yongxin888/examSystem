package com.examsystem.controller.admin;

import com.examsystem.common.RestResponse;
import com.examsystem.entity.Message;
import com.examsystem.entity.MessageUser;
import com.examsystem.entity.User;
import com.examsystem.service.MessageService;
import com.examsystem.service.UserService;
import com.examsystem.utility.*;
import com.examsystem.viewmodel.admin.message.MessagePageRequestVM;
import com.examsystem.viewmodel.admin.message.MessageResponseVM;
import com.examsystem.viewmodel.admin.message.MessageSendVM;
import com.examsystem.viewmodel.admin.user.UserResponseVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 消息中心
 * @DATE: 2023/5/23 11:53
 */
@RestController
@RequestMapping(value = "/api/admin/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *分页查询消息列表
     * @param messagePageRequest 请求数据
     * @return
     */
    @PostMapping("/page")
    public RestResponse<PageInfo<MessageResponseVM>> pageList(@RequestBody MessagePageRequestVM messagePageRequest) {
        //分页查询消息数据
        PageInfo<Message> pageInfo = messageService.page(messagePageRequest);

        //pageInfo.getList()获取分页查询结果 拿到ID形成一个新的List集合
        List<Integer> ids = pageInfo.getList().stream().map(d -> d.getId()).collect(Collectors.toList());
        //根据消息ID查询用户消息表
        List<MessageUser> messageUsers = ids.size() == 0 ? null : messageService.selectByMessageIds(ids);

        //pageInfo替换
        PageInfo<MessageResponseVM> page = PageInfoHelperUtil.copyMap(pageInfo, message -> {
            //数据拷贝
            MessageResponseVM vm = ModelMapperSingle.Instance().map(message, MessageResponseVM.class);
            //在集合中查询消息内容ID与消息ID相同的集合,并提取其接收人用户名用，分隔形成一个String字符串
            String collect = messageUsers.stream().filter(messageUser -> messageUser.getMessageId().equals(message.getId()))
                    .map(messageUser -> messageUser.getReceiveUserName()).collect(Collectors.joining(","));
            //接收人用户名
            vm.setReceives(collect);
            return vm;
        });
        return RestResponse.ok(page);
    }

    /**
     * 发送消息
     * @param messageSend 请求数据
     * @return
     */
    @PostMapping("/send")
    public RestResponse send(@RequestBody @Valid MessageSendVM messageSend, HttpServletRequest request) {

        //获取cookie里的uuid
        String uuid = CookieUtil.getCookie(request, RedisKeyUtil.getTicketKey());

        //从Redis获取个人信息数据
        String str = (String) redisTemplate.opsForValue().get(RedisKeyUtil.getUserKey(uuid));

        //反序列化将JSON数据转UserResponseVM对象
        UserResponseVM userResponse = JsonUtil.jsonToPojo(str, UserResponseVM.class);

        //根据ID批量查询用户
        List<User> receiveUser = userService.selectByIds(messageSend.getReceiveUserIds());
        Date now = new Date();
        Message message = new Message();
        //设置标题
        message.setTitle(messageSend.getTitle());
        //设置内容
        message.setContent(messageSend.getContent());
        //设置提交时间
        message.setCreateTime(now);
        //设置初始已读人数
        message.setReadCount(0);
        //设置接收人数
        message.setReceiveUserCount(receiveUser.size());
        //发送者用户ID
        message.setSendUserId(userResponse.getId());
        //设置用户名
        message.setSendUserName(userResponse.getUserName());
        //用户真实姓名
        message.setSendRealName(userResponse.getRealName());
        //遍历集合
        List<MessageUser> messageUsers = receiveUser.stream().map(user -> {
            //设置接收者信息
            MessageUser messageUser = new MessageUser();
            //接收时间
            messageUser.setCreateTime(now);
            //设置是否已读
            messageUser.setReaded(false);
            //设置接收人真实姓名
            messageUser.setReceiveRealName(user.getRealName());
            //设置接收人ID
            messageUser.setReceiveUserId(user.getId());
            //接收人用户名
            messageUser.setReceiveUserName(user.getUserName());
            return messageUser;
        }).collect(Collectors.toList());
        //新增消息数据，包括发送方以及接收方
        messageService.sendMessage(message, messageUsers);
        return RestResponse.ok();
    }
}
