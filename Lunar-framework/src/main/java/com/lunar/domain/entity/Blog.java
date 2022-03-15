package com.lunar.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * (Blog)表实体类
 *
 * @author makejava
 * @since 2022-02-23 18:31:17
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("blog")
public class Blog  {
    //博客id(主键)
    @TableId(type = IdType.AUTO)
    private Integer blogId;

    //博客的作者用户id(外键)
    private Integer blogAuthorId;
    //博客内容的实际存储位置
    private String blogResource;
    //博客的标题
    private String blogTitle;
    //博客的摘要(150字以内的摘要)
    private String blogDigest;
    //博客的创建时间戳
    private Date blogCreateTime;
    //博客被查看的次数
    private Integer blogVisitNumber;
    //博客被点赞?的次数
    private Integer blogLikeNumber;
    //博客被点踩?的次数
    private Integer blogDislikeNumber;
    //博客被收藏的次数
    private Integer blogCollectNumber;
    //博客被分享的次数
    private Integer blogShareNumber;
    //博客的可见性:0 - 公开/1 - 粉丝可见/2 - 私有
    private Integer blogForm;
    //博客的类型:0 - 原创/1 - 转载
    private Integer blogType;



}
