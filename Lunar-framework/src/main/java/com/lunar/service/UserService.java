package com.lunar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
public interface UserService extends IService<User> {

    ResponseResult getUser(Integer userId);

    ResponseResult getUserDetail(Integer userId);

    ResponseResult followUser(Integer toId);

    ResponseResult cancelFollowUser(Integer toId);

    ResponseResult getFollowList(Integer userId, Integer pageNumber, Integer pageSize);

    ResponseResult getFanList(Integer userId, Integer pageNumber, Integer pageSize);

    ResponseResult updateUser(Integer userId, User user);

    ResponseResult updateUserAvatar(Integer userId, MultipartFile file);

    ResponseResult hasFollowUser(Integer userId);

    ResponseResult getBlogListOfUser(Integer userId, Integer pageNumber, Integer pageSize);

    ResponseResult adminGetUserList();

    ResponseResult adminDeleteUser(Integer userId);

    ResponseResult adminUpdateUser(Integer userId, User user);
}
