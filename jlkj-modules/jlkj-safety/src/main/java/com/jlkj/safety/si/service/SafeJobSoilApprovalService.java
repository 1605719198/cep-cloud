package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobSoilApproval;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 动土安全作业证-审批记录 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobSoilApprovalService extends IService<SafeJobSoilApproval> {

    /**
     * 动土安全作业证-设置审批人
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobSoilApproval(Map<String, Object> params);

    /**
     * 动土安全作业证-审批
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobSoilApproval(Map<String, Object> params);

    /**
     * 动土安全作业证-确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobSoilApprovalTime(Map<String, Object> params);

    /**
     * 动土安全作业证-审批退回
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobSoilApprovalReturn(Map<String, Object> params);
}
