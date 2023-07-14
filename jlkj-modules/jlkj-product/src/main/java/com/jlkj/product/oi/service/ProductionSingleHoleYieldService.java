package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionSingleHoleYield;
import com.jlkj.product.oi.dto.productionsingleholeyield.AddProductionSingleHoleYieldDTO;
import com.jlkj.product.oi.dto.productionsingleholeyield.DeleteProductionSingleHoleYieldDTO;
import com.jlkj.product.oi.dto.productionsingleholeyield.PageProductionSingleHoleYieldDTO;
import com.jlkj.product.oi.dto.productionsingleholeyield.UpdateProductionSingleHoleYieldDTO;

import java.util.Map;

/**
*@description: 参数维护-焦炉单孔产量
*@Author: 265823
*@date: 2023/7/11 11:31
*/
public interface ProductionSingleHoleYieldService extends IService<ProductionSingleHoleYield> {

    /**
     * K值标准查询
     * @param pageProductionSingleHoleYieldDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionSingleHoleYieldDTO pageProductionSingleHoleYieldDTO);

    /**
     * 新增焦炉单孔产量
     * @param addProductionSingleHoleYieldDTO
     */
    void addProductionSingleHoleYield(AddProductionSingleHoleYieldDTO addProductionSingleHoleYieldDTO);

    /**
     * 修改焦炉单孔产量
     * @param updateProductionSingleHoleYieldDTO
     */
    void editProductionSingleHoleYield(UpdateProductionSingleHoleYieldDTO updateProductionSingleHoleYieldDTO);

    /**
     * 删除焦炉单孔产量
     * @param deleteProductionSingleHoleYieldDTO
     */
    void delProductionSingleHoleYield(DeleteProductionSingleHoleYieldDTO deleteProductionSingleHoleYieldDTO);
}
