package com.lunar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Blog;
import com.lunar.domain.entity.User;
import com.lunar.domain.entity.UserFollow;
import com.lunar.service.BlogService;
import com.lunar.service.SystemService;
import com.lunar.service.UserFollowService;
import com.lunar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("systemService")
public class SystemServiceImpl implements SystemService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFollowService userFollowService;

    @Autowired
    private BlogService blogService;

    @Override
    public ResponseResult refreshSystem() {
        refreshUser();

        return ResponseResult.okResult();
    }

    private void refreshUser() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        List<User> userList = userService.list(queryWrapper);

        for (User user : userList) {
            LambdaQueryWrapper<UserFollow> userFollowLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userFollowLambdaQueryWrapper.eq(UserFollow::getAuthorId, user.getUserId());
            int followNumber = userFollowService.list(userFollowLambdaQueryWrapper).size();
            user.setUserFollowNumber(followNumber);
        }
        for (User user : userList) {
            LambdaQueryWrapper<UserFollow> userFollowLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userFollowLambdaQueryWrapper.eq(UserFollow::getToId, user.getUserId());
            int fansNumber = userFollowService.list(userFollowLambdaQueryWrapper).size();
            user.setUserFansNumber(fansNumber);
        }
        for (User user : userList) {
            LambdaQueryWrapper<Blog> blogLambdaQueryWrapper = new LambdaQueryWrapper<>();
            blogLambdaQueryWrapper.eq(Blog::getBlogAuthorId, user.getUserId());
            int articleNumber = blogService.list(blogLambdaQueryWrapper).size();
            user.setUserArticleNumber(articleNumber);
        }
        for (User user : userList) {
            userService.updateById(user);
        }
    }
}
