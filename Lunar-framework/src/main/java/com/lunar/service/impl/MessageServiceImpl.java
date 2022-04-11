package com.lunar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Message;
import com.lunar.domain.vo.MessageVo;
import com.lunar.domain.vo.PageVo;
import com.lunar.enums.AppHttpCodeEnum;
import com.lunar.mapper.MessageMapper;
import com.lunar.service.MessageService;
import com.lunar.service.UserService;
import com.lunar.utils.BeanCopyUtils;
import com.lunar.utils.UserFillUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (Message)表服务实现类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult getMessageSenderList(Integer userId, Integer pageNumber, Integer pageSize) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getMessageReceiverId, userId)
                        .or().eq(Message::getMessageSenderId, userId);
        queryWrapper.orderByDesc(Message::getMessageCreateTime);

        Page<Message> page = new Page<>(pageNumber, pageSize);

        List<MessageVo> messageVoList = page(page, queryWrapper).getRecords().stream()
                .map(Message -> BeanCopyUtils.copyBean(Message, MessageVo.class))
                .collect(Collectors.toList());

        for (MessageVo messageVo : messageVoList) {
            messageVo.setMessageSenderName(userService.getById(messageVo.getMessageSenderId()).getUserName());

            messageVo.setMessageReceiverName(userService.getById(messageVo.getMessageReceiverId()).getUserName());
        }

        return ResponseResult.okResult(new PageVo(messageVoList, page.getTotal()));
    }

    @Override
    public ResponseResult getMessageList(Integer userId, Integer toId) {
        //根据userId和toId查找信息列表
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(i -> i.eq(Message::getMessageSenderId, userId).eq(Message::getMessageReceiverId, toId))
                .or().and(i -> i.eq(Message::getMessageSenderId, toId).eq(Message::getMessageReceiverId, userId));
        queryWrapper.orderByDesc(Message::getMessageCreateTime);

        List<Message> messageList = list(queryWrapper);
        List<MessageVo> messageVoList = BeanCopyUtils.copyBeanList(messageList, MessageVo.class);

        for (MessageVo messageVo : messageVoList) {
            messageVo.setMessageSenderName(userService.getById(messageVo.getMessageSenderId()).getUserName());

            messageVo.setMessageReceiverName(userService.getById(messageVo.getMessageReceiverId()).getUserName());
        }

        return ResponseResult.okResult(messageVoList);
    }

    @Override
    public ResponseResult sendMessageToUser(Integer toId, String messageContent) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();

        //如果没有找到userId 返回需要登陆
        if(Objects.isNull(userId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), AppHttpCodeEnum.NEED_LOGIN.getMsg());
        }
        Message message = new Message();
        message.setMessageSenderId(userId);
        message.setMessageReceiverId(toId);
        message.setMessageCreateTime(new Timestamp(System.currentTimeMillis()));
        message.setMessageContent(messageContent);
        save(message);

        return ResponseResult.okResult(message);
    }
}
