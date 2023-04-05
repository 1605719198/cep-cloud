package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisDate;
import com.jlkj.product.oi.dto.home.HomeDTO;
import com.jlkj.product.oi.dto.productionyieldanalysisdate.ListProductionYieldAnalysisDateDTO;
import com.jlkj.product.oi.dto.productionyieldanalysisdate.ListProductionYieldAnalysisDateMaterialDTO;
import com.jlkj.product.oi.vo.home.*;
import com.jlkj.product.oi.vo.productionyieldanalysisdate.ListProductionYieldAnalysisDateMaterialVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-产量分析日表
 * @author sudeyou
 * @since 2022-07-15 14:27:41
 */
public interface ProductionYieldAnalysisDateMapper extends BaseMapper<ProductionYieldAnalysisDate> {

    /**
     * 生产管理-产量跟踪-查询-获取动态列表
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> getProductionYieldAnalysisColList();

    /**
     * 生产管理-产量跟踪-查询-日
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> getProductionYieldAnalysisDateList(ListProductionYieldAnalysisDateDTO condition);

    /**
     * 生产管理-产量跟踪-图表-物料名称(日)
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<ListProductionYieldAnalysisDateMaterialVO> getProductionYieldAnalysisChartDate(ListProductionYieldAnalysisDateMaterialDTO condition);

    /**
     * 生产管理-首页-焦碳计划实绩图表
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<ListYieldChartVO> getProductionHomeYieldChartData(HomeDTO condition);

    /**
     * 生产管理-首页-焦碳计划实绩统计
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    InfoYieldStatisticsVO getProductionHomeYieldStatisticsData(HomeDTO condition);

    /**
     * 生产管理-首页-发电计划实绩统计
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    InfoPowerStatisticsVO getProductionHomePowerStatisticsData(HomeDTO condition);

    /**
     * 生产管理-首页-发电仪表图表
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<ListPowerMeterChartVO> getProductionHomePowerMeterChartData(HomeDTO condition);

    /**
     * 生产管理-首页-统计
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    InfoStatisticsVO getProductionHomeStatisticsData(HomeDTO condition);
}

