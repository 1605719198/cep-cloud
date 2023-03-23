package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobFireApproval;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 动火安全作业证-审批记录 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeJobFireApprovalService extends IService<SafeSiJobFireApproval> {

    /**
     * 动火安全作业证-设置审批人
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobFireApproval(Map<String, Object> params);

    /**
     * 动火安全作业证-审批
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobFireApproval(Map<String, Object> params);

    /**
     * 动火安全作业证-确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobFireApprovalTime(Map<String, Object> params);

    /**
     * 动火安全作业证-审批退回
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobFireApprovalReturn(Map<String, Object> params);

    /**
     * 动火安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobFireApprovals(Map<String, Object> params);
}
