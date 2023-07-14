package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.dto.productionparametertargetitem.*;

import java.util.List;
import java.util.Map;

/**
*@description: 针对表【product_oi_parameter_target_item(生产参数--指标项维护)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/10 17:14
*/
public interface ProductionParameterTargetItemService extends IService<ProductionParameterTargetItem> {

    /**
     * 查询指标项
     * @param pageProductionParameterTargetItemDTO
     * @return
     */
    IPage<Map<String, String>> getListPage(PageProductionParameterTargetItemDTO pageProductionParameterTargetItemDTO);

    /**
     * 新增指标项
     * @param addProductionParameterTargetItemDTO
     */
    void addProductionTargetItem(AddProductionParameterTargetItemDTO addProductionParameterTargetItemDTO);

    /**
     * 修改指标项
     * @param updateProductionParameterTargetItemDTO
     */
    void editProductionTargetItem(UpdateProductionParameterTargetItemDTO updateProductionParameterTargetItemDTO);

    /**
     * 删除指标项
     * @param deleteProductionParameterTargetItemDTO
     */
    void delProductionTargetItem(DeleteProductionParameterTargetItemDTO deleteProductionParameterTargetItemDTO);

    /**
     * 获取指标项列表
     * @param productionParameterTargetItemDTO
     * @return
     */
    List<ProductionParameterTargetItem> getList(GetProductionParameterTargetItemDTO productionParameterTargetItemDTO);

}
