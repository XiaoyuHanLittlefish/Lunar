package com.lunar.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    //用户id(主键)
    private Integer userId;
    //用户昵称
    private String userName;
    //用户头像路径
    private String userAvatar;
    //用户关注的人数
    private Integer userFollowNumber;
    //用户的粉丝数(关注该用户的人数)
    private Integer userFansNumber;
    //用户个性签名
    private String userSignature;
    //用户权限
    private Integer userLimit;
}
