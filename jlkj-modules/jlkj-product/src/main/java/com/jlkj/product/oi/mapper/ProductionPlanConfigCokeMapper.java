package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionPlanConfigCoke;
import com.jlkj.product.oi.dto.productionplanconfigcoke.PageProductionPlanConfigCokeDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Mapper接口-配煤计划主记录
 * @author sudeyou
 */
public interface ProductionPlanConfigCokeMapper extends BaseMapper<ProductionPlanConfigCoke> {

    /**
     * 查询-分页-配煤计划主记录
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page,@Param("condition") PageProductionPlanConfigCokeDTO condition);

    /**
     * 配煤计划主记录-计划编号
     * @param condition 查询条件
     * @return 单行数据
     */
    @SuppressWarnings("all")
    Map<String, Object> getPlanCodeData(Map<String, Object> condition);
}

