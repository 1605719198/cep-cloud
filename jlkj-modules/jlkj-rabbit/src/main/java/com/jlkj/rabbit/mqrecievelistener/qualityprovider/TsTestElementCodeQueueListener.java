package com.jlkj.rabbit.mqrecievelistener.qualityprovider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.core.domain.R;
import com.jlkj.common.dto.quality.TsTestElementCodeDTO;
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
 * @Description 质量模块试验元素代号队列监听
 * @date 2022/5/13 - 16:10
 */
@Component
@Slf4j
public class TsTestElementCodeQueueListener {

    @Autowired
    QualityFeignService qualityFeignService;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 试验元素代号数据接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：erp
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(TSTESTELEMENTCODE_QUEUE))
    public void tsTestElementCodeListener(String msg, Channel channel, Message message) throws Exception {
        log.info("rabbitmq:topicmode:receive-messageA => {}", msg);
        try {
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
        //判断数据来源是否=erp
        if (dataSource.equals(QUALITY_DATASOURCE)) {
            //执行增删改操作
            tsTestElementCodeExecuteOperation(dataArr, actionCode, timeStamp);
        }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(TSTESTELEMENTCODE_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", TSTESTELEMENTCODE_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param dataArr
     * @param actionCode
     * @throws Exception
     */
    private void tsTestElementCodeExecuteOperation(JSONArray dataArr, String actionCode, String timeStamp) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = "保存成功";
        String message1 = "删除成功";
        //判断操作标识
        if (actionCode.equals(QUALITY_N)){
            //循环数组  新增
            dataArr.forEach( item -> {
                //将item复制给TsTestElementCodeDTO
                TsTestElementCodeDTO tsTestElementCodeDTO = objectMapper.convertValue(item, TsTestElementCodeDTO.class);
                String token = "client-test";
                R result = qualityFeignService.addTsTestElementCode(tsTestElementCodeDTO, token);
                if (message.equals(result.getMsg())) {
                    //返回数据
                    sendJson(result, actionCode, tsTestElementCodeDTO, timeStamp);
                } else {
                    sendJson(result, actionCode, tsTestElementCodeDTO, timeStamp);
                }
            });
        } else if (actionCode.equals(QUALITY_R)) {
            //修改
            dataArr.forEach( item -> {
                TsTestElementCodeDTO tsTestElementCodeDTO = objectMapper.convertValue(item, TsTestElementCodeDTO.class);
                String token = "client-test";
                R result = qualityFeignService.updateTsTestElementCode(tsTestElementCodeDTO, token);
                if (message.equals(result.getMsg())) {
                    //返回数据
                    sendJson(result, actionCode, tsTestElementCodeDTO, timeStamp);
                } else {
                    sendJson(result, actionCode, tsTestElementCodeDTO, timeStamp);
                }
            });
        } else if (actionCode.equals(QUALITY_D)) {
            //删除
            dataArr.forEach( item -> {
                TsTestElementCodeDTO tsTestElementCodeDTO = objectMapper.convertValue(item, TsTestElementCodeDTO.class);
                String elementIndex = tsTestElementCodeDTO.getElementIndex();
                String token = "client-test";
                R result = qualityFeignService.deleteTsTestElementCode(elementIndex, token);
                if (message1.equals(result.getMsg())) {
                    //返回数据
                    sendJson(result, actionCode, tsTestElementCodeDTO, timeStamp);
                } else {
                    sendJson(result, actionCode, tsTestElementCodeDTO, timeStamp);
                }
            });
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }

    /**
     * 返回数据
     */
    private void sendJson(R result, String actionCode,TsTestElementCodeDTO tsTestElementCodeDTO,String timeStamp) {
        JSONObject obj = new JSONObject();
        obj.put("class", "com.icsc.ts.api.tsjc010MsgApi");
        obj.put("messageId", "TSJC010MSGAPI");
        obj.put("actionCode", actionCode);
        obj.put("dataSource", "cep");
        List<Object> ls  = new ArrayList();
        Map map = new HashMap<>(16);
        map.put("elementName", tsTestElementCodeDTO.getElementName());
        map.put("timeStamp", timeStamp);
        map.put("ret", result.getCode());
        map.put("msg", "");
        ls.add(map);
        obj.put("data", ls);
        rabbitTemplate.convertAndSend("qualityExchange", "cep.erp.ts.ts010", JSON.toJSONString(obj));
    }
}
