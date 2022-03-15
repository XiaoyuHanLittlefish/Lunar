package com.lunar.controller;

import com.lunar.domain.ResponseResult;
import com.lunar.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/sender/list")
    public ResponseResult getMessageSenderList(Integer userId, Integer pageNumber, Integer pageSize) {
        return messageService.getMessageSenderList(userId, pageNumber, pageSize);
    }
    
    @GetMapping("/list")
    public ResponseResult getMessageList(Integer userId, Integer toId) {
        return messageService.getMessageList(userId, toId);
    }

}
