package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionRealOutputDate;
import com.jlkj.product.oi.dto.productionrealoutputdate.ListProductionRealOutputDateTargetItemDTO;
import com.jlkj.product.oi.dto.productionrealoutputdate.PageProductionRealOutputDateDTO;
import com.jlkj.product.oi.vo.productionrealoutputdate.ListProductionRealOutputDateTargetItemVO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.PageProductionRealOutputMonthVO;

import java.util.List;

/**
*@description: 服务接口-生产管理-日产量
*@Author: 265823
*@date: 2023/7/11 11:12
*/
public interface ProductionRealOutputDateService extends IService<ProductionRealOutputDate> {

    /**
     * 生产管理-指标跟踪-图表-指标项(日实际)
     * @param listProductionRealOutputDateTargetItemDTO 查询条件dto
     * @return 列表数据
     */
    List<ListProductionRealOutputDateTargetItemVO> getProductionRealOutputDateTargetItemChartData(ListProductionRealOutputDateTargetItemDTO listProductionRealOutputDateTargetItemDTO);

    /**
     * 查询-分页-生产管理-统计分析-指标跟踪(日)
     * @param pageProductionRealOutputDateDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionRealOutputMonthVO> getPageData(PageProductionRealOutputDateDTO pageProductionRealOutputDateDTO);

    /**
     * 生产管理-统计分析-指标分析-日产量指标跟踪-更新统计数据
     */
    void updateProductionRealOutputDateStatisticsData();
}

