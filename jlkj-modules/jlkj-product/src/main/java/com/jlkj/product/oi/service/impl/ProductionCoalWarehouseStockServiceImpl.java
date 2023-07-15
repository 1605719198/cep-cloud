package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionCoalWarehouseStock;
import com.jlkj.product.oi.dto.productiondataboard.GetDataBoardDTO;
import com.jlkj.product.oi.mapper.ProductionCoalWarehouseStockMapper;
import com.jlkj.product.oi.service.ProductionCoalWarehouseStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2022-12-08 14:32:11.533027
 */
@Service
@Slf4j
public class ProductionCoalWarehouseStockServiceImpl extends ServiceImpl<ProductionCoalWarehouseStockMapper, ProductionCoalWarehouseStock>
        implements ProductionCoalWarehouseStockService {

    /**
     * 上煤仓余量-物料看板-图表
     * @return
     */
    @Override
    public List<Map<String, String>> getMaterialsCoalWarehouseStock(GetDataBoardDTO dto) {
        return getBaseMapper().getMaterialsCoalWarehouseStock(dto);
    }
}

