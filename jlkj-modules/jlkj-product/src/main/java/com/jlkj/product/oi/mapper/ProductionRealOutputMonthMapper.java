package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionRealOutputMonth;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.dto.productionrealoutputmonth.ListProductionRealOutputMonthRangeDTO;
import com.jlkj.product.oi.dto.productionrealoutputmonth.ListProductionRealOutputMonthTargetItemDTO;
import com.jlkj.product.oi.dto.productionrealoutputmonth.PageProductionRealOutputMonthDTO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.ListProductionRealOutputMonthRangeVO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.ListProductionRealOutputMonthTargetItemVO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.PageProductionRealOutputMonthVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-生产管理-月产量
 * @author sudeyou
 * @since 2022-07-13 14:18:51
 */
public interface ProductionRealOutputMonthMapper extends BaseMapper<ProductionRealOutputMonth> {

    /**
     * 查询-分页-生产管理-统计分析-指标跟踪
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<PageProductionRealOutputMonthVO> getPageData(Page<?> page, PageProductionRealOutputMonthDTO condition);

    /**
     * 生产管理-指标跟踪-图表-指标项(月实际)
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<ListProductionRealOutputMonthTargetItemVO> getProductionRealOutputMonthTargetItemChartData(ListProductionRealOutputMonthTargetItemDTO condition);

    /**
     * 生产管理-指标跟踪-图表-日期(同环比)
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<ListProductionRealOutputMonthRangeVO> getProductionRealOutputMonthRangeChartData(ListProductionRealOutputMonthRangeDTO condition);

    /**
     * 获取全焦最大最小值
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getMonthQjMinMax();

    /**
     * 获取全焦去年平均值
     * @param condition 查询条件
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getMonthQjAvg(Map<String, Object> condition);

    /**
     * 获取全焦去年同期值
     * @param condition 查询条件
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getMonthQjTq(Map<String, Object> condition);

    /**
     * 获取冶金焦最大最小值
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getMonthYjjMinMax();

    /**
     * 获取冶金焦去年平均值
     * @param condition 查询条件
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getMonthYjjAvg(Map<String, Object> condition);

    /**
     * 获取冶金焦去年同期值
     * @param condition 查询条件
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getMonthYjjTq(Map<String, Object> condition);


    /**
     * 首页-焦炭产量-收率对比
     * @param condition 查询条件
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>>getProductivityAnalys(GetCokePlanPerformanceDTO condition);

    /**
     * 首页-焦炭产量-收率对比-详情
     * @param condition 查询条件
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>>getProductivityAnalysInfo(GetCokePlanPerformanceDTO condition);
}

