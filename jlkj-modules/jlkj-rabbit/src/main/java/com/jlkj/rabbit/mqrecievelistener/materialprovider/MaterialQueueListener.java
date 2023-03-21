package com.jlkj.rabbit.mqrecievelistener.materialprovider;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlkj.common.core.utils.ConvertUtil;
import com.jlkj.rabbit.dto.material.AddMaterialsStorageDTO;
import com.jlkj.rabbit.dto.material.AddMaterialsWeighingPerformanceDTO;
import com.jlkj.rabbit.dto.material.AddMaterialsWeighingPlanDTO;
import com.jlkj.rabbit.dto.material.DelMaterialsWeighingPlanDTO;
import com.jlkj.rabbit.dto.material.materialscokesaledeliverplan.DeleteMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.rabbit.dto.material.materialscokesaledeliverplan.InsertMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.rabbit.dto.material.materialscokesalewriteoffreal.DeleteMaterialsCokeSaleWriteOffRealDTO;
import com.jlkj.rabbit.dto.material.materialscokesalewriteoffreal.InsertMaterialsCokeSaleWriteOffRealDTO;
import com.jlkj.rabbit.dto.production.AddProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.rabbit.dto.production.UpdateProductionMaterialUnloadingPerformanceWeightDTO;
import com.jlkj.rabbit.entity.MaterialsCategory;
import com.jlkj.rabbit.entity.MaterialsCode;
import com.jlkj.rabbit.entity.SysLogs;
import com.jlkj.rabbit.feign.materialprovider.MaterialFeignService;
import com.jlkj.rabbit.feign.productionprovider.ProductionFeignService;
import com.jlkj.rabbit.service.impl.*;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.jlkj.rabbit.constants.JsonConstants.*;
import static com.jlkj.rabbit.mqconstant.EnergyConstants.ENERGY_DATASOURCE;
import static com.jlkj.rabbit.mqconstant.MaterialConstants.*;

/**
 * @author zyf
 * @Description
 * @create 2022-05-19 15:34
 */
@Component
@Slf4j
public class MaterialQueueListener {

    @Autowired
    MaterialFeignService materialFeignService;

    @Autowired
    ProductionFeignService productionFeignService;

    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;

    @Autowired
    StorageMaintainServiceImpl storageMaintainService;

    @Autowired
    MaterialsCodeServiceImpl materialsCodeService;

    @Autowired
    MaterialsCategoryServiceImpl materialsCategoryService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    SysLogsServiceImpl sysLogsService;

