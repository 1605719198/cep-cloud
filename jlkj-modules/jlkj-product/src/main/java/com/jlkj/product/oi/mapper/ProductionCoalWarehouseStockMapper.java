package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionCoalWarehouseStock;
import com.jlkj.product.oi.dto.productiondataboard.GetDataBoardDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2022-12-08 14:32:11.534028
 */
public interface ProductionCoalWarehouseStockMapper extends BaseMapper<ProductionCoalWarehouseStock> {

    /**
     * 上煤仓余量-物料看板-图表
     * @param dto dto
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getMaterialsCoalWarehouseStock(GetDataBoardDTO dto);

}


