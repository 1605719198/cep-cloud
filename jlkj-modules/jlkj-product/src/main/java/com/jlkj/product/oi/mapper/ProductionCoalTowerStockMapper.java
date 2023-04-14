package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionCoalTowerStock;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_coal_tower_stock(生产实绩-储煤塔存量)】的数据库操作Mapper
* @createDate 2022-12-06 10:46:56
* @Entity com.jlkj.product.oi.entity.ProductionCoalTowerStock
*/
public interface ProductionCoalTowerStockMapper extends BaseMapper<ProductionCoalTowerStock> {

    /**
     * 指标项查询
     * @param param
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> getWaterRate(Map<String, Object> param);
}




