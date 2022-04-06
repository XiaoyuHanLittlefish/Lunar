package com.lunar.controller;

import com.lunar.domain.ResponseResult;
import com.lunar.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

    @PostMapping("/upload/multiple")
    public ResponseResult multipleUpload(@RequestParam("file") MultipartFile[] files) {
        return fileService.multipleUpload(files);
    }
}
