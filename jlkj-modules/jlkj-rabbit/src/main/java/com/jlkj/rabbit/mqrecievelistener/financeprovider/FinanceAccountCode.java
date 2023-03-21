package com.jlkj.rabbit.mqrecievelistener.financeprovider;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.dto.finance.ip.FinanceAccountCodeDTO;
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
public class FinanceAccountCode {

    @Autowired
    private FinanceperQueService financeperQueService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 板块资料数据接收
     * 操作标识:
     * dataSource：会计科目
     * @param msg  接到msg
     * @param channel 接到channel
     * @param message 接到message
     */
    @RabbitListener(queuesToDeclare = @Queue(FINANCEPER_ACCOUNTCODE))
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
        JSONArray dataArr = JSONArray.parseArray(json.getString("data"));
        //判断数据来源
        if (dataSource.equals(FINANCEPER_DATASOURCE)) {
            //执行增删改操作
            executeOperation(dataArr,actionCode);
        }
        } catch (Exception e ) {
            sysQueueDlxService.saveQueueException(FINANCEPER_ACCOUNTCODE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", FINANCEPER_ACCOUNTCODE, msg);
        }
    }
    /**
     * 执行操作
     * @param dataArr 获取数据
     * @param actionCode 获取数据
     */
    private void executeOperation(JSONArray dataArr,String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //判断操作标识
        switch (actionCode) {
            case FINANCEPER_N:
                //循环数组  新增
                dataArr.forEach(item -> {
                    JSONObject json = (JSONObject) item;
                    for (String str : json.keySet()) {
                        if ("mainData".equals(str) && json.get(str) != null) {
                            String token ="client-test";
                            FinanceAccountCodeDTO financeAccountCodeDTO = objectMapper.convertValue(json.get(str), FinanceAccountCodeDTO.class);
                            financeperQueService.addAccountCodeTree(financeAccountCodeDTO,token);
                        } else {
                            String token ="client-test";
                            FinanceAccountCodeDTO financeAccountCodeDTO = objectMapper.convertValue(json.get(str), FinanceAccountCodeDTO.class);
                            financeperQueService.addAccountCode(financeAccountCodeDTO,token);
                        }
                    }
                });
                break;
            case FINANCEPER_R:
                //修改
                dataArr.forEach(item -> {
                    JSONObject json = (JSONObject) item;
                    for (String str : json.keySet()) {
                        if ("mainData".equals(str) && json.get(str) != null) {
                            String token ="client-test";
                            FinanceAccountCodeDTO financeAccountCodeDTO = objectMapper.convertValue(json.get(str), FinanceAccountCodeDTO.class);
                            financeperQueService.updateBoardAccountCodeTree(financeAccountCodeDTO,token);
                        } else {
                            String token ="client-test";
                            FinanceAccountCodeDTO financeAccountCodeDTO = objectMapper.convertValue(json.get(str), FinanceAccountCodeDTO.class);
                            financeperQueService.updateBoardAccountCode(financeAccountCodeDTO,token);
                        }
                    }
                });
                break;
            case FINANCEPER_D:
                //删除
                dataArr.forEach(item -> {
                    JSONObject json = (JSONObject) item;
                    for (String str : json.keySet()) {
                        if ("mainData".equals(str) && json.get(str) != null) {
                            String token ="client-test";
                            FinanceAccountCodeDTO financeAccountCodeDTO = objectMapper.convertValue(json.get(str), FinanceAccountCodeDTO.class);
                            financeperQueService.deleteBoardAccountCodeTree(financeAccountCodeDTO,token);
                        } else {
                            String token ="client-test";
                            FinanceAccountCodeDTO financeAccountCodeDTO = objectMapper.convertValue(json.get(str), FinanceAccountCodeDTO.class);
                            financeperQueService.deleteBoardAccountCode(financeAccountCodeDTO,token);
                        }
                    }
                });
                break;
            default:
                throw new Exception("操作标识传入错误！无法识别！");
        }
    }
}
