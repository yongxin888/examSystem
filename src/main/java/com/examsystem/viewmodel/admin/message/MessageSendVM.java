package com.examsystem.viewmodel.admin.message;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 发送消息请求数据
 * @DATE: 2023/5/23 13:32
 */
@Data
public class MessageSendVM {

    //标题
    @NotBlank
    private String title;

    //内容
    @NotBlank
    private String content;

    //接收人ID
    @Size(min = 1, message = "接收人不能为空")
    private List<Integer> receiveUserIds;
}
