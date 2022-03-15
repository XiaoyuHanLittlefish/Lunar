package com.lunar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.constants.SystemConstants;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Blog;
import com.lunar.domain.vo.BlogDetailVo;
import com.lunar.domain.vo.HotBlogVo;
import com.lunar.mapper.BlogMapper;
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

/**
 * (Blog)表服务实现类
 *
 * @author makejava
 * @since 2022-02-23 18:31:19
 */
@Service("blogService")
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private UserService userService;

    @Autowired
    private HasTagService hasTagService;

    @Autowired
    private TagService tagService;

    @Override
    public ResponseResult getBlogDetail(Integer blogId) {
        //TODO 访问后增加1被查看次数
        //根据blogId找到博客
        Blog blog = getById(blogId);
        //封装成vo
        BlogDetailVo blogDetailVo = BeanCopyUtils.copyBean(blog, BlogDetailVo.class);
        //根据blogAuthorId查询作者昵称
        blogDetailVo.setBlogAuthorName(userService.getById(blogDetailVo.getBlogAuthorId()).getUserName());
        //根据blogId查询标签列表

        blogDetailVo.setBlogTags(BlogFillUtils.getBlogTags(blogId, hasTagService, tagService));

        return ResponseResult.okResult(blogDetailVo);
    }

    @Override
    public ResponseResult getHotBlogList() {
        //查询所有公开博客，按浏览量降序排列
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getBlogForm, SystemConstants.BLOG_FORM_PUBLIC);
        queryWrapper.orderByDesc(Blog::getBlogVisitNumber);
        //分页查询
        Page<Blog> page = new Page(SystemConstants.HOT_BLOG_PAGE_NUMBER, SystemConstants.HOT_BLOG_PAGE_SIZE);
        page(page, queryWrapper);
        //返回List并且封装成vo
        List<Blog> blogList = page.getRecords();
        List<HotBlogVo> hotBlogVoList = BeanCopyUtils.copyBeanList(blogList, HotBlogVo.class);

        for (HotBlogVo hotBlogVo : hotBlogVoList) {
            //根据blogAuthorId查询作者昵称
            hotBlogVo.setBlogAuthorName(userService.getById(hotBlogVo.getBlogAuthorId()).getUserName());
            //根据blogId查询标签列表
            hotBlogVo.setBlogTags(BlogFillUtils.getBlogTags(hotBlogVo.getBlogId(), hasTagService, tagService));
        }

        return ResponseResult.okResult(hotBlogVoList);
    }

    @Override
    public ResponseResult addNewBlog(Blog blog) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();
        // TODO: 检查blogTitle blogResource blogForm blogType非空
        // TODO: 填充blogDigest blogCreateTime blogVisitNumber blogLikeNumber blogDislikeNumber blogCollectNumber blogShareNumber
        //设置blog的blogAuthorId为userId
        blog.setBlogAuthorId(userId);
        //保存blog
        save(blog);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteBlog(Integer blogId) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();
        // TODO: 判断用户是否是否有权限

        removeById(blogId);

        return ResponseResult.okResult();
    }


}
