package com.lunar.controller;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Blog;
import com.lunar.service.BlogService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/{blogId}")
    public ResponseResult getBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.getBlogDetail(blogId);
    }

    @GetMapping("/hot")
    public ResponseResult getHotBlogList() {
        return blogService.getHotBlogList();
    }

    @PostMapping
    public ResponseResult addNewBlog(Blog blog) {
        return blogService.addNewBlog(blog);
    }

    @DeleteMapping("/{blogId}")
    public ResponseResult deleteBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.deleteBlog(blogId);
    }
}
