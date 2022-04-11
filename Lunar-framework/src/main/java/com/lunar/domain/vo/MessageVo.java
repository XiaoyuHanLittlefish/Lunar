package com.lunar.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {
    //消息id(主键)
    private Integer messageId;
    //消息的发送者用户id(外键)
    private Integer messageSenderId;
    //消息的发送者用户id(外键)
    private String messageSenderName;
    //消息内容
    private String messageContent;
    //消息的创建时间戳
    private Date messageCreateTime;
    //消息的接收者用户id(外键)
    private Integer messageReceiverId;
    //消息的接收者用户id(外键)
    private String messageReceiverName;
    //消息是否已读: 0 - 未读/ 1 - 已读
    private Integer messageIfRead;
}
