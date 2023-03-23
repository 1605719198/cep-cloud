package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.Empty;

import java.util.Map;

/**
 * <p>
 * 公共-树型列表 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface CommonTreeService extends IService<Empty> {

    /**
     * 施工人员树
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeConstructionUnitPersonTree(Map<String, Object> params);

    /**
     * 施工单位树
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeConstructionUnitTree(Map<String, Object> params);

    /**
     * 知识类别树
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeKnowledgeCategoryTree(Map<String, Object> params);
}
