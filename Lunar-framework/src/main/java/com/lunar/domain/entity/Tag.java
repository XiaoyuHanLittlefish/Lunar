package com.lunar.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * (Tag)表实体类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tag")
public class Tag  {
    //标签id(主键)
    @TableId(type = IdType.AUTO)
    private Integer tagId;

    //标签的名字
    private String tagContent;



}
