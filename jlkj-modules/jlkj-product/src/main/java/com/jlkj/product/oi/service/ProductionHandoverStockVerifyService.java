package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionHandoverStockVerify;
import com.jlkj.product.oi.dto.productionhandoverstockverify.PageProductionHandoverStockVerifyDTO;
import com.jlkj.product.oi.dto.productionhandoverstockverify.UpdateProductionHandoverStockVerifyDTO;

import java.util.Map;

/**
*@description: 交班仓存核验
*@Author: 265823
*@date: 2023/7/10 14:53
*/
public interface ProductionHandoverStockVerifyService extends IService<ProductionHandoverStockVerify> {
    /**
     * 查询
     * @param pageProductionHandoverStockVerifyDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionHandoverStockVerifyDTO pageProductionHandoverStockVerifyDTO);

    /**
     * 修改
     * @param dto
     */
    void updateCustom(UpdateProductionHandoverStockVerifyDTO dto);
}
