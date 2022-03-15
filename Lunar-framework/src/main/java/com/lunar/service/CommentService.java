package com.lunar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Comment;


/**
 * (Comment)表服务接口
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
public interface CommentService extends IService<Comment> {

    ResponseResult getBlogCommentList(Integer blogId, Integer pageNumber, Integer pageSize);

    ResponseResult createBlogComment(Comment comment);

    ResponseResult deleteComment(Integer commentId);
}
