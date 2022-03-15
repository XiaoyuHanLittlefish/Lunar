package com.lunar.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.domain.entity.HasTag;
import com.lunar.mapper.HasTagMapper;
import com.lunar.service.HasTagService;
import org.springframework.stereotype.Service;

/**
 * (HasTag)表服务实现类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@Service("hasTagService")
public class HasTagServiceImpl extends ServiceImpl<HasTagMapper, HasTag> implements HasTagService {

}
