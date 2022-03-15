package com.lunar.controller;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.User;
import com.lunar.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        return loginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user) {
        return loginService.register(user);
    }

}
