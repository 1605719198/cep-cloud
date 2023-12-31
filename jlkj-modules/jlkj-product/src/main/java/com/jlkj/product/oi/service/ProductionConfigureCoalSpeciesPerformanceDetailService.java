package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionConfigureCoalSpeciesPerformanceDetail;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformancedetail.PageProductionConfigureCoalSpeciesPerformanceDetailDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformancedetail.SaveOrUpdateDTO;

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

    /**
     * 配煤实绩明细-新增
     * @param dto
     */
    void saveCustom(SaveOrUpdateDTO dto);

    /**
     * 配煤实绩明细-修改
     * @param dto
     */
    void updateCustom(SaveOrUpdateDTO dto);

    /**
     * 配煤实绩明细-删除
     * @param dto
     */
    void del(SaveOrUpdateDTO dto);
}

