package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisMonth;
import com.jlkj.product.oi.dto.productionyieldanalysismonth.ListProductionYieldAnalysisMonthDTO;
import com.jlkj.product.oi.dto.productionyieldanalysismonth.ListProductionYieldAnalysisMonthMaterialDTO;
import com.jlkj.product.oi.vo.productionyieldanalysismonth.ListProductionYieldAnalysisMonthMaterialVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-产量分析日表
 * @author sudeyou
 * @since 2022-07-15 14:27:41
 */
public interface ProductionYieldAnalysisMonthMapper extends BaseMapper<ProductionYieldAnalysisMonth> {

    /**
     * 生产管理-产量跟踪-查询-月
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> getProductionYieldAnalysisMonthList(ListProductionYieldAnalysisMonthDTO condition);

    /**
     * 生产管理-产量跟踪-图表-物料名称(月)
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<ListProductionYieldAnalysisMonthMaterialVO> getProductionYieldAnalysisChartMonth(ListProductionYieldAnalysisMonthMaterialDTO condition);
}

