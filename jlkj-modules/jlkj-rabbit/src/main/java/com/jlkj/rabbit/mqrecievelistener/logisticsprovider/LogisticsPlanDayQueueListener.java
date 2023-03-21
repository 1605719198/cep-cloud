package com.jlkj.rabbit.mqrecievelistener.logisticsprovider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.dto.logistics.wv.LogisticsPlanDayDTO;
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
public class LogisticsPlanDayQueueListener {
    private final LogisticsFeignService logisticsFeignService;

    public LogisticsPlanDayQueueListener(LogisticsFeignService logisticsFeignService) {
        this.logisticsFeignService = logisticsFeignService;
    }
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;

    /**
     * 日运输计划资料数据接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：hr
     */
    @RabbitListener(queuesToDeclare = @Queue(LOGISTICS_PLAN_DAY_QUEUE))
    public void receiveListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", LOGISTICS_PLAN_DAY_QUEUE, msg);
        //接到msg转换为json
        JSONObject json = JSONObject.parseObject(msg);
        //获取操作标识
        String actionCode = json.getString(ACTION_CODE);
        //获取数据来源
        String dataSource = json.getString(DATA_SOURCE);
        //获取数据data，转换为json
        JSONArray dataArr = JSONArray.parseArray(json.getString(DATA));

        try {
            //判断数据来源是否=hr
            if (dataSource.equals(LOGISTICS_DATASOURCE)) {
                //执行增删改操作
                executeOperation(dataArr,actionCode);
            }
        } catch (Exception e) {
            //捕获异常消息
            sysQueueDlxService.saveQueueException(LOGISTICS_PLAN_DAY_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", LOGISTICS_PLAN_DAY_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param dataArr 数组
     */
    private void executeOperation(JSONArray dataArr,String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //判断操作标识
        switch (actionCode) {
            case LOGISTICS_N:
                //循环数组  新增
                dataArr.forEach(item -> {
                    //将item复制给humanResource BoardDTO
                    LogisticsPlanDayDTO logisticsPlanDayDTO = objectMapper.convertValue(item, LogisticsPlanDayDTO.class);
                    logisticsFeignService.addPlanDayData(logisticsPlanDayDTO,TOKEN);
                });
                break;
            case LOGISTICS_R:
                //修改
                dataArr.forEach(item -> {
                    LogisticsPlanDayDTO logisticsPlanDayDTO = objectMapper.convertValue(item, LogisticsPlanDayDTO.class);
                    logisticsFeignService.updatePlanDayData(logisticsPlanDayDTO,TOKEN);
                });
                break;
            case LOGISTICS_D:
                //删除
                dataArr.forEach(item -> {
                    LogisticsPlanDayDTO logisticsPlanDayDTO = objectMapper.convertValue(item, LogisticsPlanDayDTO.class);
                    logisticsFeignService.deletePlanDayData(logisticsPlanDayDTO,TOKEN);
                });
                break;
            default:
                throw new Exception("操作标识传入错误！无法识别！");
        }
    }

}
