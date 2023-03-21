package com.jlkj.rabbit.mqrecievelistener.logisticsprovider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.dto.logistics.wv.*;
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
public class LogisticsTransActualQueueListener {
    private final LogisticsFeignService logisticsFeignService;

    public LogisticsTransActualQueueListener(LogisticsFeignService logisticsFeignService) {
        this.logisticsFeignService = logisticsFeignService;
    }
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    @RabbitListener(queuesToDeclare = @Queue(LOGISTICS_TRANSPORT))
    public void receiveListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", LOGISTICS_TRANSPORT, msg);
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
            sysQueueDlxService.saveQueueException(LOGISTICS_TRANSPORT, msg, e.toString());
            log.error("error:rabbitmq{} => {}", LOGISTICS_TRANSPORT, msg);
        }
    }

    private void executeOperation(JSONArray dataArr,String actionCode) throws Exception {
        //判断操作标识
        switch (actionCode) {
            case LOGISTICS_N:
                //循环数组  新增
                dataArr.forEach(item -> {
                    //将item复制给DTO
                    JSONObject itemJson = JSONObject.parseObject(item.toString());
                    if (StringUtils.isNotNull(itemJson.getJSONObject(PLAN_DAY))) {
                        LogisticsPlanDayDTO planDayDTO = itemJson.getJSONObject(PLAN_DAY).toJavaObject(LogisticsPlanDayDTO.class);
                        logisticsFeignService.updatePlanDayByParams(planDayDTO, TOKEN);
                    }
                    if (StringUtils.isNotNull(itemJson.getJSONObject(TMEP_DAY))) {
                        LogisticsTempDayDTO tempDayDTO = itemJson.getJSONObject(TMEP_DAY).toJavaObject(LogisticsTempDayDTO.class);
                        logisticsFeignService.updateTempDayByParams(tempDayDTO,TOKEN);
                    }
                    if (StringUtils.isNotNull(itemJson.getJSONObject(PLAN_MONTH))) {
                        LogisticsPlanMonthDTO planMonthDTO  = itemJson.getJSONObject(PLAN_MONTH).toJavaObject(LogisticsPlanMonthDTO.class);
                        logisticsFeignService.updatePlanMonthByParams(planMonthDTO, TOKEN);
                    }
                    if (StringUtils.isNotNull(itemJson.getJSONObject(CAR_EXECUTE))) {
                        if (StringUtils.isNotEmpty(itemJson.getString(GOOD_TYPE))) {
                            LogisticsTransActualOtherDTO dto = new LogisticsTransActualOtherDTO();
                            switch (itemJson.getString(GOOD_TYPE)) {
                                case COKE:
                                    dto = itemJson.getJSONObject(CAR_EXECUTE).toJavaObject(LogisticsTransActualOtherDTO.class);
                                    logisticsFeignService.addActualCoke(dto,TOKEN);
                                    break;
                                case NUT:
                                    dto = itemJson.getJSONObject(CAR_EXECUTE).toJavaObject(LogisticsTransActualOtherDTO.class);
                                    logisticsFeignService.addActualNut(dto,TOKEN);
                                    break;
                                case FINES:
                                    dto = itemJson.getJSONObject(CAR_EXECUTE).toJavaObject(LogisticsTransActualOtherDTO.class);
                                    logisticsFeignService.addActualFines(dto,TOKEN);
                                    break;
                                default:
                                    break;
                            }
                        }
                        LogisticsTransActualDTO carExecuteDTO = itemJson.getJSONObject(CAR_EXECUTE).toJavaObject(LogisticsTransActualDTO.class);
                        logisticsFeignService.addTransportActual(carExecuteDTO,TOKEN);
                    }
                });
                break;
            case LOGISTICS_R:
                //修改
                dataArr.forEach(item -> {
                    //将item复制给DTO
                    JSONObject itemJson = JSONObject.parseObject(item.toString());
                    if (StringUtils.isNotNull(itemJson.getJSONObject(PLAN_DAY))) {
                        LogisticsPlanDayDTO planDayDTO = itemJson.getJSONObject(PLAN_DAY).toJavaObject(LogisticsPlanDayDTO.class);
                        logisticsFeignService.updatePlanDayByParams(planDayDTO, TOKEN);
                    }
                    if (StringUtils.isNotNull(itemJson.getJSONObject(TMEP_DAY))) {
                        LogisticsTempDayDTO tempDayDTO = itemJson.getJSONObject(TMEP_DAY).toJavaObject(LogisticsTempDayDTO.class);
                        logisticsFeignService.updateTempDayByParams(tempDayDTO,TOKEN);
                    }
                    if (StringUtils.isNotNull(itemJson.getJSONObject(PLAN_MONTH))) {
                        LogisticsPlanMonthDTO planMonthDTO  = itemJson.getJSONObject(PLAN_MONTH).toJavaObject(LogisticsPlanMonthDTO.class);
                        logisticsFeignService.updatePlanMonthByParams(planMonthDTO, TOKEN);
                    }
                    if (StringUtils.isNotNull(itemJson.getJSONObject(CAR_EXECUTE))) {
                        LogisticsTransActualDTO carExecuteDTO = itemJson.getJSONObject(CAR_EXECUTE).toJavaObject(LogisticsTransActualDTO.class);
                        logisticsFeignService.updateTransportActual(carExecuteDTO,TOKEN);
                    }
                });
                break;
            case LOGISTICS_D:
                //删除
                dataArr.forEach(item -> {
                    JSONObject itemJson = JSONObject.parseObject(item.toString());
                    LogisticsTransActualDTO carExecuteDTO = itemJson.getJSONObject(CAR_EXECUTE).toJavaObject(LogisticsTransActualDTO.class);
                    logisticsFeignService.deleteTransportActual(carExecuteDTO,TOKEN);
                });
                break;
            default:
                throw new Exception("操作标识传入错误！无法识别！");
        }
    }

}
