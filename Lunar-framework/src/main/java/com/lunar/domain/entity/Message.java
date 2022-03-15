package com.lunar.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * (Message)表实体类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message")
public class Message  {
    //消息id(主键)
    @TableId(type = IdType.AUTO)
    private Integer messageId;

    //消息的发送者用户id(外键)
    private Integer messageSenderId;
    //消息内容
    private String messageContent;
    //消息的创建时间戳
    private Date messageCreateTime;
    //消息的接收者用户id(外键)
    private Integer messageReceiverId;
    //消息是否已读: 0 - 未读/ 1 - 已读
    private Integer messageIfRead;



}
