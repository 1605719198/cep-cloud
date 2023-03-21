package com.jlkj.rabbit.mqrecievelistener.energyprovider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.dto.energy.ee.EnergyDayPlanConsumeDTO;
import com.jlkj.common.dto.energy.ee.EnergyDayPlanOutputDTO;
import com.jlkj.common.dto.energy.ee.EnergyMonthPlanConsumeDTO;
import com.jlkj.common.dto.energy.ee.EnergyMonthPlanOutputDTO;
import com.jlkj.rabbit.dto.energy.AddEnergyDayPlanConsumeDTO;
import com.jlkj.rabbit.dto.energy.AddEnergyDayPlanOutputDTO;
import com.jlkj.rabbit.dto.energy.AddEnergyMonthPlanConsumeDTO;
import com.jlkj.rabbit.dto.energy.AddEnergyMonthPlanOutputDTO;
import com.jlkj.rabbit.feign.energyprovider.EnergyFeignService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.jlkj.rabbit.mqconstant.EnergyConstants.*;


/**
 * 能源 报文 接收
 *
 * @author yzl
 */
@Component
@Slf4j
public class EnergyMessageQueueListener {

    @Autowired
    EnergyFeignService energyFeignService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;

    /**
     * 月计划能耗维护
     * 操作标识: actionCode: N：新增  R：修改 D：删除
     */
    @RabbitListener(queuesToDeclare = @Queue(ENERGY_MONTH_PLAN_ENERGY_QUEUE), errorHandler = "rabbitListenerErrorHandler")
    public void saveMonthPlanEnergyMessageQueue(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", ENERGY_MONTH_PLAN_ENERGY_QUEUE, msg);
        try {
            String token = "client-test";
            JSONObject inMsg = JSON.parseObject(msg);
            String actionCode = inMsg.getString("actionCode");
            if (inMsg.getString(DATASOURCE).equals(ENERGYMONTHPLAN_DATASOURCE)) {
                AddEnergyMonthPlanConsumeDTO dto = inMsg.getJSONObject("data").toJavaObject(AddEnergyMonthPlanConsumeDTO.class);
                EnergyMonthPlanConsumeDTO energy = new EnergyMonthPlanConsumeDTO();
                energy.setId(dto.getUuid());
                energy.setEnergyName(dto.getEnergyName());
                energy.setEnergyCode(dto.getCostCenterCode());
                energy.setTypes(dto.getTypes());
                energy.setFactoryName(dto.getFactoryName());
                energy.setFactoryArea(dto.getFactoryArea());
                energy.setPlanyearMonth(dto.getPlanyearMonth());
                energy.setPlanValue(dto.getPlanValue());
                energy.setUnit(dto.getUnit());
                energy.setMemo(dto.getMemo());
                energy.setMemoa(dto.getMemoa());
                energy.setAdvancedValue(dto.getAdvancedValue());
                energy.setLawsValue(dto.getLawsValue());
                energy.setCreateDate(dto.getCreateDate());
                energy.setCreateEmpNo(dto.getCreateEmpno());
                energy.setUpdateEmpNo(dto.getUpdateEmpno());
                energy.setUpdateDate(dto.getUpdateDate());
                if(actionCode.equals(ENERGY_N)){
                    energyFeignService.addEnergyMonthPlanConsume(energy,token);
                } else if (actionCode.equals(ENERGY_R)) {
                    energyFeignService.updateEnergyMonthPlanConsume(energy,token);
                } else if (actionCode.equals(ENERGY_D)) {
                    List<String> list =new ArrayList<>();
                    list.add(dto.getUuid());
                    energyFeignService.deleteEnergyMonthPlanConsume(list,token);
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(ENERGY_MONTH_PLAN_ENERGY_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", ENERGY_MONTH_PLAN_ENERGY_QUEUE, msg);
        }
    }


    /**
     * 月计划产量维护
     * 操作标识: actionCode: N：新增  R：修改 D：删除
     */
    @RabbitListener(queuesToDeclare = @Queue(ENERGY_MONTH_PLAN_YIELD_QUEUE), errorHandler = "rabbitListenerErrorHandler")
    public void saveMonthPlanYieldMessageQueue(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", ENERGY_MONTH_PLAN_YIELD_QUEUE, msg);
        try {
            String token = "client-test";
            JSONObject inMsg = JSON.parseObject(msg);
            String actionCode = inMsg.getString("actionCode");
            if (inMsg.getString(DATASOURCE).equals(ENERGYMONTHPLAN_DATASOURCE)) {
                AddEnergyMonthPlanOutputDTO dto = inMsg.getJSONObject("data").toJavaObject(AddEnergyMonthPlanOutputDTO.class);

                EnergyMonthPlanOutputDTO energy = new EnergyMonthPlanOutputDTO();
                energy.setId(dto.getUuid());
                energy.setSerialNo(dto.getSerialNo());
                energy.setFactoryName(dto.getFactoryName());
                energy.setFactoryArea(dto.getFactoryArea());
                energy.setPlanValue(dto.getPlanValue());
                energy.setPlanyearMonth(dto.getPlanMonth());
                energy.setUnit(dto.getUnit());
                energy.setCreateDate(dto.getCreateDate());
                energy.setCreateEmpNo(dto.getCreateEmpno());
                energy.setUpdateEmpNo(dto.getUpdateEmpno());
                energy.setUpdateDate(dto.getUpdateDate());
                energy.setMemo(dto.getMemo());
                if(actionCode.equals(ENERGY_N)){
                    energyFeignService.addEnergyMonthPlanOutPut(energy,token);
                } else if (actionCode.equals(ENERGY_R)) {
                    energyFeignService.updateEnergyMonthPlanOutPut(energy,token);
                } else if (actionCode.equals(ENERGY_D)) {
                    List<String> list =new ArrayList<>();
                    list.add(dto.getUuid());
                    energyFeignService.deleteEnergyMonthPlanOutPut(list,token);
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(ENERGY_MONTH_PLAN_YIELD_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", ENERGY_MONTH_PLAN_YIELD_QUEUE, msg);
        }
    }

    /**
     * 日计划能耗维护
     * 操作标识: actionCode: N：新增  R：修改 D：删除
     */
    @RabbitListener(queuesToDeclare = @Queue(ENERGY_DAY_PLAN_ENERGY_QUEUE), errorHandler = "rabbitListenerErrorHandler")
    public void saveDayPlanEnergyMessageQueue(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", ENERGY_DAY_PLAN_ENERGY_QUEUE, msg);
        try {
            String token = "client-test";
            JSONObject inMsg = JSON.parseObject(msg);
            String actionCode = inMsg.getString("actionCode");
            if (inMsg.getString(DATASOURCE).equals(ENERGYMONTHPLAN_DATASOURCE)) {
                AddEnergyDayPlanConsumeDTO dto = inMsg.getJSONObject("data").toJavaObject(AddEnergyDayPlanConsumeDTO.class);
                EnergyDayPlanConsumeDTO energy = new EnergyDayPlanConsumeDTO();
                energy.setId(dto.getUuid());
                energy.setEnergyName(dto.getMemob());
                energy.setEnergyCode(dto.getMemoa());
                energy.setTypes(dto.getMemoc());
                energy.setFactoryName(dto.getFactoryName());
                energy.setFactoryArea(dto.getFactoryArea());
                energy.setPlanyearMonthDay(dto.getPlanDate());
                energy.setPlanValue(dto.getPlanValue());
                energy.setUnit(dto.getUnit());
                energy.setMemo(dto.getMemo());
                energy.setMemoa(dto.getMemoa());
                energy.setAdvancedValue(dto.getAdvancedValue());
                energy.setLawsValue(dto.getLawsValue());
                energy.setCreateDate(dto.getCreateDate());
                energy.setCreateEmpNo(dto.getCreateEmpno());
                energy.setUpdateEmpNo(dto.getUpdateEmpno());
                energy.setUpdateDate(dto.getUpdateDate());
                if(actionCode.equals(ENERGY_N)){
                    energyFeignService.addEnergyDayPlanConsume(energy,token);
                } else if (actionCode.equals(ENERGY_R)) {
                    energyFeignService.updateEnergyDayPlanConsume(energy,token);
                } else if (actionCode.equals(ENERGY_D)) {
                    List<String> list =new ArrayList<>();
                    list.add(dto.getUuid());
                    energyFeignService.deleteEnergyDayPlanConsume(list,token);
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(ENERGY_DAY_PLAN_ENERGY_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", ENERGY_DAY_PLAN_ENERGY_QUEUE, msg);
        }
    }

    /**
     * 日计划产量维护
     * 操作标识: actionCode: N：新增  R：修改 D：删除
     */
    @RabbitListener(queuesToDeclare = @Queue(ENERGY_DAY_PLAN_YIELD_QUEUE), errorHandler = "rabbitListenerErrorHandler")
    public void saveDayPlanYieldMessageQueue(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", ENERGY_DAY_PLAN_YIELD_QUEUE, msg);
        try {
            String token = "client-test";
            JSONObject inMsg = JSON.parseObject(msg);
            String actionCode = inMsg.getString("actionCode");
            if (inMsg.getString(DATASOURCE).equals(ENERGYMONTHPLAN_DATASOURCE)) {
                AddEnergyDayPlanOutputDTO dto = inMsg.getJSONObject("data").toJavaObject(AddEnergyDayPlanOutputDTO.class);

                EnergyDayPlanOutputDTO energy = new EnergyDayPlanOutputDTO();
                energy.setId(dto.getUuid());
                energy.setSerialNo(dto.getSerialNo());
                energy.setFactoryName(dto.getFactoryName());
                energy.setFactoryArea(dto.getFactoryArea());
                energy.setPlanValue(dto.getPlanValue());
                energy.setPlanyearMonthDay(dto.getPlanDate());
                energy.setUnit(dto.getUnit());
                energy.setCreateDate(dto.getCreateDate());
                energy.setCreateEmpNo(dto.getCreateEmpno());
                energy.setUpdateEmpNo(dto.getUpdateEmpno());
                energy.setUpdateDate(dto.getUpdateDate());
                energy.setMemo(dto.getMemo());
                if(actionCode.equals(ENERGY_N)){
                    energyFeignService.addEnergyDayPlanOutPut(energy,token);
                } else if (actionCode.equals(ENERGY_R)) {
                    energyFeignService.updateEnergyDayPlanOutPut(energy,token);
                } else if (actionCode.equals(ENERGY_D)) {
                    List<String> list =new ArrayList<>();
                    list.add(dto.getUuid());
                    energyFeignService.deleteEnergyDayPlanOutPut(list,token);
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(ENERGY_DAY_PLAN_YIELD_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", ENERGY_DAY_PLAN_YIELD_QUEUE, msg);
        }
    }

}


