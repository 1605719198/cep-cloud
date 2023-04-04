package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionConveyingCoalActual;
import com.jlkj.product.oi.dto.productionconveyingcoalactual.PageProductionConveyingCoalActualDTO;
import com.jlkj.product.oi.vo.productionconveyingcoalactual.PageProductionConveyingCoalActualVO;

/**
 * 服务接口-生产实绩-上煤实绩
 *
 * @author sudeyou
 * @since 2022-11-18 09:39:27
 */
public interface ProductionConveyingCoalActualService extends IService<ProductionConveyingCoalActual> {
    /**
     * 生产实绩-上煤实绩-查询-分页
     * @param pageProductionConveyingCoalActualDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionConveyingCoalActualVO> getProductionConveyingCoalActualPageData(PageProductionConveyingCoalActualDTO pageProductionConveyingCoalActualDTO);
}
