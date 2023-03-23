package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeKnowledgeLibraryFile;
import com.jlkj.safety.si.mapper.SafeKnowledgeLibraryFileMapper;
import com.jlkj.safety.si.service.SafeKnowledgeLibraryFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author su
 * @since 2021-05-21
 */
@Service
public class SafeKnowledgeLibraryFileServiceImpl extends ServiceImpl<SafeKnowledgeLibraryFileMapper, SafeKnowledgeLibraryFile> implements SafeKnowledgeLibraryFileService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeKnowledgeLibraryFile(Map<String, Object> params) {
        SafeKnowledgeLibraryFile safeKnowledgeLibraryFile = new SafeKnowledgeLibraryFile();
        safeKnowledgeLibraryFile.setId(IdUtil.randomUUID());
        safeKnowledgeLibraryFile.setKnowledgeId(params.get("knowledge_id").toString());
        safeKnowledgeLibraryFile.setFileName(params.get("file_name").toString());
        safeKnowledgeLibraryFile.setDiskName(params.get("disk_name").toString());
        safeKnowledgeLibraryFile.setFileSize(Integer.parseInt(params.get("file_size").toString()));
        safeKnowledgeLibraryFile.setFileId(params.get("file_id").toString());

        return save(safeKnowledgeLibraryFile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeKnowledgeLibraryFile(Map<String, Object> params) {
        return removeById(params.get("id").toString());
    }

}
