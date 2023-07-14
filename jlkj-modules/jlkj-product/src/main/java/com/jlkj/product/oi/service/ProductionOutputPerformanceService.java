package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionOutputPerformance;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.dto.productionoutputperformance.GetProductionOutputPerformanceDTO;

import java.util.List;
import java.util.Map;

/**
*@description: 针对表【product_oi_output_performance(出炉实绩)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/10 9:33
*/
public interface ProductionOutputPerformanceService extends IService<ProductionOutputPerformance> {


    /**
     * 焦炭产量记录查询
     * @param dto
     * @return
     */
    Map<String, Object> getCokePerformanceAnalys(GetCokePlanPerformanceDTO dto);

    /**
     * 焦炭产量-详情-焦炭产量 基于 焦炭等级
     * @param dto
     * @return
     */
    List<Map<String, Object>> getCokeOutputAnalysByLevel(GetCokePlanPerformanceDTO dto);

    /**
     * 焦炭产量-详情-焦炭产量 基于 炉号
     * @param dto
     * @return
     */
    List<Map<String, Object>> getCokeOutputAnalysByStove(GetCokePlanPerformanceDTO dto);

    /**
     * 焦炭产量-详情-焦炭产率（干熄焦、湿熄焦）
     * @param dto
     * @return
     */
    Map<String, Object> getProductivity(GetCokePlanPerformanceDTO dto);

    /**
     * 出炉实绩查询
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> get(GetProductionOutputPerformanceDTO dto);
}
