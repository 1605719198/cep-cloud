package com.jlkj.rabbit.mqrecievelistener.financeprovider;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jlkj.common.dto.finance.ip.FinanceProductMillDTO;
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
public class FinanceMill {
    @Autowired
    private FinanceperQueService financeperQueService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 板块资料数据接收
     * 操作标识:
     * dataSource ：产线
     * @param msg  接到msg
     * @param channel 接到channel
     * @param message 接到message
     */
    @RabbitListener(queuesToDeclare = @Queue(FINANCEPER_MILLCODE))
    public void receiveListener(String msg, Channel channel, Message message) throws Exception {
        log.info("rabbitmq:topicmode:receive-messageA => {}", msg);
        try {
            //接到msg转换为json
            JSONObject json = JSONObject.parseObject(msg);
            //获取操作标识
            String actionCode = json.getString("actionCode");
            //获取数据来源
            String dataSource = json.getString("dataSource");
            //获取数据data
            JSONObject dataArr =JSONObject.parseObject(json.getString("data")) ;
            /*        JSONArray dataArr = JSONArray.parseArray(json.getString("data"));*/
            //判断数据来源
            if (dataSource.equals(FINANCEPER_DATASOURCE)) {
                //执行增删改操作
                executeOperation(dataArr,actionCode);
            }
        } catch (Exception e ) {
            sysQueueDlxService.saveQueueException(FINANCEPER_MILLCODE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", FINANCEPER_MILLCODE, msg);
        }
    }
    /**
     * 执行操作
     * @param dataArr 获取数据
     * @param actionCode 获取数据
     */
    private void executeOperation(JSONObject dataArr,String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String token ="client-test";
        //判断操作标识
        switch (actionCode) {
            case FINANCEPER_N: {
                FinanceProductMillDTO financeProductMillDTO = objectMapper.convertValue(dataArr, FinanceProductMillDTO.class);
                financeperQueService.addMillCode(financeProductMillDTO,token);
                break;
            }
            case FINANCEPER_R: {
                //修改
                FinanceProductMillDTO financeProductMillDTO = objectMapper.convertValue(dataArr, FinanceProductMillDTO.class);
                financeperQueService.updateMillCode(financeProductMillDTO,token);
                break;
            }
            case FINANCEPER_D: {
                //删除
                FinanceProductMillDTO financeProductMillDTO = objectMapper.convertValue(dataArr, FinanceProductMillDTO.class);
                financeperQueService.deleteMillCode(financeProductMillDTO,token);
                break;
            }
            default:
                throw new Exception("操作标识传入错误！无法识别！");
        }
    }
}
