package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.SafeSiJobHoistingApproval;

import java.util.Map;

/**
 * <p>
 * 吊装安全作业证-审批记录 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobHoistingApprovalService extends IService<SafeSiJobHoistingApproval> {

    /**
     * 吊装安全作业证-设置审批人
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobHoistingApproval(Map<String, Object> params);

    /**
     * 吊装安全作业证-审批
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobHoistingApproval(Map<String, Object> params);

    /**
     * 吊装安全作业证-确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobHoistingApprovalTime(Map<String, Object> params);

    /**
     * 吊装安全作业证-审批退回
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobHoistingApprovalReturn(Map<String, Object> params);

    /**
     * 吊装安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobHoistingApprovals(Map<String, Object> params);
}
