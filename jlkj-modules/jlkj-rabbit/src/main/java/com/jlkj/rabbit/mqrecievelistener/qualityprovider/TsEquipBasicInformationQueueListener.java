package com.jlkj.rabbit.mqrecievelistener.qualityprovider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.dto.quality.TsEquipBasicInformationDTO;
import com.jlkj.rabbit.feign.qualityprovider.QualityFeignService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jlkj.rabbit.mqconstant.QualityConstants.*;


/**
 * @author 智能研发室 黄兵
 * @Description 质量模块试验仪器基本资料队列监听
 * @date 2022/5/11 - 8:59
 */
@Component
@Slf4j
public class TsEquipBasicInformationQueueListener {

    @Autowired
    QualityFeignService qualityFeignService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 试验仪器基本资料数据接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：erp
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(TSEQUIPBASICINFORMATION_QUEUE))
    public void tsEquipBasicInformationListener(String msg, Channel channel, Message message) throws Exception {
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
        if (dataSource.equals(QUALITY_DATASOURCE)) {
            //执行增删改操作
            tsEquipBasicInformationExecuteOperation(data, actionCode);
        }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(TSELEMENTTABLEDATA_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", TSELEMENTTABLEDATA_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param data
     * @param actionCode
     * @throws Exception
     */
    private void tsEquipBasicInformationExecuteOperation(Object data, String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //判断操作标识
        if (actionCode.equals(QUALITY_N)){
                //将item复制给TsEquipBasicInformationDTO
                TsEquipBasicInformationDTO tsEquipBasicInformationDTO = objectMapper.convertValue(data, TsEquipBasicInformationDTO.class);
                String token = "client-test";
                qualityFeignService.addTsEquipBasicInformation(tsEquipBasicInformationDTO, token);
        } else if (actionCode.equals(QUALITY_R)) {
            //修改
                TsEquipBasicInformationDTO tsEquipBasicInformationDTO = objectMapper.convertValue(data, TsEquipBasicInformationDTO.class);
                String token = "client-test";
                qualityFeignService.updateTsEquipBasicInformation(tsEquipBasicInformationDTO, token);
        } else if (actionCode.equals(QUALITY_D)) {
            //删除
                TsEquipBasicInformationDTO tsEquipBasicInformationDTO = objectMapper.convertValue(data, TsEquipBasicInformationDTO.class);
                String equipNo = tsEquipBasicInformationDTO.getEquipNo();
                String token = "client-test";
                qualityFeignService.deleteTsEquipBasicInformation(equipNo, token);
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }
}
