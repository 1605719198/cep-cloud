package com.jlkj.file.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.file.entity.SysFileresource;
import com.jlkj.file.mapper.SysFileresourceMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author zyf
 * @description 针对表【sys_fileresource(文件资源表)】的数据库操作Service实现
 * @createDate 2022-05-24 08:12:21
 */
@Service
public class SysFileresourceServiceImpl extends ServiceImpl<SysFileresourceMapper, SysFileresource>
        implements SysFileresourceService{

    @Value("${file.target-disk}")
    private String targetDisk;

    @Value("${file.save-path}")
    private String savePath;

    @Value("${file.max-size}")
    private long maxSize;

    public Object uploadFiles(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            // 文件名非空校验
            if (fileName.isEmpty()) {
                return AjaxResult.error("文件名不能为空");
            }
            // 大文件判定
            if (file.getSize() > maxSize) {
                return AjaxResult.error("文件过大，请使用大文件传输");
            }
            // 生成新文件名
            String suffixName = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : null;
            String newName = IdUtil.randomUUID() + suffixName;
            // 重命名文件
            File newFile = new File(targetDisk + savePath + DateUtil.today(), newName);
            String newPath = newFile.getPath();
            // 如果该存储路径不存在则新建存储路径
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            // 文件写入
            file.transferTo(newFile);
            // 保存文件信息
            SysFileresource files = new SysFileresource();
            files.setId(IdUtil.randomUUID());
            files.setTargetName(newPath);
            files.setRelativePath(newPath.replace(targetDisk, ""));
            files.setFilePath(newName);
            files.setFileName(fileName);
            files.setSuffix(suffixName == null ? null : suffixName.substring(1).toLowerCase());
            files.setCreateTime(DateUtil.date());
            getBaseMapper().insert(files);
            return AjaxResult.success("上传完成", files);
        } catch (Exception e) {
            log.error("上传协议文件出错", e);
        }
        return AjaxResult.error("上传失败");
    }
}




