package com.lunar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Blog;
import com.lunar.domain.entity.User;
import com.lunar.domain.entity.UserFollow;
import com.lunar.domain.vo.*;
import com.lunar.enums.AppHttpCodeEnum;
import com.lunar.mapper.UserMapper;
import com.lunar.service.*;
import com.lunar.utils.BeanCopyUtils;
import com.lunar.utils.BlogFillUtils;
import com.lunar.utils.FileUtils;
import com.lunar.utils.UserFillUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
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

    @Autowired
    private BlogService blogService;

    @Autowired
    private HasTagService hasTagService;

    @Autowired
    private TagService tagService;

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

        PageVo pageVo = new PageVo(followerVoList, page.getTotal());
        return ResponseResult.okResult(pageVo);
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

        PageVo pageVo = new PageVo(fanList, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult updateUser(Integer userId, User user) {
        //如果userId与Token中的id不同 返回无效操作权限
        if(!userId.equals(UserFillUtils.getUserIdFromToken())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }

        User user1 = getById(userId);

        if (!Objects.isNull(user.getUserName())) {
            user1.setUserName(user.getUserName());
        }

        if (!Objects.isNull(user.getUserBirthday())) {
            user1.setUserBirthday(user.getUserBirthday());
        }

        if (!Objects.isNull(user.getUserSignature())) {
            user1.setUserSignature(user.getUserSignature());
        }

        if (!Objects.isNull(user.getUserProfile())) {
            user1.setUserProfile(user.getUserProfile());
        }

        if (!Objects.isNull(user.getUserArea())) {
            user1.setUserArea(user.getUserArea());
        }

        if (!Objects.isNull(user.getUserGender())) {
            user1.setUserGender(user.getUserGender());
        }

        if(!Objects.isNull(user.getUserAvatar())) {
            user1.setUserAvatar(user.getUserAvatar());
        }

        updateById(user1);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateUserAvatar(Integer userId, MultipartFile file) {
        //如果userId与Token中的id不同 返回无效操作权限
        if(!userId.equals(UserFillUtils.getUserIdFromToken())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }

        FileSaveVo fileSaveVo = FileUtils.saveFile(file);
        if(fileSaveVo.getMessage().equals("未选择文件")) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }else if(fileSaveVo.getMessage().equals("上传错误")) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }else {
            User user = getById(userId);
            user.setUserAvatar(fileSaveVo.getUrl());

            updateById(user);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult hasFollowUser(Integer userId) {
        Integer userIdFromToken = UserFillUtils.getUserIdFromToken();

        if(Objects.isNull(userId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        HasFollowVo hasFollowVo = new HasFollowVo();

        LambdaQueryWrapper<UserFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFollow::getAuthorId, userIdFromToken);
        queryWrapper.eq(UserFollow::getToId, userId);
        UserFollow userFollow = userFollowService.getOne(queryWrapper);
        hasFollowVo.setHasFollow(Objects.isNull(userFollow));

        return ResponseResult.okResult(hasFollowVo);
    }

    @Override
    public ResponseResult getBlogListOfUser(Integer userId, Integer pageNumber, Integer pageSize) {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getBlogAuthorId, userId);
        Page<Blog> page = new Page<>(pageNumber, pageSize);
        blogService.page(page, queryWrapper);

        List<HotBlogVo> blogVoList = page.getRecords().stream()
                .map(Blog -> BeanCopyUtils.copyBean(Blog, HotBlogVo.class))
                .collect(Collectors.toList());

        for (HotBlogVo blogVo : blogVoList) {
            //根据blogAuthorId查询作者昵称
            blogVo.setBlogAuthorName(getById(blogVo.getBlogAuthorId()).getUserName());
            //根据blogId查询标签列表
            blogVo.setBlogTags(BlogFillUtils.getBlogTags(blogVo.getBlogId(), hasTagService, tagService));
        }

        PageVo pageVo = new PageVo(blogVoList, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
