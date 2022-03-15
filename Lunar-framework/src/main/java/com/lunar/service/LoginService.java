package com.lunar.service;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult register(User user);
}
