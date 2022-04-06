package com.lunar.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileSaveVo {
    //存储路径
    private String url;
    //存储信息
    private String message;
}
