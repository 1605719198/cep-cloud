package com.jlkj.rabbit.mqrecievelistener.financeprovider;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jlkj.common.dto.finance.gp.*;
import com.jlkj.rabbit.feign.financeprovider.FinanceperQueService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static com.jlkj.rabbit.mqconstant.Financeper.*;



/**
 * @author wang'mai
 */
@Component
@Slf4j
public class FinanceperQuery {

    @Autowired
    private FinanceperQueService financeperQueService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 板块资料数据接收
     * 操作标识:
     *  actionCode: R：修改
     * 数据来源：
     *  dataSource：fd
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(FINANCEPER_FINANCEPER))
    public void receiveListener(String msg, Channel channel, Message message) throws Exception {
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
        //判断数据来源
        if (dataSource.equals(FINANCEPER_DATASOURCE)) {
            //执行增删改操作
            executeOperation(dataArr,actionCode);
        }
        } catch (Exception e ) {
            sysQueueDlxService.saveQueueException(FINANCEPER_FINANCEPER, msg, e.toString());
            log.error("error:rabbitmq{} => {}", FINANCEPER_FINANCEPER, msg);
        }
    }
    /**
     * 执行操作
     * @param dataArr
     * @param actionCode
     * @throws Exception
     */
    private void executeOperation(JSONArray dataArr,String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //判断操作标识
        if (actionCode.equals(FINANCEPER_R)){
            //循环数组  新增
            dataArr.forEach( item -> {
                JSONObject json = (JSONObject) item;
                for (String str:json.keySet()){
                    if(str=="mainData"&&json.get(str)!=null){
                        String token ="client-test";
                        ManufacturerBaseDTO manufacturerBaseDTO = objectMapper.convertValue(json.get(str), ManufacturerBaseDTO.class);
                        financeperQueService.add1(manufacturerBaseDTO,token);
                    } else if(str=="relations"&&json.get(str)!=null){
                        JSONArray json1 = (JSONArray) json.get(str);
                        for (int i=0;i<json1.size();i++) {
                            String token ="client-test";
                            JSONObject json2 = json1.getJSONObject(i);
                            ManufacturerRelationDTO manufacturerRelationDTO = objectMapper.convertValue(json2, ManufacturerRelationDTO.class);
                            financeperQueService.add(manufacturerRelationDTO,token);
                        }
                    } else if(str=="fcData"&&json.get(str)!=null){
                        String token ="client-test";
                        ManufacturerFinanceDTO manufacturerFinanceDTO = objectMapper.convertValue(json.get(str), ManufacturerFinanceDTO.class);
                        financeperQueService.add4(manufacturerFinanceDTO,token);
                    } else if(str=="mpData"&&json.get(str)!=null){
                        String token ="client-test";
                        ManufacturerPurchaseDTO manufacturerPurchaseDTO = objectMapper.convertValue(json.get(str), ManufacturerPurchaseDTO.class);
                        financeperQueService.add3(manufacturerPurchaseDTO,token);
                        System.out.println("4");
                    } else if(str=="soData"&&json.get(str)!=null){
                        String token ="client-test";
                        ManufacturerCustomerDTO manufacturerCustomerDTO = objectMapper.convertValue(json.get(str), ManufacturerCustomerDTO.class);
                        financeperQueService.add2(manufacturerCustomerDTO,token);

                    } else if(str=="stData"&&json.get(str)!=null){
                        String token ="client-test";
                        ManufacturerTransportDTO manufacturerTransportDTO = objectMapper.convertValue(json.get(str), ManufacturerTransportDTO.class);
                        financeperQueService.add5(manufacturerTransportDTO,token);
                    }
                }
            });
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }
}
