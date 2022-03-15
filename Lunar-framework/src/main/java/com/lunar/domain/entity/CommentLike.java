package com.lunar.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * (CommentLike)表实体类
 *
 * @author makejava
 * @since 2022-03-13 12:01:43
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comment_like")
public class CommentLike  {
    //用户id(外键)
    private Integer userId;
    //评论id(外键) /两个属性联合做主键
    private Integer commentId;

    //点赞类型 0 - 点踩/1 - 点赞
    private Integer likeType;
    //点赞评论的时间戳
    private Date commentLikeTime;



}
