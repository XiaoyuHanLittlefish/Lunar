package com.lunar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Tag;


/**
 * (Tag)表服务接口
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
public interface TagService extends IService<Tag> {

    ResponseResult getBlogHasTag(Integer tagId, Integer pageNumber);

    ResponseResult getTagList();

    ResponseResult addNewTag(String tagContent);
}
