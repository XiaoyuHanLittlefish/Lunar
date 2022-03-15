package com.lunar.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.domain.entity.UserFollow;
import com.lunar.mapper.UserFollowMapper;
import com.lunar.service.UserFollowService;
import org.springframework.stereotype.Service;

/**
 * (UserFollow)表服务实现类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@Service("userFollowService")
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {

}
