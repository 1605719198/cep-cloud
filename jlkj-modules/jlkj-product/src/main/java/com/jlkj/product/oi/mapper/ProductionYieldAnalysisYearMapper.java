package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisYear;
import com.jlkj.product.oi.dto.productionyieldanalysisyear.ListProductionYieldAnalysisYearDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-产量分析日表
 * @author sudeyou
 * @since 2022-07-15 14:27:41
 */
public interface ProductionYieldAnalysisYearMapper extends BaseMapper<ProductionYieldAnalysisYear> {

    /**
     * 生产管理-产量跟踪-查询-月
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> getProductionYieldAnalysisYearList(ListProductionYieldAnalysisYearDTO condition);

}

