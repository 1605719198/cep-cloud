package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobTemporaryElectricity;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 5.临时用电安全作业证 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobTemporaryElectricityService extends IService<SafeJobTemporaryElectricity> {

    /**
     * 临时用电安全作业证-作业票编号
     * @param params 请求参数
     * @return 结果
     */
    public Object getjobCode(Map<String, Object> params);

    /**
     * 临时用电安全作业证-详情
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobTemporaryElectricityInfo(Map<String, Object> params);

    /**
     * 临时用电安全作业证-作业人员列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobTemporaryElectricityPersonsList(Map<String, Object> params);

    /**
     * 临时用电安全作业证-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobTemporaryElectricitySafetyMeasuresList(Map<String, Object> params);

    /**
     * 临时用电安全作业证-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobTemporaryElectricityApprovalList(Map<String, Object> params);

    /**
     * 临时用电安全作业证-查询列表
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobTemporaryElectricityPageList(Map<String, Object> params);

    /**
     * 临时用电安全作业证-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeJobTemporaryElectricity(Map<String, Object> params);

    /**
     * 临时用电安全作业证-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobTemporaryElectricity(Map<String, Object> params);

    /**
     * 临时用电安全作业证-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeJobTemporaryElectricity(Map<String, Object> params);

    /**
     * 临时用电安全作业证-新增安全措施
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobTemporaryElectricitySafetyMeasures(Map<String, Object> params);

    /**
     * 临时用电安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobTemporaryElectricityApproval(Map<String, Object> params);

    /**
     * 临时用电安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobTemporaryElectricityPersons(Map<String, Object> params);

    /**
     * 临时用电安全作业证-更新审批状态
     * @param params 请求参数
     * @return 结果
     */
    public boolean updateSafeJobTemporaryElectricityApprovalStatus(Map<String, Object> params);

    /**
     * 临时用电安全作业证-审批模板列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobTemporaryElectricityApprovalTemplateList(Map<String, Object> params);

    /**
     * 临时用电安全作业证-附件列表
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobHoistingFileList(Map<String, Object> params);

    /**
     * 临时用电安全作业证-pdf
     * @param response HttpServletResponse
     * @param params 请求参数
     * @return 结果
     */
    public void getSafeJobHoistingFilePdf(HttpServletResponse response, Map<String, Object> params);
}
