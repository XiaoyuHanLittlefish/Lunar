package com.lunar.controller;

import com.lunar.domain.ResponseResult;
import com.lunar.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/{tagId}/blog")
    public ResponseResult getBlogHasTag(@PathVariable("tagId") Integer tagId,
                                        Integer pageNumber) {
        return tagService.getBlogHasTag(tagId, pageNumber);
    }

    @GetMapping
    public ResponseResult getTagList() {
        return tagService.getTagList();
    }

    @PostMapping
    public ResponseResult addNewTag(String tagContent) {
        return tagService.addNewTag(tagContent);
    }

}
