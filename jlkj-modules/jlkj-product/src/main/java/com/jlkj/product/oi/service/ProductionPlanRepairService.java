package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionPlanRepair;
import com.jlkj.product.oi.dto.productionplanrepair.PageProductionPlanRepairDTO;

import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_repair(计划管理--维修计划)】的数据库操作Service
* @createDate 2022-04-28 13:43:40
*/
public interface ProductionPlanRepairService extends IService<ProductionPlanRepair> {

    /**
     * 维修计划查询
     * @param pageProductionPlanRepairDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionPlanRepairDTO pageProductionPlanRepairDTO);

}
