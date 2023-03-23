package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobSpace;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 2.受限空间安全作业证 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobSpaceService extends IService<SafeJobSpace> {

    /**
     * 受限空间安全作业证-作业票编号
     * @param params 请求参数
     * @return 结果
     */
    public Object getjobCode(Map<String, Object> params);

    /**
     * 受限空间安全作业证-详情
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobSpaceInfo(Map<String, Object> params);

    /**
     * 受限空间安全作业证-作业人员列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobSpacePersonsList(Map<String, Object> params);

    /**
     * 受限空间安全作业证-作业分析列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobSpaceAnalysisList(Map<String, Object> params);

    /**
     * 受限空间安全作业证-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobSpaceSafetyMeasuresList(Map<String, Object> params);

    /**
     * 受限空间安全作业证-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobSpaceApprovalList(Map<String, Object> params);

    /**
     * 受限空间安全作业证-查询列表
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobSpacePageList(Map<String, Object> params);

    /**
     * 受限空间安全作业证-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeJobSpace(Map<String, Object> params);

    /**
     * 受限空间安全作业证-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobSpace(Map<String, Object> params);

    /**
     * 受限空间安全作业证-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeJobSpace(Map<String, Object> params);

    /**
     * 受限空间安全作业证-新增安全措施
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobSpaceSafetyMeasures(Map<String, Object> params);

    /**
     * 受限空间安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobSpaceApproval(Map<String, Object> params);

    /**
     * 受限空间安全作业证-新增作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobSpacePersons(Map<String, Object> params);

    /**
     * 受限空间安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobSpacePersons(Map<String, Object> params);

    /**
     * 受限空间安全作业证-删除作业分析
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobSpaceAnalysis(Map<String, Object> params);

    /**
     * 受限空间安全作业证-更新审批状态
     * @param params 请求参数
     * @return 结果
     */
    public boolean updateSafeJobSpaceApprovalStatus(Map<String, Object> params);

    /**
     * 受限空间安全作业证-审批模板列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobSpaceApprovalTemplateList(Map<String, Object> params);

    /**
     * 受限空间安全作业证-删除附件
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobSpaceFiles(Map<String, Object> params);

    /**
     * 受限空间安全作业证-附件列表
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobSpaceFileList(Map<String, Object> params);

    /**
     * 受限空间安全作业证-报文-发送
     * @param jobSpace 请求参数
     * @return 结果
     */
    public void sendSafeJobSpaceMessageQueue(SafeJobSpace jobSpace);

    /**
     * 受限空间安全作业证-更新作业证编号
     * @param params 请求参数
     * @return 结果
     */
    Object updateSafeJobSpaceJobCode(Map<String, Object> params);
}
