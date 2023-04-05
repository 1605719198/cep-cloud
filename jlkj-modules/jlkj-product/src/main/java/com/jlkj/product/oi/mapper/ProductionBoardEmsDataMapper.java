package com.jlkj.product.oi.mapper;

import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @description
 * @createDate 2023年3月13日 15:10:07
 */
public interface ProductionBoardEmsDataMapper {



    /**
     * 首页看板-蒸气产量-蒸气实绩
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getSteamByBoiler(GetCokePlanPerformanceDTO dto);

    /**
     * 首页看板-蒸气产量-蒸气总产量
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getSteamByOutput(GetCokePlanPerformanceDTO dto);

    /**
     * 首页看板-蒸气产量-蒸气回收量实绩
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getSteamByRetrieve(GetCokePlanPerformanceDTO dto);

    /**
     * 首页看板-蒸气产量-水耗用量
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getSteamByWater(GetCokePlanPerformanceDTO dto);


    /**
     * 首页看板-发电量-日耗电
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getElectricityDayConsume(GetCokePlanPerformanceDTO dto);

    /**
     * 首页看板-发电量-日耗电-峰谷平
     * @param dto dto
     * @return 数据查询
     */
    @MapKey("id")
    List<Map<String, Object>> getElectricityDayConsumeInfo(GetCokePlanPerformanceDTO dto);


}
