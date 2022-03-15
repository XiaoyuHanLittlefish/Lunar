package com.lunar.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * (BlogLike)表实体类
 *
 * @author makejava
 * @since 2022-03-13 12:01:29
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("blog_like")
public class BlogLike  {
    //用户id(外键)
    private Integer userId;
    //博客id(外键) /两个属性联合做主键
    private Integer blogId;

    //点赞类型 0 - 点踩/1 - 点赞
    private Integer likeType;
    //点赞博客的时间戳
    private Date blogLikeTime;



}
