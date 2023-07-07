package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.dto.productionparametertargetitem.AddProductionParameterTargetItemDTO;
import com.jlkj.product.oi.dto.productionparametertargetitem.DeleteProductionParameterTargetItemDTO;
import com.jlkj.product.oi.dto.productionparametertargetitem.PageProductionParameterTargetItemDTO;
import com.jlkj.product.oi.dto.productionparametertargetitem.UpdateProductionParameterTargetItemDTO;

import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_parameter_target_item(生产参数--指标项维护)】的数据库操作Service
* @createDate 2022-04-21 14:46:03
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
     * @return
     */
    Object addProductionTargetItem(AddProductionParameterTargetItemDTO addProductionParameterTargetItemDTO);

    /**
     * 修改指标项
     * @param updateProductionParameterTargetItemDTO
     * @return
     */
    Object editProductionTargetItem(UpdateProductionParameterTargetItemDTO updateProductionParameterTargetItemDTO);

    /**
     * 删除指标项
     * @param deleteProductionParameterTargetItemDTO
     * @return
     */
    Object delProductionTargetItem(DeleteProductionParameterTargetItemDTO deleteProductionParameterTargetItemDTO);
}
