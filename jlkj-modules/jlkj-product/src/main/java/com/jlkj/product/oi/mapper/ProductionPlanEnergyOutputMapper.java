package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.EnergyMonthPlanOutput;
import com.jlkj.product.oi.dto.productionplanenergyoutput.GetProductionPlanEnergyOutputDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author yzl
 * @Description 发电产量
 * @create 2022年9月6日 09:13:04
 */
public interface ProductionPlanEnergyOutputMapper  extends BaseMapper<EnergyMonthPlanOutput> {


    /**
     * 查询 月计划
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPlanEnergyOutputMonthPage(Page<Map<String, String>> page,@Param("condition") GetProductionPlanEnergyOutputDTO condition);
    /**
     * 查询 日计划
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPlanEnergyOutputDayPage(Page<Map<String, String>> page,@Param("condition") GetProductionPlanEnergyOutputDTO condition);


}
