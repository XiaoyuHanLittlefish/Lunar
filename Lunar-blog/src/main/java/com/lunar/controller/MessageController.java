package com.lunar.controller;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.vo.ReadMessageVo;
import com.lunar.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseResult sendMessageToUser(Integer toId, String messageContent) {
        return messageService.sendMessageToUser(toId, messageContent);
    }

    @DeleteMapping("/{messageId}")
    public ResponseResult deleteMessage(@PathVariable("messageId") Integer messageId) {
        return messageService.deleteMessage(messageId);
    }

    @PutMapping
    public ResponseResult readMessages(@RequestBody ReadMessageVo readMessageVo) {
        return messageService.readMessages(readMessageVo);
    }
}
