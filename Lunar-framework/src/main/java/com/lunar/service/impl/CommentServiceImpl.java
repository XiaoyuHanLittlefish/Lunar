package com.lunar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Comment;
import com.lunar.domain.entity.CommentLike;
import com.lunar.domain.vo.CommentVo;
import com.lunar.domain.vo.PageVo;
import com.lunar.mapper.CommentMapper;
import com.lunar.service.CommentLikeService;
import com.lunar.service.CommentService;
import com.lunar.service.UserService;
import com.lunar.utils.CommentFillUtils;
import com.lunar.utils.UserFillUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentLikeService commentLikeService;

    @Override
    public ResponseResult getBlogCommentList(Integer blogId, Integer pageNumber, Integer pageSize) {
        //查询对应文章根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对博客id进行判断
        queryWrapper.eq(Comment::getCommentBlogId, blogId);
        //检测是否为根评论
        queryWrapper.isNull(Comment::getCommentParentId);

        queryWrapper.orderByDesc(Comment::getCommentCreateTime);
        //分页查询
        Page<Comment> page = new Page<>(pageNumber, pageSize);
        page(page, queryWrapper);
        //转换成vo
        List<CommentVo> commentVoList = CommentFillUtils.toCommentVoList(page.getRecords(), commentService, userService);

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult createBlogComment(Comment comment) {
        //获取userId 并给comment设置commentAuthorId
        Integer userId = UserFillUtils.getUserIdFromToken();
        comment.setCommentAuthorId(userId);
        // TODO: 检查commentBlogId commentContent非空
        // TODO: 填充commentCreateTime commentLikeNumber commentDislikeNumber

        save(comment);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteComment(Integer commentId) {
        // TODO 检查是否有权限
        LambdaQueryWrapper<CommentLike> commentLikeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLikeLambdaQueryWrapper.eq(CommentLike::getCommentId, commentId);
        commentLikeService.remove(commentLikeLambdaQueryWrapper);

        removeById(commentId);

        return ResponseResult.okResult();
    }

}
