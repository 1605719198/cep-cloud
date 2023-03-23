package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobSoil;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 7.动土安全作业证 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobSoilService extends IService<SafeJobSoil> {

    /**
     * 动土安全作业证-作业票编号
     * @param params 请求参数
     * @return 结果
     */
    public Object getjobCode(Map<String, Object> params);

    /**
     * 动土安全作业证-详情
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobSoilInfo(Map<String, Object> params);

    /**
     * 动土安全作业证-作业人员列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobSoilPersonsList(Map<String, Object> params);

    /**
     * 动土安全作业证-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobSoilSafetyMeasuresList(Map<String, Object> params);

    /**
     * 动土安全作业证-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobSoilApprovalList(Map<String, Object> params);

    /**
     * 动土安全作业证-查询列表
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobSoilPageList(Map<String, Object> params);

    /**
     * 动土安全作业证-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeJobSoil(Map<String, Object> params);

    /**
     * 动土安全作业证-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobSoil(Map<String, Object> params);

    /**
     * 动土安全作业证-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeJobSoil(Map<String, Object> params);

    /**
     * 动土安全作业证-新增安全措施
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobSoilSafetyMeasures(Map<String, Object> params);

    /**
     * 动土安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobSoilApproval(Map<String, Object> params);

    /**
     * 动土安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobSoilPersons(Map<String, Object> params);

    /**
     * 动土安全作业证-更新审批状态
     * @param params 请求参数
     * @return 结果
     */
    public boolean updateSafeJobSoilApprovalStatus(Map<String, Object> params);

    /**
     * 动土安全作业证-审批模板列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobSoilApprovalTemplateList(Map<String, Object> params);

    /**
     * 动土安全作业证-附件列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobSoilFileList(Map<String, Object> params);

    /**
     动土安全作业证
     * @param response HttpServletResponse
     * @param params 请求参数
     * @return 结果
     */
    public void getSafeJobSoilFilePdf(HttpServletResponse response, Map<String, Object> params);
}
