package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionPlanEnergyMonth;
import com.jlkj.product.oi.dto.productionplanenergy.GetProductionPlanEnergyMonthDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_energy_month(计划管理-能源月计划)】的数据库操作Mapper
* @createDate 2022-05-05 10:22:34
* @Entity com.jlkj.product.oi.entity.ProductionPlanEnergyMonth
*/
public interface ProductionPlanEnergyMonthMapper extends BaseMapper<ProductionPlanEnergyMonth> {
    /**
     * 查询 月计划
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPlanEnergyMonthPage(Page<Map<String, String>> page,@Param("condition") GetProductionPlanEnergyMonthDTO condition);
    /**
     * 查询 日计划
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPlanEnergyDayPage(Page<Map<String, String>> page,@Param("condition") GetProductionPlanEnergyMonthDTO condition);

}




