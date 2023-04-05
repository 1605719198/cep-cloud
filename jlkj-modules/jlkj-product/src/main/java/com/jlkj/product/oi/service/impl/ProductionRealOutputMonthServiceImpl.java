package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionRealOutputMonth;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.dto.productionrealoutputmonth.ListProductionRealOutputMonthRangeDTO;
import com.jlkj.product.oi.dto.productionrealoutputmonth.ListProductionRealOutputMonthTargetItemDTO;
import com.jlkj.product.oi.dto.productionrealoutputmonth.PageProductionRealOutputMonthDTO;
import com.jlkj.product.oi.mapper.ProductionRealOutputMonthMapper;
import com.jlkj.product.oi.service.ProductionRealOutputMonthService;
import com.jlkj.product.oi.vo.productionrealoutputmonth.ListProductionRealOutputMonthRangeVO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.ListProductionRealOutputMonthTargetItemVO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.PageProductionRealOutputMonthVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-生产管理-月产量
 * @author sudeyou
 * @since 2022-07-13 14:18:51
 */
@Service
public class ProductionRealOutputMonthServiceImpl extends ServiceImpl<ProductionRealOutputMonthMapper, ProductionRealOutputMonth>
    implements ProductionRealOutputMonthService {

    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionRealOutputMonthVO> getPageData(PageProductionRealOutputMonthDTO pageProductionRealOutputMonthDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionRealOutputMonthDTO.getCurrent(), pageProductionRealOutputMonthDTO.getSize());
        return getBaseMapper().getPageData(page, pageProductionRealOutputMonthDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListProductionRealOutputMonthTargetItemVO> getProductionRealOutputMonthTargetItemChartData(ListProductionRealOutputMonthTargetItemDTO listProductionRealOutputMonthTargetItemDTO) {
        return getBaseMapper().getProductionRealOutputMonthTargetItemChartData(listProductionRealOutputMonthTargetItemDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListProductionRealOutputMonthRangeVO> getProductionRealOutputMonthRangeChartData(ListProductionRealOutputMonthRangeDTO listProductionRealOutputMonthRangeDTO) {
        return getBaseMapper().getProductionRealOutputMonthRangeChartData(listProductionRealOutputMonthRangeDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateProductionRealOutputMonthStatisticsData() {
        String qj = "全焦";
        String yjj = "冶金焦";
        Map<String, Object> qjMinMax = getBaseMapper().getMonthQjMinMax();
        Map<String, Object> qjAvg = new HashMap<>(1);
        Map<String, Object> yjjMinMax = getBaseMapper().getMonthYjjMinMax();
        Map<String, Object> yjjAvg = new HashMap<>(1);
        Map<String, Object> param = new HashMap<>(1);
        List<ProductionRealOutputMonth> list = list();
        for (ProductionRealOutputMonth productionRealOutputMonth: list) {
            if(qj.equals(productionRealOutputMonth.getTargetItemName())) {
                productionRealOutputMonth.setHistoricalMinimum(new BigDecimal(qjMinMax.get("min_target_item_output_real").toString()));
                productionRealOutputMonth.setHistoricalMaximum(new BigDecimal(qjMinMax.get("max_target_item_output_real").toString()));
                if(!qjAvg.containsKey(String.valueOf(productionRealOutputMonth.getYear() - 1))) {
                    param.put("year", productionRealOutputMonth.getYear() - 1);
                    Map<String, Object> qjAvgResult = getBaseMapper().getMonthQjAvg(param);
                    if(null != qjAvgResult) {
                        qjAvg.put(String.valueOf(productionRealOutputMonth.getYear() - 1), new BigDecimal(qjAvgResult.get("avg_target_item_output_real").toString()));
                    }
                    else {
                        qjAvg.put(String.valueOf(productionRealOutputMonth.getYear() - 1), new BigDecimal("0"));
                    }
                }
                productionRealOutputMonth.setAverageLastYear(new BigDecimal(qjAvg.get(String.valueOf(productionRealOutputMonth.getYear() - 1)).toString()));
                param.put("year", productionRealOutputMonth.getYear() - 1);
                param.put("month", productionRealOutputMonth.getMonth());
                Map<String, Object> qjTqResult = getBaseMapper().getMonthQjTq(param);
                if(null != qjTqResult) {
                    productionRealOutputMonth.setSameTimeLastYear(new BigDecimal(qjTqResult.get("target_item_output_real").toString()));
                }
                else {
                    productionRealOutputMonth.setSameTimeLastYear(new BigDecimal("0"));
                }
            }
            else if(yjj.equals(productionRealOutputMonth.getTargetItemName())) {
                productionRealOutputMonth.setHistoricalMinimum(new BigDecimal(yjjMinMax.get("min_target_item_output_real").toString()));
                productionRealOutputMonth.setHistoricalMaximum(new BigDecimal(yjjMinMax.get("max_target_item_output_real").toString()));
                if(!yjjAvg.containsKey(String.valueOf(productionRealOutputMonth.getYear() - 1))) {
                    param.put("year", productionRealOutputMonth.getYear() - 1);
                    Map<String, Object> qjAvgResult = getBaseMapper().getMonthYjjAvg(param);
                    if(null != qjAvgResult) {
                        yjjAvg.put(String.valueOf(productionRealOutputMonth.getYear() - 1), new BigDecimal(qjAvgResult.get("avg_target_item_output_real").toString()));
                    }
                    else {
                        yjjAvg.put(String.valueOf(productionRealOutputMonth.getYear() - 1), new BigDecimal("0"));
                    }
                }
                productionRealOutputMonth.setAverageLastYear(new BigDecimal(yjjAvg.get(String.valueOf(productionRealOutputMonth.getYear() - 1)).toString()));
                param.put("year", productionRealOutputMonth.getYear() - 1);
                param.put("month", productionRealOutputMonth.getMonth());
                Map<String, Object> yjjTqResult = getBaseMapper().getMonthYjjTq(param);
                if(null != yjjTqResult) {
                    productionRealOutputMonth.setSameTimeLastYear(new BigDecimal(yjjTqResult.get("target_item_output_real").toString()));
                }
                else {
                    productionRealOutputMonth.setSameTimeLastYear(new BigDecimal("0"));
                }
            }
            updateById(productionRealOutputMonth);
        }
        return AjaxResult.success("操作成功");
    }





    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getProductivityAnalys(GetCokePlanPerformanceDTO dto) {
        return getBaseMapper().getProductivityAnalys(dto);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getProductivityAnalysInfo(GetCokePlanPerformanceDTO dto) {
        return getBaseMapper().getProductivityAnalysInfo(dto);
    }
}

