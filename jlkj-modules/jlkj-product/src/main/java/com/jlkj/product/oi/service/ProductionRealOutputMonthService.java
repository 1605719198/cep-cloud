package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionRealOutputMonth;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.dto.productionrealoutputmonth.ListProductionRealOutputMonthRangeDTO;
import com.jlkj.product.oi.dto.productionrealoutputmonth.ListProductionRealOutputMonthTargetItemDTO;
import com.jlkj.product.oi.dto.productionrealoutputmonth.PageProductionRealOutputMonthDTO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.ListProductionRealOutputMonthRangeVO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.ListProductionRealOutputMonthTargetItemVO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.PageProductionRealOutputMonthVO;

import java.util.List;
import java.util.Map;

/**
 * 服务接口-生产管理-月产量
 * @author sudeyou
 * @since 2022-07-13 14:18:51
 */
public interface ProductionRealOutputMonthService extends IService<ProductionRealOutputMonth> {

    /**
     * 查询-分页-生产管理-统计分析-指标跟踪
     * @param pageProductionRealOutputMonthDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionRealOutputMonthVO> getPageData(PageProductionRealOutputMonthDTO pageProductionRealOutputMonthDTO);

    /**
     * 生产管理-指标跟踪-图表-指标项(月实际)
     * @param listProductionRealOutputMonthTargetItemDTO 查询条件dto
     * @return 列表数据
     */
    List<ListProductionRealOutputMonthTargetItemVO> getProductionRealOutputMonthTargetItemChartData(ListProductionRealOutputMonthTargetItemDTO listProductionRealOutputMonthTargetItemDTO);

    /**
     * 生产管理-指标跟踪-图表-日期(同环比)
     * @param listProductionRealOutputMonthRangeDTO 查询条件dto
     * @return 列表数据
     */
    List<ListProductionRealOutputMonthRangeVO> getProductionRealOutputMonthRangeChartData(ListProductionRealOutputMonthRangeDTO listProductionRealOutputMonthRangeDTO);

    /**
     * 生产管理-统计分析-指标分析-月产量指标跟踪-更新统计数据
     * @return 执行结果
     */
    Object updateProductionRealOutputMonthStatisticsData();


    /**
     * 首页-焦炭产量-收率对比
     * @param dto dto
     * @return 执行结果
     */
    List<Map<String, Object>> getProductivityAnalys(GetCokePlanPerformanceDTO dto);

    /**
     * 首页-焦炭产量-收率对比-详情
     * @param dto dto
     * @return 执行结果
     */
    List<Map<String, Object>> getProductivityAnalysInfo(GetCokePlanPerformanceDTO dto);
}

