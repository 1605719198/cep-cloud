package com.jlkj.rabbit.mqrecievelistener.qualityprovider;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.dto.quality.TsExperimentRecordDTO;
import com.jlkj.rabbit.entity.QualityTsRecord;
import com.jlkj.rabbit.entity.QualityTsRecordBoiler;
import com.jlkj.rabbit.feign.qualityprovider.QualityFeignService;
import com.jlkj.rabbit.mapper.TsExperimentRecordMapper;
import com.jlkj.rabbit.service.QualityTsRecordBoilerService;
import com.jlkj.rabbit.service.QualityTsRecordService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.jlkj.rabbit.mqconstant.QualityConstants.*;

/**
 * @author 智能研发室 黄兵
 * @Description 质量模块煤质量记录队列监听
 * @date 2022/8/31 - 08:29
 */
@Component
@Slf4j
public class TsExperimentRecordCoalQueueListener {

    @Autowired
    QualityFeignService qualityFeignService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    TsExperimentRecordMapper tsExperimentRecordMapper;

    @Autowired
    QualityTsRecordService qualityTsRecordService;

    @Autowired
    QualityTsRecordBoilerService qualityTsRecordBoilerService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;

    /**
     * 业务人员维护传输焦化煤试验值时接收数据
     * 操作标识:
     *  actionCode: S：传送
     * 数据来源：
     *  dataSource：erp
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(TSEXPERIMENTRECORDCOAL_QUEUE))
    public void tsExperimentRecordListener(String msg, Channel channel, Message message) throws Exception {
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
                tsExperimentRecordExecuteOperation(data, actionCode);
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(TSEXPERIMENTRECORDCOAL_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", TSEXPERIMENTRECORDCOAL_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param data
     * @param actionCode
     * @throws Exception
     */
    private void tsExperimentRecordExecuteOperation(Object data, String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //判断操作标识
        if (actionCode.equals(QUALITY_S)){
                //将item复制给TsExperimentRecordDTO
                TsExperimentRecordDTO tsExperimentRecordDTO = objectMapper.convertValue(data, TsExperimentRecordDTO.class);
                String receiveTime = tsExperimentRecordDTO.getReceiveTime();
                DateTime parse = DateUtil.parse(receiveTime);
                String shift = tsExperimentRecordMapper.getShift(String.valueOf(parse));
                if (ObjectUtil.isNotNull(shift)) {
                    tsExperimentRecordDTO.setShift(shift);
                }
                tsExperimentRecordDTO.setJsonString(String.valueOf(data));
                qualityFeignService.addTsExperimentRecord(tsExperimentRecordDTO);
                String code = tsExperimentRecordDTO.getCode();
                String coke = "MC";
                String coal = "MA2JM";
                String boiler = "MS";
                if (coke.equals(code)) {
                    //返回焦炭质量记录
                    sendJsonMc(tsExperimentRecordDTO);
                } else if (coal.equals(code)) {
                    //返回煤质量记录
                    sendJson(tsExperimentRecordDTO);
                } else if (boiler.equals(code)) {
                    //返回锅炉水质量记录
                    sendJsonBoiler(tsExperimentRecordDTO);
                }
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }

    /**
     * 回传煤（入炉煤、装车煤）质量记录
     */
    private void sendJson(TsExperimentRecordDTO tsExperimentRecordDTO) {
        String sampleId = tsExperimentRecordDTO.getSampleId();
        String sixthLetterF = "F";
        String sixthLetterZ = "Z";
        String water = "Mt";
        String crozzle = "Ad";
        String volatileFlux = "Vdaf";
        String sulfuration = "St,ad";
        String fineness = "细度";
        String gChem = "G";
        String xChem = "X";
        String yChem = "Y";
        String substring = sampleId.substring(5, 6);
        if (substring.equals(sixthLetterF)) {
            JSONObject obj = new JSONObject();
            obj.put("class", "com.icsc.oi.mq.oijcTQC01Listener");
            obj.put("messageId", "OITQC01");
            obj.put("actionCode", "N");
            obj.put("dataSource", "cep");
            Map map = new HashMap<>(16);
            map.put("castDate", tsExperimentRecordDTO.getReceiveTime().substring(0,12));
            map.put("sampleNo", tsExperimentRecordDTO.getSampleId());
            LambdaQueryWrapper<QualityTsRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(QualityTsRecord::getSampleId, sampleId)
                        .orderByDesc(QualityTsRecord::getReceiveTime)
                        .last("limit 1");
            QualityTsRecord record = qualityTsRecordService.getOne(queryWrapper);
            String shift = record.getShift();
            if (shift == null) {
                shift = "";
            }
            map.put("className", shift);
            for ( Map<String, String> item : tsExperimentRecordDTO.getSampleValues()) {
                if (water.equals(item.get("element"))) {
                    map.put("water", item.get("value"));
                } else if (crozzle.equals(item.get("element"))) {
                    map.put("crozzle", item.get("value"));
                } else if (volatileFlux.equals(item.get("element"))) {
                    map.put("volatileFlux", item.get("value"));
                } else if (sulfuration.equals(item.get("element"))) {
                    map.put("sulfuration", item.get("value"));
                } else if (fineness.equals(item.get("element"))) {
                    map.put("fineness", item.get("value"));
                } else if (gChem.equals(item.get("element"))) {
                    map.put("gChem", item.get("value"));
                } else if (xChem.equals(item.get("element"))) {
                    map.put("xChem", item.get("value"));
                } else if (yChem.equals(item.get("element"))) {
                    map.put("yChem", item.get("value"));
                }
            }
            obj.put("data", map);
            rabbitTemplate.convertAndSend("productExchange", "cep.erp.oi.oitqc01", JSON.toJSONString(obj));
        } else if (substring.equals(sixthLetterZ)) {
            JSONObject obj = new JSONObject();
            obj.put("class", "com.icsc.oi.mq.oijcC05BListener");
            obj.put("messageId", "OIC05B");
            obj.put("actionCode", "N");
            obj.put("dataSource", "cep");
            Map map = new HashMap<>(16);
            map.put("castDate", tsExperimentRecordDTO.getReceiveTime().substring(0,12));
            map.put("sampleNo", tsExperimentRecordDTO.getSampleId());
            LambdaQueryWrapper<QualityTsRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(QualityTsRecord::getSampleId, sampleId)
                        .orderByDesc(QualityTsRecord::getReceiveTime)
                        .last("limit 1");
            QualityTsRecord record = qualityTsRecordService.getOne(queryWrapper);
            String shift = record.getShift();
            map.put("className", shift);
            for ( Map<String, String> item : tsExperimentRecordDTO.getSampleValues()) {
                if (water.equals(item.get("element"))) {
                    map.put("water", item.get("value"));
                }
            }
            obj.put("data", map);
            rabbitTemplate.convertAndSend("productExchange", "cep.erp.oi.oic05b", JSON.toJSONString(obj));
        }
    }

    /**
     * 回传焦炭质量记录
     */
    private void sendJsonMc(TsExperimentRecordDTO tsExperimentRecordDTO) {
        String sampleId = tsExperimentRecordDTO.getSampleId();
        String water = "Mt";
        String crozzle = "Ad";
        String volatileFlux = "Vdaf";
        String sulfuration = "St,ad";
        String m40 = "M40";
        String m10 = "M10";
        String cri = "CRI";
        String csr = "CSR";
        JSONObject obj = new JSONObject();
        obj.put("class", "com.icsc.oi.mq.oijcTQC02Listener");
        obj.put("messageId", "OITQC02");
        obj.put("actionCode", "N");
        obj.put("dataSource", "cep");
        Map map = new HashMap<>(16);
        map.put("castDate", tsExperimentRecordDTO.getReceiveTime().substring(0,12));
        map.put("sampleNo", tsExperimentRecordDTO.getSampleId());
        LambdaQueryWrapper<QualityTsRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QualityTsRecord::getSampleId, sampleId)
                    .orderByDesc(QualityTsRecord::getReceiveTime)
                    .last("limit 1");
        QualityTsRecord record = qualityTsRecordService.getOne(queryWrapper);
        String shift = record.getShift();
        map.put("className", shift);
        for ( Map<String, String> item : tsExperimentRecordDTO.getSampleValues()) {
            if (water.equals(item.get("element"))) {
                map.put("water", item.get("value"));
            } else if (crozzle.equals(item.get("element"))) {
                map.put("crozzle", item.get("value"));
            } else if (volatileFlux.equals(item.get("element"))) {
                map.put("volatileFlux", item.get("value"));
            } else if (sulfuration.equals(item.get("element"))) {
                map.put("sulfuration", item.get("value"));
            } else if (m40.equals(item.get("element"))) {
                map.put("m40", item.get("value"));
            } else if (m10.equals(item.get("element"))) {
                map.put("m10", item.get("value"));
            } else if (cri.equals(item.get("element"))) {
                map.put("cri", item.get("value"));
            } else if (csr.equals(item.get("element"))) {
                map.put("csr", item.get("value"));
            }
        }
        obj.put("data", map);
        rabbitTemplate.convertAndSend("productExchange", "cep.erp.oi.oitqc02", JSON.toJSONString(obj));
    }

