package com.lunar.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SenderVo {

    //用户id(主键)
    private Integer userId;
    //用户昵称
    private String userName;

}
