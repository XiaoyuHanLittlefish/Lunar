package com.lunar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.constants.SystemConstants;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Blog;
import com.lunar.domain.entity.HasTag;
import com.lunar.domain.entity.Tag;
import com.lunar.domain.vo.TagBlogVo;
import com.lunar.enums.AppHttpCodeEnum;
import com.lunar.mapper.TagMapper;
import com.lunar.service.BlogService;
import com.lunar.service.HasTagService;
import com.lunar.service.TagService;
import com.lunar.service.UserService;
import com.lunar.utils.BeanCopyUtils;
import com.lunar.utils.BlogFillUtils;
import com.lunar.utils.UserFillUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private BlogService blogService;

    @Autowired
    private HasTagService hasTagService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult getBlogHasTag(Integer tagId, Integer pageNumber) {

        if (Objects.isNull(pageNumber)) {
            pageNumber = SystemConstants.TAG_BLOG_PAGE_NUMBER_DEFAULT;
        }

        //根据tagId查找所有博客的blogId
        LambdaQueryWrapper<HasTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HasTag::getTagId, tagId);
        //分页查询
        Page<HasTag> page = new Page<>(pageNumber, SystemConstants.TAG_BLOG_PAGE_SIZE);
        hasTagService.page(page, queryWrapper);
        List<HasTag> hasTagList = page.getRecords();
        //将查询结果转换成vo 并且只保留公开的博客
        List<Blog> blogList = hasTagList.stream()
                .map(hasTag -> blogService.getById(hasTag.getBlogId()))
                .filter(blog -> blog.getBlogForm().equals(SystemConstants.BLOG_FORM_PUBLIC))
                .collect(Collectors.toList());

        List<TagBlogVo> tagBlogVoList = BeanCopyUtils.copyBeanList(blogList, TagBlogVo.class);

        for (TagBlogVo tagBlogVo : tagBlogVoList) {
            //根据blogAuthorId查询作者昵称
            tagBlogVo.setBlogAuthorName(userService.getById(tagBlogVo.getBlogAuthorId()).getUserName());
            //根据blogId查询标签列表
            tagBlogVo.setBlogTags(BlogFillUtils.getBlogTags(tagBlogVo.getBlogId(), hasTagService, tagService));
        }

        return ResponseResult.okResult(tagBlogVoList);
    }

    @Override
    public ResponseResult getTagList() {
        //查询所有标签
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tagList = list(queryWrapper);

        return ResponseResult.okResult(tagList);
    }

    @Override
    public ResponseResult addNewTag(String tagContent) {
        Integer userId = UserFillUtils.getUserIdFromToken();

        if(userId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        Tag tag = new Tag();
        save(tag);
        return ResponseResult.okResult();
    }

}
