package com.lunar.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HasDislikeVo {
    //是否点踩过博客/评论
    private Boolean hasDislike;
}
