package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobSpaceApproval;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 受限空间安全作业证-审批记录 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobSpaceApprovalService extends IService<SafeSiJobSpaceApproval> {

    /**
     * 受限空间安全作业证-设置审批人
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobSpaceApproval(Map<String, Object> params);

    /**
     * 受限空间安全作业证-审批
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobSpaceApproval(Map<String, Object> params);

    /**
     * 受限空间安全作业证-确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobSpaceApprovalTime(Map<String, Object> params);

    /**
     * 受限空间安全作业证-审批退回
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobSpaceApprovalReturn(Map<String, Object> params);

    /**
     * 受限空间安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobSpaceApprovals(Map<String, Object> params);
}
