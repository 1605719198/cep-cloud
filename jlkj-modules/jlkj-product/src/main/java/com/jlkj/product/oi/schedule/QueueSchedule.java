package com.jlkj.product.oi.schedule;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.product.oi.constants.RabbitConstant.*;

/**
 * @author sudeyou
 * @time 2022/8/26
 */
@Component
@EnableScheduling
@Slf4j
public class QueueSchedule {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 焦化产出与外发同步
     */
    public void sendCokingOutputAndOutsourcingMessageQueue() {
        Map<String, Object> outMap = new HashMap<>(1);
        outMap.put("class", "com.icsc.oi.mq.oijcC01Listener");
        outMap.put("messageId", "OIC01");
        outMap.put("actionCode", "N");
        outMap.put("dataSource", "cep");
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("castDate", "20220801");
        dataMap.put("castTime", "121212");
        dataMap.put("totalCoOutput", 123.45);
        dataMap.put("ferroCoOutput", 123.45);
        dataMap.put("nutCoOutput", 123.45);
        dataMap.put("coFinesOutput", 123.45);
        dataMap.put("waterCoFinesOutput", 123.45);
        dataMap.put("sulgaoOutput", 123.45);
        dataMap.put("coOvenGasOutput", 123.45);
        dataMap.put("totalCoOuter", 123.45);
        dataMap.put("ferroCoOuter", 123.45);
        dataMap.put("nutCoOuter", 123.45);
        dataMap.put("coFinesOuter", 123.45);
        dataMap.put("waterCoFinesOuter", 123.45);
        dataMap.put("sulgaoOuter", 123.45);
        dataMap.put("coOvenGasOuter", 123.45);
        dataMap.put("totalCoStore", 123.45);
        dataMap.put("ferroCoStore", 123.45);
        dataMap.put("nutCoStore", 123.45);
        dataMap.put("coFinesStore", 123.45);
        dataMap.put("waterCoFinesStore", 123.45);
        dataMap.put("sulgaoStore", 123.45);
        dataMap.put("planStoveNumber", 123.45);
        dataMap.put("realStoveNumber", 123.45);
        dataMap.put("dryStoveNumber", 123.45);
        dataMap.put("moistStoveNumber", 123.45);
        dataMap.put("vapourOutPut", 123.45);
        dataMap.put("vapourOuter", 123.45);
        dataMap.put("vapourStore", 123.45);
        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", PRODUCT_EXCHANGE, PRODUCT_COKING_OUTPUT_AND_OUTSOURCING_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, PRODUCT_COKING_OUTPUT_AND_OUTSOURCING_ROUTE_KEY, JSONObject.toJSONString(outMap));
    }
    /**
     * 焦化主要经济指标
     */
    public void sendCokingMainEconomicIndicatorsMessageQueue() {
        Map<String, Object> outMap = new HashMap<>(1);
        outMap.put("class", "com.icsc.oi.mq.oijcC02Listener");
        outMap.put("messageId", "OIC02");
        outMap.put("actionCode", "N");
        outMap.put("dataSource", "cep");
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("castDate", "20220801");
        dataMap.put("castTime", "121212");
        dataMap.put("totalCoRate", 23.45);
        dataMap.put("metaCoRate", 43.45);
        dataMap.put("dryCoRate", 53.45);
        dataMap.put("cokingCoal", 123);
        dataMap.put("cokingCaloric", 123);
        dataMap.put("coCoal", 123);
        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", PRODUCT_EXCHANGE, PRODUCT_COKING_MAIN_ECONOMIC_INDICATORS_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, PRODUCT_COKING_MAIN_ECONOMIC_INDICATORS_ROUTE_KEY, JSONObject.toJSONString(outMap));
    }

    /**
     * 焦化能源消耗记录
     */
    public void sendCokingEnergyConsumptionRecordMessageQueue() {
        Map<String, Object> outMap = new HashMap<>(1);
        outMap.put("class", "com.icsc.oi.mq.oijcC03Listener");
        outMap.put("messageId", "OIC03");
        outMap.put("actionCode", "N");
        outMap.put("dataSource", "cep");
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("castDate", "20220801");
        dataMap.put("castTime", "121212");
        dataMap.put("coChPower", 123.45);
        dataMap.put("coChIndustrialWater", 123.45);
        dataMap.put("coChNitrogen", 123.45);
        dataMap.put("coChSteam", 123.45);
        dataMap.put("coChDemineralizedWater", 123.45);
        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", PRODUCT_EXCHANGE, PRODUCT_COKING_ENERGY_CONSUMPTION_RECORD_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, PRODUCT_COKING_ENERGY_CONSUMPTION_RECORD_ROUTE_KEY, JSONObject.toJSONString(outMap));
    }

