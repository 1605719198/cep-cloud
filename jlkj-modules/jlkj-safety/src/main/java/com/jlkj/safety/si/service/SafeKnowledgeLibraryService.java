package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiKnowledgeLibrary;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 知识库 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeKnowledgeLibraryService extends IService<SafeSiKnowledgeLibrary> {

    /**
     * 知识库-查询列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeKnowledgeLibraryPageList(Map<String, Object> params);

    /**
     * 知识库-附件列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeKnowledgeLibraryFileList(Map<String, Object> params);

    /**
     * 知识库-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeKnowledgeLibrary(Map<String, Object> params);

    /**
     * 知识库-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeKnowledgeLibrary(Map<String, Object> params);

    /**
     * 知识库-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeKnowledgeLibrary(Map<String, Object> params);

    /**
     * 知识库-删除附件
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeKnowledgeLibraryFiles(Map<String, Object> params);

}
