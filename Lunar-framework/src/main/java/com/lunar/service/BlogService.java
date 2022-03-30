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

    ResponseResult getHotBlogList();

    ResponseResult addNewBlog(Blog blog);

    ResponseResult deleteBlog(Integer blogId);

    ResponseResult updateBlog(Integer blogId, Blog blog);

    ResponseResult likeBlog(Integer blogId);

    ResponseResult cancelLikeBlog(Integer blogId);

    ResponseResult disikeBlog(Integer blogId);

    ResponseResult cancelDislikeBlog(Integer blogId);
}
