package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobCutRoad;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 8.断路安全作业证 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobCutRoadService extends IService<SafeSiJobCutRoad> {

    /**
     * 断路安全作业证-作业票编号
     * @param params 请求参数
     * @return 结果
     */
    public Object getjobCode(Map<String, Object> params);

    /**
     * 断路安全作业证-详情
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobCutRoadInfo(Map<String, Object> params);

    /**
     * 断路安全作业证-作业人员列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobCutRoadPersonsList(Map<String, Object> params);

    /**
     * 断路安全作业证-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobCutRoadSafetyMeasuresList(Map<String, Object> params);

    /**
     * 断路安全作业证-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobCutRoadApprovalList(Map<String, Object> params);

    /**
     * 断路安全作业证-查询列表
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobCutRoadPageList(Map<String, Object> params);

    /**
     * 断路安全作业证-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeJobCutRoad(Map<String, Object> params);

    /**
     * 断路安全作业证-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobCutRoad(Map<String, Object> params);

    /**
     * 断路安全作业证-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeJobCutRoad(Map<String, Object> params);

    /**
     * 断路安全作业证-新增安全措施
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobCutRoadSafetyMeasures(Map<String, Object> params);

    /**
     * 断路安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobCutRoadApproval(Map<String, Object> params);

    /**
     * 断路安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobCutRoadPersons(Map<String, Object> params);

    /**
     * 断路安全作业证-更新审批状态
     * @param params 请求参数
     * @return 结果
     */
    public boolean updateSafeJobCutRoadApprovalStatus(Map<String, Object> params);

    /**
     * 断路安全作业证-审批模板列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobCutRoadApprovalTemplateList(Map<String, Object> params);

    /**
     * 断路安全作业证-附件列表
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobCutRoadFileList(Map<String, Object> params);

    /**
     * 断路安全作业证-pdf
     * @param response HttpServletResponse
     * @param params 请求参数
     * @return 结果
     */
    public void getSafeJobHoistingFilePdf(HttpServletResponse response, Map<String, Object> params);
}
