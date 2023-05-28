package com.examsystem.viewmodel.admin.message;

import com.examsystem.common.BasePage;
import lombok.Data;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 消息列表请求数据
 * @DATE: 2023/5/23 11:58
 */
@Data
public class MessagePageRequestVM extends BasePage {
    //发送者用户名
    private String sendUserName;
}
