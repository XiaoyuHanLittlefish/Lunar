package com.lunar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Blog;


/**
 * (Blog)表服务接口
 *
 * @author makejava
 * @since 2022-02-23 18:31:19
 */
public interface BlogService extends IService<Blog> {

    ResponseResult getBlogDetail(Integer blogId);

    ResponseResult getHotBlogList(Integer pageNumber, Integer pageSize);

    ResponseResult addNewBlog(Blog blog, String[] tags);

    ResponseResult deleteBlog(Integer blogId);

    ResponseResult updateBlog(Integer blogId, Blog blog);

    ResponseResult likeBlog(Integer blogId);

    ResponseResult cancelLikeBlog(Integer blogId);

    ResponseResult dislikeBlog(Integer blogId);

    ResponseResult cancelDislikeBlog(Integer blogId);

    ResponseResult hasLikeBlog(Integer blogId);

    ResponseResult hasDislikeBlog(Integer blogId);

    ResponseResult collectBlogToFolder(Integer blogId, Integer folderId);

    ResponseResult cancelCollectBlogToFolder(Integer blogId, Integer folderId);
}
