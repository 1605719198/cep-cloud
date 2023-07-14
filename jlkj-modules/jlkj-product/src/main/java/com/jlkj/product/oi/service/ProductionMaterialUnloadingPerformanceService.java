package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionMaterialUnloadingPerformance;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.AddProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.GetProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.UpdateProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.UpdateProductionMaterialUnloadingPerformanceWeightDTO;

import java.util.Map;

/**
*@description: 针对表【product_oi_material_unloading_performance(物料卸车实绩)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/10 16:50
*/
public interface ProductionMaterialUnloadingPerformanceService extends IService<ProductionMaterialUnloadingPerformance> {

    /**
     * 物料卸货实绩查询
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> get(GetProductionMaterialUnloadingPerformanceDTO dto);

    /**
     * 物料卸货实绩新增
     * @param dto
     */
    void saveCustom(AddProductionMaterialUnloadingPerformanceDTO dto);

    /**
     * 物料卸货实绩修改
     * @param dto
     */
    void updateCustom(UpdateProductionMaterialUnloadingPerformanceDTO dto);

    /**
     * 物料卸货实绩修改重量
     * @param dto
     */
    void updateMaterialUnloadingPerformanceWeight(UpdateProductionMaterialUnloadingPerformanceWeightDTO dto);

    /**
     * 物料卸货实绩删除重量
     * @param dto
     */
    void del(AddProductionMaterialUnloadingPerformanceDTO dto);
}
