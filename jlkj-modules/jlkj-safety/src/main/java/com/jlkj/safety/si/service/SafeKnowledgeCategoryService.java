package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiKnowledgeCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 知识类别 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeKnowledgeCategoryService extends IService<SafeSiKnowledgeCategory> {

    /**
     * 知识类别-查询
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeKnowledgeCategoryPageList(Map<String, Object> params);

    /**
     * 知识类别-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeKnowledgeCategory(Map<String, Object> params);

    /**
     * 知识类别-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeKnowledgeCategory(Map<String, Object> params);

    /**
     * 知识类别-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeKnowledgeCategory(Map<String, Object> params);

}
