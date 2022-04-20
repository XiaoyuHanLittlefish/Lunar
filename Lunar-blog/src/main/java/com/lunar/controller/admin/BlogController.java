package com.lunar.controller.admin;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Blog;
import com.lunar.domain.vo.NewBlogVo;
import com.lunar.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseResult adminGetBlogList() {
        return blogService.adminGetBlogList();
    }

    @DeleteMapping("/{blogId}")
    public ResponseResult adminDeleteBlog(@PathVariable("blogId") Integer blogId) {
        return blogService.adminDeleteBlog(blogId);
    }

    @PutMapping("/{blogId}")
    public ResponseResult adminUpdateBlog(@PathVariable("blogId") Integer blogId,
                                          @RequestBody Blog blog) {
        return blogService.adminUpdateBlog(blogId, blog);
    }

    @PostMapping
    public ResponseResult adminAddNewBlog(@RequestBody NewBlogVo blog) {
        return blogService.addNewBlog(blog);
    }
}
