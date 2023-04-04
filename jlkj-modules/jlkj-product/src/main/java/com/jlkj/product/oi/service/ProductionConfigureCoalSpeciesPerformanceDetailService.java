package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionConfigureCoalSpeciesPerformanceDetail;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformancedetail.PageProductionConfigureCoalSpeciesPerformanceDetailDTO;

import java.util.Map;

/**
 * 服务接口-配煤实绩明细
 * @author sudeyou
 */
public interface ProductionConfigureCoalSpeciesPerformanceDetailService extends IService<ProductionConfigureCoalSpeciesPerformanceDetail> {

    /**
     * 查询-分页-配煤实绩明细
     * @param pageProductionConfigureCoalSpeciesPerformanceDetailDTO 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, String>> getPageData(PageProductionConfigureCoalSpeciesPerformanceDetailDTO pageProductionConfigureCoalSpeciesPerformanceDetailDTO);
}

