package com.lunar.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * (Comment)表实体类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comment")
public class Comment  {
    //评论id(主键)
    @TableId(type = IdType.AUTO)
    private Integer commentId;

    //评论的所属者用户id(外键)
    private Integer commentAuthorId;
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



}
