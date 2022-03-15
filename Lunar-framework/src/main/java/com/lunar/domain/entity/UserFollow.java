package com.lunar.domain.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (UserFollow)表实体类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_follow")
public class UserFollow  {
    //用户的id(外键)
    private Integer authorId;
    //被关注的用户id(外键) /例: author_id 关注了 to_id
    private Integer toId;

    //关注的时间戳
    private Date followTime;



}
