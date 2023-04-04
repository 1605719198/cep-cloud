package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisDate;
import com.jlkj.product.oi.dto.home.HomeDTO;
import com.jlkj.product.oi.dto.productionyieldanalysisdate.ListProductionYieldAnalysisDateDTO;
import com.jlkj.product.oi.dto.productionyieldanalysisdate.ListProductionYieldAnalysisDateMaterialDTO;
import com.jlkj.product.oi.vo.home.*;
import com.jlkj.product.oi.vo.productionyieldanalysisdate.ListProductionYieldAnalysisDateMaterialVO;

import java.util.List;
import java.util.Map;

/**
 * 服务接口-产量分析日表
 * @author sudeyou
 * @since 2022-07-15 14:27:41
 */
public interface ProductionYieldAnalysisDateService extends IService<ProductionYieldAnalysisDate> {

    /**
     * 生产管理-产量跟踪-查询(日)
     * @param listProductionYieldAnalysisDateDTO 查询条件dto
     * @return 列表数据
     */
    List<Map<String, Object>> getProductionYieldAnalysisDateList(ListProductionYieldAnalysisDateDTO listProductionYieldAnalysisDateDTO);

    /**
     * 生产管理-产量跟踪-图表-物料名称(日)
     * @param listProductionYieldAnalysisDateMaterialDTO 查询条件dto
     * @return 列表数据
     */
    List<ListProductionYieldAnalysisDateMaterialVO> getProductionYieldAnalysisChartDate(ListProductionYieldAnalysisDateMaterialDTO listProductionYieldAnalysisDateMaterialDTO);

    /**
     * 生产管理-首页-焦碳计划实绩图表
     * @param homeDTO 查询条件dto
     * @return 列表数据
     */
    List<ListYieldChartVO> getProductionHomeYieldChartData(HomeDTO homeDTO);

    /**
     * 生产管理-首页-焦碳计划实绩统计
     * @param homeDTO 查询条件dto
     * @return 列表数据
     */
    InfoYieldStatisticsVO getProductionHomeYieldStatisticsData(HomeDTO homeDTO);

    /**
     * 生产管理-首页-发电计划实绩统计
     * @param homeDTO 查询条件dto
     * @return 列表数据
     */
    InfoPowerStatisticsVO getProductionHomePowerStatisticsData(HomeDTO homeDTO);

    /**
     * 生产管理-首页-发电仪表图表
     * @param homeDTO 查询条件dto
     * @return 列表数据
     */
    List<ListPowerMeterChartVO> getProductionHomePowerMeterChartData(HomeDTO homeDTO);

    /**
     * 生产管理-首页-统计
     * @param homeDTO 查询条件dto
     * @return 列表数据
     */
    InfoStatisticsVO getProductionHomeStatisticsData(HomeDTO homeDTO);

    /**
     * 生产管理-产量跟踪-查询-获取动态列表
     * @return 列表数据
     */
    List<Map<String, Object>> getProductionYieldAnalysisColList();

}

