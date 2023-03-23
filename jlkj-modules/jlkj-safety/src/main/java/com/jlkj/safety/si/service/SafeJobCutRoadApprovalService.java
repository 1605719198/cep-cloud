package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobCutRoadApproval;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 断路安全作业证-审批记录 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobCutRoadApprovalService extends IService<SafeJobCutRoadApproval> {

    /**
     * 断路安全作业证-设置审批人
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobCutRoadApproval(Map<String, Object> params);

    /**
     * 断路安全作业证-审批
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobCutRoadApproval(Map<String, Object> params);

    /**
     * 断路安全作业证-确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobCutRoadApprovalTime(Map<String, Object> params);

    /**
     * 断路安全作业证-退回
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobCutRoadApprovalReturn(Map<String, Object> params);
}
