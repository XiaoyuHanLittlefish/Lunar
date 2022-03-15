package com.lunar.controller;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.User;
import com.lunar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseResult getUser(@PathVariable("userId") Integer userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/{userId}/detail")
    public ResponseResult getUserDetail(@PathVariable("userId") Integer userId) {
        return userService.getUserDetail(userId);
    }

    @PostMapping("/{userId}/follow")
    public ResponseResult followUser(@PathVariable("userId") Integer toId) {
        return userService.followUser(toId);
    }

    @DeleteMapping("/{userId}/follow")
    public ResponseResult cancelFollowUser(@PathVariable("userId") Integer toId) {
        return userService.cancelFollowUser(toId);
    }

    @GetMapping("/{userId}/follow/list")
    public ResponseResult getFollowList(@PathVariable("userId") Integer userId,
                                        Integer pageNumber, Integer pageSize) {
        return userService.getFollowList(userId, pageNumber, pageSize);
    }

    @GetMapping("/{userId}/fan/list")
    public ResponseResult getFanList(@PathVariable("userId") Integer userId,
                                     Integer pageNumber, Integer pageSize) {

        return userService.getFanList(userId, pageNumber, pageSize);
    }

    @PutMapping("/{userId}")
    public ResponseResult updateUser(@PathVariable("userId") Integer userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

}
