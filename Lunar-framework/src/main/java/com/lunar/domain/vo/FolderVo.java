package com.lunar.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FolderVo {

    //收藏夹id(主键)
    private Integer folderId;

    //收藏夹所属用户id(外键)
    private Integer folderAuthorId;
    //收藏夹所属用户昵称
    private String folderAuthorName;
    //收藏夹名称
    private String folderName;
    //收藏夹创建时间戳
    private Date folderCreateTime;
    //收藏夹所包含博客数量
    private Integer folderBlogNumber;

}
