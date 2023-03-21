package com.jlkj.rabbit.mqrecievelistener.energyprovider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.dto.energy.ee.EnergyMonthPlanOutputDTO;
import com.jlkj.rabbit.feign.energyprovider.EnergyFeignService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.jlkj.rabbit.mqconstant.EnergyConstants.*;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/5 - 14:41
 */
@Component
@Slf4j
public class EnergyMonthPlanOutputQueueListener {

    @Autowired
    EnergyFeignService energyFeignService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;

    /**
     * 月计划产量资料数据接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：emp
     * @param msg
     * @param channel
     * @param message
     * @RabbitListener(queuesToDeclare = @Queue(ENERGYMONTHPLANOUTPUT_QUEUE))
     */
    public void energyMonthPlanOutputReceiveListener(String msg, Channel channel, Message message) throws Exception {
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
        //判断数据来源是否=emp
        if (dataSource.equals(ENERGYMONTHPLAN_DATASOURCE)) {
            //执行增删改操作
            energyMonthPlanOutputExecuteOperation(dataArr,actionCode);
        }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(ENERGYMONTHPLANOUTPUT_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", ENERGYMONTHPLANOUTPUT_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param dataArr
     * @param actionCode
     * @throws Exception
     */
    private void energyMonthPlanOutputExecuteOperation(JSONArray dataArr, String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String token = "client-test";
        //判断操作标识
        if (actionCode.equals(ENERGY_N)){
            //循环数组  新增
            dataArr.forEach( item -> {
                //将item复制给EnergyMonthPlanOutputDTO
                EnergyMonthPlanOutputDTO energyMonthPlanOutputDTO = objectMapper.convertValue(item, EnergyMonthPlanOutputDTO.class);
                energyFeignService.addEnergyMonthPlanOutPut(energyMonthPlanOutputDTO,token);
            });
        } else if (actionCode.equals(ENERGY_R)) {
            //修改
            dataArr.forEach( item -> {
                EnergyMonthPlanOutputDTO energyMonthPlanOutputDTO = objectMapper.convertValue(item, EnergyMonthPlanOutputDTO.class);
                energyFeignService.updateEnergyMonthPlanOutPut(energyMonthPlanOutputDTO,token);
            });
        } else if (actionCode.equals(ENERGY_D)) {
            //删除
            dataArr.forEach( item -> {
                EnergyMonthPlanOutputDTO energyMonthPlanOutputDTO = objectMapper.convertValue(item, EnergyMonthPlanOutputDTO.class);
                List<String> id = Collections.singletonList(energyMonthPlanOutputDTO.getId());
                energyFeignService.deleteEnergyMonthPlanOutPut(id,token);
            });
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }
}
