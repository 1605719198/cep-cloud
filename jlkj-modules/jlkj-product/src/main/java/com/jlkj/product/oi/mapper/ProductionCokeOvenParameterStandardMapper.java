package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionCokeOvenParameterStandard;
import com.jlkj.product.oi.dto.productioncokeovenparameterstandard.PageProductionCokeOvenParameterStandardDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_coke_oven_parameter_standard(生产参数-焦炉参数标准)】的数据库操作Mapper
* @createDate 2022-04-26 09:12:50
* @Entity com.jlkj.product.oi.entity.ProductionCokeOvenParameterStandard
*/
public interface ProductionCokeOvenParameterStandardMapper extends BaseMapper<ProductionCokeOvenParameterStandard> {

    /**
     * 指标项查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<?> page, @Param("condition") PageProductionCokeOvenParameterStandardDTO condition);

}




