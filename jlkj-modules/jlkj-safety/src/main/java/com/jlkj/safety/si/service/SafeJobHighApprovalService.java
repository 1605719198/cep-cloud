package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobHighApproval;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 高处安全作业证-审批记录 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobHighApprovalService extends IService<SafeSiJobHighApproval> {

    /**
     * 高处安全作业证-设置审批人
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobHighApproval(Map<String, Object> params);

    /**
     * 高处安全作业证-审批
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobHighApproval(Map<String, Object> params);

    /**
     * 高处安全作业证-确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobHighApprovalTime(Map<String, Object> params);

    /**
     * 高处安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobHighApprovals(Map<String, Object> params);

    /**
     * 高处安全作业证-审批退回
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobHighApprovalReturn(Map<String, Object> params);
}
