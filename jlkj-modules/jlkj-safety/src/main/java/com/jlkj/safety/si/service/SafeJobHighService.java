package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobHigh;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 3.高处安全作业证 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobHighService extends IService<SafeJobHigh> {

    /**
     * 高处安全作业证-作业票编号
     * @param params 请求参数
     * @return 结果
     */
    public Object getjobCode(Map<String, Object> params);

    /**
     * 高处安全作业证-详情
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobHighInfo(Map<String, Object> params);

    /**
     * 高处安全作业证-作业人员列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobHighPersonsList(Map<String, Object> params);

    /**
     * 高处安全作业证-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobHighSafetyMeasuresList(Map<String, Object> params);

    /**
     * 高处安全作业证-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobHighApprovalList(Map<String, Object> params);

    /**
     * 高处安全作业证-查询列表
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobHighPageList(Map<String, Object> params);

    /**
     * 高处安全作业证-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeJobHigh(Map<String, Object> params);

    /**
     * 高处安全作业证-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobHigh(Map<String, Object> params);

    /**
     * 高处安全作业证-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeJobHigh(Map<String, Object> params);

    /**
     * 高处安全作业证-新增安全措施
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobHighSafetyMeasures(Map<String, Object> params);

    /**
     * 高处安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobHighApproval(Map<String, Object> params);

    /**
     * 高处安全作业证-新增作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobHighPersons(Map<String, Object> params);

    /**
     * 高处安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobHighPersons(Map<String, Object> params);

    /**
     * 高处安全作业证-更新审批状态
     * @param params 请求参数
     * @return 结果
     */
    public boolean updateSafeJobHighApprovalStatus(Map<String, Object> params);

    /**
     * 高处安全作业证-审批模板列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobHighApprovalTemplateList(Map<String, Object> params);

    /**
     * 高处安全作业证-删除附件
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobHighFiles(Map<String, Object> params);

    /**
     * 高处安全作业证-附件列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobHighFileList(Map<String, Object> params);

    /**
     * 高处安全作业证-报文-发送
     * @param jobSpace 请求参数
     */
    public void sendSafeJobHighMessageQueue(SafeJobHigh jobSpace);

    /**
     * 高处安全作业证-更新作业证编号
     * @param params 请求参数
     * @return 结果
     */
    Object updateSafeJobHighJobCode(Map<String, Object> params);
}
