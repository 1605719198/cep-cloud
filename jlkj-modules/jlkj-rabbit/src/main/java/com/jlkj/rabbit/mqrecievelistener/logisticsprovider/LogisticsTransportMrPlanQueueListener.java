package com.jlkj.rabbit.mqrecievelistener.logisticsprovider;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.dto.logistics.wv.LogisticsTransportPlanMrDTO;
import com.jlkj.rabbit.feign.logisticsprovider.LogisticsFeignService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jlkj.rabbit.mqconstant.LogisticsConstants.*;

/**
 * @author Xin
 */
@Component
@Slf4j
public class LogisticsTransportMrPlanQueueListener {
    private final LogisticsFeignService logisticsFeignService;

    public LogisticsTransportMrPlanQueueListener(LogisticsFeignService logisticsFeignService) {
        this.logisticsFeignService = logisticsFeignService;
    }
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;

    @RabbitListener(queuesToDeclare = @Queue(LOGISTICS_TRANSPORT_MR))
    public void receiveListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", LOGISTICS_TRANSPORT_MR, msg);
        //接到msg转换为json
        JSONObject json = JSONObject.parseObject(msg);
        //获取操作标识
        String actionCode = json.getString(ACTION_CODE);
        //获取数据来源
        String dataSource = json.getString(DATA_SOURCE);
        //获取数据data，转换为json
        JSONObject jsonData = JSONObject.parseObject(json.getString(DATA));

        try {
            //判断数据来源是否=hr
            if (dataSource.equals(LOGISTICS_DATASOURCE)) {
                //执行增删改操作
                executeOperation(jsonData,actionCode);
            }
        } catch (Exception e) {
            //捕获异常消息
            sysQueueDlxService.saveQueueException(LOGISTICS_TRANSPORT_MR, msg, e.toString());
            log.error("error:rabbitmq{} => {}", LOGISTICS_TRANSPORT_MR, msg);
        }
    }

    /**
     * 执行增删改操作
     * @param jsonData
     * @param actionCode
     * @throws Exception
     */
    private void executeOperation(JSONObject jsonData,String actionCode) throws Exception {
        LogisticsTransportPlanMrDTO dto = jsonData.toJavaObject(LogisticsTransportPlanMrDTO.class);
        //判断操作标识
        switch (actionCode) {
            case LOGISTICS_N:
                logisticsFeignService.save(dto,TOKEN);
                break;
            case LOGISTICS_R:
                logisticsFeignService.update(dto,TOKEN);
                break;
            case LOGISTICS_D:
                logisticsFeignService.delete(dto,TOKEN);
                break;
            default:
                throw new Exception("操作标识传入错误！无法识别！");
        }
    }

}
