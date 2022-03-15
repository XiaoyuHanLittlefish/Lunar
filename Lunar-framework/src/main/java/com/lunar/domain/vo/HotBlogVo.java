package com.lunar.domain.vo;

import com.lunar.domain.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 用于展示热门博客
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotBlogVo {
    //博客id(主键)
    private Integer blogId;

    //博客的作者用户id(外键)
    private Integer blogAuthorId;
    //博客的作者用户昵称
    private String blogAuthorName;
    //博客的标题
    private String blogTitle;
    //博客的摘要(150字以内的摘要)
    private String blogDigest;
    //博客的创建时间戳
    private Date blogCreateTime;
    //博客的可见性:0 - 公开/1 - 粉丝可见/2 - 私有
    private Integer blogForm;
    //博客的标签列表
    private List<Tag> blogTags;
}
