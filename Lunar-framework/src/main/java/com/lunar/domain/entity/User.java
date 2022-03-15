package com.lunar.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User  {
    //用户id(主键)
    @TableId(type = IdType.AUTO)
    private Integer userId;

    //用户手机号
    private String userAccount;
    //用户账号密码
    private String userPassword;
    //用户昵称
    private String userName;
    //用户出生时间
    private Date userBirthday;
    //用户个性签名
    private String userSignature;
    //用户关注的人数
    private Integer userFollowNumber;
    //用户的粉丝数(关注该用户的人数)
    private Integer userFansNumber;
    //用户的文章数
    private Integer userArticleNumber;
    //用户类别(权限) 0 - 管理员/1 - 普通注册用户 /2 - 游客
    private Integer userLimit;
    //用户简介
    private String userProfile;
    //用户所在地址
    private String userArea;



}
