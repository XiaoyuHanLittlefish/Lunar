package com.lunar.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.domain.entity.FolderCollect;
import com.lunar.mapper.FolderCollectMapper;
import com.lunar.service.FolderCollectService;
import org.springframework.stereotype.Service;

/**
 * (FolderCollect)表服务实现类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@Service("folderCollectService")
public class FolderCollectServiceImpl extends ServiceImpl<FolderCollectMapper, FolderCollect> implements FolderCollectService {

}
