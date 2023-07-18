package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiJobFire;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 1.动火安全作业证 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface SafeJobFireService extends IService<SafeSiJobFire> {

    /**
     * 动火安全作业证-作业票编号
     * @param params 请求参数
     * @return 结果
     */
    Object getjobCode(Map<String, Object> params);

    /**
     * 动火安全作业证-详情
     * @param params 请求参数
     * @return 结果
     */
    Object getSafeJobFireInfo(Map<String, Object> params);

    /**
     * 动火安全作业证-作业人员列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobFirePersonsList(Map<String, Object> params);

    /**
     * 动火安全作业证-作业分析列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobFireAnalysisList(Map<String, Object> params);

    /**
     * 动火安全作业证-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobFireSafetyMeasuresList(Map<String, Object> params);

    /**
     * 动火安全作业证-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobFireApprovalList(Map<String, Object> params);

    /**
     * 动火安全作业证-查询列表
     * @param params 请求参数
     * @return 结果
     */
    Object getSafeJobFirePageList(Map<String, Object> params);

    /**
     * 动火安全作业证-新增
     * @param params 请求参数
     * @return 结果
     */
    Object insertSafeJobFire(Map<String, Object> params);

    /**
     * 动火安全作业证-修改
     * @param params 请求参数
     * @return 结果
     */
    Object updateSafeJobFire(Map<String, Object> params);

    /**
     * 动火安全作业证-删除
     * @param params 请求参数
     * @return 结果
     */
    Object deleteSafeJobFire(Map<String, Object> params);

    /**
     * 动火安全作业证-新增安全措施
     * @param params 请求参数
     * @return 结果
     */
    boolean insertSafeJobFireSafetyMeasures(Map<String, Object> params);

    /**
     * 动火安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    boolean insertSafeJobFireApproval(Map<String, Object> params);

    /**
     * 动火安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    boolean deleteSafeJobFirePersons(Map<String, Object> params);

    /**
     * 动火安全作业证-删除作业分析
     * @param params 请求参数
     * @return 结果
     */
    boolean deleteSafeJobFireAnalysis(Map<String, Object> params);

    /**
     * 动火安全作业证-更新审批状态
     * @param params 请求参数
     * @return 结果
     */
    boolean updateSafeJobFireApprovalStatus(Map<String, Object> params);

    /**
     * 动火安全作业证-审批模板列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobFireApprovalTemplateList(Map<String, Object> params);

    /**
     * 动火安全作业证-删除附件
     * @param params 请求参数
     * @return 结果
     */
    boolean deleteSafeJobFireFiles(Map<String, Object> params);

    /**
     * 动火安全作业证-附件列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobFireFileList(Map<String, Object> params);

    /**
     * 动火安全作业证-作业级别模板列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobFireJobLevelTemplateList(Map<String, Object> params);

    /**
     * 动火安全作业证-报文-信息
     * @param params 请求参数
     * @return 结果
     */
    Object getErpDataSafeJobFireInfo(Map<String, Object> params);

    /**
     * 动火安全作业证-报文-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobFireSafetyMeasuresList(Map<String, Object> params);

    /**
     * 动火安全作业证-报文-作业级别列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobFireJobLevelList(Map<String, Object> params);

    /**
     * 动火安全作业证-报文-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobFireApprovalList(Map<String, Object> params);

    /**
     * 动火安全作业证-报文-确认列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobFireConfirmList(Map<String, Object> params);

    /**
     * 动火安全作业证-报文-分析列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobFireAnalysisList(Map<String, Object> params);

    /**
     * 动火安全作业证-报文-动火人列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobFirePersonList(Map<String, Object> params);

    /**
     * 动火安全作业证-报文-附件列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobFireFileList(Map<String, Object> params);

    /**
     * 动火安全作业证-报文-发送
     * @param params 请求参数
     */
    void sendSafeJobFireMessageQueue(Map<String, Object> params);

    /**
     * 动火安全作业证-更新作业证编号
     * @param params 请求参数
     * @return 结果
     */
    Object updateSafeJobFireJobCode(Map<String, Object> params);

    /**
     * 动火作业证-新增
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return java.lang.Object
     */
    Object insertSafeJobFireData(Map<String, Object> params);

    /**
     * 动火作业证-修改
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return java.lang.Object
     */
    Object updateSafeJobFireData(Map<String, Object> params);

    /**
     * 动火作业证-批量保存动火人
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return java.lang.Object
     */
    Object batchSaveSafeJobPerson(Map<String, Object> params);

    /**
     * 动火作业证-批量保存动火分析
     * @author 265800
     * @date 2023/7/14 9:15
     * @param params
     * @return java.lang.Object
     */
    Object batchSaveSafeJobAnalysis(Map<String, Object> params);
}
