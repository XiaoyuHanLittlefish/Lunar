package com.lunar.service.impl;

import com.lunar.domain.ResponseResult;
import com.lunar.domain.vo.FileSaveVo;
import com.lunar.enums.AppHttpCodeEnum;
import com.lunar.service.FileService;
import com.lunar.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Override
    public ResponseResult upload(MultipartFile file) {
        return ResponseResult.okResult(FileUtils.saveFile(file));
    }

    @Override
    public ResponseResult multipleUpload(MultipartFile[] files) {
        List<FileSaveVo> fileSaveVoList = new ArrayList<>();
        for (MultipartFile file : files) {
            fileSaveVoList.add(FileUtils.saveFile(file));
        }
        return ResponseResult.okResult(fileSaveVoList);
    }

}
