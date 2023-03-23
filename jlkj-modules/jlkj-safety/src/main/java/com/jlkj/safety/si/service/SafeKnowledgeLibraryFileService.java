package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeKnowledgeLibraryFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author su
 * @since 2021-05-21
 */
public interface SafeKnowledgeLibraryFileService extends IService<SafeKnowledgeLibraryFile> {

    /**
     * 知识库附件-新增
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeKnowledgeLibraryFile(Map<String, Object> params);

    /**
     * 知识库附件-删除
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeKnowledgeLibraryFile(Map<String, Object> params);
}
