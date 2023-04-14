package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.*;
import com.jlkj.product.oi.dto.coalconfigmanual.*;
import com.jlkj.product.oi.feignclients.LogisticsFeignService;
import com.jlkj.product.oi.mapper.*;
import com.jlkj.product.oi.service.ProductionCoalTowerStockService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.jlkj.product.oi.constants.CommonConstant.LIMIT_ONE_ROW;
import static com.jlkj.product.oi.constants.RabbitConstant.MATERIAL_CONSUMPTION_ROUTE_KEY;
import static com.jlkj.product.oi.constants.RabbitConstant.MATERIAL_EXCHANGE;

/**
 * @author zyf
 * @description 针对表【product_oi_coal_tower_stock(生产实绩-储煤塔存量)】的数据库操作Service实现
 * @createDate 2022-12-06 10:46:56
 */
@Service
@Slf4j
public class ProductionCoalTowerStockServiceImpl extends ServiceImpl<ProductionCoalTowerStockMapper, ProductionCoalTowerStock>
        implements ProductionCoalTowerStockService {

    @Resource
    ProductionCoalTowerBlendedCoalRecordMapper coalTowerBlendedCoalRecordMapper;

    @Resource
    ProductionCoalWarehouseBlendedCoalRecordMapper coalWarehouseBlendedCoalRecordMapper;

    @Resource
    ProductionPlanConfigCokeMapper planConfigCokeMapper;

    @Resource
    ProductionPlanConfigCokeDetailMapper planConfigCokeDetailMapper;

    @Resource
    ProductionConveyingCoalRecordMapper conveyingCoalRecordMapper;

    @Resource
    ProductionConfigureCoalSpeciesPerformanceMapper configureCoalSpeciesPerformanceMapper;

    @Resource
    ProductionConfigureCoalSpeciesPerformanceDetailMapper configureCoalSpeciesPerformanceDetailMapper;

    @Resource
    MaterialsCoalStockMapper coalStockMapper;

    @Resource
    MaterialsCoalDayStockMapper coalDayStockMapper;

    @Resource
    MaterialsCodeMapper materialsCodeMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private LogisticsFeignService logisticsFeignService;

    public Object getTowerList() {
        List<Map<String, Object>> list = listMaps(new LambdaQueryWrapper<ProductionCoalTowerStock>()
                .orderByAsc(ProductionCoalTowerStock::getCoalTowerNumber));
        return AjaxResult.success(list);
    }

    public Object getTowerBlendDetailList(GetPageDTO dto) {
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<Map<String, Object>> list = coalTowerBlendedCoalRecordMapper.selectMapsPage(page, new QueryWrapper<ProductionCoalTowerBlendedCoalRecord>()
                .orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .eq(ProductionCoalTowerBlendedCoalRecord::getCoalTowerNumber, dto.getTower())
        );
        List<Map<String, Object>> records = list.getRecords();
        if (records.size() > 0) {
            for (Map<String, Object> objectMap : records) {
                try {
                    ProductionPlanConfigCoke planConfigCoke = planConfigCokeMapper.selectById(objectMap.get("coal_blending_plan_id").toString());
                    objectMap.put("coal_blending_plan_number", planConfigCoke.getPlanNumber());
                } catch (Exception e) {
                    objectMap.put("coal_blending_plan_number", "获取计划编号异常");
                }
            }
        }
        return AjaxResult.success(list);
    }

    public Object getBlendWareDetailList(GetWareHouseDTO dto) {
        List<Map<String, Object>> list = coalWarehouseBlendedCoalRecordMapper.selectMaps(new LambdaQueryWrapper<ProductionCoalWarehouseBlendedCoalRecord>()
                .eq(ProductionCoalWarehouseBlendedCoalRecord::getCoalTowerRecordId, dto.getTower_id())
        );
        return AjaxResult.success(list);
    }

    /**
     * 高恒定制
     * 根据配煤开始时间，系统自动获取配煤计划号（这个时间点之前的最后一次配煤记划）。
     * 选择配煤结束时间，根据获取到的配煤计划，生成配煤仓选择下拉列表。
     * 选择配煤仓号，根据选择的配煤仓号，找到配煤计划中对应这个仓号的煤的料号。
     * 输入配煤重量，获取当前选择的配煤仓上一次配煤的配煤后重量（如果没有上一次配煤记录，则获取当前配煤计划的，配煤仓号的，输入的配煤开始时间之前的，最后一次上煤记录的上煤后重量），作为本次配煤的配煤仓配煤前重量，如果获取不到配煤前重量，则不允许保存
     * <p>
     * 选择储煤塔号（固定下拉列表1#、2#、3#、4#），根据当前的配煤计划最后一次当前储煤塔的配煤记录的配煤后料位，作为本次配煤的储煤塔的配煤前料位，
     * 输入储煤塔配料后料位，获取当前储煤塔当前配煤计划的最后一次配煤后重量，作为本次配煤储煤塔的配煤前重量，
     * 本次配煤储煤塔的配煤后重量=本次配煤储煤塔的配煤前重量+本次配煤重量
     */
    public Object getLastConfigPlan(GetDTO dto) {
        ProductionPlanConfigCoke planConfigCoke = planConfigCokeMapper.selectOne(new QueryWrapper<ProductionPlanConfigCoke>()
                .le("date_format(plan_start_time, '%Y-%m-%d %H:%i')", dto.getStart_time())
                .lambda()
                .eq(ProductionPlanConfigCoke::getPlanState, 3)
                .orderByDesc(ProductionPlanConfigCoke::getPlanStartTime)
                .last(LIMIT_ONE_ROW));
        if (ObjectUtil.isNull(planConfigCoke)) {
            return AjaxResult.error("没有匹配的配煤计划");
        }
        List<Map<String, Object>> lastBlendedCoalRecords =
                coalTowerBlendedCoalRecordMapper.selectMaps(new LambdaQueryWrapper<ProductionCoalTowerBlendedCoalRecord>()
                        .eq(ProductionCoalTowerBlendedCoalRecord::getCoalTowerNumber, dto.getTower_number())
                        .orderByDesc(ProductionCoalTowerBlendedCoalRecord::getCreateTime)
                        .last(LIMIT_ONE_ROW));
        List<Map<String, Object>> list = planConfigCokeDetailMapper.selectMaps(new LambdaQueryWrapper<ProductionPlanConfigCokeDetail>()
                .eq(ProductionPlanConfigCokeDetail::getPlanId, planConfigCoke.getId()));
        if (list.size() < 1) {
            return AjaxResult.error("没有匹配的配煤计划详细数据");
        }
        for (Map<String, Object> stringObjectMap : list) {
            ProductionCoalWarehouseBlendedCoalRecord blendedCoalRecord =
                    coalWarehouseBlendedCoalRecordMapper.selectOne(new LambdaQueryWrapper<ProductionCoalWarehouseBlendedCoalRecord>()
                            .eq(ProductionCoalWarehouseBlendedCoalRecord::getBunkerNumber, Integer.parseInt(stringObjectMap.get("warehouse_number").toString()))
                            .orderByDesc(ProductionCoalWarehouseBlendedCoalRecord::getCreateTime)
                            .last(LIMIT_ONE_ROW));
            if (ObjectUtil.isNull(blendedCoalRecord)) {
                ProductionConveyingCoalRecord conveyingCoalRecord =
                        conveyingCoalRecordMapper.selectOne(new LambdaQueryWrapper<ProductionConveyingCoalRecord>()
                                .eq(ProductionConveyingCoalRecord::getConfigCokePlanId, planConfigCoke.getId())
                                .eq(ProductionConveyingCoalRecord::getCokeWarehouseNumber, Integer.parseInt(stringObjectMap.get("warehouse_number").toString()))
                                .le(ProductionConveyingCoalRecord::getCreateTime, dto.getStart_time())
                                .orderByDesc(ProductionConveyingCoalRecord::getCreateTime)
                                .last(LIMIT_ONE_ROW));
                if (ObjectUtil.isNull(conveyingCoalRecord)) {
                    return AjaxResult.error("已匹配到配煤计划编号：" + planConfigCoke.getPlanNumber() + "，没有匹配的配煤前重量，请检查计划的上煤实绩");
                } else {
                    stringObjectMap.put("before_stock", conveyingCoalRecord.getCokeWeightAfter());
                    stringObjectMap.put("coke_material_level_before", conveyingCoalRecord.getCokeMaterialLevelAfter());
                }
            } else {
                stringObjectMap.put("before_stock", blendedCoalRecord.getAfterStock());
                stringObjectMap.put("coke_material_level_before", blendedCoalRecord.getAfterMaterialLevel());
            }
            MaterialsCode materialsCode = materialsCodeMapper.selectById(stringObjectMap.get("coal_type_id").toString());
            if (Objects.nonNull(materialsCode)) {
                stringObjectMap.put("materials_code", materialsCode.getMaterialsCode());
            }
        }
        Map<String, Object> res = new HashMap<>(2);
        res.put("plan", planConfigCoke);
        if (lastBlendedCoalRecords.size() < 1) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("coal_weight_after", 0.00);
            jsonObject.put("coal_material_level_after", 0.00);
            res.put("tower", jsonObject);
        } else {
            res.put("tower", lastBlendedCoalRecords.get(0));
        }
        res.put("config", list);
        return AjaxResult.success(res);
    }

    /**
     * 高恒定制
     * 选择储煤塔号（固定下拉列表1#、2#、3#、4#），根据当前的配煤计划最后一次当前储煤塔的配煤记录的配煤后料位，作为本次配煤的储煤塔的配煤前料位，输入储煤塔配料后料位，获取当前储煤塔当前配煤计划的最后一次配煤后重量，作为本次配煤储煤塔的配煤前重量，本次配煤储煤塔的配煤后重量=本次配煤储煤塔的配煤前重量+本次配煤重量。根据当前登录人员，获取登录人员所在的班次、班别、上班时间、下班时间。
     * 保存配煤记录时的同时，还要根据班次、班别，更新或新增配煤实绩记录。配煤记录要区分手动记录还是自动记录。
     * 配煤实绩记录根据配煤记录，按班次、班别、上班时间、下班时间等汇总配煤重量作为这个班次班别的配煤实绩。
     * 保存储煤塔的配煤记录。同时配煤记录记录储煤塔配煤记录ID。
     * 更新本次配煤仓存量表（在上煤设计量）。使用配煤仓存量的公用队列接口更新配煤仓存量表
     */
    @Transactional(rollbackFor = Exception.class)
    public Object save(SaveOrUpdateManualDTO dto) {
        Date now = new Date();
        ProductionCoalTowerBlendedCoalRecord blendedCoalRecord = new ProductionCoalTowerBlendedCoalRecord();
        blendedCoalRecord.setId(IdUtil.randomUUID());
        blendedCoalRecord.setShiftName(dto.getShift_name());
        blendedCoalRecord.setClassName(dto.getClass_name());
        blendedCoalRecord.setShiftStartTime(DateUtil.parseDateTime(dto.getShift_start_time()));
        blendedCoalRecord.setShiftEndTime(DateUtil.parseDateTime(dto.getShift_end_time()));
        blendedCoalRecord.setCoalBlendingPlanId(dto.getPlan_id());
        blendedCoalRecord.setCoalLevelMaterialsCode(dto.getCoal_level_materials_code());
        blendedCoalRecord.setCoalLevelMaterialsName(dto.getCoal_level_materials_name());
        blendedCoalRecord.setCoalTowerNumber(dto.getCoal_tower_number());
        blendedCoalRecord.setStartTime(DateUtil.parseDateTime(dto.getStart_time()));
        blendedCoalRecord.setEndTime(DateUtil.parseDateTime(dto.getEnd_time()));
        blendedCoalRecord.setCoalWeight(dto.getCoal_weight());
        blendedCoalRecord.setCoalWeightBefor(dto.getCoal_weight_befor());
        blendedCoalRecord.setCoalWeightAfter(dto.getCoal_weight_after());
        blendedCoalRecord.setCoalMaterialLevelBefor(dto.getCoal_material_level_befor());
        blendedCoalRecord.setCoalMaterialLevelAfter(dto.getCoal_material_level_after());
        blendedCoalRecord.setDataSources(0);
        blendedCoalRecord.setCreateUserId(dto.getUser_id());
        blendedCoalRecord.setCreateUserName(dto.getUser_name());
        blendedCoalRecord.setCreateTime(now);
        coalTowerBlendedCoalRecordMapper.insert(blendedCoalRecord);

        ProductionConfigureCoalSpeciesPerformance configureCoalSpeciesPerformance =
                configureCoalSpeciesPerformanceMapper.selectOne(new LambdaQueryWrapper<ProductionConfigureCoalSpeciesPerformance>()
                        .eq(ProductionConfigureCoalSpeciesPerformance::getPlanId, dto.getPlan_id())
                        .eq(ProductionConfigureCoalSpeciesPerformance::getCoalTowerNumber, dto.getCoal_tower_number())
                        .eq(ProductionConfigureCoalSpeciesPerformance::getShiftName, dto.getShift_name())
                        .eq(ProductionConfigureCoalSpeciesPerformance::getClassName, dto.getClass_name())
                        .eq(ProductionConfigureCoalSpeciesPerformance::getShiftStartTime, dto.getShift_start_time())
                        .eq(ProductionConfigureCoalSpeciesPerformance::getShiftEndTime, dto.getShift_end_time()));
        if (ObjectUtil.isNull(configureCoalSpeciesPerformance)) {
            configureCoalSpeciesPerformance = new ProductionConfigureCoalSpeciesPerformance();
            configureCoalSpeciesPerformance.setId(IdUtil.randomUUID());
            configureCoalSpeciesPerformance.setPlanId(dto.getPlan_id());
            configureCoalSpeciesPerformance.setShiftName(dto.getShift_name());
            configureCoalSpeciesPerformance.setClassName(dto.getClass_name());
            configureCoalSpeciesPerformance.setConfigureCoalSpeciesDate(DateUtil.parseDateTime(dto.getStart_time()));
            configureCoalSpeciesPerformance.setCoalTowerNumber(dto.getCoal_tower_number());
            configureCoalSpeciesPerformance.setBeforeMaterialLevel(dto.getCoal_material_level_befor());
            configureCoalSpeciesPerformance.setAfterMaterialLevel(dto.getCoal_material_level_after());
            configureCoalSpeciesPerformance.setBeforeStock(dto.getCoal_weight_befor());
            configureCoalSpeciesPerformance.setAfterStock(dto.getCoal_weight_after());
            configureCoalSpeciesPerformance.setCoalLevelMaterialsCode(dto.getCoal_level_materials_code());
            configureCoalSpeciesPerformance.setCoalLevelMaterialsName(dto.getCoal_level_materials_name());
            configureCoalSpeciesPerformance.setStartTime(DateUtil.parseDateTime(dto.getStart_time()));
            configureCoalSpeciesPerformance.setEndTime(DateUtil.parseDateTime(dto.getEnd_time()));
            configureCoalSpeciesPerformance.setDuration(dto.getDuration());
            configureCoalSpeciesPerformance.setCoalTowerWeight(dto.getCoal_weight());
            configureCoalSpeciesPerformance.setDataSources(0);
            configureCoalSpeciesPerformance.setCreateTime(now);
            configureCoalSpeciesPerformance.setShiftStartTime(DateUtil.parseDateTime(dto.getShift_start_time()));
            configureCoalSpeciesPerformance.setShiftEndTime(DateUtil.parseDateTime(dto.getShift_end_time()));
            configureCoalSpeciesPerformanceMapper.insert(configureCoalSpeciesPerformance);
        } else {
            configureCoalSpeciesPerformance.setAfterMaterialLevel(dto.getCoal_material_level_after());
            configureCoalSpeciesPerformance.setAfterStock(dto.getCoal_weight_after());
            BigDecimal weight = configureCoalSpeciesPerformance.getCoalTowerWeight().add(dto.getCoal_weight());
            configureCoalSpeciesPerformance.setCoalTowerWeight(weight);
            BigDecimal duration = configureCoalSpeciesPerformance.getDuration().add(dto.getDuration());
            configureCoalSpeciesPerformance.setDuration(duration);
            configureCoalSpeciesPerformanceMapper.updateById(configureCoalSpeciesPerformance);
        }

        for (CoalBlendingSiloDTO coalBlendingSiloDTO : dto.getCoal_blendings()) {
            ProductionConfigureCoalSpeciesPerformanceDetail configureCoalSpeciesPerformanceDetail = new ProductionConfigureCoalSpeciesPerformanceDetail();
            configureCoalSpeciesPerformanceDetail.setId(IdUtil.randomUUID());
            configureCoalSpeciesPerformanceDetail.setMainId(configureCoalSpeciesPerformance.getId());
            configureCoalSpeciesPerformanceDetail.setShiftName(dto.getShift_name());
            configureCoalSpeciesPerformanceDetail.setClassName(dto.getClass_name());
            configureCoalSpeciesPerformanceDetail.setShiftStartTime(DateUtil.parseDateTime(dto.getShift_start_time()));
            configureCoalSpeciesPerformanceDetail.setShiftEndTime(DateUtil.parseDateTime(dto.getShift_end_time()));
            configureCoalSpeciesPerformanceDetail.setPlanId(dto.getPlan_id());
            configureCoalSpeciesPerformanceDetail.setBunkerNumber(coalBlendingSiloDTO.getBunker_number());
            configureCoalSpeciesPerformanceDetail.setCoalTowerNumber(dto.getCoal_tower_number());
            configureCoalSpeciesPerformanceDetail.setBeforeStock(coalBlendingSiloDTO.getBefore_stock());
            configureCoalSpeciesPerformanceDetail.setAfterStock(coalBlendingSiloDTO.getAfter_stock());
            configureCoalSpeciesPerformanceDetail.setCoalDeliveryDate(DateUtil.parseDateTime(dto.getStart_time()));
            configureCoalSpeciesPerformanceDetail.setStartTime(DateUtil.parseDateTime(dto.getStart_time()));
            configureCoalSpeciesPerformanceDetail.setEndTime(DateUtil.parseDateTime(dto.getEnd_time()));
            configureCoalSpeciesPerformanceDetail.setDuration(coalBlendingSiloDTO.getDuration());
            configureCoalSpeciesPerformanceDetail.setMaterialWeight(coalBlendingSiloDTO.getMaterial_weight());
            configureCoalSpeciesPerformanceDetail.setMaterialCode(coalBlendingSiloDTO.getMaterial_number());
            configureCoalSpeciesPerformanceDetail.setMaterialName(coalBlendingSiloDTO.getMaterial_name());
            configureCoalSpeciesPerformanceDetail.setDataSources(0);
            configureCoalSpeciesPerformanceDetail.setCreateUserId(dto.getUser_id());
            configureCoalSpeciesPerformanceDetail.setCreateUserName(dto.getUser_name());
            configureCoalSpeciesPerformanceDetail.setCreateTime(now);
            configureCoalSpeciesPerformanceDetail.setMaterialSmallCode(coalBlendingSiloDTO.getMaterial_small_code());
            configureCoalSpeciesPerformanceDetail.setMaterialSmallName(coalBlendingSiloDTO.getMaterial_small_name());
            configureCoalSpeciesPerformanceDetailMapper.insert(configureCoalSpeciesPerformanceDetail);

            ProductionCoalWarehouseBlendedCoalRecord warehouseBlendedCoalRecord = new ProductionCoalWarehouseBlendedCoalRecord();
            warehouseBlendedCoalRecord.setId(IdUtil.randomUUID());
            warehouseBlendedCoalRecord.setShiftName(dto.getShift_name());
            warehouseBlendedCoalRecord.setClassName(dto.getClass_name());
            warehouseBlendedCoalRecord.setShiftStartTime(DateUtil.parseDateTime(dto.getShift_start_time()));
            warehouseBlendedCoalRecord.setShiftEndTime(DateUtil.parseDateTime(dto.getShift_end_time()));
            warehouseBlendedCoalRecord.setCoalTowerRecordId(blendedCoalRecord.getId());
            warehouseBlendedCoalRecord.setBlendedCoalActualPerformanceId(configureCoalSpeciesPerformanceDetail.getId());
            warehouseBlendedCoalRecord.setPlanId(dto.getPlan_id());
            warehouseBlendedCoalRecord.setCoalLevelMaterialsCode(dto.getCoal_level_materials_code());
            warehouseBlendedCoalRecord.setCoalLevelMaterialsName(dto.getCoal_level_materials_name());
            warehouseBlendedCoalRecord.setBunkerNumber(coalBlendingSiloDTO.getBunker_number());
            warehouseBlendedCoalRecord.setCoalTowerNumber(dto.getCoal_tower_number());
            warehouseBlendedCoalRecord.setBeforeMaterialLevel(coalBlendingSiloDTO.getBefore_material_level());
            warehouseBlendedCoalRecord.setAfterMaterialLevel(coalBlendingSiloDTO.getAfter_material_level());
            warehouseBlendedCoalRecord.setBeforeStock(coalBlendingSiloDTO.getBefore_stock());
            warehouseBlendedCoalRecord.setAfterStock(coalBlendingSiloDTO.getAfter_stock());
            warehouseBlendedCoalRecord.setStartTime(DateUtil.parseDateTime(dto.getStart_time()));
            warehouseBlendedCoalRecord.setEndTime(DateUtil.parseDateTime(dto.getEnd_time()));
            warehouseBlendedCoalRecord.setDuration(coalBlendingSiloDTO.getDuration());
            warehouseBlendedCoalRecord.setMaterialWeight(coalBlendingSiloDTO.getMaterial_weight());
            warehouseBlendedCoalRecord.setMaterialCode(coalBlendingSiloDTO.getMaterial_number());
            warehouseBlendedCoalRecord.setMaterialName(coalBlendingSiloDTO.getMaterial_name());
            warehouseBlendedCoalRecord.setDataSources(0);
            warehouseBlendedCoalRecord.setCreateUserId(dto.getUser_id());
            warehouseBlendedCoalRecord.setCreateUserName(dto.getUser_name());
            warehouseBlendedCoalRecord.setCreateTime(now);
            warehouseBlendedCoalRecord.setMaterialSmallCode(coalBlendingSiloDTO.getMaterial_small_code());
            warehouseBlendedCoalRecord.setMaterialSmallName(coalBlendingSiloDTO.getMaterial_small_name());
            coalWarehouseBlendedCoalRecordMapper.insert(warehouseBlendedCoalRecord);

            MaterialsCoalStock materialsCoalStock = coalStockMapper.selectOne(new LambdaQueryWrapper<MaterialsCoalStock>()
                    .eq(MaterialsCoalStock::getMaterialsId, coalBlendingSiloDTO.getMaterial_number()));
            if (Objects.nonNull(materialsCoalStock)) {
                BigDecimal cWeight = materialsCoalStock.getInventory().subtract(coalBlendingSiloDTO.getMaterial_weight());
                materialsCoalStock.setInventory(cWeight);
                coalStockMapper.updateById(materialsCoalStock);
            } else {
                log.info("materials_mr_coal_stock未找到{}匹配的煤场存量", coalBlendingSiloDTO.getMaterial_number());
            }
            MaterialsCoalDayStock materialsCoalDayStock = coalDayStockMapper.selectOne(new LambdaQueryWrapper<MaterialsCoalDayStock>()
                    .eq(MaterialsCoalDayStock::getMaterialsId, coalBlendingSiloDTO.getMaterial_number())
                    .eq(MaterialsCoalDayStock::getStatDate, DateUtil.formatDate(now)));
            if (Objects.nonNull(materialsCoalDayStock)) {
                BigDecimal cWeight = materialsCoalDayStock.getInventory().subtract(coalBlendingSiloDTO.getMaterial_weight());
                materialsCoalDayStock.setInventory(cWeight);
                coalDayStockMapper.updateById(materialsCoalDayStock);
            } else {
                log.info("materials_mr_coal_day_stock未找到{}匹配的煤场存量", coalBlendingSiloDTO.getMaterial_number());
            }
        }

        ProductionCoalTowerStock coalTowerStock = getOne(new LambdaQueryWrapper<ProductionCoalTowerStock>()
                .eq(ProductionCoalTowerStock::getCoalTowerNumber, dto.getCoal_tower_number()));
        if (ObjectUtil.isNull(coalTowerStock)) {
            coalTowerStock = new ProductionCoalTowerStock();
            coalTowerStock.setCoalTowerNumber(dto.getCoal_tower_number());
            coalTowerStock.setCoalSurplus(dto.getCoal_weight());
            coalTowerStock.setMaterialLevel(dto.getCoal_material_level_after());
            save(coalTowerStock);
        } else {
            coalTowerStock.setCoalSurplus(dto.getCoal_weight_after());
            coalTowerStock.setMaterialLevel(dto.getCoal_material_level_after());
            updateById(coalTowerStock);
        }
        sendQueueMessage(dto, now, "N");
        return AjaxResult.success();
    }

    /**
     * 高恒定制
     * 只能删除配煤实记录的最后一条记录。删除时，判断不是当前登录人员创建的记录不能删除，不是最后一条记录不能删除，自动采集数据不能删除。
     * 删除配煤记录后，配煤实绩和储煤塔配煤记录跟着更新。不影响上煤记录。
     * 更新本次配煤仓存量表（在上煤设计量）。使用配煤仓存量的公用队列接口更新配煤仓存量表。
     */
    @Transactional(rollbackFor = Exception.class)
    public Object del(DelDTO dto) {
        Date now = new Date();
        ProductionCoalTowerBlendedCoalRecord blendedCoalRecordLast =
                coalTowerBlendedCoalRecordMapper.selectOne(new LambdaQueryWrapper<ProductionCoalTowerBlendedCoalRecord>()
                        .orderByDesc(ProductionCoalTowerBlendedCoalRecord::getCreateTime)
                        .last(LIMIT_ONE_ROW));
        if (!blendedCoalRecordLast.getId().equals(dto.getId())) {
            return AjaxResult.error("非最近添加的记录不能删除");
        }
        ProductionCoalTowerBlendedCoalRecord blendedCoalRecord = coalTowerBlendedCoalRecordMapper.selectById(dto.getId());
        if (!blendedCoalRecord.getDataSources().equals(0)) {
            return AjaxResult.error("非手动采集记录不能删除");
        }
        if (!blendedCoalRecord.getCreateUserId().equals(dto.getUser_id())) {
            return AjaxResult.error("非本人创建记录不能删除");
        }
        SaveOrUpdateManualDTO sDto = new SaveOrUpdateManualDTO();
        sDto.setShift_name(blendedCoalRecordLast.getShiftName());
        sDto.setCoal_level_materials_code(blendedCoalRecordLast.getCoalLevelMaterialsCode());
        List<CoalBlendingSiloDTO> cList = new ArrayList<>();
        List<ProductionCoalWarehouseBlendedCoalRecord> recordsList =
                coalWarehouseBlendedCoalRecordMapper.selectList(new LambdaQueryWrapper<ProductionCoalWarehouseBlendedCoalRecord>()
                        .eq(ProductionCoalWarehouseBlendedCoalRecord::getCoalTowerRecordId, blendedCoalRecord.getId()));
        ArrayList<String> idsPerformance = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        for (ProductionCoalWarehouseBlendedCoalRecord record : recordsList) {
            idsPerformance.add(record.getBlendedCoalActualPerformanceId());
            ids.add(record.getId());
            CoalBlendingSiloDTO cDto = new CoalBlendingSiloDTO();
            cDto.setMaterial_weight(record.getMaterialWeight());
            cDto.setMaterial_number(record.getMaterialNumber());
            cList.add(cDto);
        }
        sDto.setCoal_blendings(cList);
        configureCoalSpeciesPerformanceDetailMapper.deleteBatchIds(idsPerformance);
        ProductionConfigureCoalSpeciesPerformance configureCoalSpeciesPerformance =
                configureCoalSpeciesPerformanceMapper.selectOne(new LambdaQueryWrapper<ProductionConfigureCoalSpeciesPerformance>()
                        .eq(ProductionConfigureCoalSpeciesPerformance::getPlanId, blendedCoalRecord.getCoalBlendingPlanId())
                        .eq(ProductionConfigureCoalSpeciesPerformance::getCoalTowerNumber, blendedCoalRecord.getCoalTowerNumber())
                        .eq(ProductionConfigureCoalSpeciesPerformance::getShiftName, blendedCoalRecord.getShiftName())
                        .eq(ProductionConfigureCoalSpeciesPerformance::getClassName, blendedCoalRecord.getClassName())
                        .eq(ProductionConfigureCoalSpeciesPerformance::getShiftStartTime, blendedCoalRecord.getShiftStartTime())
                        .eq(ProductionConfigureCoalSpeciesPerformance::getShiftEndTime, blendedCoalRecord.getShiftEndTime()));
        BigDecimal weightPerformance = configureCoalSpeciesPerformance.getAfterStock().subtract(blendedCoalRecord.getCoalWeight());
        BigDecimal towerWeightPerformance = configureCoalSpeciesPerformance.getCoalTowerWeight().subtract(blendedCoalRecord.getCoalWeight());
///        BigDecimal sumLevel = blendedCoalRecord.getCoalMaterialLevelAfter().subtract(blendedCoalRecord.getCoalMaterialLevelBefor());
///        BigDecimal towerLevelPerformance = configureCoalSpeciesPerformance.getAfterMaterialLevel().subtract(sumLevel);
        configureCoalSpeciesPerformance.setAfterStock(weightPerformance);
        configureCoalSpeciesPerformance.setCoalTowerWeight(towerWeightPerformance);
///        configureCoalSpeciesPerformance.setAfterMaterialLevel(towerLevelPerformance);
        configureCoalSpeciesPerformanceMapper.updateById(configureCoalSpeciesPerformance);

        ProductionCoalTowerStock coalTowerStock = getOne(new LambdaQueryWrapper<ProductionCoalTowerStock>()
                .eq(ProductionCoalTowerStock::getCoalTowerNumber, blendedCoalRecord.getCoalTowerNumber()));
        BigDecimal towerWeight = coalTowerStock.getCoalSurplus().subtract(blendedCoalRecord.getCoalWeight());
///        BigDecimal towerLevel = coalTowerStock.getMaterialLevel().subtract(towerLevelPerformance);
        coalTowerStock.setCoalSurplus(towerWeight);
///        coalTowerStock.setMaterialLevel(towerLevel);
        updateById(coalTowerStock);

        coalTowerBlendedCoalRecordMapper.deleteById(blendedCoalRecord);
        coalWarehouseBlendedCoalRecordMapper.deleteBatchIds(ids);
        sendQueueMessage(sDto, now, "D");
        return AjaxResult.success();
    }

    public Object getPlanConfigCoke() {
        List<Map<String, Object>> list = planConfigCokeMapper.selectMaps(new LambdaQueryWrapper<ProductionPlanConfigCoke>()
                .eq(ProductionPlanConfigCoke::getPlanState, 2)
                .or(true)
                .eq(ProductionPlanConfigCoke::getPlanState, 3)
                .orderByDesc(ProductionPlanConfigCoke::getPlanState));
        if (list.size() > 0) {
            for (Map<String, Object> objectMap : list) {
                List<Map<String, Object>> childList = planConfigCokeDetailMapper.selectMaps(new LambdaQueryWrapper<ProductionPlanConfigCokeDetail>()
                        .eq(ProductionPlanConfigCokeDetail::getPlanId, objectMap.get("id").toString())
                        .orderByAsc(ProductionPlanConfigCokeDetail::getWarehouseNumber));
                objectMap.put("detail_list", childList);
            }
        }
        return AjaxResult.success(list);
    }

    @Async
    public void sendQueueMessage(SaveOrUpdateManualDTO dto, Date now, String stat) {
        for (int i = 0; i < dto.getCoal_blendings().size(); i++) {
            try {
                Map<String, Object> outMap = new HashMap<>(1);
                outMap.put("class", "com.icsc.mr.api.mrjc150Api");
                outMap.put("messageId", "MR58");
                outMap.put("actionCode", stat);
                outMap.put("dataSource", "cep");
                Map<String, Object> param = new HashMap<>(2);
                param.put("shift", dto.getShift_name());
                param.put("date", DateUtil.format(now, "yyyyMMdd"));
                List<Map<String, Object>> waterList = baseMapper.getWaterRate(param);
                outMap.put("data", logisticsFeignService.getLogisticsMrPlan(
                        DateUtil.format(now, "yyyyMMdd"),
                        dto.getCoal_blendings().get(i).getMaterial_weight(),
                        "41",
                        DateUtil.format(DateUtil.date(), "yyyyMMddHHmm") + getSeqNo(i + 1),
                        new BigDecimal(waterList.size() > 0 ? waterList.get(0).get("mt").toString() : "-1"),
                        dto.getCoal_blendings().get(i).getMaterial_number(),
                        "carrierType_C_" + dto.getCoal_level_materials_code(),
                        "clent-test"
                ));
                log.info("send mq message:{}:{} => {}", MATERIAL_EXCHANGE, MATERIAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(outMap));
                rabbitTemplate.convertAndSend(MATERIAL_EXCHANGE, MATERIAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(outMap));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    private String getSeqNo(Integer no) {
        // 0 代表前面补充0
        // 5 代表长度为5
        // d 代表参数为正数型
        return String.format("%05d", no);
    }
}




