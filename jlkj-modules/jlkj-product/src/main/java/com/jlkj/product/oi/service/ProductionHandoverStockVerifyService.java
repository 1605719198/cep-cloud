package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionHandoverStockVerify;
import com.jlkj.product.oi.dto.productionhandoverstockverify.PageProductionHandoverStockVerifyDTO;

import java.util.Map;

/**
 * @author yzl
 * @Description 交班仓存核验
 * @create 2022年7月26日 08:48:35
 */
public interface ProductionHandoverStockVerifyService extends IService<ProductionHandoverStockVerify> {
    /**
     * 查询
     * @param pageProductionHandoverStockVerifyDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionHandoverStockVerifyDTO pageProductionHandoverStockVerifyDTO);
}
