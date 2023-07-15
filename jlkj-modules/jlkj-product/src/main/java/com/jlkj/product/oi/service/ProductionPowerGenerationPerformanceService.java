package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionPowerGenerationPerformance;
import com.jlkj.product.oi.dto.productionpowergenerationperformance.ListHomeProductionPowerGenerationYieldDTO;
import com.jlkj.product.oi.vo.productionpowergenerationperformance.ListHomeProductionPowerGenerationYieldVO;

import java.util.List;

/**
*@description: 服务接口-发电实绩
*@Author: 265823
*@date: 2023/7/11 8:55
*/
public interface ProductionPowerGenerationPerformanceService extends IService<ProductionPowerGenerationPerformance> {

    /**
     * 首页-发电量
     * @param listHomeProductionPowerGenerationYieldDTO 查询条件dto
     * @return 列表数据
     */
    List<ListHomeProductionPowerGenerationYieldVO> getHomeProductionPowerGenerationYieldListData(ListHomeProductionPowerGenerationYieldDTO listHomeProductionPowerGenerationYieldDTO);
}

