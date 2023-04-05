package com.jlkj.product.oi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.product.oi.service.ProductionMessageQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.product.oi.constants.RabbitConstant.*;

/**
 * @author yzl
 * @description 针对 报生产文操作 ServiceImpl
 * @createDate 2022年8月26日 09:05:00
 */
@Slf4j
@Service
@EnableScheduling
public class ProductionMessageQueueServiceImpl implements ProductionMessageQueueService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
//    @Scheduled(cron = "0 20 20 1/1 * ? ")
    public void sendCokeOvenOperationRecordMessageQueue(){
        Map<String, Object> outMap = new HashMap<>(8);
        outMap.put("class", "com.icsc.oi.mq.oijcC05Listener");
        outMap.put("messageId", "OIC05");
        outMap.put("actionCode", "N");
        outMap.put("dataSource", "cep");
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("castDate","20220826");
        dataMap.put("castTime","123010");
        dataMap.put("className","1");
        List<Map<String, Object>> cokingData = new ArrayList<>();

        Map<String, Object> cokingMapData = new HashMap<>(32);
        cokingMapData.put("cokeFurnace","1#锅炉");
        cokingMapData.put("cokeTime",1.5);
        cokingMapData.put("standTmpMachSide",100);
        cokingMapData.put("standTmpCokeSide",100);
        cokingMapData.put("totalBFGasFlux",100);
        cokingMapData.put("machSideBFGasFlux",100);
        cokingMapData.put("cokeSideBFGasFlux",100);
        cokingMapData.put("totalBFGasDrg",100);
        cokingMapData.put("machSideBFGasDrg",100);
        cokingMapData.put("cokeSideBFGasDrg",100);
        cokingMapData.put("cokeGasFlux",100);
        cokingMapData.put("cokeGasDrg",100);
        cokingMapData.put("flueSuctionMachSide",100);
        cokingMapData.put("flueSuctionCokeSide",100);
        cokingMapData.put("flueTmpMachSide",100);
        cokingMapData.put("flueTmpCokeSide",100);
        cokingMapData.put("flueOxoMachSide",1.22);
        cokingMapData.put("flueOxoCokeSide",1.12);
        cokingMapData.put("gasColctFlux",100);
        cokingMapData.put("gasColctTmp",100);
        cokingMapData.put("planOutFurnace",100);
        cokingMapData.put("realOutFurnace",100);
        cokingMapData.put("dryFurnace",100);
        cokingMapData.put("wetFurnace",100);
        cokingMapData.put("loadCoke",500.52);

        cokingData.add(cokingMapData);
        dataMap.put("cokingData",cokingData);
        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", PRODUCT_EXCHANGE, COKE_OVEN_OPERATION_RECORD_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, COKE_OVEN_OPERATION_RECORD_ROUTE_KEY, JSONObject.toJSONString(outMap));
    }


    @Override
//    @Scheduled(cron = "0 20 20 1/1 * ? ")
    public void sendCokeDryQuenchingOperationRecordMessageQueue(){
        Map<String, Object> outMap = new HashMap<>(8);
        outMap.put("class", "com.icsc.oi.mq.oijcC12Listener");
        outMap.put("messageId", "OIC12");
        outMap.put("actionCode", "N");
        outMap.put("dataSource", "cep");
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("castDate","20220826");
        dataMap.put("castTime","123011");
        dataMap.put("className","1");
        dataMap.put("cokeDryQuenSys","1");
        dataMap.put("cokeDisTem",100);
        dataMap.put("cokeDisFlux",100);
        dataMap.put("boilCirGasTemEnt",100);
        dataMap.put("boilCirGasTemExi",100);
        dataMap.put("boxRoomMaterBit",188.5);
        dataMap.put("outSteamDrg",10.11);
        dataMap.put("outSteamTem",100);
        dataMap.put("cokeDryQuenRate",99.5);
        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", PRODUCT_EXCHANGE, COKE_DRY_QUENCHING_OPERATION_RECORD_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, COKE_DRY_QUENCHING_OPERATION_RECORD_ROUTE_KEY, JSONObject.toJSONString(outMap));
    }

    @Override
//    @Scheduled(cron = "0 20 20 1/1 * ? ")
    public void sendBoilerProcessIndexMessageQueue() {
        Map<String, Object> outMap = new HashMap<>(8);
        outMap.put("class", "com.icsc.oi.mq.oijcC20Listener");
        outMap.put("messageId", "OIC20");
        outMap.put("actionCode", "N");
        outMap.put("dataSource", "cep");
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("castDate","20220826");
        dataMap.put("castTime","123012");
        dataMap.put("className","1");

        List<Map<String, Object>> boilerDataList = new ArrayList<>();
        Map<String, Object> boilerData = new HashMap<>(16);
        boilerData.put("boilerNo","1");
        boilerData.put("wind",10.123);
        boilerData.put("boilerWaterTotal",10.123);
        boilerData.put("steamOccur",10.123);
        boilerData.put("boilerInTemperature",10.123);
        boilerData.put("boilerOutTemperature",10.123);
        boilerData.put("mainSteamTemperature",10.123);
        boilerData.put("boilerWaterTemperature",10.123);
        boilerData.put("boilerInStress",10.123);
        boilerData.put("boilerOutStress",10.123);
        boilerData.put("steamPocketStress",10.123);
        boilerData.put("mainSteamStress",10.123);
        boilerData.put("so2",10.123);
        boilerData.put("chimneyOxygen",10.123);
        boilerData.put("nox",10.123);
        boilerData.put("dustiness",10.123);
        boilerDataList.add(boilerData);

        dataMap.put("boilerData",boilerDataList);
        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", PRODUCT_EXCHANGE, BOILER_PROCESS_INDEX_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, BOILER_PROCESS_INDEX_ROUTE_KEY, JSONObject.toJSONString(outMap));
    }

    @Override
//    @Scheduled(cron = "0 20 20 1/1 * ? ")
    public void sendCokeWarehouseStockRecordMessageQueue() {
        Map<String, Object> outMap = new HashMap<>(8);
        outMap.put("class", "com.icsc.oi.mq.oijcC21Listener");
        outMap.put("messageId", "OIC21");
        outMap.put("actionCode", "N");
        outMap.put("dataSource", "cep");
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("castDate","20220826");
        dataMap.put("castTime","123013");
        dataMap.put("className","1");

        dataMap.put("storeNum1",11.123);
        dataMap.put("storeNum2",12.123);
        dataMap.put("storeNum3",13.123);
        dataMap.put("storeNum4",14.123);
        dataMap.put("storeNum5",15.123);
        dataMap.put("storeNum6",16.123);
        dataMap.put("storeNum7",17.123);
        dataMap.put("storeNum8",18.123);
        dataMap.put("storeNum9",19.123);
        dataMap.put("storeNum10",20.123);

        outMap.put("data", dataMap);
        log.info("send mq message:{}:{} => {}", PRODUCT_EXCHANGE, COKE_WAREHOUSE_STOCK_RECORD_ROUTE_KEY, JSONObject.toJSONString(outMap));
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, COKE_WAREHOUSE_STOCK_RECORD_ROUTE_KEY, JSONObject.toJSONString(outMap));
    }


}
