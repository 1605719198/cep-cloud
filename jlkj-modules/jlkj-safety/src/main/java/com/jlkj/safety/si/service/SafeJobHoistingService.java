package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.SafeSiJobHoisting;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 4.吊装安全作业证 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobHoistingService extends IService<SafeSiJobHoisting> {

    /**
     * 吊装安全作业证-作业票编号
     * @param params 请求参数
     * @return 结果
     */
    Object getjobCode(Map<String, Object> params);

    /**
     * 吊装安全作业证-详情
     * @param params 请求参数
     * @return 结果
     */
    Object getSafeJobHoistingInfo(Map<String, Object> params);

    /**
     * 吊装安全作业证-作业人员列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobHoistingPersonsList(Map<String, Object> params);

    /**
     * 吊装安全作业证-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobHoistingSafetyMeasuresList(Map<String, Object> params);

    /**
     * 吊装安全作业证-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobHoistingApprovalList(Map<String, Object> params);

    /**
     * 吊装安全作业证-查询列表
     * @param params 请求参数
     * @return 结果
     */
    Object getSafeJobHoistingPageList(Map<String, Object> params);

    /**
     * 吊装安全作业证-新增
     * @param params 请求参数
     * @return 结果
     */
    Object insertSafeJobHoisting(Map<String, Object> params);

    /**
     * 吊装安全作业证-修改
     * @param params 请求参数
     * @return 结果
     */
    Object updateSafeJobHoisting(Map<String, Object> params);

    /**
     * 吊装安全作业证-删除
     * @param params 请求参数
     * @return 结果
     */
    Object deleteSafeJobHoisting(Map<String, Object> params);

    /**
     * 吊装安全作业证-新增安全措施
     * @param params 请求参数
     * @return 结果
     */
    boolean insertSafeJobHoistingSafetyMeasures(Map<String, Object> params);

    /**
     * 吊装安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    boolean insertSafeJobHoistingApproval(Map<String, Object> params);

    /**
     * 吊装安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    boolean deleteSafeJobHoistingPersons(Map<String, Object> params);

    /**
     * 吊装安全作业证-更新审批状态
     * @param params 请求参数
     * @return 结果
     */
    boolean updateSafeJobHoistingApprovalStatus(Map<String, Object> params);

    /**
     * 吊装安全作业证-审批模板列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobHoistingApprovalTemplateList(Map<String, Object> params);

    /**
     * 吊装安全作业证-删除附件
     * @param params 请求参数
     * @return 结果
     */
    boolean deleteSafeJobHoistingFiles(Map<String, Object> params);

    /**
     * 吊装安全作业证-附件列表
     * @param params 请求参数
     * @return 结果列表
     */
    Object getSafeJobHoistingFileList(Map<String, Object> params);

    /**
     * 吊装安全作业证-报文-信息
     * @param params 请求参数
     * @return 结果
     */
    Object getErpDataSafeJobHoistingInfo(Map<String, Object> params);

    /**
     * 吊装安全作业证-报文-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobHoistingSafetyMeasuresList(Map<String, Object> params);

    /**
     * 吊装安全作业证-报文-作业级别列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobHoistingJobLevelList(Map<String, Object> params);

    /**
     * 吊装安全作业证-报文-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobHoistingApprovalList(Map<String, Object> params);

    /**
     * 吊装安全作业证-报文-确认列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobHoistingConfirmList(Map<String, Object> params);

    /**
     * 吊装安全作业证-报文-附件列表
     * @param params 请求参数
     * @return 结果列表
     */
    List<Map<String, Object>> getErpDataSafeJobHoistingFileList(Map<String, Object> params);

    /**
     * 吊装安全作业证-报文-发送
     * @param params 请求参数
     */
    void sendSafeJobHoistingMessageQueue(Map<String, Object> params);

    /**
     * 吊装安全作业证-更新作业证编号
     * @param params 请求参数
     * @return 结果
     */
    Object updateSafeJobHoistingJobCode(Map<String, Object> params);

    /**
     * 吊装安全作业证-新增
     * @author 265800
     * @date 2023/7/12 15:37
     * @param params 请求参数
     * @return java.lang.Object
     */
    Object insertSafeJobHoistingData(Map<String, Object> params);

    /**
     * 吊装安全作业证-修改
     * @author 265800
     * @date 2023/7/13 8:41
     * @param params 请求参数
     * @return java.lang.Object
     */
    Object updateSafeJobHoistingData(Map<String, Object> params);

    /**
     * 吊装安全作业证-批量保存作业人员
     * @author 265800
     * @date 2023/7/13 8:54
     * @param params 请求参数
     * @return java.lang.Object
     */
    Object batchSaveSafeJobHoistingPerson(Map<String, Object> params);
}
