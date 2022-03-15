package com.lunar.controller;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Folder;
import com.lunar.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/folder")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @GetMapping("/list")
    public ResponseResult getFolderList(Integer userId, Integer pageNumber, Integer pageSize) {
        return folderService.getFolderList(userId, pageNumber, pageSize);
    }

    @GetMapping("/{folderId}")
    public ResponseResult getFolderDetail(@PathVariable("folderId") Integer folderId, Integer pageNumber, Integer pageSize) {
        return folderService.getFolderDetail(folderId, pageNumber, pageSize);
    }

    @DeleteMapping("/{folderId}")
    public ResponseResult deleteFolder(@PathVariable("folderId") Integer folderId) {
        return folderService.deleteFolder(folderId);
    }

    @PutMapping("/{folderId}/name")
    public ResponseResult updateFolderName(@PathVariable("folderId") Integer folderId, String folderName) {
        return folderService.updateFolderName(folderId, folderName);
    }

    @PostMapping("/{folderId}/copy")
    public ResponseResult copyFolder(@PathVariable("folderId") Integer folderId) {
        return folderService.copyFolder(folderId);
    }

    @PostMapping
    public ResponseResult createFolder(@RequestBody Folder folder) {
        return folderService.createFolder(folder);
    }

}
