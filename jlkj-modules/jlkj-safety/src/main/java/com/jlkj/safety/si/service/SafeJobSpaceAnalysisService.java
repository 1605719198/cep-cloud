package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobSpaceAnalysis;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 受限空间安全作业证-分析 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobSpaceAnalysisService extends IService<SafeJobSpaceAnalysis> {

    /**
     * 受限空间安全作业证-新增分析
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobSpaceAnalysis(Map<String, Object> params);

    /**
     * 受限空间安全作业证-删除分析
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobSpaceAnalysis(Map<String, Object> params);
}
