package com.lunar.controller;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Blog;
import com.lunar.domain.vo.NewBlogVo;
import com.lunar.service.BlogService;
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
    public ResponseResult getHotBlogList(Integer pageNumber, Integer pageSize) {
        return blogService.getHotBlogList(pageNumber, pageSize);
    }

    @PostMapping
    public ResponseResult addNewBlog(@RequestBody NewBlogVo blog) {
        return blogService.addNewBlog(blog);
    }

    @PutMapping("/{blogId}")
    public ResponseResult updateBlog(@PathVariable("blogId") Integer blogId,
                                     @RequestBody Blog blog) {
        return blogService.updateBlog(blogId, blog);
    }

    @DeleteMapping("/{blogId}")
    public ResponseResult deleteBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.deleteBlog(blogId);
    }

    @PostMapping("/{blogId}/like")
    public ResponseResult likeBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.likeBlog(blogId);
    }

    @DeleteMapping("/{blogId}/like")
    public ResponseResult cancelLikeBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.cancelLikeBlog(blogId);
    }

    @PostMapping("/{blogId}/dislike")
    public ResponseResult dislikeBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.dislikeBlog(blogId);
    }

    @DeleteMapping("/{blogId}/dislike")
    public ResponseResult cancelDislikeBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.cancelDislikeBlog(blogId);
    }

    @GetMapping("/{blogId}/like")
    public ResponseResult hasLikeBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.hasLikeBlog(blogId);
    }

    @GetMapping("/{blogId}/dislike")
    public ResponseResult hasDislikeBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.hasDislikeBlog(blogId);
    }

    @PostMapping("/{blogId}/collect")
    public ResponseResult collectBlogToFolder(@PathVariable("blogId") Integer blogId,
                                              Integer folderId) {
        return blogService.collectBlogToFolder(blogId, folderId);
    }

    @DeleteMapping("/{blogId}/collect")
    public ResponseResult cancelCollectBlogToFolder(@PathVariable("blogId") Integer blogId,
                                                    Integer folderId) {
        return blogService.cancelCollectBlogToFolder(blogId, folderId);
    }

    @GetMapping("/{blogId}/collect")
    public ResponseResult hasCollectBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.hasCollectBlog(blogId);
    }
}
