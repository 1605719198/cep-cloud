package com.jlkj.rabbit.mqrecievelistener.qualityprovider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.core.domain.R;
import com.jlkj.common.dto.quality.TsElementTableDataDTO;
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
 * @Description 质量模块试验元素表格队列监听
 * @date 2022/5/28 - 10:24
 */
@Component
@Slf4j
public class TsElementTableDataQueueListener {

    @Autowired
    QualityFeignService qualityFeignService;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 试验元素表格数据接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：erp
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(TSELEMENTTABLEDATA_QUEUE))
    public void tsElementTableDataListener(String msg, Channel channel, Message message) throws Exception {
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
        //获取数据时间戳，回传
        String formatMemo = json.getString("formatMemo");
        //判断数据来源是否=erp
        if (dataSource.equals(QUALITY_DATASOURCE)) {
            //执行增删改操作
            tsElementTableDataExecuteOperation(dataArr, actionCode, timeStamp, formatMemo);
        }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(TSELEMENTTABLEDATA_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", TSELEMENTTABLEDATA_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param dataArr
     * @param actionCode
     * @throws Exception
     */
    private void tsElementTableDataExecuteOperation(JSONArray dataArr, String actionCode, String timeStamp, String formatMemo) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = "保存成功";
        String message1 = "删除成功";
        //判断操作标识
        if (actionCode.equals(QUALITY_N)){
            //循环数组  新增
            dataArr.forEach( item -> {
                //将item复制给TsElementTableDataDTO
                TsElementTableDataDTO tsElementTableDataDTO = objectMapper.convertValue(item, TsElementTableDataDTO.class);
                tsElementTableDataDTO.setFormatMemo(formatMemo);
                String token = "client-test";
                R result = qualityFeignService.addTsElementTableData(tsElementTableDataDTO, token);
                if (message.equals(result.getMsg())) {
                    //返回数据
                    sendJson(result, actionCode, tsElementTableDataDTO, timeStamp);
                } else {
                    sendJson(result, actionCode, tsElementTableDataDTO, timeStamp);
                }
            });
        } else if (actionCode.equals(QUALITY_R)) {
            //修改
            dataArr.forEach( item -> {
                TsElementTableDataDTO tsElementTableDataDTO = objectMapper.convertValue(item, TsElementTableDataDTO.class);
                String token = "client-test";
                R result = qualityFeignService.updateTsElementTableData(tsElementTableDataDTO, token);
                if (message.equals(result.getMsg())) {
                    //返回数据
                    sendJson(result, actionCode, tsElementTableDataDTO, timeStamp);
                } else {
                    sendJson(result, actionCode, tsElementTableDataDTO, timeStamp);
                }
            });
        } else if (actionCode.equals(QUALITY_D)) {
            //删除
            dataArr.forEach( item -> {
                TsElementTableDataDTO tsElementTableDataDTO = objectMapper.convertValue(item, TsElementTableDataDTO.class);
                String formatId = tsElementTableDataDTO.getFormatId();
                String elementIndex = tsElementTableDataDTO.getElementIndex();
                String token = "client-test";
                R result = qualityFeignService.deleteTsElementTableData(formatId, elementIndex, token);
                if (message1.equals(result.getMsg())) {
                    //返回数据
                    sendJson(result, actionCode, tsElementTableDataDTO, timeStamp);
                } else {
                    sendJson(result, actionCode, tsElementTableDataDTO, timeStamp);
                }
            });
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }

    /**
     * 返回数据
     */
    private void sendJson(R result, String actionCode, TsElementTableDataDTO tsElementTableDataDTO, String timeStamp) {
        JSONObject obj = new JSONObject();
        obj.put("class", "com.icsc.ts.api.tsjc011MsgApi");
        obj.put("messageId", "TSJC011MSGAPI");
        obj.put("actionCode", actionCode);
        obj.put("dataSource", "cep");
        List<Object> ls  = new ArrayList();
        Map map = new HashMap<>(16);
        map.put("formatId", tsElementTableDataDTO.getFormatId());
        map.put("elementType", tsElementTableDataDTO.getElementType());
        map.put("elementIndex", tsElementTableDataDTO.getElementIndex());
        map.put("timeStamp", timeStamp);
        map.put("ret", result.getCode());
        map.put("msg", "");
        ls.add(map);
        obj.put("data", ls);
        rabbitTemplate.convertAndSend("qualityExchange", "cep.erp.ts.ts011", JSON.toJSONString(obj));
    }
}
