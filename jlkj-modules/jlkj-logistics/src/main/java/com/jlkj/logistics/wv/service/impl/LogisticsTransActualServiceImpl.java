package com.jlkj.logistics.wv.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.logistics.wv.domain.LogisticsPlanDay;
import com.jlkj.logistics.wv.domain.LogisticsTransActual;
import com.jlkj.logistics.wv.mapper.LogisticsTransActualMapper;
import com.jlkj.logistics.wv.service.ILogisticsTransActualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 运输实绩表 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@Service
public class LogisticsTransActualServiceImpl extends ServiceImpl<LogisticsTransActualMapper, LogisticsTransActual> implements ILogisticsTransActualService {

    @Autowired
    private LogisticsTransActualMapper logisticsTransActualMapper;

    /**
     * 查询月倒出焦炭比
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public JSONArray getOutCokeScale(String startDate, String endDate) {
        JSONArray arr = new JSONArray();
        List<LogisticsTransActual> list =  logisticsTransActualMapper.getOutCokeScale(startDate,endDate);
        if (list.size() > 0) {
            list.forEach(item -> {
                JSONObject json = new JSONObject();
                json.put("value",item.getCompletePoundNum());
                json.put("name",item.getGoodsName());
                arr.add(json);
            });
        }
        return arr;
    }

    /**
     * 查询月焦炭倒出量(按收货地点)
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public JSONObject getOutCokeLocation(String startDate, String endDate) {
        JSONObject resultJson = new JSONObject();
        List<LogisticsTransActual> list =  logisticsTransActualMapper.getOutCokeLocation(startDate,endDate);
        Map<String,JSONObject> jsonMap = new HashMap<>();
        Map<String,String> goodsMap = new HashMap<>();
        // 初始化焦炭种类
        JSONArray dimensions = new JSONArray();
        dimensions.add("product");
        // 初始化对应地点所收种类
        JSONArray source = new JSONArray();
        if (list.size() > 0) {
            list.forEach(item -> {
                JSONObject json = new JSONObject();
                if (!jsonMap.containsKey(item.getAddressToName())) {
                    json.put("product",item.getAddressToName());
                    json.put(item.getGoodsName(),item.getCompletePoundNum());
                    jsonMap.put(item.getAddressToName(),json);
                } else {
                    JSONObject getJson = jsonMap.get(item.getAddressToName());
                    getJson.put(item.getGoodsName(),item.getCompletePoundNum());
                    jsonMap.put(item.getAddressToName(),getJson);
                }
                if (!goodsMap.containsKey(item.getGoodsName())) {
                    goodsMap.put(item.getGoodsName(),item.getGoodsName());
                }
            });
        }
        for (Map.Entry<String,String> entry : goodsMap.entrySet()) {
            dimensions.add(entry.getValue());
        }
        // 遍历map组数据
        for (Map.Entry<String,JSONObject> entry : jsonMap.entrySet()) {
            source.add(entry.getValue());
        }
        resultJson.put("dimensions",dimensions);
        resultJson.put("source",source);
        return resultJson;
    }

    /**
     * 询月焦炭倒出量实际-计划
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public JSONObject getOutCokeAmount(String startDate, String endDate) {
//        Map<String,String> dateMap = getAllDatesInDateRange(startDate, endDate);
        JSONArray realData = new JSONArray();
        JSONArray planData = new JSONArray();
        JSONArray xData = new JSONArray();
        List<LogisticsTransActual> amountReal = logisticsTransActualMapper.getOutCokeAmountReal(startDate, endDate);
        List<LogisticsPlanDay> amountPlan = logisticsTransActualMapper.getOutCokeAmountPlan(startDate, endDate);
        if (amountReal.size() > 0) {
            amountReal.forEach(item -> {
                realData.add(item.getCompletePoundNum());
            });
        }
        if (amountPlan.size() > 0) {
            amountPlan.forEach(item -> {
                xData.add(item.getPlanDay());
                planData.add(item.getPlanPoundNum());
            });
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("xData",xData);
        resultJson.put("realData",realData);
        resultJson.put("planData",planData);
        return resultJson;
    }

    /**
     * 查询月焦炭倒入量
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public JSONObject getInCokeAmount(String startDate, String endDate) {
        List<LogisticsTransActual> inCokeList = logisticsTransActualMapper.getInCokeAmount(startDate, endDate);
        Map<String,String> goodsMap = new HashMap<>(64);
        Map<String,String> dateMap = new HashMap<>(64);
        JSONArray legend = new JSONArray();
        JSONArray xData = new JSONArray();
        JSONArray jsonData = new JSONArray();
        JSONArray dataArr = new JSONArray();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (inCokeList.size() > 0) {
            for(LogisticsTransActual actual : inCokeList) {
                if (!goodsMap.containsKey(actual.getGoodsName().trim())) {
                    goodsMap.put(actual.getGoodsName().trim(), actual.getGoodsName().trim());
                    legend.add(actual.getGoodsName());
                    if(dataArr.size() != 0) {
                        jsonData.add(dataArr);
                    }
                    dataArr = new JSONArray();
                }
                if (!dateMap.containsKey(sdf.format(actual.getPoundDate()))) {
                    dateMap.put(sdf.format(actual.getPoundDate()), sdf.format(actual.getPoundDate()));
                    xData.add(sdf.format(actual.getPoundDate()));
                }
                dataArr.add(actual.getCompletePoundNum());
            }
            jsonData.add(dataArr);
        }
        JSONObject json = new JSONObject();
        json.put("xData",xData);
        json.put("jsonData",jsonData);
        json.put("legend",legend);
        return json;
    }
    /**
     * 根据日期区间获取所有日期
     * @param startDate
     * @param endDate
     */
    public Map<String,String> getAllDatesInDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<String> list = new ArrayList<>();
        Map<String,String> map = new HashMap<>(64);
        // 开始日期必须小于结束日期
        if (start.isAfter(end)) {
            return new HashMap<>(64);
        }
        while (start.isBefore(end)) {
            map.put(start.toString(),start.toString());
            start = start.plusDays(1);
        }
        map.put(end.toString(),end.toString());
        return map;
    }
}
