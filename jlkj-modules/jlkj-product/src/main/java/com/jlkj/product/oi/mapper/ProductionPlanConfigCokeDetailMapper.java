package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionPlanConfigCokeDetail;
import com.jlkj.product.oi.dto.productionplanconfigcokedetail.InfoProductionPlanConfigCokeDetailByWarehouseNumberDTO;
import com.jlkj.product.oi.dto.productionplanconfigcokedetail.PageProductionPlanConfigCokeDetailDTO;
import com.jlkj.product.oi.vo.productionplanconfigcokedetail.InfoProductionPlanConfigCokeDetailByWarehouseNumberVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Mapper接口-配煤计划配煤记录
 * @author sudeyou
 */
public interface ProductionPlanConfigCokeDetailMapper extends BaseMapper<ProductionPlanConfigCokeDetail> {

    /**
     * 查询-分页-配煤计划配煤记录
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page,@Param("condition") PageProductionPlanConfigCokeDetailDTO condition);

    /**
     * 获取配煤仓配煤计划信息
     * @param condition 查询条件dto
     * @return 单行数据
     */
    @MapKey("id")
    InfoProductionPlanConfigCokeDetailByWarehouseNumberVO getProductionPlanConfigCokeDetailInfoByWarehouseNumberData(InfoProductionPlanConfigCokeDetailByWarehouseNumberDTO condition);
}

