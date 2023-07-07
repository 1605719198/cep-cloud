package com.jlkj.rabbit.mqrecievelistener.energyprovider;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.dto.energy.ee.EnergyCodeForMaterialDTO;
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
 * @Description 能源模块固液体能源代码对应料号队列监听
 * @date 2022/5/5 - 12:05
 */
@Component
@Slf4j
public class EnergyCodeFoMaterialQueueListener {

    @Autowired
    EnergyFeignService energyFeignService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;

    /**
     * 固液体能源代码对应料号数据接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：erp
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(ENERGYCODEFOMATERIAL_QUEUE))
    public void energyCodeFoMaterialReceiveListener(String msg, Channel channel, Message message) throws Exception {
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
            energyCodeFoMaterialExecuteOperation(data,actionCode);
        }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(ENERGYCODEFOMATERIAL_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", ENERGYCODEFOMATERIAL_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param data
     * @param actionCode
     * @throws Exception
     */
    private void energyCodeFoMaterialExecuteOperation(Object data, String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //判断操作标识
        if (actionCode.equals(ENERGY_N)){
                //将item复制给EnergyCodeFoMaterialDTO
                EnergyCodeForMaterialDTO energyCodeForMaterialDTO = objectMapper.convertValue(data, EnergyCodeForMaterialDTO.class);
                String token = "client-test";
                energyFeignService.addEnergyCodeFoMaterial(energyCodeForMaterialDTO, token);
        } else if (actionCode.equals(ENERGY_R)) {
            //修改
                EnergyCodeForMaterialDTO energyCodeForMaterialDTO = objectMapper.convertValue(data, EnergyCodeForMaterialDTO.class);
                String token = "client-test";
                energyFeignService.updateEnergyCodeFoMaterial(energyCodeForMaterialDTO, token);
        } else if (actionCode.equals(ENERGY_D)) {
            //删除
                EnergyCodeForMaterialDTO energyCodeForMaterialDTO = objectMapper.convertValue(data, EnergyCodeForMaterialDTO.class);
                String engyId = energyCodeForMaterialDTO.getEngyId();
                String token = "client-test";
                energyFeignService.deleteEnergyCodeFoMaterial(engyId, token);
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }
}
