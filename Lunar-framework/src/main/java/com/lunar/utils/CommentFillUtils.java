package com.lunar.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lunar.domain.entity.Comment;
import com.lunar.domain.entity.User;
import com.lunar.domain.vo.CommentVo;
import com.lunar.service.CommentService;
import com.lunar.service.UserService;

import java.util.List;

public class CommentFillUtils {

    public static List<CommentVo> toCommentVoList(List<Comment> commentList,
                                                  CommentService commentService,
                                                  UserService userService) {
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);

        for (CommentVo commentVo : commentVoList) {
            fillCommentVo(commentVo, commentService, userService);
        }

        return commentVoList;
    }

    private static void fillCommentVo(CommentVo commentVo, CommentService commentService,UserService userService) {
        //查找评论作者昵称、头像
        User user = userService.getById(commentVo.getCommentAuthorId());
        commentVo.setCommentAuthorName(user.getUserName());
        commentVo.setCommentAuthorAvatar(user.getUserAvatar());

        //查找commentVo的children 按照时间倒序排序
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getCommentParentId, commentVo.getCommentId());
        queryWrapper.orderByDesc(Comment::getCommentCreateTime);

        List<Comment> commentList = commentService.list(queryWrapper);
        //如果该评论没有子评论 结束填充
        if(commentList.isEmpty()) {
            return;
        }
        //对子评论进行填充
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);

        for (CommentVo vo : commentVoList) {
            CommentFillUtils.fillCommentVo(vo, commentService, userService);
        }

        commentVo.setChildren(commentVoList);
    }
}
