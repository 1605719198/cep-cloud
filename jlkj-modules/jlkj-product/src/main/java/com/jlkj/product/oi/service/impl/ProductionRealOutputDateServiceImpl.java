package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionRealOutputDate;
import com.jlkj.product.oi.dto.productionrealoutputdate.ListProductionRealOutputDateTargetItemDTO;
import com.jlkj.product.oi.dto.productionrealoutputdate.PageProductionRealOutputDateDTO;
import com.jlkj.product.oi.mapper.ProductionRealOutputDateMapper;
import com.jlkj.product.oi.service.ProductionRealOutputDateService;
import com.jlkj.product.oi.vo.productionrealoutputdate.ListProductionRealOutputDateTargetItemVO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.PageProductionRealOutputMonthVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-生产管理-日产量
 * @author sudeyou
 * @since 2022-07-13 14:48:56
 */
@Service
public class ProductionRealOutputDateServiceImpl extends ServiceImpl<ProductionRealOutputDateMapper, ProductionRealOutputDate>
    implements ProductionRealOutputDateService {

    @Override
    @Transactional(readOnly = true)
    public List<ListProductionRealOutputDateTargetItemVO> getProductionRealOutputDateTargetItemChartData(ListProductionRealOutputDateTargetItemDTO listProductionRealOutputDateTargetItemDTO) {
        return getBaseMapper().getProductionRealOutputDateTargetItemChartData(listProductionRealOutputDateTargetItemDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionRealOutputMonthVO> getPageData(PageProductionRealOutputDateDTO pageProductionRealOutputDateDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionRealOutputDateDTO.getCurrent(), pageProductionRealOutputDateDTO.getSize());
        return getBaseMapper().getPageData(page, pageProductionRealOutputDateDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateProductionRealOutputDateStatisticsData() {
        String qj = "全焦";
        String yjj = "冶金焦";
        Map<String, Object> qjMinMax = getBaseMapper().getDateQjMinMax();
        Map<String, Object> qjAvg = new HashMap<>(1);
        Map<String, Object> yjjMinMax = getBaseMapper().getDateYjjMinMax();
        Map<String, Object> yjjAvg = new HashMap<>(1);
        Map<String, Object> param = new HashMap<>(1);
        List<ProductionRealOutputDate> list = list();
        for (ProductionRealOutputDate productionRealOutputDate: list) {
            if(qj.equals(productionRealOutputDate.getTargetItemName())) {
                productionRealOutputDate.setHistoricalMinimum(new BigDecimal(qjMinMax.get("min_target_item_output_real").toString()));
                productionRealOutputDate.setHistoricalMaximum(new BigDecimal(qjMinMax.get("max_target_item_output_real").toString()));
                if(!qjAvg.containsKey(String.valueOf(productionRealOutputDate.getYear() - 1))) {
                    param.put("year", productionRealOutputDate.getYear() - 1);
                    Map<String, Object> qjAvgResult = getBaseMapper().getDateQjAvg(param);
                    if(null != qjAvgResult) {
                        qjAvg.put(String.valueOf(productionRealOutputDate.getYear() - 1), new BigDecimal(qjAvgResult.get("avg_target_item_output_real").toString()));
                    }
                    else {
                        qjAvg.put(String.valueOf(productionRealOutputDate.getYear() - 1), new BigDecimal("0"));
                    }
                }
                productionRealOutputDate.setAverageLastYear(new BigDecimal(qjAvg.get(String.valueOf(productionRealOutputDate.getYear() - 1)).toString()));
                param.put("year", productionRealOutputDate.getYear() - 1);
                param.put("month", productionRealOutputDate.getMonth());
                param.put("date", productionRealOutputDate.getDate());
                Map<String, Object> qjTqResult = getBaseMapper().getDateQjTq(param);
                if(null != qjTqResult) {
                    productionRealOutputDate.setSameTimeLastYear(new BigDecimal(qjTqResult.get("target_item_output_real").toString()));
                }
                else {
                    productionRealOutputDate.setSameTimeLastYear(new BigDecimal("0"));
                }
            }
            else if(yjj.equals(productionRealOutputDate.getTargetItemName())) {
                productionRealOutputDate.setHistoricalMinimum(new BigDecimal(yjjMinMax.get("min_target_item_output_real").toString()));
                productionRealOutputDate.setHistoricalMaximum(new BigDecimal(yjjMinMax.get("max_target_item_output_real").toString()));
                if(!yjjAvg.containsKey(String.valueOf(productionRealOutputDate.getYear() - 1))) {
                    param.put("year", productionRealOutputDate.getYear() - 1);
                    Map<String, Object> qjAvgResult = getBaseMapper().getDateYjjAvg(param);
                    if(null != qjAvgResult) {
                        yjjAvg.put(String.valueOf(productionRealOutputDate.getYear() - 1), new BigDecimal(qjAvgResult.get("avg_target_item_output_real").toString()));
                    }
                    else {
                        yjjAvg.put(String.valueOf(productionRealOutputDate.getYear() - 1), new BigDecimal("0"));
                    }
                }
                productionRealOutputDate.setAverageLastYear(new BigDecimal(yjjAvg.get(String.valueOf(productionRealOutputDate.getYear() - 1)).toString()));
                param.put("year", productionRealOutputDate.getYear() - 1);
                param.put("month", productionRealOutputDate.getMonth());
                param.put("date", productionRealOutputDate.getDate());
                Map<String, Object> yjjTqResult = getBaseMapper().getDateYjjTq(param);
                if(null != yjjTqResult) {
                    productionRealOutputDate.setSameTimeLastYear(new BigDecimal(yjjTqResult.get("target_item_output_real").toString()));
                }
                else {
                    productionRealOutputDate.setSameTimeLastYear(new BigDecimal("0"));
                }
            }
            updateById(productionRealOutputDate);
        }
        return AjaxResult.success("操作成功");
    }
}

