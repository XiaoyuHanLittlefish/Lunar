package com.lunar.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * (Folder)表实体类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("folder")
public class Folder  {
    //收藏夹id(主键)
    @TableId(type = IdType.AUTO)
    private Integer folderId;

    //收藏夹所属用户id(外键)
    private Integer folderAuthorId;
    //收藏夹名称
    private String folderName;
    //收藏夹创建时间戳
    private Date folderCreateTime;



}
