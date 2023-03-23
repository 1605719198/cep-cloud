package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.entity.Empty;

import java.util.Map;

/**
 * <p>
 * 安环看板 服务类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface HomeService extends IService<Empty> {

    /**
     * 信息-作业票数
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getInfoSafeJobCount(Map<String, Object> params);

    /**
     * 信息-施工单位数
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getInfoConstructionUnitCount(Map<String, Object> params);

    /**
     * 信息-外委人数
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getInfoConstructionUnitPersonsCount(Map<String, Object> params);

    /**
     * 信息-巡检人数
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getInfoCheckPersonsCount(Map<String, Object> params);

    /**
     * 安全作业列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeAreaJobList(Map<String, Object> params);

    /**
     * 安全巡检列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeAreaCheckList(Map<String, Object> params);

    /**
     * 区域人数列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeAreaPersonList(Map<String, Object> params);

    /**
     * 安环采集-安全报警配置列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeAreaAlarmsList(Map<String, Object> params);
//    public Object getSafeJobPersonList(Map<String, Object> params);
}
