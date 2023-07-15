package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionConfigureCoalSpeciesPerformance;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageConfigureCoalSpeciesAnalysisStatisticsDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesAnalysisChartDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesAnalysisDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesPerformanceDTO;

import java.util.List;
import java.util.Map;

/**
*@description: 服务接口-配煤实绩主记录
*@Author: 265823
*@date: 2023/7/10 14:21
*/
public interface ProductionConfigureCoalSpeciesPerformanceService extends IService<ProductionConfigureCoalSpeciesPerformance> {

    /**
     * 查询-分页-配煤实绩主记录
     * @param pageProductionConfigureCoalSpeciesPerformanceDTO 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, String>> getPageData(PageProductionConfigureCoalSpeciesPerformanceDTO pageProductionConfigureCoalSpeciesPerformanceDTO);

    /**
     * 查询-分页-配煤分析
     * @param dto 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, Object>> getConfigureCoalSpeciesAnalysis(PageProductionConfigureCoalSpeciesAnalysisDTO dto);

    /**
     * 查询-配煤分析-图表-年
     * @param dto 查询条件dto
     * @return 数据
     */
    List<Map<String, Object>> getConfigureCoalSpeciesAnalysisChartByYear(PageProductionConfigureCoalSpeciesAnalysisChartDTO dto);
    /**
     * 查询-配煤分析-图表-月
     * @param dto 查询条件dto
     * @return 数据
     */
    List<Map<String, Object>> getConfigureCoalSpeciesAnalysisChartByMonth(PageProductionConfigureCoalSpeciesAnalysisChartDTO dto);
    /**
     * 统计分析-配煤比分析-图表
     * @param dto 查询条件dto
     * @return 数据
     */
    Map<String, Object> getConfigureCoalSpeciesAnalysisStatistics(PageConfigureCoalSpeciesAnalysisStatisticsDTO dto);
}

