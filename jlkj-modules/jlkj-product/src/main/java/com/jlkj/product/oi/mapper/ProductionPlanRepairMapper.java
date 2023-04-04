package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionPlanRepair;
import com.jlkj.product.oi.dto.productionplanrepair.PageProductionPlanRepairDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_repair(计划管理--维修计划)】的数据库操作Mapper
* @createDate 2022-04-28 13:43:40
* @Entity com.jlkj.product.oi.entity.ProductionPlanRepair
*/
public interface ProductionPlanRepairMapper extends BaseMapper<ProductionPlanRepair> {
    /**
     * 维修计划查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<?> page,@Param("condition") PageProductionPlanRepairDTO condition);
}




