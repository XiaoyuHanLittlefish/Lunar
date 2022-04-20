package com.lunar.controller.admin;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Blog;
import com.lunar.domain.vo.NewBlogVo;
import com.lunar.service.UserFollowService;
import com.lunar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseResult adminGetUserList() {
        return userService.adminGetUserList();
    }

    @DeleteMapping("/{userId}")
    public ResponseResult adminDeleteUser(@PathVariable("userId") Integer userId) {
        return userService.adminDeleteUser(userId);
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
