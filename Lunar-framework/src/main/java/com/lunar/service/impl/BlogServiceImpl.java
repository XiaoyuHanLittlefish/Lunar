package com.lunar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.constants.SystemConstants;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.*;
import com.lunar.domain.vo.*;
import com.lunar.enums.AppHttpCodeEnum;
import com.lunar.mapper.BlogMapper;
import com.lunar.service.*;
import com.lunar.utils.BeanCopyUtils;
import com.lunar.utils.BlogFillUtils;
import com.lunar.utils.UserFillUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private BlogLikeService blogLikeService;

    @Autowired
    private FolderService folderService;

    @Override
    public ResponseResult getBlogDetail(Integer blogId) {
        //根据blogId找到博客
        Blog blog = getById(blogId);
        //访问后增加1被查看次数 并保存到数据库
        blog.setBlogVisitNumber(blog.getBlogVisitNumber() + 1);
        updateById(blog);
        //封装成vo
        BlogDetailVo blogDetailVo = BeanCopyUtils.copyBean(blog, BlogDetailVo.class);
        //根据blogAuthorId查询作者昵称
        blogDetailVo.setBlogAuthorName(userService.getById(blogDetailVo.getBlogAuthorId()).getUserName());
        //根据blogId查询标签列表
        blogDetailVo.setBlogTags(BlogFillUtils.getBlogTags(blogId, hasTagService, tagService));

        return ResponseResult.okResult(blogDetailVo);
    }

    @Override
    public ResponseResult getHotBlogList(Integer pageNumber, Integer pageSize) {
        //查询所有公开博客，按浏览量降序排列
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getBlogForm, SystemConstants.BLOG_FORM_PUBLIC);
        queryWrapper.orderByDesc(Blog::getBlogVisitNumber);
        //分页查询
        if(Objects.isNull(pageNumber))
            pageNumber = SystemConstants.HOT_BLOG_PAGE_NUMBER;
        if(Objects.isNull(pageSize))
            pageSize = SystemConstants.HOT_BLOG_PAGE_SIZE;
        Page<Blog> page = new Page(pageNumber, pageSize);
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

        //转换成PageVo
        PageVo pageVo = new PageVo(hotBlogVoList, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addNewBlog(NewBlogVo blog) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();

        //如果没有找到userId 返回需要登陆
        if(Objects.isNull(userId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        Blog newBlog = BeanCopyUtils.copyBean(blog, Blog.class);
        //设置blog的blogAuthorId为userId
        newBlog.setBlogAuthorId(userId);
        //保存blog
        save(newBlog);

        //为已经存在的tag增加与该博客的关系，创建不存在的tag
        for (String tagContent : blog.getTags()) {
            if(Objects.isNull(tagContent))
                break;
            LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(Tag::getTagContent, tagContent);

            Tag tag = tagService.getOne(queryWrapper);

            HasTag hasTag = new HasTag();
            hasTag.setBlogId(newBlog.getBlogId());

            if (Objects.isNull(tag)) {
                tag = new Tag();
                tag.setTagContent(tagContent);
                tagService.save(tag);
            }

            hasTag.setTagId(tag.getTagId());
            hasTagService.save(hasTag);
        }

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteBlog(Integer blogId) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();
        if(Objects.isNull(userId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        // 判断用户是否是否有权限
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getBlogId, blogId);
        queryWrapper.eq(Blog::getBlogAuthorId, userId);
        Blog blog = getOne(queryWrapper);
        if(Objects.isNull(blog)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH.getCode(), AppHttpCodeEnum.NO_OPERATOR_AUTH.getMsg());
        }
        removeById(blogId);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateBlog(Integer blogId, Blog blog) {

        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();

        //如果没有找到userId 返回需要登陆
        if(Objects.isNull(userId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), AppHttpCodeEnum.NEED_LOGIN.getMsg());
        }

        updateById(blog);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult likeBlog(Integer blogId) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();

        //如果没有找到userId 返回需要登陆
        if(Objects.isNull(userId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), AppHttpCodeEnum.NEED_LOGIN.getMsg());
        }

        //判断博客是否存在
        Blog blog = getById(blogId);
        if(Objects.isNull(blog)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "博客未找到");
        }

        //判断是否已经点赞过了
        if(isLikeBlog(userId, blogId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "已经点赞过了");
        }

        blog.setBlogLikeNumber(blog.getBlogLikeNumber() + 1);
        BlogLike blogLike = new BlogLike(userId, blogId, 1, new Timestamp(System.currentTimeMillis()));

        updateById(blog);
        blogLikeService.save(blogLike);


        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult cancelLikeBlog(Integer blogId) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();

        //如果没有找到userId 返回需要登陆
        if(Objects.isNull(userId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), AppHttpCodeEnum.NEED_LOGIN.getMsg());
        }

        //判断博客是否存在
        Blog blog = getById(blogId);
        if(Objects.isNull(blog)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "博客未找到");
        }

        //判断是否已经点赞过了
        if(!isLikeBlog(userId, blogId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "还没有点赞博客");
        }

        blog.setBlogLikeNumber(blog.getBlogLikeNumber() - 1);
        LambdaQueryWrapper<BlogLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogLike::getUserId, userId);
        queryWrapper.eq(BlogLike::getBlogId, blogId);
        queryWrapper.eq(BlogLike::getLikeType, 1);

        blogLikeService.remove(queryWrapper);
        updateById(blog);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult dislikeBlog(Integer blogId) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();

        //如果没有找到userId 返回需要登陆
        if(Objects.isNull(userId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), AppHttpCodeEnum.NEED_LOGIN.getMsg());
        }

        //判断博客是否存在
        Blog blog = getById(blogId);
        if(Objects.isNull(blog)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "博客未找到");
        }

        //判断是否已经点踩过了
        if(isDislikeBlog(userId, blogId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "已经点踩过了");
        }

        blog.setBlogDislikeNumber(blog.getBlogDislikeNumber() + 1);
        BlogLike blogLike = new BlogLike(userId, blogId, 0, new Timestamp(System.currentTimeMillis()));

        updateById(blog);
        blogLikeService.save(blogLike);


        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult cancelDislikeBlog(Integer blogId) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();

        //如果没有找到userId 返回需要登陆
        if(Objects.isNull(userId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), AppHttpCodeEnum.NEED_LOGIN.getMsg());
        }

        //判断博客是否存在
        Blog blog = getById(blogId);
        if(Objects.isNull(blog)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "博客未找到");
        }

        //判断是否已经点踩过了
        if(!isLikeBlog(userId, blogId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "还没有点踩博客");
        }

        blog.setBlogDislikeNumber(blog.getBlogDislikeNumber() - 1);
        LambdaQueryWrapper<BlogLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogLike::getUserId, userId);
        queryWrapper.eq(BlogLike::getBlogId, blogId);
        queryWrapper.eq(BlogLike::getLikeType, 0);

        blogLikeService.remove(queryWrapper);
        updateById(blog);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult hasLikeBlog(Integer blogId) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();
        HasLikeVo hasLikeVo = new HasLikeVo();
        //如果没有找到userId 返回false
        if(Objects.isNull(userId)) {
            hasLikeVo.setHasLike(false);
        }else {
            hasLikeVo.setHasLike(isLikeBlog(userId, blogId));
        }

        return ResponseResult.okResult(hasLikeVo);
    }

    @Override
    public ResponseResult hasDislikeBlog(Integer blogId) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();
        HasDislikeVo hasDislikeVo = new HasDislikeVo();
        //如果没有找到userId 返回false
        if(Objects.isNull(userId)) {
            hasDislikeVo.setHasDislike(false);
        }else {
            hasDislikeVo.setHasDislike(isDislikeBlog(userId, blogId));
        }

        return ResponseResult.okResult(hasDislikeVo);
    }

    @Override
    public ResponseResult collectBlogToFolder(Integer blogId, Integer folderId) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();
        //如果没有找到userId 返回需要登陆
        if(Objects.isNull(userId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), AppHttpCodeEnum.NEED_LOGIN.getMsg());
        }
        //查询是否有folderId收藏夹
        LambdaQueryWrapper<Folder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Folder::getFolderId, folderId);
        queryWrapper.eq(Folder::getFolderAuthorId, userId);
        Folder folder = folderService.getOne(queryWrapper);
        //如果没有找到，返回未找到收藏夹
        if(Objects.isNull(folder)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH.getCode(), "未找到收藏夹");
        }

        return folderService.collectBlogToFolder(blogId, folderId);
    }

    @Override
    public ResponseResult cancelCollectBlogToFolder(Integer blogId, Integer folderId) {
        //获取token中的userId
        Integer userId = UserFillUtils.getUserIdFromToken();
        //如果没有找到userId 返回需要登陆
        if(Objects.isNull(userId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), AppHttpCodeEnum.NEED_LOGIN.getMsg());
        }
        //查询是否有folderId收藏夹
        LambdaQueryWrapper<Folder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Folder::getFolderId, folderId);
        queryWrapper.eq(Folder::getFolderAuthorId, userId);
        Folder folder = folderService.getOne(queryWrapper);
        //如果没有找到，返回未找到收藏夹
        if(Objects.isNull(folder)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH.getCode(), "未找到收藏夹");
        }

        return folderService.cancelCollectBlogToFolder(blogId, folderId);
    }

    private Boolean isLikeBlog(Integer userId, Integer blogId) {
        LambdaQueryWrapper<BlogLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogLike::getUserId, userId);
        queryWrapper.eq(BlogLike::getBlogId, blogId);
        queryWrapper.eq(BlogLike::getLikeType, 1);

        List<BlogLike> blogLikeList = blogLikeService.list(queryWrapper);

        return !blogLikeList.isEmpty();
    }

    private Boolean isDislikeBlog(Integer userId, Integer blogId) {
        LambdaQueryWrapper<BlogLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogLike::getUserId, userId);
        queryWrapper.eq(BlogLike::getBlogId, blogId);
        queryWrapper.eq(BlogLike::getLikeType, 0);

        List<BlogLike> blogLikeList = blogLikeService.list(queryWrapper);

        return !blogLikeList.isEmpty();
    }

}
