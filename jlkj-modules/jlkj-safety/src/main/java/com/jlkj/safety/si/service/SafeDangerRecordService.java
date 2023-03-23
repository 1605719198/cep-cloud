package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiDangerRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 隐患处理 服务类
 * </p>
 *
 * @author su
 * @since 2022-06-22
 */
public interface SafeDangerRecordService extends IService<SafeSiDangerRecord> {

    /**
     * 安全隐患-查询列表
     * @param params 查询条件
     * @return 结果列表
     */
    Object getSafeDangerRecordPageList(Map<String, Object> params);

    /**
     * 安全隐患-详情
     * @param params 查询条件
     * @return 结果
     */
    Object getSafeDangerRecordById(Map<String, Object> params);

    /**
     * 安全隐患-新增
     * @param params 请求参数
     * @return 结果
     */
    Object addSafeDangerRecord(Map<String, Object> params);

    /**
     * 安全隐患-处理
     * @param params 请求参数
     * @return 结果
     */
    Object disposeSafeDanger(Map<String, Object> params);

    /**
     * 安全隐患-验收
     * @param params 请求参数
     * @return 结果
     */
    Object acceptanceSafeDanger(Map<String, Object> params);

    /**
     * 安全隐患-删除
     * @param params 请求参数
     * @return 结果
     */
    Object deleteSafeDanger(Map<String, Object> params);

}
