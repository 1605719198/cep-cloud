package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.EnergyMonthPlanOutput;
import com.jlkj.product.oi.dto.productionplanenergyoutput.GetProductionPlanEnergyOutputDTO;

import java.util.Map;

/**
*@description: 发电产量
*@Author: 265823
*@date: 2023/7/11 8:19
*/
public interface ProductionPlanEnergyOutputService  extends IService<EnergyMonthPlanOutput> {
    /**
     * 查询-分页-发电产量
     * @param dto 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, String>> getPageData(GetProductionPlanEnergyOutputDTO dto);

}
