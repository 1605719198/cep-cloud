package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisMonth;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.dto.productiondataboard.GetDataBoardDTO;
import com.jlkj.product.oi.dto.productiondataboard.GetUpCoalValueByNowDayDTO;

import java.util.List;
import java.util.Map;

/**
*@description:生产看板
*@Author: 265823
*@date: 2023/7/10 10:35
*/
public interface ProductionDataBoardService  extends IService<ProductionYieldAnalysisMonth> {

    /**
     * 生产看板-焦炭日产量 查询
     * @return 数据查询
     */
    List<Map<String, String>> getCokeOutputAnalysisByNowDay();
    /**
     * 生产看板-本年度-焦炭产量分析-分类汇总 查询
     * @return 数据查询
     */
    List<Map<String, String>> getCokeOutputAnalysis();
    /**
     * 物料看板-本日-上煤量 查询
     * @param dto dto
     * @return 数据查询
     */
    List<Map<String, String>> getUpCoalWeightByNowDay(GetUpCoalValueByNowDayDTO dto);

    /**
     * 生产看板-今日产量
     * @param dto dto
     * @return 数据查询
     */
    Map<String, Object> getTodayYield(GetDataBoardDTO dto);

    /**
     * 生产看板-总耗用量
     * @param dto dto
     * @return 数据查询
     */
    Map<String, Object> getTodayConsumption(GetDataBoardDTO dto);

    /**
     * 生产看板-焦棚库存量
     * @param dto dto
     * @return 数据查询
     */
    List<Map<String, Object>> getTodayCokeStock(GetDataBoardDTO dto);

    /**
     * 生产看板-焦炭发运量
     * @param dto dto
     * @return 数据查询
     */
    Map<String, Object> getTodayCokeShipment(GetDataBoardDTO dto);

    /**
     * 生产看板-蒸汽产量
     * @param dto dto
     * @return 数据查询
     */
    Map<String, Object> getTodayCokeSteam(GetDataBoardDTO dto);

    /**
     * 首页-焦炭产量-煤种耗煤量
     * @param dto dto
     * @return 数据查询
     */
    Map<String, Object> getCoalConsumption(GetCokePlanPerformanceDTO dto);

    /**
     * 首页-焦炭产量-煤种耗煤量-详情
     * @param dto dto
     * @return 数据查询
     */
    List<Map<String, Object>> getCoalConsumptionInfo(GetCokePlanPerformanceDTO dto);

    /**
     * 首页-焦炭产量-能源耗煤量
     * @param dto dto
     * @return 数据查询
     */
    Map<String, Object> getEnergyConsumption(GetCokePlanPerformanceDTO dto);

    /**
     * 生产看板-焦炉技术指标数据
     * @param dto
     * @return
     */
    List<Map<String, Object>> getCraftCokeTemp(GetDataBoardDTO dto);

    /**
     * 生产看板 - 收率
     * @param dto dto
     * @return 数据查询
     */
    List<Map<String, Object>> getCokeTotalYield(GetDataBoardDTO dto);

    /**
     * 生产看板 - 干熄焦技术指标
     * @param dto dto
     * @return 数据查询
     */
    List<Map<String, Object>> getCokeTechnologyIndex(GetDataBoardDTO dto);

    /**
     * 生产看板 - 财务月日成本
     * @param dto dto
     * @return 数据查询
     */
    Map<String, Object> getCostStatistics(GetDataBoardDTO dto);
}
