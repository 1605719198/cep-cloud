package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionCokeOvenParameterStandard;
import com.jlkj.product.oi.dto.productioncokeovenparameterstandard.AddProductionCokeOvenParameterStandardDTO;
import com.jlkj.product.oi.dto.productioncokeovenparameterstandard.PageProductionCokeOvenParameterStandardDTO;
import com.jlkj.product.oi.dto.productioncokeovenparameterstandard.UpdateProductionCokeOvenParameterStandardDTO;
import com.jlkj.product.oi.dto.productionparametertargetitem.DeleteProductionParameterTargetItemDTO;

import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_coke_oven_parameter_standard(生产参数-焦炉参数标准)】的数据库操作Service
* @createDate 2022-04-26 09:12:50
*/
public interface ProductionCokeOvenParameterStandardService extends IService<ProductionCokeOvenParameterStandard> {
    /**
     * K值标准查询
     * @param pageProductionCokeOvenParameterStandardDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageProductionCokeOvenParameterStandardDTO pageProductionCokeOvenParameterStandardDTO);

    /**
     * 新增K值标准
     * @param addProductionCokeOvenParameterStandardDTO
     */
    void saveCustom(AddProductionCokeOvenParameterStandardDTO addProductionCokeOvenParameterStandardDTO);

    /**
     * 修改K值标准
     * @param updateProductionCokeOvenParameterStandardDTO
     */
    void updateCustom(UpdateProductionCokeOvenParameterStandardDTO updateProductionCokeOvenParameterStandardDTO);

    /**
     * 删除k值标准
     * @param deleteProductionParameterTargetItemDTO
     */
    void delete(DeleteProductionParameterTargetItemDTO deleteProductionParameterTargetItemDTO);
}
