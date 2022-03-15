package com.lunar.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    //评论id(主键)
    private Integer commentId;

    //评论的所属者用户id(外键)
    private Integer commentAuthorId;
    //评论的所属者用户昵称
    private String commentAuthorName;
    //评论的所属博客id(外键)
    private Integer commentBlogId;
    //评论的具体内容
    private String commentContent;
    //评论的创建时间戳
    private Date commentCreateTime;
    //评论被点赞的数量
    private Integer commentLikeNumber;
    //评论被点踩的数量
    private Integer commentDislikeNumber;
    //该条评论的父级评论id(本表外键)
    private Integer commentParentId;
    //下一级子评论
    private List<CommentVo> children;
}
