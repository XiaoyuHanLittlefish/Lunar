package com.lunar.controller;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Comment;
import com.lunar.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    public ResponseResult getBlogCommentList(Integer blogId, Integer pageNumber, Integer pageSize) {
        return commentService.getBlogCommentList(blogId, pageNumber, pageSize);
    }

    @PostMapping
    public ResponseResult createBlogComment(@RequestBody Comment comment) {
        return commentService.createBlogComment(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") Integer commentId) {
        return commentService.deleteComment(commentId);
    }

}
