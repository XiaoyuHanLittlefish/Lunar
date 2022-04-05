package com.lunar.service.impl;

import com.lunar.domain.ResponseResult;
import com.lunar.enums.AppHttpCodeEnum;
import com.lunar.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Override
    public ResponseResult upload(MultipartFile file) {
        return saveFile(file);
    }

    private ResponseResult saveFile(MultipartFile file){
        if (file.isEmpty()){
            return ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        String filename = file.getOriginalFilename(); //获取上传文件原来的名称
        String filePath = "/UserFiles/";
        File temp = new File(filePath);
        if (!temp.exists()){
            temp.mkdirs();
        }

        File localFile = new File(filePath+filename);
        try {
            file.transferTo(localFile); //把上传的文件保存至本地
//            System.out.println(file.getOriginalFilename()+" 上传成功");
        }catch (IOException e){
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        return ResponseResult.okResult();
    }
}
