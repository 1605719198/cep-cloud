package com.jlkj.rabbit.mqrecievelistener.energyprovider;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.dto.energy.ee.EnergyTreeDTO;
import com.jlkj.rabbit.feign.energyprovider.EnergyFeignService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.jlkj.rabbit.mqconstant.EnergyConstants.*;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/6 - 10:41
 */
@Component
@Slf4j
public class EnergyTreeQueueListener {

    @Autowired
    EnergyFeignService energyFeignService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 能介资料数据接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：emp
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(ENERGYTREE_QUEUE))
    public void energyTreeReceiveListener(String msg, Channel channel, Message message) throws Exception {
        log.info("rabbitmq:topicmode:receive-messageA => {}", msg);
        try {
        //接到msg转换为json
        JSONObject json = JSONObject.parseObject(msg);
        //获取操作标识
        String actionCode = json.getString("actionCode");
        //获取数据来源
        String dataSource = json.getString("dataSource");
        //获取数据data，转换为json
        JSONObject dataObj = JSONObject.parseObject(json.getString("data"));
        //判断数据来源是否=emp
        if (dataSource.equals(ENERGYMONTHPLAN_DATASOURCE)) {
            //执行增删改操作
            energyTreeExecuteOperation(dataObj,actionCode);
        }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(ENERGYTREE_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", ENERGYTREE_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param dataObj
     * @param actionCode
     * @throws Exception
     */
    private void energyTreeExecuteOperation(JSONObject dataObj, String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String token = "client-test";
        //将item复制给EnergyTreeDTO
        EnergyTreeDTO energyTreeDTO = dataObj.toJavaObject(EnergyTreeDTO.class);
        //判断操作标识
        if (actionCode.equals(ENERGY_N)){
            energyFeignService.addEnergyTree(energyTreeDTO,token);
        } else if (actionCode.equals(ENERGY_R)) {
            //修改
            energyFeignService.updateEnergyTree(energyTreeDTO,token);
        } else if (actionCode.equals(ENERGY_D)) {
            //删除
            List<String> id = Collections.singletonList(energyTreeDTO.getItemId());
            energyFeignService.deleteEnergyTree(id,token);
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }
}
