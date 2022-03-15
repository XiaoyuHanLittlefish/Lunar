package com.lunar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Message;
import com.lunar.domain.entity.User;
import com.lunar.domain.vo.MessageVo;
import com.lunar.domain.vo.SenderVo;
import com.lunar.mapper.MessageMapper;
import com.lunar.service.MessageService;
import com.lunar.service.UserService;
import com.lunar.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        queryWrapper.eq(Message::getMessageReceiverId, userId);
        queryWrapper.orderByDesc(Message::getMessageCreateTime);

        List<User> senderList = list(queryWrapper).stream()
                .map(Message -> userService.getById(Message.getMessageSenderId()))
                .skip((long) (pageNumber - 1) * pageSize)
                .limit((long) pageSize)
                .collect(Collectors.toList());

        List<SenderVo> senderVoList = BeanCopyUtils.copyBeanList(senderList, SenderVo.class);

        return ResponseResult.okResult(senderVoList);
    }

    @Override
    public ResponseResult getMessageList(Integer userId, Integer toId) {
        //根据userId和toId查找信息列表
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getMessageSenderId, userId)
                .or().eq(Message::getMessageReceiverId, userId);
        queryWrapper.orderByDesc(Message::getMessageCreateTime);

        List<Message> messageList = list(queryWrapper);
        List<MessageVo> messageVoList = BeanCopyUtils.copyBeanList(messageList, MessageVo.class);

        return ResponseResult.okResult(messageVoList);
    }
}
