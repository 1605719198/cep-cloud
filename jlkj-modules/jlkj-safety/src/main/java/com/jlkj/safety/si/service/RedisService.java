package com.jlkj.safety.si.service;

import java.util.Map;

/**
 * <p>
 * Redis 服务类
 * </p>
 *
 * @author su
 * @since 2021-01-07
 */
public interface RedisService {

    /**
     * 安环采集-全部数据采集列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafePointList(Map<String, Object> params);

    /**
     * 安环采集-安全数据明细采集列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeDetailPointList(Map<String, Object> params);

    /**
     * 安环采集-安全数据汇总采集列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeTotalPointList(Map<String, Object> params);

    /**
     * 安环采集-环保数据明细采集列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeEnvDetailPointList(Map<String, Object> params);

    /**
     * 安环采集-环保数据汇总采集列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeEnvTotalPointList(Map<String, Object> params);
}
