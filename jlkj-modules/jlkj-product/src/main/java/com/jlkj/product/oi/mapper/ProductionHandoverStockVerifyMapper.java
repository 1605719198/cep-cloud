package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionHandoverStockVerify;
import com.jlkj.product.oi.dto.productionhandoverstockverify.PageProductionHandoverStockVerifyDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author yzl
 * @Description 交班仓存核验
 * @create 2022年7月26日 08:48:35
 */
public interface ProductionHandoverStockVerifyMapper extends BaseMapper<ProductionHandoverStockVerify> {

    /**
     * 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page,@Param("condition") PageProductionHandoverStockVerifyDTO condition);

}


