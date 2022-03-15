package com.lunar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.User;
import com.lunar.domain.entity.UserFollow;
import com.lunar.domain.vo.FollowerVo;
import com.lunar.domain.vo.UserDetailVo;
import com.lunar.domain.vo.UserVo;
import com.lunar.mapper.UserMapper;
import com.lunar.service.UserFollowService;
import com.lunar.service.UserService;
import com.lunar.utils.BeanCopyUtils;
import com.lunar.utils.UserFillUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserFollowService userFollowService;

    @Override
    public ResponseResult getUser(Integer userId) {
        //根据用户id查找用户
        User user = getById(userId);
        //封装成vo
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);

        return ResponseResult.okResult(userVo);
    }

    @Override
    public ResponseResult getUserDetail(Integer userId) {
        //根据用户id查找用户
        User user = getById(userId);
        //封装成vo
        UserDetailVo userDetailVo = BeanCopyUtils.copyBean(user, UserDetailVo.class);

        return ResponseResult.okResult(userDetailVo);
    }

    @Override
    public ResponseResult followUser(Integer toId) {
        //得到userId
        Integer userId = UserFillUtils.getUserIdFromToken();
        //创建UserFollow
        UserFollow userFollow = new UserFollow();
        userFollow.setAuthorId(userId);
        userFollow.setToId(toId);
        //存储到数据库
        userFollowService.save(userFollow);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult cancelFollowUser(Integer toId) {
        //得到userId
        Integer userId = UserFillUtils.getUserIdFromToken();
        //查找并删除userFollow
        LambdaQueryWrapper<UserFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFollow::getAuthorId, userId);
        queryWrapper.eq(UserFollow::getToId, toId);

        userFollowService.remove(queryWrapper);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getFollowList(Integer userId, Integer pageNumber, Integer pageSize) {
        //获取followerList
        LambdaQueryWrapper<UserFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFollow::getAuthorId, userId);
        queryWrapper.orderByDesc(UserFollow::getFollowTime);
        //分页查询
        Page<UserFollow> page = new Page<>(pageNumber, pageSize);
        userFollowService.page(page, queryWrapper);

        List<FollowerVo> followerVoList = page.getRecords().stream()
                .map(UserFollow -> getById(UserFollow.getToId()))
                .map(User -> BeanCopyUtils.copyBean(User, FollowerVo.class))
                .collect(Collectors.toList());

        return ResponseResult.okResult(followerVoList);
    }

    @Override
    public ResponseResult getFanList(Integer userId, Integer pageNumber, Integer pageSize) {
        //获取fanList
        LambdaQueryWrapper<UserFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFollow::getToId, userId);
        queryWrapper.orderByDesc(UserFollow::getFollowTime);
        //分页查询
        Page<UserFollow> page = new Page<>(pageNumber, pageSize);
        userFollowService.page(page, queryWrapper);

        List<FollowerVo> fanList = page.getRecords().stream()
                .map(UserFollow -> getById(UserFollow.getAuthorId()))
                .map(User -> BeanCopyUtils.copyBean(User, FollowerVo.class))
                .collect(Collectors.toList());

        return ResponseResult.okResult(fanList);
    }

    @Override
    public ResponseResult updateUser(Integer userId, User user) {
        // TODO 检查是否有权限
        User user1 = getById(userId);

        if (user.getUserName() != null) {
            user1.setUserName(user.getUserName());
        }

        if (user.getUserBirthday() != null) {
            user1.setUserBirthday(user.getUserBirthday());
        }

        if (user.getUserSignature() != null) {
            user1.setUserSignature(user.getUserSignature());
        }

        if (user.getUserProfile() != null) {
            user1.setUserProfile(user.getUserProfile());
        }

        if (user.getUserArea() != null) {
            user1.setUserArea(user.getUserArea());
        }

        updateById(user1);

        return ResponseResult.okResult();
    }
}
