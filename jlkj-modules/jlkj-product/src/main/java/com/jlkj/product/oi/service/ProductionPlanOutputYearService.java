package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.domain.ProductionPlanOutputYear;
import com.jlkj.product.oi.dto.productionplantarget.DeleteProductionPlanYearDTO;

import java.util.List;

/**
* @author zyf
* @description 针对表【product_oi_plan_output_year(计划管理--年产量计划)】的数据库操作Service
* @createDate 2022-04-21 14:46:03
*/
public interface ProductionPlanOutputYearService extends IService<ProductionPlanOutputYear> {

    /**
     * 年产量计划查询
     * @param itemlist
     * @return
     */
    Object get(List<ProductionParameterTargetItem> itemlist);

}
