package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisMonth;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.dto.productiondataboard.GetDataBoardDTO;
import com.jlkj.product.oi.dto.productiondataboard.GetUpCoalValueByNowDayDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @description
 * @createDate 2022年7月20日 08:45:22
 */
public interface ProductionDataBoardMapper extends BaseMapper<ProductionYieldAnalysisMonth> {

    /**
     * 生产看板-焦炭日产量 查询
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, String>> getCokeOutputAnalysisByNowDay();
    /**
     * 生产看板-本年度-焦炭产量分析-分类汇总 查询
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, String>> getCokeOutputAnalysis();
    /**
     * 物料看板-本日-上煤量 查询
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, String>> getUpCoalWeightByNowDay(GetUpCoalValueByNowDayDTO dto);


    /**
     * 生产看板-今日产量 - 焦炭
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getTodayYieldByCoke(GetDataBoardDTO dto);

    /**
     * 生产看板-今日产量 - 焦粉焦末
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getTodayYieldByPowder(GetDataBoardDTO dto);

    /**
     * 生产看板-今日产量 - 炉数
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getTodayYieldByStoveCount(GetDataBoardDTO dto);




    /**
     * 生产看板-总耗用量
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getTodayConsumption(GetDataBoardDTO dto);

    /**
     * 生产看板-焦棚库存量
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getTodayCokeStock(GetDataBoardDTO dto);

    /**
     * 生产看板-焦炭代码
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getCokeCode(GetDataBoardDTO dto);

    /**
     * 生产看板-焦炭发运量-内耗
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getTodayCokeShipmentInternal(GetDataBoardDTO dto);

    /**
     * 生产看板-焦炭发运量-外发
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getTodayCokeShipmentOutgoing(GetDataBoardDTO dto);

    /**
     * 生产看板-蒸汽产量
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getTodayCokeSteam(GetDataBoardDTO dto);

    /**
     * 首页-焦炭产量-煤种耗煤量
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getCoalConsumption(GetCokePlanPerformanceDTO dto);

    /**
     * 首页-焦炭产量-煤种耗煤量-详情
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getCoalConsumptionInfo(GetCokePlanPerformanceDTO dto);

    /**
     * 首页-焦炭产量-能源耗煤量
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getEnergyConsumption(GetCokePlanPerformanceDTO dto);

}