    /**
     * 原料检斤计划
     */
    @RabbitListener(queuesToDeclare = @Queue(MATERIAL_SWEIGHINGPLAN_QUEUE), errorHandler = "rabbitListenerErrorHandler")
    public void addMaterialsWeighingPlanListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", MATERIAL_SWEIGHINGPLAN_QUEUE, msg);
        try {
            JSONObject inMsg = JSON.parseObject(msg);
            if (inMsg.getString(DATASOURCE).equals(ENERGY_DATASOURCE)) {
                AddMaterialsWeighingPlanDTO dto = inMsg.getJSONObject(KEY_DATA).toJavaObject(AddMaterialsWeighingPlanDTO.class);
                if (KEY_N.equals(inMsg.getString(ACTIONCODE))) {
                    materialFeignService.addMaterialsWeighingPlan(dto, TEMP_TOKEN);
                }
                if (KEY_D.equals(inMsg.getString(ACTIONCODE))) {
                    materialFeignService.delMaterialsWeighingPlan(dto, TEMP_TOKEN);
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(MATERIAL_SWEIGHINGPLAN_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", MATERIAL_SWEIGHINGPLAN_QUEUE, msg);
        }
    }

    /**
     * 原料检斤实绩
     */
    @RabbitListener(queuesToDeclare = @Queue(MATERIAL_SAVEMATERIALUNLOADINGPERFORMANCE_QUEUE), errorHandler = "rabbitListenerErrorHandler")
    public void saveMaterialUnloadingPerformanceListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", MATERIAL_SAVEMATERIALUNLOADINGPERFORMANCE_QUEUE, msg);
        try {
            JSONObject inMsg = JSON.parseObject(msg);
            if (inMsg.getString(DATASOURCE).equals(ENERGY_DATASOURCE)) {
                AddMaterialsWeighingPerformanceDTO dto = inMsg.getJSONObject(KEY_DATA).toJavaObject(AddMaterialsWeighingPerformanceDTO.class);
                if (KEY_N.equals(inMsg.getString(ACTIONCODE))) {
                    materialFeignService.addMaterialsWeighingPerformance(dto, TEMP_TOKEN);

                    AddProductionMaterialUnloadingPerformanceDTO unloadingPerformance = new AddProductionMaterialUnloadingPerformanceDTO();
                    unloadingPerformance.setPlanId(dto.getPlanListNo());
                    unloadingPerformance.setPlanDate(dto.getIssueDate());
                    unloadingPerformance.setSupplierName(dto.getIssueDept());
                    unloadingPerformance.setShippingMethod(dto.getCarrierType());
                    unloadingPerformance.setVehicleNumber(dto.getCarrierNo());
                    unloadingPerformance.setMaterialName(dto.getMtrlName());
                    unloadingPerformance.setMaterialCode(dto.getMtrlNo());
                    unloadingPerformance.setMaterialWeight(dto.getNetWgt());
                    productionFeignService.saveMaterialUnloadingPerformance(unloadingPerformance, TEMP_TOKEN);
                }
                if (KEY_D.equals(inMsg.getString(ACTIONCODE))) {
                    materialFeignService.delMaterialsWeighingPerformance(dto, TEMP_TOKEN);
                    AddProductionMaterialUnloadingPerformanceDTO unloadingPerformance = new AddProductionMaterialUnloadingPerformanceDTO();
                    unloadingPerformance.setPlanId(dto.getPlanListNo());
                    productionFeignService.delMaterialUnloadingPerformance(unloadingPerformance, TEMP_TOKEN);
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(MATERIAL_SAVEMATERIALUNLOADINGPERFORMANCE_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", MATERIAL_SAVEMATERIALUNLOADINGPERFORMANCE_QUEUE, msg);
        }
    }

    /**
     * 原料入储信息
     * 入储后根据单号更新卸煤实绩
     */
    @RabbitListener(queuesToDeclare = @Queue(MATERIAL_ADDMATERIALSSTORAGE_QUEUE), errorHandler = "rabbitListenerErrorHandler")
    public void addMaterialsStorageListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", MATERIAL_ADDMATERIALSSTORAGE_QUEUE, msg);
        try {
            JSONObject inMsg = JSON.parseObject(msg);
            if (inMsg.getString(DATASOURCE).equals(ENERGY_DATASOURCE)) {
                AddMaterialsStorageDTO dto = inMsg.getJSONObject(KEY_DATA).toJavaObject(AddMaterialsStorageDTO.class);
                if (KEY_N.equals(inMsg.getString(ACTIONCODE))) {
                    materialFeignService.addMaterialsStorage(dto, TEMP_TOKEN);
                    UpdateProductionMaterialUnloadingPerformanceWeightDTO unloadingPerformance = new UpdateProductionMaterialUnloadingPerformanceWeightDTO();
                    unloadingPerformance.setPlanListNo(dto.getPlanListNo());
                    unloadingPerformance.setStgNo(dto.getStgNo());
                    unloadingPerformance.setStgName(dto.getStgName());
                    unloadingPerformance.setChkNo(dto.getChkNo());
                    unloadingPerformance.setRecvDate(dto.getRecvDate());
                    productionFeignService.updateMaterialUnloadingPerformanceWeight(unloadingPerformance, TEMP_TOKEN);
                }
                if (KEY_D.equals(inMsg.getString(ACTIONCODE))) {
                    DelMaterialsWeighingPlanDTO delDto = new DelMaterialsWeighingPlanDTO();
                    delDto.setId(dto.getPlanListNo());
                    materialFeignService.delMaterialsStorage(delDto, TEMP_TOKEN);
                }

            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(MATERIAL_ADDMATERIALSSTORAGE_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", MATERIAL_ADDMATERIALSSTORAGE_QUEUE, msg);
        }
    }

    /**
     * 料号基本资料
     */
    @RabbitListener(queuesToDeclare = @Queue(MATERIAL_BASEPROFILE_QUEUE), errorHandler = "rabbitListenerErrorHandler")
    public void addMaterialsBaseProfileListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", MATERIAL_BASEPROFILE_QUEUE, msg);
        try {
            JSONObject inMsg = JSON.parseObject(msg);
            if (inMsg.getString(DATASOURCE).equals(ENERGY_DATASOURCE)) {
                if (KEY_N.equals(inMsg.getString(ACTIONCODE))) {
                    if (StrUtil.isNotEmpty(inMsg.getJSONObject(KEY_DATA).getString("mtrlNo")) && StrUtil.isNotEmpty(inMsg.getJSONObject(KEY_DATA).getString("mtrlNo"))) {
                        MaterialsCategory entity = materialsCategoryService.getOne(new LambdaQueryWrapper<MaterialsCategory>()
                                .eq(MaterialsCategory::getCategoryCode, inMsg.getJSONObject(KEY_DATA).getString("sortId"))
                                .eq(MaterialsCategory::getDeleteFlag, 0));
                        if (entity == null) {
                            entity = new MaterialsCategory();
                            entity.setId(IdUtil.randomUUID());
                            entity.setCategoryCode(inMsg.getJSONObject(KEY_DATA).getString("sortId"));
                            entity.setCategoryName(inMsg.getJSONObject(KEY_DATA).getString("sortName"));
                            entity.setMtrltype(inMsg.getJSONObject(KEY_DATA).getString("mtrlType"));
                            entity.setDeleteFlag(0);
                            entity.setCreateTime(DateUtil.date());
                            materialsCategoryService.save(entity);
                        }
                        MaterialsCode materialsCode = new MaterialsCode();
                        materialsCode.setId(IdUtil.randomUUID());
                        materialsCode.setMaterialsCode(inMsg.getJSONObject(KEY_DATA).getString("mtrlNo"));
                        materialsCode.setMaterialsName(inMsg.getJSONObject(KEY_DATA).getString("mtrlName"));
                        materialsCode.setCategoryId(entity.getId());
                        materialsCode.setMeasurementUnit(inMsg.getJSONObject(KEY_DATA).getString("unit"));
                        materialsCode.setMtrltype(inMsg.getJSONObject(KEY_DATA).getString("mtrlType"));
                        materialsCode.setDeleteFlag(0);
                        materialsCode.setCreateTime(DateUtil.date());
                        materialsCodeService.save(materialsCode);
                    } else {
                        SysLogs sysLogs = new SysLogs();
                        sysLogs.setId(IdUtil.randomUUID());
                        sysLogs.setCreateTime(new Date());
                        sysLogs.setType("异常");
                        sysLogs.setCreateUserName("报文队列");
                        sysLogs.setContent(msg);
                        sysLogsService.save(sysLogs);
                    }
                }
                if (KEY_D.equals(inMsg.getString(ACTIONCODE))) {
                    materialsCodeService.del(inMsg.getJSONObject(KEY_DATA));
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(MATERIAL_BASEPROFILE_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", MATERIAL_BASEPROFILE_QUEUE, msg);
        }
    }

    /**
     * 物料品种资料
     */
    @RabbitListener(queuesToDeclare = @Queue(MATERIAL_CATEGORY_QUEUE), errorHandler = "rabbitListenerErrorHandler")
    public void materialsCategoryListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", MATERIAL_CATEGORY_QUEUE, msg);
        try {
            JSONObject inMsg = JSON.parseObject(msg);
            if (inMsg.getString(DATASOURCE).equals(ENERGY_DATASOURCE)) {
                if (KEY_N.equals(inMsg.getString(ACTIONCODE))) {
                    materialsCategoryService.add(inMsg.getJSONObject(KEY_DATA));
                }
                if (KEY_D.equals(inMsg.getString(ACTIONCODE))) {
                    materialsCategoryService.del(inMsg.getJSONObject(KEY_DATA));
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(MATERIAL_CATEGORY_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", MATERIAL_CATEGORY_QUEUE, msg);
        }
    }

    /**
     * 储位基本资料
     */
    @RabbitListener(queuesToDeclare = @Queue(MATERIAL_STORAGEPROFILE_QUEUE), errorHandler = "rabbitListenerErrorHandler")
    public void addMaterialsStorageProfileListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:{} => {}", MATERIAL_STORAGEPROFILE_QUEUE, msg);
        try {
            JSONObject inMsg = JSON.parseObject(msg);
            if (inMsg.getString(DATASOURCE).equals(ENERGY_DATASOURCE)) {
                if (KEY_N.equals(inMsg.getString(ACTIONCODE))) {
                    storageMaintainService.add(inMsg.getJSONObject(KEY_DATA));
                }
                if (KEY_D.equals(inMsg.getString(ACTIONCODE))) {
                    storageMaintainService.del(inMsg.getJSONObject(KEY_DATA));
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(MATERIAL_STORAGEPROFILE_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", MATERIAL_STORAGEPROFILE_QUEUE, msg);
        }
    }

    /**
     * 焦炭外售发货计划-报文接收
     *
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(MATERIAL_COKE_SALE_DELIVER_PLAN_QUEUE))
    public void materialsCokeSaleDeliverPlanListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:topicMode:MATERIAL_COKE_SALE_DELIVER_PLAN_QUEUE => {}", msg);
        try {
            JSONObject json = JSONObject.parseObject(msg);
            String actionCode = json.getString(ACTIONCODE);
            String dataSource = json.getString(DATASOURCE);
            String messageId = json.getString("messageId");
            JSONObject data = JSONObject.parseObject(json.getString(KEY_DATA));
            if (MATERIAL_COKE_SALE_DELIVER_PLAN_DATASOURCE.equals(dataSource) && MATERIAL_COKE_SALE_DELIVER_PLAN_MESSAGE_ID.equals(messageId)) {
                if (MATERIAL_COKE_SALE_DELIVER_PLAN_NEW_ACTION.equals(actionCode)) {
                    InsertMaterialsCokeSaleDeliverPlanDTO insertMaterialsCokeSaleDeliverPlanDTO = ConvertUtil.convertValue(data, InsertMaterialsCokeSaleDeliverPlanDTO.class);
                    materialFeignService.insertMaterialsCokeSaleDeliverPlanData(insertMaterialsCokeSaleDeliverPlanDTO, TEMP_TOKEN);
                } else if (MATERIAL_COKE_SALE_DELIVER_PLAN_DELETE_ACTION.equals(actionCode)) {
                    DeleteMaterialsCokeSaleDeliverPlanDTO deleteMaterialsCokeSaleDeliverPlanDTO = ConvertUtil.convertValue(data, DeleteMaterialsCokeSaleDeliverPlanDTO.class);
                    materialFeignService.deleteMaterialsCokeSaleDeliverPlanData(deleteMaterialsCokeSaleDeliverPlanDTO, TEMP_TOKEN);
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(MATERIAL_COKE_SALE_DELIVER_PLAN_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", MATERIAL_COKE_SALE_DELIVER_PLAN_QUEUE, msg);
        }
    }

    /**
     * 焦炭外售销账实际-报文接收
     *
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(MATERIAL_COKE_SALE_WRITE_OFF_REAL_QUEUE))
    public void materialsCokeSaleWriteOffRealListener(String msg, Channel channel, Message message) {
        log.info("rabbitmq:topicMode:MATERIAL_COKE_SALE_WRITE_OFF_REAL_QUEUE => {}", msg);
        try {
            JSONObject json = JSONObject.parseObject(msg);
            String actionCode = json.getString(ACTIONCODE);
            String dataSource = json.getString(DATASOURCE);
            String messageId = json.getString("messageId");
            JSONObject data = JSONObject.parseObject(json.getString(KEY_DATA));
            if (MATERIAL_COKE_SALE_WRITE_OFF_REAL_DATASOURCE.equals(dataSource) && MATERIAL_COKE_SALE_WRITE_OFF_REAL_MESSAGE_ID.equals(messageId)) {
                if (MATERIAL_COKE_SALE_WRITE_OFF_REAL_NEW_ACTION.equals(actionCode)) {
                    InsertMaterialsCokeSaleWriteOffRealDTO insertMaterialsCokeSaleWriteOffRealDTO = ConvertUtil.convertValue(data, InsertMaterialsCokeSaleWriteOffRealDTO.class);
                    materialFeignService.insertMaterialsCokeSaleWriteOffRealData(insertMaterialsCokeSaleWriteOffRealDTO, TEMP_TOKEN);
                } else if (MATERIAL_COKE_SALE_WRITE_OFF_REAL_DELETE_ACTION.equals(actionCode)) {
                    DeleteMaterialsCokeSaleWriteOffRealDTO deleteMaterialsCokeSaleWriteOffRealDTO = ConvertUtil.convertValue(data, DeleteMaterialsCokeSaleWriteOffRealDTO.class);
                    materialFeignService.deleteMaterialsCokeSaleWriteOffReal(deleteMaterialsCokeSaleWriteOffRealDTO, TEMP_TOKEN);
                }
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(MATERIAL_COKE_SALE_WRITE_OFF_REAL_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", MATERIAL_COKE_SALE_WRITE_OFF_REAL_QUEUE, msg);
        }
    }

}
