package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionPlanEnergyMonth;
import com.jlkj.product.oi.dto.productionplanenergy.GetProductionPlanEnergyMonthDTO;

import java.util.Map;

/**
*@description: 针对表【product_oi_plan_energy_month(计划管理-能源月计划)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/11 8:17
*/
public interface ProductionPlanEnergyMonthService extends IService<ProductionPlanEnergyMonth> {

    /**
     * 查询能源月计划
     * @param dto
     * @return
     */
    IPage<Map<String, String>> get(GetProductionPlanEnergyMonthDTO dto);
}
