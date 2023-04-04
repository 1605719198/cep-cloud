package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionCoalWarehouseStock;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2022-12-08 14:32:11.533027
 */
public interface ProductionCoalWarehouseStockService extends IService<ProductionCoalWarehouseStock> {
    /**
     * 上煤仓余量-物料看板-图表
     * @return
     */
    List<Map<String, String>> getMaterialsCoalWarehouseStock();
}
