package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionConveyingCoalActual;
import com.jlkj.product.oi.dto.productionconveyingcoalactual.PageProductionConveyingCoalActualDTO;
import com.jlkj.product.oi.mapper.ProductionConveyingCoalActualMapper;
import com.jlkj.product.oi.service.ProductionConveyingCoalActualService;
import com.jlkj.product.oi.vo.productionconveyingcoalactual.PageProductionConveyingCoalActualVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 服务实现-生产实绩-上煤实绩
 *
 * @author sudeyou
 * @since 2022-11-18 09:39:27
 */
@Service
public class ProductionConveyingCoalActualServiceImpl extends ServiceImpl<ProductionConveyingCoalActualMapper, ProductionConveyingCoalActual>
    implements ProductionConveyingCoalActualService {

    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionConveyingCoalActualVO> getProductionConveyingCoalActualPageData(PageProductionConveyingCoalActualDTO pageProductionConveyingCoalActualDTO) {
        Page<Map<String, Object>> page = new Page<>(pageProductionConveyingCoalActualDTO.getCurrent(), pageProductionConveyingCoalActualDTO.getSize());
        return getBaseMapper().getProductionConveyingCoalActualPageData(page, pageProductionConveyingCoalActualDTO);
    }
}
