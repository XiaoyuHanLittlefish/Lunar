package com.lunar.controller.admin;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.User;
import com.lunar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseResult adminGetUserList() {
        return userService.adminGetUserList();
    }

    @DeleteMapping("/{userId}")
    public ResponseResult adminDeleteUser(@PathVariable("userId") Integer userId) {
        return userService.adminDeleteUser(userId);
    }

    @PutMapping("/{userId}")
    public ResponseResult adminUpdateBlog(@PathVariable("userId") Integer userId,
                                          @RequestBody User user) {
        return userService.adminUpdateUser(userId, user);
    }

}
