package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeJobBlindPlatePlugging;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 6.盲板抽堵安全作业证 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
public interface SafeJobBlindPlatePluggingService extends IService<SafeJobBlindPlatePlugging> {

    /**
     * 盲板抽堵安全作业证-作业票编号
     * @param params 请求参数
     * @return 结果
     */
    public Object getjobCode(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-详情
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobBlindPlatePluggingInfo(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-作业人员列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobBlindPlatePluggingPersonsList(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-安全措施列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobBlindPlatePluggingSafetyMeasuresList(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-审批列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobBlindPlatePluggingApprovalList(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-查询列表
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobBlindPlatePluggingPageList(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeJobBlindPlatePlugging(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeJobBlindPlatePlugging(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeJobBlindPlatePlugging(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-新增安全措施
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobBlindPlatePluggingSafetyMeasures(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-新增审批人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean insertSafeJobBlindPlatePluggingApproval(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-删除作业人员
     * @param params 请求参数
     * @return 结果
     */
    public boolean deleteSafeJobBlindPlatePluggingPersons(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-更新审批状态
     * @param params 请求参数
     * @return 结果
     */
    public boolean updateSafeJobBlindPlatePluggingApprovalStatus(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-审批模板列表
     * @param params 请求参数
     * @return 结果列表
     */
    public Object getSafeJobBlendingApprovalTemplateList(Map<String, Object> params);

    /**
     * 盲板抽堵安全作业证-附件列表
     * @param params 请求参数
     * @return 结果
     */
    public Object getSafeJobBlendingFileList(Map<String, Object> params);


    /**
     * 盲板抽堵安全作业证-pdf
     * @param response HttpServletResponse
     * @param params 请求参数
     * @return 结果
     */
    public void getSafeJobBlendingFilePdf(HttpServletResponse response, Map<String, Object> params);

}
