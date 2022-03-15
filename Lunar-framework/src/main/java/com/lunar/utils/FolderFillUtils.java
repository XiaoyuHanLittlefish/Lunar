package com.lunar.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lunar.domain.entity.Blog;
import com.lunar.domain.entity.FolderCollect;
import com.lunar.domain.vo.FolderDetailVo;
import com.lunar.domain.vo.FolderVo;
import com.lunar.domain.vo.HotBlogVo;
import com.lunar.service.*;

import java.util.List;
import java.util.stream.Collectors;

public class FolderFillUtils {

    public static void fillFolderVo(FolderVo folderVo,
                                    UserService userService,
                                    FolderCollectService folderCollectService) {

        String userName = userService.getById(folderVo.getFolderAuthorId()).getUserName();
        folderVo.setFolderAuthorName(userName);

        LambdaQueryWrapper<FolderCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FolderCollect::getFolderId, folderVo.getFolderId());

        List<FolderCollect> folderCollectList = folderCollectService.list(queryWrapper);

        folderVo.setFolderBlogNumber(folderCollectList.size());
    }

    public static void fillFolderVoList(List<FolderVo> folderVoList,
                                        UserService userService,
                                        FolderCollectService folderCollectService) {
        for (FolderVo folderVo : folderVoList) {
            fillFolderVo(folderVo, userService, folderCollectService);
        }
    }

    public static void fillFolderDetailVo(FolderDetailVo folderDetailVo,
                                          UserService userService,
                                          FolderCollectService folderCollectService,
                                          BlogService blogService,
                                          HasTagService hasTagService,
                                          TagService tagService) {
        folderDetailVo.setFolderAuthorName(userService.getById(folderDetailVo.getFolderAuthorId()).getUserName());

        LambdaQueryWrapper<FolderCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FolderCollect::getFolderId, folderDetailVo.getFolderId());

        List<Blog> blogList = folderCollectService.list(queryWrapper).stream()
                .map(folderCollect -> blogService.getById(folderCollect.getBlogId()))
                .collect(Collectors.toList());

        List<HotBlogVo> blogVoList = BeanCopyUtils.copyBeanList(blogList, HotBlogVo.class);

        BlogFillUtils.fillBlogVoList(blogVoList, userService, hasTagService, tagService);
    }
}
