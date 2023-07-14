package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.product.oi.domain.SysDictData;
import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.mapper.ProductionBoardEmsDataMapper;
import com.jlkj.product.oi.service.ProductionBoardEmsDataService;
import com.jlkj.product.oi.service.SysDictDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yzl
 * @description
 * @createDate 2022年7月20日 08:45:22
 */
@Service
public class ProductionBoardEmsDataServiceImpl implements ProductionBoardEmsDataService {

    @Resource
    ProductionBoardEmsDataMapper productionBoardEmsDataMapper;
    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * 首页看板-蒸气产量-蒸气实绩
     * @param dto dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getSteamByBoiler(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getSteamByBoiler(dto);
    }

    /**
     * 首页看板-蒸气产量-蒸气总产量
     * @param dto dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getSteamByOutput(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getSteamByOutput(dto);
    }

    /**
     * 首页看板-蒸气产量-蒸气回收量实绩
     * @param dto dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getSteamByRetrieve(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getSteamByRetrieve(dto);
    }

    /**
     * 首页看板-蒸气产量-水耗用量
     * @param dto dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getSteamByWater(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getSteamByWater(dto);
    }

    /**
     * 首页看板-发电量-日耗电
     * @param dto dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getElectricityDayConsume(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getElectricityDayConsume(dto);
    }

    /**
     * 首页看板-发电量-日耗电-峰谷平
     * @param dto dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getElectricityDayConsumeInfo(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getElectricityDayConsumeInfo(dto);
    }
    @Override
    public List<Object> getDate(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getDate(dto);
    }


    /**
     * 焦炭产量-详情-能源消耗量
     * @param dto dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> getCokeEnergyConsumption(GetCokePlanPerformanceDTO dto) {
        Map<String, Object> res = new HashMap<>(4);
        List<Map<String, Object>> list = productionBoardEmsDataMapper.getCokeEnergyConsumption(dto);
        List<String> types = new ArrayList<>();
        types.add("水");
        types.add("电");
        types.add("蒸气");
        List<Object> date = productionBoardEmsDataMapper.getDate(dto);
        List<Double> data = date.stream().map(i -> 0.0).collect(Collectors.toList());
        Map<String, List<Double>> datas = new HashMap<>(8);
        for (String type : types) {
            List<Double> dl = new ArrayList<>(data);
            datas.put(type, dl);
        }
        for (Map<String, Object> map : list) {
            String name = StringUtils.isNotNull(map.get("type")) ? map.get("type").toString() : null;
            int index = date.indexOf(map.get("e_count_date").toString());
            if (StringUtils.isNotNull(name) && datas.containsKey(name)) {
                datas.get(name).set(index, new BigDecimal(map.get("pro_con_qty").toString()).doubleValue());
            }

        }
        res.put("types", types);
        res.put("date", date);
        res.put("datas", datas);
        return res;
    }

    /**
     * 蒸汽产量-详情-蒸汽实绩
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> getSteamPerformance(GetCokePlanPerformanceDTO dto) {
        Map<String, Object> res = new HashMap<>(4);
        List<Map<String, Object>> list = productionBoardEmsDataMapper.getSteamPerformance(dto);
        List<SysDictData> dictList = sysDictDataService.list(new QueryWrapper<SysDictData>().lambda()
                .eq(SysDictData::getDictType, "energy_consume_output")
                .eq(SysDictData::getDictValue, "超高压蒸汽")
                .eq(SysDictData::getStatus, "0"));
        Set<String> typesSet = dictList.stream().map(item -> item.getDictLabel()).collect(Collectors.toCollection(TreeSet::new));
        List<String> types = typesSet.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        List<Object> date = productionBoardEmsDataMapper.getDate(dto);
        List<Double> data = date.stream().map(i -> 0.0).collect(Collectors.toList());
        Map<String, List<Double>> datas = new HashMap<>(8);
        for (String type : types) {
            List<Double> dl = new ArrayList<>(data);
            datas.put(type, dl);
        }
        for (Map<String, Object> map : list) {
            String name = StringUtils.isNotNull(map.get("type")) ? map.get("type").toString() : null;
            int index = date.indexOf(map.get("e_count_date").toString());
            if (StringUtils.isNotNull(name) && datas.containsKey(name)) {
                datas.get(name).set(index, new BigDecimal(map.get("pro_con_qty").toString()).doubleValue());
            }

        }
        res.put("types", types);
        res.put("date", date);
        res.put("datas", datas);
        res.put("data", list);
        return res;
    }

    /**
     * 蒸汽产量-详情-蒸汽实绩
     * @param dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getSteamTotalOutput(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getSteamTotalOutput(dto);
    }

    /**
     * 蒸汽产量-详情-水耗用量
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> getWaterConsumption(GetCokePlanPerformanceDTO dto) {
        Map<String, Object> res = new HashMap<>(4);
        List<Map<String, Object>> list = productionBoardEmsDataMapper.getWaterConsumption(dto);
        List<String> types = new ArrayList<>();
        types.add("除盐水");
        types.add("工业水");
        List<Object> date = productionBoardEmsDataMapper.getDate(dto);
        List<Double> data = date.stream().map(i -> 0.0).collect(Collectors.toList());
        Map<String, List<Double>> datas = new HashMap<>(8);
        for (String type : types) {
            List<Double> dl = new ArrayList<>(data);
            datas.put(type, dl);
        }
        for (Map<String, Object> map : list) {
            String name = StringUtils.isNotNull(map.get("type")) ? map.get("type").toString() : null;
            int index = date.indexOf(map.get("e_count_date").toString());
            if (StringUtils.isNotNull(name) && datas.containsKey(name)) {
                datas.get(name).set(index, new BigDecimal(map.get("pro_con_qty").toString()).doubleValue());
            }

        }
        res.put("types", types);
        res.put("date", date);
        res.put("datas", datas);
        return res;
    }

    /**
     * 首页看板-发电量
     * @param dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getPowerGeneration(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getPowerGeneration(dto);
    }

    /**
     * 发电量-详情-发电量-日发电量分析
     * @param dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getPowerGenerationDayAnalysis(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getPowerGenerationDayAnalysis(dto);
    }

    /**
     * 发电量-详情-发电量-日发电量分析-消耗
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Map<String, Object>> getPowerGenerationDayAnalysisByConsume(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getPowerGenerationDayAnalysisByConsume(dto);
    }

    /**
     * 发电量-详情-并网电量实绩
     * @param dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getGridConnectionElectricityQuantity(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getGridConnectionElectricityQuantity(dto);
    }

    /**
     * 发电量-详情-蒸气耗用量
     * @param dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getSteamConsumption(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getSteamConsumption(dto);
    }

    /**
     * 发电量-详情-蒸气耗用量-三级看板
     * @param dto
     * @return
     */
    @Override
    public List<Map<String, Object>> getSteamConsumptionByDay(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getSteamConsumptionByDay(dto);
    }
}
