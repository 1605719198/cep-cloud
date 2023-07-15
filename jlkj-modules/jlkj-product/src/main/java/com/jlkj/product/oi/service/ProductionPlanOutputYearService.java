package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.domain.ProductionPlanOutputYear;
import com.jlkj.product.oi.dto.productionplantarget.DeleteProductionPlanYearDTO;

import java.util.List;
import java.util.Map;

/**
*@description: 针对表【product_oi_plan_output_year(计划管理--年产量计划)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/11 8:26
*/
public interface ProductionPlanOutputYearService extends IService<ProductionPlanOutputYear> {

    /**
     * 年产量计划查询
     * @return
     */
    List<Map<String, String>> get();

}
