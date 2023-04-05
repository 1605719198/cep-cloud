package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionSingleHoleYield;
import com.jlkj.product.oi.dto.productionsingleholeyield.PageProductionSingleHoleYieldDTO;

import java.util.Map;

/**
 * @author sudeyou
 */
public interface ProductionSingleHoleYieldService extends IService<ProductionSingleHoleYield> {

    /**
     * K值标准查询
     * @param pageProductionSingleHoleYieldDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionSingleHoleYieldDTO pageProductionSingleHoleYieldDTO);
}
