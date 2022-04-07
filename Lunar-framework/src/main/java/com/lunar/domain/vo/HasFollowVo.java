package com.lunar.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HasFollowVo {
    //是否关注过该用户
    private Boolean hasFollow;
}
