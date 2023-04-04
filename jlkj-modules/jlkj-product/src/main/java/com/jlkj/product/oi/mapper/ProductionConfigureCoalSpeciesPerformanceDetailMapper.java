package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionConfigureCoalSpeciesPerformanceDetail;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformancedetail.PageProductionConfigureCoalSpeciesPerformanceDetailDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Mapper接口-配煤实绩明细
 * @author sudeyou
 */
public interface ProductionConfigureCoalSpeciesPerformanceDetailMapper extends BaseMapper<ProductionConfigureCoalSpeciesPerformanceDetail> {

    /**
     * 查询-分页-配煤实绩明细
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page,@Param("condition") PageProductionConfigureCoalSpeciesPerformanceDetailDTO condition);
}

