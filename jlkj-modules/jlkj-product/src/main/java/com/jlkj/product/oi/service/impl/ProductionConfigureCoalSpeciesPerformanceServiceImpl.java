package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionConfigureCoalSpeciesPerformance;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageConfigureCoalSpeciesAnalysisStatisticsDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesAnalysisChartDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesAnalysisDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance.PageProductionConfigureCoalSpeciesPerformanceDTO;
import com.jlkj.product.oi.mapper.ProductionConfigureCoalSpeciesPerformanceMapper;
import com.jlkj.product.oi.service.ProductionConfigureCoalSpeciesPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现-配煤实绩主记录
 *
 * @author sudeyou
 */
@Service
public class ProductionConfigureCoalSpeciesPerformanceServiceImpl extends ServiceImpl<ProductionConfigureCoalSpeciesPerformanceMapper, ProductionConfigureCoalSpeciesPerformance>
        implements ProductionConfigureCoalSpeciesPerformanceService {

    /**
     * 月数
     */
    public static final Integer MONTH = 12;
    /**
     * 天数
     */
    public static final List<Integer> MONTH_DAY31 = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    public static final List<Integer> MONTH_DAY30 = Arrays.asList(4, 6, 9, 11);

    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageProductionConfigureCoalSpeciesPerformanceDTO pageProductionConfigureCoalSpeciesPerformanceDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionConfigureCoalSpeciesPerformanceDTO.getCurrent(), pageProductionConfigureCoalSpeciesPerformanceDTO.getSize());
        return getBaseMapper().getPageData(page, pageProductionConfigureCoalSpeciesPerformanceDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, Object>> getConfigureCoalSpeciesAnalysis(PageProductionConfigureCoalSpeciesAnalysisDTO dto) {
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getConfigureCoalSpeciesAnalysis(page, dto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getConfigureCoalSpeciesAnalysisChartByYear(PageProductionConfigureCoalSpeciesAnalysisChartDTO dto) {
        List<Map<String, Object>> row = new ArrayList<>();
        List<Map<String, Object>> list = getBaseMapper().getConfigureCoalSpeciesAnalysisChartByYear(dto);
        List<Map<String, Object>> material = getBaseMapper().getMaterialByYear(dto.getYear());
        for (Map<String, Object> map : material) {
            Map<String, Object> monthMap = new HashMap<>(2);
            List data = new ArrayList<>();
            monthMap.put("name", map.get("material_name"));
            List<Map<String, Object>> materialList = list.stream().filter(item -> map.get("material_number").equals(item.get("material_number"))).collect(Collectors.toList());
            for (int i = 0; i < MONTH; i++) {
                final int x = i + 1;
                List<Map<String, Object>> month = materialList.stream().filter(item -> x == Integer.parseInt(item.get("month").toString())).collect(Collectors.toList());
                if (month.size() > 0) {
                    data.add(month.get(0).get("weight"));
                } else {
                    data.add(0);
                }
            }
            monthMap.put("data", data);
            row.add(monthMap);
        }
        return row;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getConfigureCoalSpeciesAnalysisChartByMonth(PageProductionConfigureCoalSpeciesAnalysisChartDTO dto) {
        List<Map<String, Object>> row = new ArrayList<>();
        List<Map<String, Object>> list = getBaseMapper().getConfigureCoalSpeciesAnalysisChartByMonth(dto);
        List<Map<String, Object>> material = getBaseMapper().getMaterialByMonth(dto.getYear(), dto.getMonth());
        int days = 0;
        int year = Integer.parseInt(dto.getYear());
        int month = Integer.parseInt(dto.getMonth());
        if (MONTH_DAY31.stream().anyMatch(i -> i == month)) {
            days = 31;
        } else if (MONTH_DAY30.stream().anyMatch(i -> i == month)) {
            days = 30;
        } else {
            days = year % 4 == 0 ? 29 : 28;
        }
        for (Map<String, Object> map : material) {
            Map<String, Object> monthMap = new HashMap<>(2);
            List data = new ArrayList();
            monthMap.put("name", map.get("material_name"));
            List<Map<String, Object>> materialList = list.stream().filter(item -> map.get("material_number").equals(item.get("material_number"))).collect(Collectors.toList());
            for (int i = 0; i < days; i++) {
                final int x = i + 1;
                List<Map<String, Object>> day = materialList.stream().filter(item -> x == Integer.parseInt(item.get("day").toString())).collect(Collectors.toList());
                if (day.size() > 0) {
                    data.add(day.get(0).get("weight"));
                } else {
                    data.add(0);
                }
            }
            monthMap.put("data", data);
            row.add(monthMap);
        }
        return row;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getConfigureCoalSpeciesAnalysisStatistics(PageConfigureCoalSpeciesAnalysisStatisticsDTO dto) {
        Map<String, Object> row = new HashMap<>(4);
        List<Map<String, Object>> plan = getBaseMapper().getConfigureCoalSpeciesAnalysisStatisticsPlan(dto);
        List<Map<String, Object>> performance = getBaseMapper().getConfigureCoalSpeciesAnalysisStatisticsPerformance(dto);
        TreeSet<String> planNumber = new TreeSet<>();
        TreeSet<String> material = new TreeSet<>();
        if(plan.size() > 0){
            material.addAll(plan.stream().map(map->map.get("material_name").toString()).collect(Collectors.toList()));
            planNumber.addAll(plan.stream().map(map->map.get("plan_number").toString()).collect(Collectors.toList()));
        }
        if(performance.size() > 0) {
            material.addAll(performance.stream().map(map -> map.get("material_name").toString()).collect(Collectors.toList()));
            planNumber.addAll(performance.stream().map(map -> map.get("plan_number").toString()).collect(Collectors.toList()));
        }
        row.put("plan",plan);
        row.put("performance",performance);
        row.put("planNumber",planNumber);
        row.put("material",material);
        return row;
    }

}

