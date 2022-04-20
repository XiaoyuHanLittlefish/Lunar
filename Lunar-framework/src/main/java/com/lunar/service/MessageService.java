package com.lunar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Message;
import com.lunar.domain.vo.ReadMessageVo;


/**
 * (Message)表服务接口
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
public interface MessageService extends IService<Message> {

    ResponseResult getMessageSenderList(Integer userId, Integer pageNumber, Integer pageSize);

    ResponseResult getMessageList(Integer userId, Integer toId);

    ResponseResult sendMessageToUser(Integer toId, String messageContent);

    ResponseResult deleteMessage(Integer messageId);

    ResponseResult readMessages(ReadMessageVo readMessageVo);
}