    /**
     * 回传锅炉水质量记录
     */
    private void sendJsonBoiler(TsExperimentRecordDTO tsExperimentRecordDTO) {
        String sampleId = tsExperimentRecordDTO.getSampleId();
        String chlorideIon = "Cl-";
        String magnesiumIon = "Mg2+";
        String calciumIon = "Ca2+";
        String pH = "pH";
        String sodiumIon = "Na+";
        String totalHardness = "总硬度";
        String totalAlkalinity = "总碱度";
        String turbidity = "浊度";
        String totalPhosphorus = "总磷";
        String orthophosphate = "正磷";
        String availablePhosphorus = "有效磷";
        String sulfurMonoxide = "S0";
        String volatileAmmonia = "挥发氨";
        String sulfurIon = "S2-";
        String dissolvedOxygen = "溶解氧";
        String methylOrangeAlkalinity = "甲基橙碱度";
        String phenolphthaleinAlkalinity = "酚酞碱度";
        String oil = "油";
        String allIron = "全铁";
        String totalSaltContent = "全盐量";
        String suspendedSolids = "悬浮物";
        String sulfateRadical = "SO42-";
        String phenolic = "含酚";
        String phosphateRadical = "PO43-";
        String siliconDioxide = "SiO2";
        String conductivity = "电导率";
        String residualChlorine = "余氯";
        JSONObject obj = new JSONObject();
        obj.put("class", "com.icsc.oi.mq.oijcTQC01Listener");
        obj.put("messageId", "OITQC01");
        obj.put("actionCode", "N");
        obj.put("dataSource", "cep");
        Map map = new HashMap<>(16);
        map.put("castDate", tsExperimentRecordDTO.getReceiveTime().substring(0,12));
        map.put("sampleNo", tsExperimentRecordDTO.getSampleId());
        LambdaQueryWrapper<QualityTsRecordBoiler> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QualityTsRecordBoiler::getSampleId, sampleId)
                .orderByDesc(QualityTsRecordBoiler::getSendTime)
                .last("limit 1");
        QualityTsRecordBoiler record = qualityTsRecordBoilerService.getOne(queryWrapper);
        String shift = record.getShift();
        if (shift == null) {
            shift = "";
        }
        map.put("className", shift);
        for ( Map<String, String> item : tsExperimentRecordDTO.getSampleValues()) {
            if (chlorideIon.equals(item.get("element"))) {
                map.put("chlorideIon", item.get("value"));
            } else if (magnesiumIon.equals(item.get("element"))) {
                map.put("magnesiumIon", item.get("value"));
            } else if (calciumIon.equals(item.get("element"))) {
                map.put("calciumIon", item.get("value"));
            } else if (pH.equals(item.get("element"))) {
                map.put("pH", item.get("value"));
            } else if (sodiumIon.equals(item.get("element"))) {
                map.put("sodiumIon", item.get("value"));
            } else if (totalHardness.equals(item.get("element"))) {
                map.put("totalHardness", item.get("value"));
            } else if (totalAlkalinity.equals(item.get("element"))) {
                map.put("totalAlkalinity", item.get("value"));
            } else if (turbidity.equals(item.get("element"))) {
                map.put("turbidity", item.get("value"));
            } else if (totalPhosphorus.equals(item.get("element"))) {
                map.put("totalPhosphorus", item.get("value"));
            } else if (orthophosphate.equals(item.get("element"))) {
                map.put("orthophosphate", item.get("value"));
            } else if (availablePhosphorus.equals(item.get("element"))) {
                map.put("availablePhosphorus", item.get("value"));
            } else if (sulfurMonoxide.equals(item.get("element"))) {
                map.put("sulfurMonoxide", item.get("value"));
            } else if (volatileAmmonia.equals(item.get("element"))) {
                map.put("volatileAmmonia", item.get("value"));
            } else if (sulfurIon.equals(item.get("element"))) {
                map.put("sulfurIon", item.get("value"));
            } else if (dissolvedOxygen.equals(item.get("element"))) {
                map.put("dissolvedOxygen", item.get("value"));
            } else if (methylOrangeAlkalinity.equals(item.get("element"))) {
                map.put("methylOrangeAlkalinity", item.get("value"));
            } else if (phenolphthaleinAlkalinity.equals(item.get("element"))) {
                map.put("phenolphthaleinAlkalinity", item.get("value"));
            } else if (oil.equals(item.get("element"))) {
                map.put("oil", item.get("value"));
            } else if (allIron.equals(item.get("element"))) {
                map.put("allIron", item.get("value"));
            } else if (totalSaltContent.equals(item.get("element"))) {
                map.put("totalSaltContent", item.get("value"));
            } else if (suspendedSolids.equals(item.get("element"))) {
                map.put("suspendedSolids", item.get("value"));
            } else if (sulfateRadical.equals(item.get("element"))) {
                map.put("sulfateRadical", item.get("value"));
            } else if (phenolic.equals(item.get("element"))) {
                map.put("phenolic", item.get("value"));
            } else if (phosphateRadical.equals(item.get("element"))) {
                map.put("phosphateRadical", item.get("value"));
            } else if (siliconDioxide.equals(item.get("element"))) {
                map.put("siliconDioxide", item.get("value"));
            } else if (conductivity.equals(item.get("element"))) {
                map.put("conductivity", item.get("value"));
            } else if (residualChlorine.equals(item.get("element"))) {
                map.put("residualChlorine", item.get("value"));
            }
        }
        obj.put("data", map);
        rabbitTemplate.convertAndSend("productExchange", "cep.erp.oi.oitqc01", JSON.toJSONString(obj));
    }
}
