package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionRealOutputDate;
import com.jlkj.product.oi.dto.productionrealoutputdate.ListProductionRealOutputDateTargetItemDTO;
import com.jlkj.product.oi.dto.productionrealoutputdate.PageProductionRealOutputDateDTO;
import com.jlkj.product.oi.vo.productionrealoutputdate.ListProductionRealOutputDateTargetItemVO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.PageProductionRealOutputMonthVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-生产管理-日产量
 * @author sudeyou
 * @since 2022-07-13 14:48:56
 */
public interface ProductionRealOutputDateMapper extends BaseMapper<ProductionRealOutputDate> {

    /**
     * 生产管理-指标跟踪-图表-指标项(日实际)
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<ListProductionRealOutputDateTargetItemVO> getProductionRealOutputDateTargetItemChartData(ListProductionRealOutputDateTargetItemDTO condition);

    /**
     * 查询-分页-生产管理-统计分析-指标跟踪(日)
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<PageProductionRealOutputMonthVO> getPageData(Page<?> page, PageProductionRealOutputDateDTO condition);


    /**
     * 获取全焦最大最小值
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getDateQjMinMax();

    /**
     * 获取全焦去年平均值
     * @param condition 查询条件
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getDateQjAvg(Map<String, Object> condition);

    /**
     * 获取全焦去年同期值
     * @param condition 查询条件
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getDateQjTq(Map<String, Object> condition);

    /**
     * 获取冶金焦最大最小值
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getDateYjjMinMax();

    /**
     * 获取冶金焦去年平均值
     * @param condition 查询条件
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getDateYjjAvg(Map<String, Object> condition);

    /**
     * 获取冶金焦去年同期值
     * @param condition 查询条件
     * @return
     */
    @SuppressWarnings("all")
    Map<String, Object> getDateYjjTq(Map<String, Object> condition);
}

