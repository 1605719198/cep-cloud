package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionPlanRepair;
import com.jlkj.product.oi.dto.productionplanrepair.*;

import java.util.Map;

/**
*@description: 针对表【product_oi_plan_repair(计划管理--维修计划)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/11 8:37
*/
public interface ProductionPlanRepairService extends IService<ProductionPlanRepair> {

    /**
     * 维修计划查询
     * @param pageProductionPlanRepairDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionPlanRepairDTO pageProductionPlanRepairDTO);

    /**
     * 新增
     * @param addProductionPlanRepairDTO
     */
    void insert(AddProductionPlanRepairDTO addProductionPlanRepairDTO);

    /**
     * 修改
     * @param updateProductionPlanRepairDTO
     */
    void updateCustom(UpdateProductionPlanRepairDTO updateProductionPlanRepairDTO);

    /**
     * 删除
     * @param deleteProductionPlanRepairDTO
     */
    void delete(DeleteProductionPlanRepairDTO deleteProductionPlanRepairDTO);

    /**
     * 完成操作
     * @param finishedProductionPlanRepairDTO
     */
    void completeOpa(FinishedProductionPlanRepairDTO finishedProductionPlanRepairDTO);
}
