package com.jlkj.rabbit.mqrecievelistener.qualityprovider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.core.domain.R;
import com.jlkj.common.dto.quality.TsSampleBasicDataaDTO;
import com.jlkj.rabbit.dto.quality.TsSampleBasicDatabDTO;
import com.jlkj.rabbit.entity.TsSampleBasicDataB;
import com.jlkj.rabbit.feign.qualityprovider.QualityFeignService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.rabbit.mqconstant.QualityConstants.*;

/**
 * @author 智能研发室 黄兵
 * @Description 质量模块样品基本资料队列监听
 * @date 2022/5/27 - 9:25
 */
@Component
@Slf4j
public class TsSampleBasicDataQueueListener {

    @Autowired
    QualityFeignService qualityFeignService;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;

    /**
     * 样品基本资料主表数据接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：erp
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(TSSAMPLEBASICMASTERDATA_QUEUE))
    public void tsSampleBasicMasterDataListener(String msg, Channel channel, Message message) throws Exception {
        log.info("rabbitmq:topicmode:receive-messageA => {}", msg);
        try {
        //接到msg转换为json
        JSONObject json = JSONObject.parseObject(msg);
        //获取操作标识
        String actionCode = json.getString("actionCode");
        //获取数据来源
        String dataSource = json.getString("dataSource");
        //获取数据data，转换为json
        Object data = json.get("data");
        //获取数据时间戳，回传
        String timeStamp = json.getString("timeStamp");
        //判断数据来源是否=erp
        if (dataSource.equals(QUALITY_DATASOURCE)) {
            //执行增删改操作
            tsSampleBasicMasterDataExecuteOperation(data, actionCode, timeStamp);
        }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(TSSAMPLEBASICMASTERDATA_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", TSSAMPLEBASICMASTERDATA_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param data
     * @param actionCode
     * @throws Exception
     */
    private void tsSampleBasicMasterDataExecuteOperation(Object data, String actionCode, String timeStamp) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = "保存成功";
        String message1 = "删除成功";
        //判断操作标识
        if (actionCode.equals(QUALITY_N)){
            //循环数组  新增
                //将data复制给TsSampleBasicDataaDTO
                TsSampleBasicDataaDTO tsSampleBasicDataaDTO = objectMapper.convertValue(data, TsSampleBasicDataaDTO.class);
                String token = "client-test";
                R result = qualityFeignService.addTsSampleBasicMasterData(tsSampleBasicDataaDTO, token);
                if (message.equals(result.getMsg())) {
                    //返回数据
                    sendJson(result, actionCode, tsSampleBasicDataaDTO, timeStamp);
                } else {
                    sendJson(result, actionCode, tsSampleBasicDataaDTO, timeStamp);
                }
        } else if (actionCode.equals(QUALITY_R)) {
            //修改
                TsSampleBasicDataaDTO tsSampleBasicDataaDTO = objectMapper.convertValue(data, TsSampleBasicDataaDTO.class);
                String token = "client-test";
                R result = qualityFeignService.updateTsSampleBasicMasterData(tsSampleBasicDataaDTO, token);
                if (message.equals(result.getMsg())) {
                    //返回数据
                    sendJson(result, actionCode, tsSampleBasicDataaDTO, timeStamp);
                } else {
                    sendJson(result, actionCode, tsSampleBasicDataaDTO, timeStamp);
                }
        } else if (actionCode.equals(QUALITY_D)) {
            //删除
                TsSampleBasicDataaDTO tsSampleBasicDataaDTO = objectMapper.convertValue(data, TsSampleBasicDataaDTO.class);
                String sampleClass = tsSampleBasicDataaDTO.getSampleClass();
                String token = "client-test";
                R result = qualityFeignService.deleteTsSampleBasicMasterData(sampleClass, token);
                if (message1.equals(result.getMsg())) {
                    //返回数据
                    sendJson(result, actionCode, tsSampleBasicDataaDTO, timeStamp);
                } else {
                    sendJson(result, actionCode, tsSampleBasicDataaDTO, timeStamp);
                }
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }

