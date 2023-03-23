package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobTemporaryElectricityApproval;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 临时用电安全作业证-审批记录 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobTemporaryElectricityApprovalService extends IService<SafeJobTemporaryElectricityApproval> {

    /**
     * 临时用电安全作业证-设置审批人
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobTemporaryElectricityApproval(Map<String, Object> params);

    /**
     * 临时用电安全作业证-审批
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobTemporaryElectricityApproval(Map<String, Object> params);

    /**
     * 临时用电安全作业证-确认
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobTemporaryElectricityApprovalTime(Map<String, Object> params);

    /**
     * 临时用电安全作业证-审批退回
     * @param params 请求参数
     * @return 结果
     */
    public Object confirmSafeJobTemporaryElectricityApprovalReturn(Map<String, Object> params);
}
