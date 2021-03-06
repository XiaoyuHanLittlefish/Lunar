package com.lunar.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.domain.entity.CommentLike;
import com.lunar.mapper.CommentLikeMapper;
import com.lunar.service.CommentLikeService;
import org.springframework.stereotype.Service;

/**
 * (CommentLike)表服务实现类
 *
 * @author makejava
 * @since 2022-03-13 12:01:43
 */
@Service("commentLikeService")
public class CommentLikeServiceImpl extends ServiceImpl<CommentLikeMapper, CommentLike> implements CommentLikeService {

}
