package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobFireAnalysis;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 动火安全作业证-动火分析 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeJobFireAnalysisService extends IService<SafeSiJobFireAnalysis> {

    /**
     * 动火安全作业证-新增动火分析
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobFireAnalysis(Map<String, Object> params);

    /**
     * 动火安全作业证-删除动火分析
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobFireAnalysis(Map<String, Object> params);
}
