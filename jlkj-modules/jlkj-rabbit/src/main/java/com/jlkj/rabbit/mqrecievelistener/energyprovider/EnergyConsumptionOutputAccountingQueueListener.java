package com.jlkj.rabbit.mqrecievelistener.energyprovider;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.core.utils.ConvertUtil;
import com.jlkj.rabbit.dto.energy.energyconsumptionoutputaccounting.InsertEnergyConsumptionOutputAccountingDTO;
import com.jlkj.rabbit.feign.energyprovider.EnergyFeignService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.jlkj.rabbit.mqconstant.EnergyConstants.*;
import static com.jlkj.rabbit.constants.JsonConstants.*;
import static com.jlkj.rabbit.constants.JsonConstants.DATASOURCE;


/**
 * @author sudeyou
 * @since 2022-09-16 16:10:10
 */
@Component
@Slf4j
public class EnergyConsumptionOutputAccountingQueueListener {

    @Resource
    EnergyFeignService energyFeignService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 能源耗用产出账务信息-报文接收
     *
     * @param msg 数据
     * @param channel 通道
     * @param message 消息
     */
    @RabbitListener(queuesToDeclare = @Queue(ENERGY_CONSUMPTION_OUTPUT_ACCOUNTING_QUEUE))
    public void energyConsumptionOutputAccountingListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:topicMode:ENERGY_CONSUMPTION_OUTPUT_ACCOUNTING_QUEUE => {}", msg);
        try {
            JSONObject json = JSONObject.parseObject(msg);
            String actionCode = json.getString(ACTIONCODE);
            String dataSource = json.getString(DATASOURCE);
            String messageId = json.getString("messageId");
            JSONObject data = JSONObject.parseObject(json.getString(KEY_DATA));
            if (ENERGY_CONSUMPTION_OUTPUT_ACCOUNTING_DATASOURCE.equals(dataSource) && ENERGY_CONSUMPTION_OUTPUT_ACCOUNTING_MESSAGE_ID.equals(messageId)) {
                if (ENERGY_CONSUMPTION_OUTPUT_ACCOUNTING_ACTION.equals(actionCode)) {
                    InsertEnergyConsumptionOutputAccountingDTO insertEnergyConsumptionOutputAccountingDTO = ConvertUtil.convertValue(data, InsertEnergyConsumptionOutputAccountingDTO.class);
                    insertEnergyConsumptionOutputAccountingDTO.setEountDate(data.get("EountDate").toString());
                    energyFeignService.insertEnergyConsumptionOutputAccounting(insertEnergyConsumptionOutputAccountingDTO, TEMP_TOKEN);
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(ENERGY_CONSUMPTION_OUTPUT_ACCOUNTING_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", ENERGY_CONSUMPTION_OUTPUT_ACCOUNTING_QUEUE, msg);
        }
    }

}