    /**
     * 样品基本资料从表数据接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：erp
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(TSSAMPLEBASICSLAVEDATA_QUEUE))
    public void tsSampleBasicSlaveDataListener(String msg, Channel channel, Message message) throws Exception {
        log.info("rabbitmq:topicmode:receive-messageA => {}", msg);
        //接到msg转换为json
        JSONObject json = JSONObject.parseObject(msg);
        //获取操作标识
        String actionCode = json.getString("actionCode");
        //获取数据来源
        String dataSource = json.getString("dataSource");
        //获取数据data，转换为json
        JSONArray dataArr = JSONArray.parseArray(json.getString("data"));
        //获取数据时间戳，回传
        String timeStamp = json.getString("timeStamp");
        try {
            //判断数据来源是否=erp
            if (dataSource.equals(QUALITY_DATASOURCE)) {
                //执行增删改操作
                tsSampleBasicSlaveDataExecuteOperation(dataArr, actionCode, timeStamp);
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(TSSAMPLEBASICSLAVEDATA_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", TSSAMPLEBASICSLAVEDATA_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param dataArr
     * @param actionCode
     * @throws Exception
     */
    private void tsSampleBasicSlaveDataExecuteOperation(JSONArray dataArr, String actionCode, String timeStamp) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = "保存成功";
        String message1 = "删除成功";
        //判断操作标识
        if (actionCode.equals(QUALITY_N)){
            //循环数组  新增
            dataArr.forEach( item -> {
                //将item复制给TsSampleBasicDataBDTO
                TsSampleBasicDatabDTO tsSampleBasicDatabDTO = objectMapper.convertValue(item, TsSampleBasicDatabDTO.class);
                String token = "client-test";
                R result = qualityFeignService.addTsSampleBasicSlaveData(tsSampleBasicDatabDTO, token);
                if (message.equals(result.getMsg())) {
                    //返回数据
                    sendSlaveJson(result, actionCode, tsSampleBasicDatabDTO, timeStamp);
                } else {
                    sendSlaveJson(result, actionCode, tsSampleBasicDatabDTO, timeStamp);
                }
            });
        } else if (actionCode.equals(QUALITY_R)) {
            //修改
            List<TsSampleBasicDataB> sendValue = JSONObject.parseArray(dataArr.toJSONString(), TsSampleBasicDataB.class);
            TsSampleBasicDatabDTO tsSampleBasicDatabDTO = new TsSampleBasicDatabDTO();
            tsSampleBasicDatabDTO.setSendValues(sendValue);
            String token = "client-test";
            R result = qualityFeignService.updateTsSampleBasicSlaveData(tsSampleBasicDatabDTO, token);
                if (message.equals(result.getMsg())) {
                    //返回数据
                    sendSlaveJson(result, actionCode, tsSampleBasicDatabDTO, timeStamp);
                } else {
                    sendSlaveJson(result, actionCode, tsSampleBasicDatabDTO, timeStamp);
                }
        } else if (actionCode.equals(QUALITY_D)) {
            //删除
            dataArr.forEach( item -> {
                TsSampleBasicDatabDTO tsSampleBasicDatabDTO = objectMapper.convertValue(item, TsSampleBasicDatabDTO.class);
                String sampleClass = tsSampleBasicDatabDTO.getSampleClass();
                String token = "client-test";
                R result = qualityFeignService.deleteTsSampleBasicSlaveData(sampleClass, token);
                if (message1.equals(result.getMsg())) {
                    //返回数据
                    sendSlaveJson(result, actionCode, tsSampleBasicDatabDTO, timeStamp);
                } else {
                    sendSlaveJson(result, actionCode, tsSampleBasicDatabDTO, timeStamp);
                }
            });
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }

    /**
     * 返回数据
     */
    private void sendJson(R result, String actionCode, TsSampleBasicDataaDTO tsSampleBasicDataaDTO, String timeStamp) {
        JSONObject obj = new JSONObject();
        obj.put("class", "com.icsc.ts.api.tsjc001MsgApi");
        obj.put("messageId", "TSJC001MSGAPI");
        obj.put("actionCode", actionCode);
        obj.put("dataSource", "cep");
        List<Object> ls  = new ArrayList();
        Map map = new HashMap<>(16);
        map.put("sampleClass", tsSampleBasicDataaDTO.getSampleClass());
        map.put("timeStamp", timeStamp);
        map.put("sendStation", "");
        map.put("sendFormat", "");
        map.put("ret", result.getCode());
        map.put("msg", "");
        ls.add(map);
        obj.put("data", ls);
        rabbitTemplate.convertAndSend("qualityExchange", "cep.erp.ts.ts001", JSON.toJSONString(obj));
    }

    /**
     * 从表返回数据
     */
    private void sendSlaveJson(R result, String actionCode, TsSampleBasicDatabDTO tsSampleBasicDatabDTO, String timeStamp) {
        JSONObject obj = new JSONObject();
        obj.put("class", "com.icsc.ts.api.tsjc001MsgApi");
        obj.put("messageId", "TSJC001MSGAPI");
        obj.put("actionCode", actionCode);
        obj.put("dataSource", "cep");
        List<Object> ls  = new ArrayList();
        Map map = new HashMap<>(16);
        map.put("sampleClass", tsSampleBasicDatabDTO.getSampleClass());
        map.put("timeStamp", timeStamp);
        map.put("sendStation", "");
        map.put("sendFormat", "");
        map.put("ret", result.getCode());
        map.put("msg", "");
        ls.add(map);
        obj.put("data", ls);
        rabbitTemplate.convertAndSend("qualityExchange", "cep.erp.ts.ts001", JSON.toJSONString(obj));
    }
}
