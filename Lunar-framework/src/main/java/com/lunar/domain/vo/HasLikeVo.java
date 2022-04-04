package com.lunar.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HasLikeVo {
    //是否点赞过博客/评论
    private Boolean hasLike;
}
