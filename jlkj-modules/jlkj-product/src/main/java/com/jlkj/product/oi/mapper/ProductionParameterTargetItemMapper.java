package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.dto.productionparametertargetitem.PageProductionParameterTargetItemDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_parameter_target_item(生产参数--指标项维护)】的数据库操作Mapper
* @createDate 2022-04-21 14:46:03
* @Entity com.jlkj.product.oi.entity.ProductionParameterTargetItem
*/
public interface ProductionParameterTargetItemMapper extends BaseMapper<ProductionParameterTargetItem> {

    /**
     * 指标项查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<?> page, @Param("condition") PageProductionParameterTargetItemDTO condition);

}




