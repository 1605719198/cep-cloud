package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionSingleHoleYield;
import com.jlkj.product.oi.dto.productionsingleholeyield.PageProductionSingleHoleYieldDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

/**
 * @author sudeyou
 * @Entity generator.domain.ProductionSingleHoleYield
 */
public interface ProductionSingleHoleYieldMapper extends BaseMapper<ProductionSingleHoleYield> {

    /**
     * 指标项查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<?> page, PageProductionSingleHoleYieldDTO condition);

}




