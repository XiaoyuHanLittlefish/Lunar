package com.lunar.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.domain.entity.BlogLike;
import com.lunar.mapper.BlogLikeMapper;
import com.lunar.service.BlogLikeService;
import org.springframework.stereotype.Service;

/**
 * (BlogLike)表服务实现类
 *
 * @author makejava
 * @since 2022-03-13 12:01:30
 */
@Service("blogLikeService")
public class BlogLikeServiceImpl extends ServiceImpl<BlogLikeMapper, BlogLike> implements BlogLikeService {

}
