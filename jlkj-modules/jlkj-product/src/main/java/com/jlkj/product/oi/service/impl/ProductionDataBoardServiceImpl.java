package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.product.oi.constants.DateConstants;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisMonth;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.dto.productiondataboard.GetDataBoardDTO;
import com.jlkj.product.oi.dto.productiondataboard.GetUpCoalValueByNowDayDTO;
import com.jlkj.product.oi.mapper.ProductionDataBoardMapper;
import com.jlkj.product.oi.service.ProductionDataBoardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
*@description:生产看板
*@Author: 265823
*@date: 2023/7/10 10:35
*/
@Service
public class ProductionDataBoardServiceImpl extends ServiceImpl<ProductionDataBoardMapper, ProductionYieldAnalysisMonth>
        implements ProductionDataBoardService {

    /**
     * 生产看板-焦炭日产量
     * @return
     */
    @Override
    public List<Map<String, String>> getCokeOutputAnalysisByNowDay() {
        return getBaseMapper().getCokeOutputAnalysisByNowDay();
    }

    /**
     * 生产看板-本年度-焦炭产量分析-分类汇总
     * @return
     */
    @Override
    public List<Map<String, String>> getCokeOutputAnalysis() {
        return getBaseMapper().getCokeOutputAnalysis();
    }

    /**
     * 物料看板-本日-上煤量
     * @param dto dto
     * @return
     */
    @Override
    public List<Map<String, String>> getUpCoalWeightByNowDay(GetUpCoalValueByNowDayDTO dto) {
        return getBaseMapper().getUpCoalWeightByNowDay(dto);
    }


    /**
     * 生产看板-今日产量
     * @param dto dto
     * @return
     */
    @Override
    public Map<String, Object> getTodayYield(GetDataBoardDTO dto) {
        Map<String, Object> map = new HashMap<>(8);
        List<Map<String, Object>> coke = getBaseMapper().getTodayYieldByCoke(dto);
        List<Map<String, Object>> powder = getBaseMapper().getTodayYieldByPowder(dto);
        List<Map<String, Object>> stoveCount = getBaseMapper().getTodayYieldByStoveCount(dto);
        DecimalFormat df = new DecimalFormat("0.000");

        //coke_type 1:干熄焦；2:湿熄焦
        List<Map<String, Object>> coke1 = coke.stream().filter(item -> "1".equals(item.get("coke_type").toString())).collect(Collectors.toList());
        List<Map<String, Object>> coke2 = coke.stream().filter(item -> "2".equals(item.get("coke_type").toString())).collect(Collectors.toList());
        List<Map<String, Object>> coke3 = coke.stream().filter(item -> "10000".equals(item.get("coke_type").toString())).collect(Collectors.toList());

        double coke1Sum = coke.stream().filter(item -> "1".equals(item.get("coke_type").toString())).mapToDouble(item -> Double.parseDouble(item.get("weight").toString())).sum();
        double coke2Sum = coke.stream().filter(item -> "2".equals(item.get("coke_type").toString())).mapToDouble(item -> Double.parseDouble(item.get("weight").toString())).sum();
        double cokeSum = coke1Sum + coke2Sum;

        double lv1 = coke3.stream().filter(item -> DateConstants.LEVEL_1.equals(item.get("materials_code").toString())).mapToDouble(item -> Double.parseDouble(item.get("weight").toString())).sum();
        double lvz1 = coke3.stream().filter(item -> DateConstants.LEVEL_Z1.equals(item.get("materials_code").toString())).mapToDouble(item -> Double.parseDouble(item.get("weight").toString())).sum();
        double lv2 = coke3.stream().filter(item -> DateConstants.LEVEL_2.equals(item.get("materials_code").toString())).mapToDouble(item -> Double.parseDouble(item.get("weight").toString())).sum();
        //焦丁焦粉
        double powderSum = Double.parseDouble(powder.get(0).get("weight").toString());
        if (powderSum > 0 && cokeSum > 0) {
            for (Map<String, Object> stringObjectMap : coke3) {
                double weight = Double.parseDouble(stringObjectMap.get("weight").toString());
                if (DateConstants.LEVEL_1.equals(stringObjectMap.get("materials_code"))) {
                    stringObjectMap.put("weight", df.format(weight - powderSum * (lv1 / cokeSum)));
                } else if (DateConstants.LEVEL_Z1.equals(stringObjectMap.get("materials_code"))) {
                    stringObjectMap.put("weight", df.format(weight - powderSum * (lvz1 / cokeSum)));
                } else if (DateConstants.LEVEL_2.equals(stringObjectMap.get("materials_code"))) {
                    stringObjectMap.put("weight", df.format(weight - powderSum * (lv2 / cokeSum)));
                }
            }
        }

        int gx = 0;
        int sx = 0;
        for (Map<String, Object> stove : stoveCount) {
            if ("1".equals(stove.get("coke_type").toString())) {
                gx = Integer.parseInt(String.valueOf(stove.get("stoveCount")));
            } else if ("2".equals(stove.get("coke_type").toString())) {
                sx = Integer.parseInt(String.valueOf(stove.get("stoveCount")));
            }
        }
        map.put("stoveCount", gx + sx);
        map.put("gxStoveCount", gx);
        map.put("sxStoveCount", sx);

        map.put("stoveCount", stoveCount.get(0).get("stoveCount"));
        map.put("coke1", coke1);
        map.put("coke1Sum", df.format(coke1Sum));
        map.put("coke2", coke2);
        map.put("coke2Sum", df.format(coke2Sum));
        map.put("coke3", coke3);
        map.put("coke3Sum", df.format(cokeSum - powderSum));

        return map;
    }

    /**
     * 生产看板-总耗用量
     * @param dto dto
     * @return
     */
    @Override
    public Map<String, Object> getTodayConsumption(GetDataBoardDTO dto) {
        Map<String, Object> map = new HashMap<>(8);
        List<Map<String, Object>> consumption = getBaseMapper().getTodayConsumption(dto);
        if (consumption.size() > 0) {
            map = consumption.get(0);
        } else {
            map.put("coke", 0);
            map.put("coal", 0);
            map.put("water", 0);
            map.put("electricity", 0);
            map.put("ton_coal", 0);
            map.put("ton_water", 0);
            map.put("ton_electricity", 0);
        }
        return map;
    }

    /**
     * 生产看板-焦棚库存量
     * @param dto dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getTodayCokeStock(GetDataBoardDTO dto) {
        return getBaseMapper().getTodayCokeStock(dto);
    }

    /**
     * 生产看板-焦炭发运量
     * @param dto dto
     * @return
     */
    @Override
    public Map<String, Object> getTodayCokeShipment(GetDataBoardDTO dto) {
        Map<String, Object> map = new HashMap<>(8);
        List<Map<String, Object>> coke = getBaseMapper().getCokeCode(dto);
        List<Map<String, Object>> internal = getBaseMapper().getTodayCokeShipmentInternal(dto);
        List<Map<String, Object>> outgoing = getBaseMapper().getTodayCokeShipmentOutgoing(dto);

        map.put("coke", coke.stream().map(item -> item.get("materials_name").toString()).collect(Collectors.toList()));
        map.put("internal", internal);
        map.put("outgoing", outgoing);
        return map;
    }

    /**
     * 生产看板-蒸汽产量
     * @param dto dto
     * @return
     */
    @Override
    public Map<String, Object> getTodayCokeSteam(GetDataBoardDTO dto) {
        Map<String, Object> map = new HashMap<>(8);
        List<Map<String, Object>> steam = getBaseMapper().getTodayCokeSteam(dto);
        map.put("yield", 0.0);
        map.put("retrieve", 0.0);
        for (Map<String, Object> item : steam) {
            if ("产量".equals(item.get("type"))) {
                map.put("yield", item.get("qty"));
            } else if ("回收量".equals(item.get("type"))) {
                map.put("retrieve", item.get("qty"));
            }
        }
        return map;
    }

    /**
     * 首页-焦炭产量-煤种耗煤量
     * @param dto dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> getCoalConsumption(GetCokePlanPerformanceDTO dto) {
        List<Map<String, Object>> coals = getBaseMapper().getCoalConsumption(dto);
        Map<String, Object> res = new HashMap<>(4);
        Set<String> coalNames = coals.stream().filter(item -> StringUtils.isNotNull(item.get("material_name"))).map(item -> item.get("material_name").toString()).collect(Collectors.toSet());
        Set<String> dates = coals.stream().map(item -> item.get("date").toString()).collect(Collectors.toSet());
        List<Object> date = dates.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        List<Double> data = date.stream().map(i -> 0.0).collect(Collectors.toList());
        List<Double> coalDataSum = data.stream().map(i -> 0.0).collect(Collectors.toList());

        Map<String, List<Double>> coalData = new HashMap<>(8);
        for (String coalName : coalNames) {
            List<Double> list = new ArrayList<>();
            list.addAll(data);
            coalData.put(coalName, list);
        }
        for (Map<String, Object> coal : coals) {
            String name = StringUtils.isNotNull(coal.get("material_name")) ? coal.get("material_name").toString() : null;
            int index = date.indexOf(coal.get("date").toString());
            if (StringUtils.isNotNull(name) && coalData.containsKey(name)) {
                coalData.get(name).set(index, new BigDecimal(coal.get("weight").toString()).doubleValue());
            }
            double sum = coalDataSum.get(index);
            sum = sum + new BigDecimal(coal.get("weight").toString()).doubleValue();
            coalDataSum.set(index, sum);
        }
        res.put("coals", coalNames);
        res.put("coalData", coalData);
        res.put("coalDataSum", coalDataSum);
        res.put("date", date);
        return res;
    }

    /**
     * 焦炭产量-详情-煤种耗煤量-详情
     * @param dto dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getCoalConsumptionInfo(GetCokePlanPerformanceDTO dto) {
        return getBaseMapper().getCoalConsumptionInfo(dto);
    }

    /**
     * 首页-焦炭产量-能源耗煤量
     * @param dto dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> getEnergyConsumption(GetCokePlanPerformanceDTO dto) {
        Map<String, Object> res = new HashMap<>(4);
        List<Map<String, Object>> energys = getBaseMapper().getEnergyConsumption(dto);
        List<Object> date = new ArrayList<>();
        List<Object> electric = new ArrayList<>();
        List<Object> water = new ArrayList<>();
        List<Object> gas = new ArrayList<>();
        for (Map<String, Object> energy : energys) {
            date.add(energy.get("date"));
            electric.add(energy.get("electric"));
            water.add(energy.get("water"));
            gas.add(energy.get("gas"));
        }
        res.put("date",date);
        res.put("electric",electric);
        res.put("water",water);
        res.put("gas",gas);
        return res;
    }

    /**
     * 生产看板-焦炉技术指标数据
     * @param dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getCraftCokeTemp(GetDataBoardDTO dto) {
        return getBaseMapper().getCraftCokeTemp(dto);
    }

    /**
     * 生产看板 - 收率
     * @param dto dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getCokeTotalYield(GetDataBoardDTO dto) {
        return getBaseMapper().getCokeTotalYield(dto);
    }

    /**
     * 生产看板 - 干熄焦技术指标
     * @param dto dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getCokeTechnologyIndex(GetDataBoardDTO dto) {
        return getBaseMapper().getCokeTechnologyIndex(dto);
    }

    /**
     * 生产看板- 财务月日成本
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> getCostStatistics(GetDataBoardDTO dto) {
        Map<String, Object> data = new HashMap<>(4);
        List<Map<String, Object>> monthPlan = getBaseMapper().getCostMonthPlan(dto);
        List<Map<String, Object>> dayTotal = getBaseMapper().getCostDayTotal(dto);
        List<Map<String, Object>> dayCost = getBaseMapper().getCostDayCost(dto);
        data.put("monthPlan", new HashMap<>());
        data.put("dayTotal", new HashMap<>());
        data.put("dayCost", dayCost);
        if (monthPlan.size() > 0) {
            data.put("monthPlan", monthPlan.get(0));
        }
        if (dayTotal.size() > 0) {
            data.put("dayTotal", dayTotal.get(0));
        }else {
            data.put("dayTotal",getBaseMapper().getCostDayTotalLimit(dto).get(0));
        }
        return data;
    }
}
