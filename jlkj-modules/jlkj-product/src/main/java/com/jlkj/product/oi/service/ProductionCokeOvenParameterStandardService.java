package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionCokeOvenParameterStandard;
import com.jlkj.product.oi.dto.productioncokeovenparameterstandard.PageProductionCokeOvenParameterStandardDTO;

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
}
