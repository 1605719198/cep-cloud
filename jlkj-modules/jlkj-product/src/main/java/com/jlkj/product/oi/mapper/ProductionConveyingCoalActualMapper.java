package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionConveyingCoalActual;
import com.jlkj.product.oi.dto.productionconveyingcoalactual.PageProductionConveyingCoalActualDTO;
import com.jlkj.product.oi.vo.productionconveyingcoalactual.PageProductionConveyingCoalActualVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper接口-生产实绩-上煤实绩
 *
 * @author sudeyou
 * @since 2022-11-18 09:39:27
 */
public interface ProductionConveyingCoalActualMapper extends BaseMapper<ProductionConveyingCoalActual> {
    /**
     * 生产实绩-上煤实绩-查询-分页
     * @param page 分页
     * @param condition 查询条件dto
     * @return 分页数据
     */
    @MapKey("id")
    IPage<PageProductionConveyingCoalActualVO> getProductionConveyingCoalActualPageData(Page<?> page,@Param("condition") PageProductionConveyingCoalActualDTO condition);
}
