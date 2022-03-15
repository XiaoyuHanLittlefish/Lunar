package com.lunar.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (HasTag)表实体类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("has_tag")
public class HasTag  {
    //博客id(外键)
    private Integer blogId;
    //标签id(外键) 两个属性联合作为本表主码
    private Integer tagId;




}
