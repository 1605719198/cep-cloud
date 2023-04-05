package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionConfigureCoalSpeciesPerformance;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageConfigureCoalSpeciesAnalysisStatisticsDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesAnalysisChartDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesAnalysisDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesPerformanceDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-配煤实绩主记录
 * @author sudeyou
 */
public interface ProductionConfigureCoalSpeciesPerformanceMapper extends BaseMapper<ProductionConfigureCoalSpeciesPerformance> {

    /**
     * 查询-分页-配煤实绩主记录
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page,@Param("condition") PageProductionConfigureCoalSpeciesPerformanceDTO condition);

    /**
     * 查询-分页-配煤分析
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, Object>> getConfigureCoalSpeciesAnalysis(Page<?> page,@Param("condition") PageProductionConfigureCoalSpeciesAnalysisDTO condition);

    /**
     * 查询-配煤分析-图表-年
     * @param condition 查询条件dto
     * @return 数据
     */
    @MapKey("id")
    List<Map<String, Object>> getConfigureCoalSpeciesAnalysisChartByYear(PageProductionConfigureCoalSpeciesAnalysisChartDTO condition);
    /**
     * 查询-配煤分析-图表-月
     * @param condition 查询条件dto
     * @return 数据
     */
    @MapKey("id")
    List<Map<String, Object>> getConfigureCoalSpeciesAnalysisChartByMonth(PageProductionConfigureCoalSpeciesAnalysisChartDTO condition);

    /**
     * 统计分析-配煤比分析-计划
     * @param condition 查询条件dto
     * @return 数据
     */
    @MapKey("id")
    List<Map<String, Object>> getConfigureCoalSpeciesAnalysisStatisticsPlan(PageConfigureCoalSpeciesAnalysisStatisticsDTO condition);
    /**
     * 统计分析-配煤比分析-实绩
     * @param condition 查询条件dto
     * @return 数据
     */
    @MapKey("id")
    List<Map<String, Object>> getConfigureCoalSpeciesAnalysisStatisticsPerformance(PageConfigureCoalSpeciesAnalysisStatisticsDTO condition);


    /**
     * 获取 指定年份中 使用的物料信息分类
     * @param year 年份
     * @return 数据
     */
    @MapKey("id")
    List<Map<String, Object>> getMaterialByYear(String year);

    /**
     * 获取 指定月份中 使用的物料信息分类
     * @param year 年份
     * @param month 月份
     * @return 数据
     */
    @MapKey("id")
    List<Map<String, Object>> getMaterialByMonth(String year,String month);



}

