package com.jlkj.product.oi.service;


import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @description
 * @createDate 2023年3月13日 15:10:07
 */
public interface ProductionBoardEmsDataService {



    /**
     * 首页看板-蒸气产量-蒸气实绩
     * @param dto dto
     * @return 数据查询
     */
    List<Map<String, Object>> getSteamByBoiler(GetCokePlanPerformanceDTO dto);

    /**
     * 首页看板-蒸气产量-蒸气总产量
     * @param dto dto
     * @return 数据查询
     */
    List<Map<String, Object>> getSteamByOutput(GetCokePlanPerformanceDTO dto);

    /**
     * 首页看板-蒸气产量-蒸气回收量实绩
     * @param dto dto
     * @return 数据查询
     */
    List<Map<String, Object>> getSteamByRetrieve(GetCokePlanPerformanceDTO dto);

    /**
     * 首页看板-蒸气产量-水耗用量
     * @param dto dto
     * @return 数据查询
     */
    List<Map<String, Object>> getSteamByWater(GetCokePlanPerformanceDTO dto);

    /**
     * 首页看板-发电量-日耗电
     * @param dto dto
     * @return 数据查询
     */
    List<Map<String, Object>> getElectricityDayConsume(GetCokePlanPerformanceDTO dto);

    /**
     * 首页看板-发电量-日耗电-峰谷平
     * @param dto dto
     * @return 数据查询
     */
    List<Map<String, Object>> getElectricityDayConsumeInfo(GetCokePlanPerformanceDTO dto);
    /**
     * 获取范围内日期
     * @param dto dto
     * @return 数据查询
     */
    List<Object> getDate(GetCokePlanPerformanceDTO dto);


    /**
     * 焦炭产量-详情-能源消耗量
     * @param dto dto
     * @return 数据查询
     */
    Map<String, Object> getCokeEnergyConsumption(GetCokePlanPerformanceDTO dto);

    /**
     * 蒸汽产量-详情-蒸汽实绩
     * @param dto
     * @return
     */
    Map<String, Object> getSteamPerformance(GetCokePlanPerformanceDTO dto);

    /**
     * 蒸汽产量-详情-蒸汽总产量
     * @param dto
     * @return
     */
    List<Map<String, Object>> getSteamTotalOutput(GetCokePlanPerformanceDTO dto);

    /**
     * 蒸汽产量-详情-水耗用量
     * @param dto
     * @return
     */
    Map<String, Object> getWaterConsumption(GetCokePlanPerformanceDTO dto);

    /**
     * 首页看板-发电量
     * @param dto
     * @return
     */
    List<Map<String, Object>> getPowerGeneration(GetCokePlanPerformanceDTO dto);
    /**
     * 发电量-详情-并网电量实绩-日发电量分析
     * @param dto
     * @return
     */
    List<Map<String, Object>> getPowerGenerationDayAnalysis(GetCokePlanPerformanceDTO dto);
    /**
     * 发电量-详情-日产耗电对比-峰谷平电量产耗对比
     * @param dto
     * @return
     */
    List<Map<String, Object>> getPowerGenerationDayAnalysisByConsume(GetCokePlanPerformanceDTO dto);
    /**
     * 发电量-详情-并网电量实绩
     * @param dto
     * @return
     */
    List<Map<String, Object>> getGridConnectionElectricityQuantity(GetCokePlanPerformanceDTO dto);
    /**
     * 发电量-详情-蒸气耗用量
     * @param dto
     * @return
     */
    List<Map<String, Object>> getSteamConsumption(GetCokePlanPerformanceDTO dto);
    /**
     * 发电量-详情-蒸气耗用量-三级看板
     * @param dto
     * @return
     */
    List<Map<String, Object>> getSteamConsumptionByDay(GetCokePlanPerformanceDTO dto);

}
