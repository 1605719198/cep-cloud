package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionConveyingCoalActual;
import com.jlkj.product.oi.dto.productionconveyingcoalactual.PageProductionConveyingCoalActualDTO;
import com.jlkj.product.oi.vo.productionconveyingcoalactual.PageProductionConveyingCoalActualVO;

/**
*@description: 服务接口-生产实绩-上煤实绩
*@Author: 265823
*@date: 2023/7/10 14:33
*/
public interface ProductionConveyingCoalActualService extends IService<ProductionConveyingCoalActual> {
    /**
     * 生产实绩-上煤实绩-查询-分页
     * @param pageProductionConveyingCoalActualDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionConveyingCoalActualVO> getProductionConveyingCoalActualPageData(PageProductionConveyingCoalActualDTO pageProductionConveyingCoalActualDTO);
}
