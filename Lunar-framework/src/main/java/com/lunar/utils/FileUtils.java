package com.lunar.utils;

import com.lunar.domain.vo.FileSaveVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

public class FileUtils {

    public static FileSaveVo saveFile(MultipartFile file){
        FileSaveVo fileSaveVo = new FileSaveVo();
        if (file.isEmpty()){
            fileSaveVo.setMessage("未选择文件");
            return fileSaveVo;
        }
        String filename = new Timestamp(System.currentTimeMillis()).getTime() + file.getOriginalFilename(); //时间戳+获取上传文件原来的名称
        //需要将此处修改为需要的URL
        String filePath = "d://uploadFiles/";
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
            fileSaveVo.setMessage("上传错误");
            return fileSaveVo;
        }

        fileSaveVo.setUrl("http://localhost:9999/" + filename);
        fileSaveVo.setMessage("上传成功");
        return fileSaveVo;
    }
}
