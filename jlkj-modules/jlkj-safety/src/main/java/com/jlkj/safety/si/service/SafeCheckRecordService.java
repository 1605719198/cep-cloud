package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeSiCheckRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 安全巡检 服务类
 * </p>
 *
 * @author cy
 * @since 2021-04-08
 */
public interface SafeCheckRecordService extends IService<SafeSiCheckRecord> {

    /**
     * 安全巡检-查询列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeCheckRecordPageList(Map<String, Object> params);

    /**
     * 安全巡检-详情
     * @param params 查询条件
     * @return 结果
     */
    public Object getSafeCheckRecordById(Map<String, Object> params);

    /**
     * 安全巡检-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeCheckRecord(Map<String, Object> params);

    /**
     * 安全巡检-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeCheckRecord(Map<String, Object> params);

    /**
     * 安全巡检-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeCheckRecord(Map<String, Object> params);

    /**
     * 安全巡检-处理
     * @param params 请求参数
     * @return 结果
     */
    public Object setSafeCheckDisposed(Map<String, Object> params);

}
