package com.jlkj.rabbit.mqrecievelistener.financeprovider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jlkj.common.dto.finance.ip.FinanceperMenuDTO;
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
public class FinanceperMenu {
    @Autowired
    private FinanceperQueService financeperQueService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 板块资料数据接收
     * 操作标识:
     * actionCode: A：新增  S：修改 D：删除
     * 数据来源：
     * dataSource：成本科目树
     *
     * @param msg     接到msg
     * @param channel 接到channel
     * @param message 接到message
     */
    @RabbitListener(queuesToDeclare = @Queue(FINANCEPER_ACTIONMENU))
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
            /* JSONObject dataArr = JSONObject.parseObject(json.getString("data")) ;*/
            //获取数据data
            JSONArray dataArr = JSONArray.parseArray(json.getString("data"));
            //判断数据来源
            if (dataSource.equals(FINANCEPER_DATASOURCE)) {
                //执行增删改操作
                executeOperation(dataArr, actionCode);
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(FINANCEPER_ACTIONMENU, msg, e.toString());
            log.error("error:rabbitmq{} => {}", FINANCEPER_ACTIONMENU, msg);
        }
    }

    /**
     * 执行操作
     *
     * @param dataArr    获取数据
     * @param actionCode 获取数据
     */
    private void executeOperation(JSONArray dataArr, String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //判断操作标识
        switch (actionCode) {
            case FINANCEPER_N: {
                dataArr.forEach(item -> {
                    //将item复制给FinanceperCodeDTO
                    String token = "client-test";
                    FinanceperMenuDTO financeperMenuDTO = objectMapper.convertValue(item, FinanceperMenuDTO.class);
                    financeperQueService.addMenu(financeperMenuDTO, token);
                });
                break;
            }
            case FINANCEPER_R: {
                dataArr.forEach(item -> {
                    //修改
                    String token = "client-test";
                    FinanceperMenuDTO financeperMenuDTO = objectMapper.convertValue(item, FinanceperMenuDTO.class);
                    financeperQueService.updateBoardMenu(financeperMenuDTO, token);
                });
                break;
            }
            case FINANCEPER_D: {
                dataArr.forEach(item -> {
                    //删除
                    String token = "client-test";
                    FinanceperMenuDTO financeperMenuDTO = objectMapper.convertValue(item, FinanceperMenuDTO.class);
                    financeperQueService.deleteBoardMenu(financeperMenuDTO, token);
                });
                break;
            }
            default:
                throw new Exception("操作标识传入错误！无法识别！");
        }
    }
}
