package com.lunar.service.impl;

import com.lunar.constants.SystemConstants;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.LoginUser;
import com.lunar.domain.entity.User;
import com.lunar.domain.vo.UserLoginVo;
import com.lunar.domain.vo.UserVo;
import com.lunar.service.LoginService;
import com.lunar.service.UserService;
import com.lunar.utils.BeanCopyUtils;
import com.lunar.utils.JwtUtil;
import com.lunar.utils.RedisCache;
import com.lunar.utils.UserFillUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder PasswordEncoder;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserAccount(),user.getUserPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.REDIS_CACHE_LOGIN_KEY + userId, loginUser);

        //把token和user封装 返回
        //把User转换成UserVo
        UserVo userVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserVo.class);
        UserLoginVo userLoginVo = new UserLoginVo(jwt, userVo);
        return ResponseResult.okResult(userLoginVo);
    }

    @Override
    public ResponseResult logout() {
        //获取userid
        String userId = UserFillUtils.getUserIdFromToken().toString();
        //删除redis中的用户信息
        redisCache.deleteObject(SystemConstants.REDIS_CACHE_LOGIN_KEY + userId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //加密密码
        user.setUserPassword(PasswordEncoder.encode(user.getUserPassword()));
        //新增用户
        userService.save(user);

        return ResponseResult.okResult();
    }
}
