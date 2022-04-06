package com.lunar.service;

import com.lunar.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ResponseResult upload(MultipartFile file);

    ResponseResult multipleUpload(MultipartFile[] files);
}
