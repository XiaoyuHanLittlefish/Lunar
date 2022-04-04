package com.lunar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunar.domain.ResponseResult;
import com.lunar.domain.entity.Folder;
import com.lunar.domain.entity.FolderCollect;
import com.lunar.domain.vo.FolderDetailVo;
import com.lunar.domain.vo.FolderVo;
import com.lunar.enums.AppHttpCodeEnum;
import com.lunar.mapper.FolderMapper;
import com.lunar.service.*;
import com.lunar.utils.BeanCopyUtils;
import com.lunar.utils.FolderFillUtils;
import com.lunar.utils.UserFillUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * (Folder)表服务实现类
 *
 * @author makejava
 * @since 2022-02-23 18:31:20
 */
@Service("folderService")
public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder> implements FolderService {

    @Autowired
    private UserService userService;

    @Autowired
    private FolderCollectService folderCollectService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private HasTagService hasTagService;

    @Autowired
    private TagService tagService;

    @Override
    public ResponseResult getFolderList(Integer userId, Integer pageNumber, Integer pageSize) {
        // 根据userId查找收藏夹
        LambdaQueryWrapper<Folder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Folder::getFolderAuthorId, userId);
        //分页查找
        Page<Folder> page = new Page(pageNumber, pageSize);
        page(page, queryWrapper);
        //转换成vo
        List<FolderVo> folderVoList = BeanCopyUtils.copyBeanList(page.getRecords(), FolderVo.class);

        FolderFillUtils.fillFolderVoList(folderVoList, userService, folderCollectService);

        return ResponseResult.okResult(folderVoList);
    }

    @Override
    public ResponseResult getFolderDetail(Integer folderId, Integer pageNumber, Integer pageSize) {
        //根据folderId查找收藏夹
        Folder folder = getById(folderId);
        //转换成vo
        FolderDetailVo folderDetailVo = BeanCopyUtils.copyBean(folder, FolderDetailVo.class);

        FolderFillUtils.fillFolderDetailVo(folderDetailVo, userService, folderCollectService, blogService, hasTagService, tagService);

        return ResponseResult.okResult(folderDetailVo);
    }

    @Override
    public ResponseResult deleteFolder(Integer folderId) {
        //TODO: 检查权限
        removeById(folderId);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateFolderName(Integer folderId, String folderName) {
        //TODO: 检查权限

        //根据id查找收藏夹
        Folder folder = getById(folderId);
        //设置名称
        folder.setFolderName(folderName);
        //存储
        updateById(folder);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult copyFolder(Integer folderId) {
        //查找被复制收藏夹
        Folder copyFolder = getById(folderId);
        //得到userId
        Integer userId = UserFillUtils.getUserIdFromToken();
        //新建收藏夹
        Folder folder = new Folder();
        folder.setFolderAuthorId(userId);
        folder.setFolderName(copyFolder.getFolderName() + "(copy)");
        folder.setFolderCreateTime(new Timestamp(System.currentTimeMillis()));
        //保存
        save(folder);
        //复制收藏博客
        LambdaQueryWrapper<FolderCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FolderCollect::getFolderId, folderId);

        List<FolderCollect> folderCollectList = folderCollectService.list(queryWrapper);

        for (FolderCollect folderCollect : folderCollectList) {
            folderCollect.setFolderId(folder.getFolderId());
            folderCollect.setCollectTime(new Timestamp(System.currentTimeMillis()));

            folderCollectService.save(folderCollect);
        }

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult createFolder(Folder folder) {
        //得到userId
        Integer userId = UserFillUtils.getUserIdFromToken();
        //设置userId
        folder.setFolderAuthorId(userId);
        //存储
        save(folder);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult collectBlogToFolder(Integer blogId, Integer folderId) {
        if(isCollected(blogId, folderId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH.getCode(), "已经收藏过了");
        }
        FolderCollect folderCollect = new FolderCollect(folderId, blogId, new Timestamp(System.currentTimeMillis()));
        folderCollectService.save(folderCollect);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult cancelCollectBlogToFolder(Integer blogId, Integer folderId) {
        if(!isCollected(blogId, folderId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH.getCode(), "还没有收藏过");
        }
        LambdaQueryWrapper<FolderCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FolderCollect::getFolderId, folderId);
        queryWrapper.eq(FolderCollect::getBlogId, blogId);
        folderCollectService.remove(queryWrapper);
        return ResponseResult.okResult();
    }

    private Boolean isCollected(Integer blogId, Integer folderId) {
        LambdaQueryWrapper<FolderCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FolderCollect::getFolderId, folderId);
        queryWrapper.eq(FolderCollect::getBlogId, blogId);

        FolderCollect folderCollect = folderCollectService.getOne(queryWrapper);
        return folderCollect != null;
    }
}
