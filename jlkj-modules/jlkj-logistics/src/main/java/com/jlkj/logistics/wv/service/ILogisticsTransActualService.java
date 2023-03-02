package com.jlkj.logistics.wv.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.logistics.wv.domain.LogisticsTransActual;

/**
 * <p>
 * 运输实绩表 服务类
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
public interface ILogisticsTransActualService extends IService<LogisticsTransActual> {

    /**
     * 查询月倒出焦炭比
     * @param startDate
     * @param endDate
     * @return
     */
    JSONArray getOutCokeScale(String startDate, String endDate);
    /**
     * 查询月焦炭倒出量(按收货地点)
     * @param startDate
     * @param endDate
     * @return
     */
    JSONObject getOutCokeLocation(String startDate, String endDate);
    /**
     * 查询月焦炭倒出量实际-计划
     * @param startDate
     * @param endDate
     * @return
     */
    JSONObject getOutCokeAmount(String startDate, String endDate);

    /**
     * 查询月焦炭倒入量
     * @param startDate
     * @param endDate
     * @return
     */
    JSONObject getInCokeAmount(String startDate, String endDate);
}
