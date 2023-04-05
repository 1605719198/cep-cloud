package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.constants.DateConstants;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisMonth;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.dto.productiondataboard.GetDataBoardDTO;
import com.jlkj.product.oi.dto.productiondataboard.GetUpCoalValueByNowDayDTO;
import com.jlkj.product.oi.mapper.ProductionDataBoardMapper;
import com.jlkj.product.oi.service.ProductionDataBoardService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yzl
 * @description
 * @createDate 2022年7月20日 08:45:22
 */
@Service
public class ProductionDataBoardServiceImpl extends ServiceImpl<ProductionDataBoardMapper, ProductionYieldAnalysisMonth>
        implements ProductionDataBoardService {

    @Override
    public List<Map<String, String>> getCokeOutputAnalysisByNowDay() {
        return getBaseMapper().getCokeOutputAnalysisByNowDay();
    }

    @Override
    public List<Map<String, String>> getCokeOutputAnalysis() {
        return getBaseMapper().getCokeOutputAnalysis();
    }

    @Override
    public List<Map<String, String>> getUpCoalWeightByNowDay(GetUpCoalValueByNowDayDTO dto) {
        return getBaseMapper().getUpCoalWeightByNowDay(dto);
    }


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

        map.put("stoveCount", stoveCount.get(0).get("stoveCount"));
        map.put("coke1", coke1);
        map.put("coke1Sum", df.format(coke1Sum));
        map.put("coke2", coke2);
        map.put("coke2Sum", df.format(coke2Sum));
        map.put("coke3", coke3);
        map.put("coke3Sum", df.format(cokeSum - powderSum));

        return map;
    }

    @Override
    public Map<String, Object> getTodayConsumption(GetDataBoardDTO dto) {
        Map<String, Object> map = new HashMap<>(8);
        List<Map<String, Object>> consumption = getBaseMapper().getTodayConsumption(dto);
        if (consumption.size() > 0) {
            map = consumption.get(0);
        } else {
            map.put("coke", 0);
            map.put("water", 0);
            map.put("electricity", 0);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> getTodayCokeStock(GetDataBoardDTO dto) {
        return getBaseMapper().getTodayCokeStock(dto);
    }

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

    @Override
    public List<Map<String, Object>> getCoalConsumption(GetCokePlanPerformanceDTO dto) {
        return getBaseMapper().getCoalConsumption(dto);
    }
    @Override
    public List<Map<String, Object>> getCoalConsumptionInfo(GetCokePlanPerformanceDTO dto) {
        return getBaseMapper().getCoalConsumptionInfo(dto);
    }
    @Override
    public List<Map<String, Object>> getEnergyConsumption(GetCokePlanPerformanceDTO dto) {
        return getBaseMapper().getEnergyConsumption(dto);
    }

}
