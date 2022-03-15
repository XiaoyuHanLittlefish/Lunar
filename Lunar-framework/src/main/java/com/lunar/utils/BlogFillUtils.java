package com.lunar.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lunar.domain.entity.HasTag;
import com.lunar.domain.entity.Tag;
import com.lunar.domain.vo.HotBlogVo;
import com.lunar.service.HasTagService;
import com.lunar.service.TagService;
import com.lunar.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class BlogFillUtils {

    public static void fillBlogVo(HotBlogVo blogVo,
                                  UserService userService,
                                  HasTagService hasTagService,
                                  TagService tagService) {

        blogVo.setBlogAuthorName(userService.getById(blogVo.getBlogAuthorId()).getUserName());

        blogVo.setBlogTags(getBlogTags(blogVo.getBlogId(), hasTagService, tagService));

    }

    public static void fillBlogVoList(List<HotBlogVo> blogVoList,
                                      UserService userService,
                                      HasTagService hasTagService,
                                      TagService tagService) {

        for (HotBlogVo blogVo : blogVoList) {
            fillBlogVo(blogVo, userService, hasTagService, tagService);
        }

    }

    /**
     * 根据博客编号得到标签列表
     * @param blogId 博客编号
     * @param hasTagService
     * @param tagService
     * @return 博客所拥有的标签列表
     */
    public static List<Tag> getBlogTags(Integer blogId,
                                        HasTagService hasTagService,
                                        TagService tagService) {

        LambdaQueryWrapper<HasTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HasTag::getBlogId, blogId);
        List<HasTag> hasTagList = hasTagService.list(queryWrapper);

        List<Tag> tags = new ArrayList<>();
        for (HasTag hasTag : hasTagList) {
            tags.add(tagService.getById(hasTag.getTagId()));
        }
        return tags;
    }

}