    /**
     * 配煤槽作业
     */
    public void sendCoalBlendingTankOperationMessageQueue() {
        Map<String, Object> outMap = new HashMap<>(1);
        outMap.put("class", "com.icsc.oi.mq.oijcC19Listener");
        outMap.put("messageId", "OIC19");
        outMap.put("actionCode", "N");
        outMap.put("dataSource", "cep");
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("castDate", "20220801");
        dataMap.put("castTime", "121212");
        dataMap.put("className", "a");
        dataMap.put("coalSystem", "a");
        dataMap.put("coalType1", "a");
        dataMap.put("coalType2", "a");
        dataMap.put("coalType3", "a");
        dataMap.put("coalType4", "a");
        dataMap.put("coalType5", "a");
        dataMap.put("coalType6", "a");
        dataMap.put("coalType7", "a");
        dataMap.put("coalType8", "a");
        dataMap.put("coalType9", "a");
        dataMap.put("coalType10", "a");
        dataMap.put("inCoal1", 1.1);
        dataMap.put("inCoal2", 1.1);
        dataMap.put("inCoal3", 1.1);
        dataMap.put("inCoal4", 1.1);
        dataMap.put("inCoal5", 1.1);
        dataMap.put("inCoal6", 1.1);
        dataMap.put("inCoal7", 1.1);
        dataMap.put("inCoal8", 1.1);
        dataMap.put("inCoal9", 1.1);
        dataMap.put("inCoal10", 1.1);
        dataMap.put("cosumptionCoal1", 1.2);
        dataMap.put("cosumptionCoal2", 1.2);
        dataMap.put("cosumptionCoal3", 1.2);
        dataMap.put("cosumptionCoal4", 1.2);
        dataMap.put("cosumptionCoal5", 1.2);
        dataMap.put("cosumptionCoal6", 1.2);
        dataMap.put("cosumptionCoal7", 1.2);
        dataMap.put("cosumptionCoal8", 1.2);
        dataMap.put("cosumptionCoal9", 1.2);
        dataMap.put("cosumptionCoal10", 1.2);
        dataMap.put("storeCoal1", 1.3);
        dataMap.put("storeCoal2", 1.3);
        dataMap.put("storeCoal3", 1.3);
        dataMap.put("storeCoal4", 1.3);
        dataMap.put("storeCoal5", 1.3);
        dataMap.put("storeCoal6", 1.3);
        dataMap.put("storeCoal7", 1.3);
        dataMap.put("storeCoal8", 1.3);
        dataMap.put("storeCoal9", 1.3);
        dataMap.put("storeCoal10", 1.3);
        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", PRODUCT_EXCHANGE, PRODUCT_COAL_BLENDING_TANK_OPERATION_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, PRODUCT_COAL_BLENDING_TANK_OPERATION_ROUTE_KEY, JSONObject.toJSONString(outMap));
    }
    /**
     * 配合煤配比及耗量
     */
    public void sendProportioningAndConsumptionOfBlendedCoalMessageQueue() {
        Map<String, Object> outMap = new HashMap<>(1);
        List<Map<String, Object>> coalDataList = new ArrayList<>();
        Map<String, Object> coalDataMap = new HashMap<>(1);

        coalDataMap.put("materialNo", "0123");
        coalDataMap.put("coalType", "a");
        coalDataMap.put("custNo", "a");
        coalDataMap.put("planMatch", 12.34);
        coalDataMap.put("actualMatch", 12.34);
        coalDataMap.put("actualFlow", 1234.5);
        coalDataList.add(coalDataMap);

        outMap.put("class", "com.icsc.oi.mq.oijcC04Listener");
        outMap.put("messageId", "OIC04");
        outMap.put("actionCode", "N");
        outMap.put("dataSource", "cep");
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("castDate", "20220801");
        dataMap.put("castTime", "121212");
        dataMap.put("className", "a");
        dataMap.put("coalSystem", "a");
        dataMap.put("gross", 1234.5);
        dataMap.put("coalData", coalDataList);
        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", PRODUCT_EXCHANGE, PRODUCT_PROPORTIONING_AND_CONSUMPTION_OF_BLENDED_COAL_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, PRODUCT_PROPORTIONING_AND_CONSUMPTION_OF_BLENDED_COAL_ROUTE_KEY, JSONObject.toJSONString(outMap));
    }
    /**
     * 煤塔作业
     */
    public void sendCoalTowerOperationMessageQueue() {
        Map<String, Object> outMap = new HashMap<>(1);
        List<Map<String, Object>> stoveDataList = new ArrayList<>();
        Map<String, Object> stoveDataMap = new HashMap<>(1);
        stoveDataMap.put("stoveOther", "a");
        stoveDataMap.put("storeCoalA", 1234.5);
        stoveDataMap.put("storeCoalB", 1234.5);
        stoveDataMap.put("storeCoalC", 1234.5);
        stoveDataMap.put("storeCoalD", 1234.5);
        stoveDataList.add(stoveDataMap);

        outMap.put("class", "com.icsc.oi.mq.oijcC18Listener");
        outMap.put("messageId", "OIC18");
        outMap.put("actionCode", "N");
        outMap.put("dataSource", "cep");
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("castDate", "20220801");
        dataMap.put("castTime", "121212");
        dataMap.put("className", "a");
        dataMap.put("coalSystem", "a");
        dataMap.put("stoveData", stoveDataList);
        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", PRODUCT_EXCHANGE, PRODUCT_COAL_TOWER_OPERATION_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, PRODUCT_COAL_TOWER_OPERATION_ROUTE_KEY, JSONObject.toJSONString(outMap));
    }
}
