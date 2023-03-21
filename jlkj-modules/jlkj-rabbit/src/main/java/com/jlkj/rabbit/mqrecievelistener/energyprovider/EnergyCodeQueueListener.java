package com.jlkj.rabbit.mqrecievelistener.energyprovider;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.dto.energy.ee.EnergyCodeDTO;
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
 * @Description 能源模块能源代码队列监听
 * @date 2022/5/2 - 9:50
 */
@Component
@Slf4j
public class EnergyCodeQueueListener {

    @Autowired
    EnergyFeignService energyFeignService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;

    /**
     * 能源代码资料数据接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：erp
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(ENERGYCODE_QUEUE))
    public void energyCodeReceiveListener(String msg, Channel channel, Message message) throws Exception {
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
        //判断数据来源是否=erp
        if (dataSource.equals(ENERGY_DATASOURCE)) {
            //执行增删改操作
            energyCodeExecuteOperation(data, actionCode);
        }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(ENERGYCODE_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", ENERGYCODE_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param data
     * @param actionCode
     * @throws Exception
     */
    private void energyCodeExecuteOperation(Object data, String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //判断操作标识
        if (actionCode.equals(ENERGY_N)){
            //循环数组  新增
                //将data复制给EnergyCodeDTO
                EnergyCodeDTO energyCodeDTO = objectMapper.convertValue(data, EnergyCodeDTO.class);
                String token = "client-test";
                energyFeignService.addEnergyCode(energyCodeDTO, token);
        } else if (actionCode.equals(ENERGY_R)) {
            //修改
                EnergyCodeDTO energyCodeDTO = objectMapper.convertValue(data, EnergyCodeDTO.class);
                String token = "client-test";
                energyFeignService.updateEnergyCode(energyCodeDTO, token);
        } else if (actionCode.equals(ENERGY_D)) {
            //删除
                EnergyCodeDTO energyCodeDTO = objectMapper.convertValue(data, EnergyCodeDTO.class);
                String engyId = energyCodeDTO.getEngyId();
                String token = "client-test";
                energyFeignService.deleteEnergyCode(engyId, token);
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }
}
