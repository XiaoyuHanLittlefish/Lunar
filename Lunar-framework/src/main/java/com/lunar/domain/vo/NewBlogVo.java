package com.lunar.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBlogVo {
    //博客id(主键)
    private Integer blogId;

    //博客的作者用户id(外键)
    private Integer blogAuthorId;
    //博客内容
    private String blogContent;
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

    private String[] tags;

}
