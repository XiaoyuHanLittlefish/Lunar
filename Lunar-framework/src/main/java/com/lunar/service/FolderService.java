package com.lunar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Folder;


/**
 * (Folder)表服务接口
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
public interface FolderService extends IService<Folder> {

    ResponseResult getFolderList(Integer userId, Integer pageNumber, Integer pageSize);

    ResponseResult getFolderDetail(Integer folderId, Integer pageNumber, Integer pageSize);

    ResponseResult deleteFolder(Integer folderId);

    ResponseResult updateFolderName(Integer folderId, String folderName);

    ResponseResult copyFolder(Integer folderId);

    ResponseResult createFolder(Folder folder);
}
