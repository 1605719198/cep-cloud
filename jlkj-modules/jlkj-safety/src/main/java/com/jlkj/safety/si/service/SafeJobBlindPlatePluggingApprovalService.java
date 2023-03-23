package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobBlindPlatePluggingApproval;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 盲板抽堵安全作业证-审批记录 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobBlindPlatePluggingApprovalService extends IService<SafeJobBlindPlatePluggingApproval> {

    /**
     * 盲板抽堵安全作业证-设置审批人
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobBlindPlatePluggingApproval(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-审批
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobBlindPlatePluggingApproval(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobBlindPlatePluggingApprovalTime(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-审批退回
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobBlindPlatePluggingApprovalReturn(Map<String, Object> params);
}
