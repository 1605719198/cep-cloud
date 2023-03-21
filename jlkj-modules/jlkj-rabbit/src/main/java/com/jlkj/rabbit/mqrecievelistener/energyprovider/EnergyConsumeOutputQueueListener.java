package com.jlkj.rabbit.mqrecievelistener.energyprovider;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.dto.energy.ee.EnergyConsumeOutputDTO;
import com.jlkj.rabbit.feign.energyprovider.EnergyFeignService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jlkj.rabbit.mqconstant.EnergyConstants.*;

/**
 * @author 智能研发室 黄兵
 * @Description 能源模块能源耗用产出账务信息接收队列监听
 * @date 2022/6/21 - 11:00
 */
@Component
@Slf4j
public class EnergyConsumeOutputQueueListener {

    @Autowired
    EnergyFeignService energyFeignService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 能源耗用产出账务信息数据接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：mes
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(ENERGYCONSUMEOUTPUT_QUEUE))
    public void energyConsumeOutputReceiveListener(String msg, Channel channel, Message message) throws Exception {
        log.info("rabbitmq:topicmode:receive-messageA => {}", msg);
        try {
        //接到msg转换为json
        JSONObject json = JSONObject.parseObject(msg);
        //获取操作标识
        String actionCode = json.getString("actionCode");
        //获取数据data，转换为json
        Object data = json.get("data");
        //执行增删改操作
        energyConsumeOutputExecuteOperation(data, actionCode);
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(ENERGYCONSUMEOUTPUT_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", ENERGYCONSUMEOUTPUT_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param data
     * @param actionCode
     * @throws Exception
     */
    private void energyConsumeOutputExecuteOperation(Object data, String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //判断操作标识
        if (actionCode.equals(ENERGY_N)){
            //将data复制给EnergyConsumeOutputDTO
            EnergyConsumeOutputDTO energyConsumeOutputDTO = objectMapper.convertValue(data, EnergyConsumeOutputDTO.class);
            energyConsumeOutputDTO.setMessageInfo(String.valueOf(data));
            energyConsumeOutputDTO.setExecJobFunc(actionCode);
            energyFeignService.addEnergyConsumeOutput(energyConsumeOutputDTO);
        } else if (actionCode.equals(ENERGY_R)) {
            //修改
            EnergyConsumeOutputDTO energyConsumeOutputDTO = objectMapper.convertValue(data, EnergyConsumeOutputDTO.class);
            energyConsumeOutputDTO.setMessageInfo(String.valueOf(data));
            energyConsumeOutputDTO.setExecJobFunc(actionCode);
            energyFeignService.addEnergyConsumeOutput(energyConsumeOutputDTO);
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }
}
