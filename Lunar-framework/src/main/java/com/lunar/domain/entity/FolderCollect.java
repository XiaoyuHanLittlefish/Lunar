package com.lunar.domain.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (FolderCollect)表实体类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("folder_collect")
public class FolderCollect  {
    //收藏夹id(外键)
    private Integer folderId;
    //博客id(外键) /两个属性联合做主键
    private Integer blogId;

    //收藏博客到该收藏夹的时间戳
    private Date collectTime;



}
