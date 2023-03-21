package com.jlkj.rabbit.mqrecievelistener.safetyprovider;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.rabbit.feign.safetyprovider.SafetyFeignService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.jlkj.rabbit.mqconstant.QualityConstants.TSELEMENTHIGHLOWSTANDARD_QUEUE;
import static com.jlkj.rabbit.mqconstant.SafetyConstants.*;

/**
 * @author sudeyou
 * @Description 安环模块队列监听
 * @date 2022/7/11 - 9:01
 */
@Component
@Slf4j
public class SafetyQueueListener {

    @Resource
    SafetyFeignService safetyFeignService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 危险作业证编号同步
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：erp
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(SAFETY_DANGERNO_QUEUE))
    public void safetyDangerNoListener(String msg, Channel channel, Message message) {

        log.info("rabbitmq:topicmode:SAFETY_DANGERNO_QUEUE => {}", msg);
        //接到msg转换为json
        JSONObject json = JSONObject.parseObject(msg);
        //获取操作标识
        String actionCode = json.getString("action_code");
        //获取数据来源
        String dataSource = json.getString("data_source");
        //获取message_id
        String messageId = json.getString("message_id");
        //获取数据data，转换为json
        JSONObject params = JSONObject.parseObject(json.getString("data"));
        try {
            //验证actioncode和datasource
            if (SAFETY_DATASOURCE.equals(dataSource) && SAFETY_NEW_ACTION.equals(actionCode)) {
                if (SAFETY_FIRE_MESSAGE_ID.equals(messageId)) {
                    safetyFeignService.updateSafeJobFireJobCode(params);
                }
                else if (SAFETY_SPACE_MESSAGE_ID.equals(messageId)) {
                    safetyFeignService.updateSafeJobSpaceJobCode(params);
                }
                else if (SAFETY_HOISTING_MESSAGE_ID.equals(messageId)) {
                    safetyFeignService.updateSafeJobHoistingJobCode(params);
                }
                else if (SAFETY_HIGH_MESSAGE_ID.equals(messageId)) {
                    safetyFeignService.updateSafeJobHighJobCode(params);
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(SAFETY_DANGERNO_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", SAFETY_DANGERNO_QUEUE, msg);
        }
    }

}
