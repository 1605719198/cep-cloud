package com.jlkj.logistics.wv.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.logistics.wv.LogisticsTransportPlanMrDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.logistics.wv.domain.DictData;
import com.jlkj.logistics.wv.domain.LogisticsTransportPlanMr;
import com.jlkj.logistics.wv.service.DictDataService;
import com.jlkj.logistics.wv.service.ILogisticsTransportPlanMrService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * <p>
 * 物流管理-MR倒运计划
 * </p>
 *
 * @author xin
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/logistics/transport/mrplan")
@Slf4j
public class LogisticsTransportPlanMrController {
    @Autowired
    private ILogisticsTransportPlanMrService logisticsTransportPlanMrService;
    @Autowired
    private DictDataService dictDataService;

    private static final String DICT_TYPE = "MR58";
    private static final String DEPT = "dept";
    private static final String REPT = "rept";
    private static final String J = "11";
    private static final String M = "41";
    private static final String DEPTNO_M = "deptNo_m";
    private static final String DEPTNO_J = "deptNo_j";


    /**
     * 物流管理-MR倒运计划新增
     * @param dto
     * @return
     */
    @Log(title = "物流管理-MR倒运计划新增",businessType = BusinessType.INSERT)
    @Operation(summary = "物流管理-MR倒运计划新增")
    @PostMapping(value = "/addLogisticsMrPlan")
    public Object save(@RequestBody LogisticsTransportPlanMrDTO dto) {
        log.info("物流管理-MR倒运计划新增=>"+dto);
        LogisticsTransportPlanMr logisticsTransportPlanMr = new LogisticsTransportPlanMr();
        logisticsTransportPlanMr.setId(UUID.randomUUID().toString());
        logisticsTransportPlanMr.setPlanlistNo(dto.getPlanListNo());
        logisticsTransportPlanMr.setCarrierType(dto.getCarrierType());
        logisticsTransportPlanMr.setMtrla(dto.getMtrlNoA());
        logisticsTransportPlanMr.setMtrlaName(dto.getMtrlNameA());
        logisticsTransportPlanMr.setMtrlb(dto.getMtrlNoB());
        logisticsTransportPlanMr.setMtrlbName(dto.getMtrlNameB());
        logisticsTransportPlanMr.setStagea(dto.getStageA());
        logisticsTransportPlanMr.setStageaName(dto.getStageNameA());
        logisticsTransportPlanMr.setStageb(dto.getStageB());
        logisticsTransportPlanMr.setStagebName(dto.getStageNameB());
        logisticsTransportPlanMr.setCostCenter(dto.getCostCenter());
        logisticsTransportPlanMr.setCostCenterDesc(dto.getCostCenterDesc());
        logisticsTransportPlanMrService.save(logisticsTransportPlanMr);
        return AjaxResult.success();
    }

    /**
     * 物流管理-MR倒运计划修改
     * @param dto
     * @return
     */
    @Log(title = "物流管理-MR倒运计划修改",businessType = BusinessType.UPDATE)
    @Operation(summary = "物流管理-MR倒运计划修改")
    @PutMapping(value = "/updateLogisticsMrPlan")
    public Object update(@RequestBody LogisticsTransportPlanMrDTO dto) {
        log.info("物流管理-MR倒运计划修改=>"+dto);
        LogisticsTransportPlanMr logisticsTransportPlanMr = logisticsTransportPlanMrService.getOne(
                new QueryWrapper<LogisticsTransportPlanMr>()
                        .eq(StringUtils.isNotNull(dto.getPlanListNo()),"planlist_No",dto.getPlanListNo()));
        logisticsTransportPlanMr.setPlanlistNo(dto.getPlanListNo());
        logisticsTransportPlanMr.setCarrierType(dto.getCarrierType());
        logisticsTransportPlanMr.setMtrla(dto.getMtrlNoA());
        logisticsTransportPlanMr.setMtrlaName(dto.getMtrlNameA());
        logisticsTransportPlanMr.setMtrlb(dto.getMtrlNoB());
        logisticsTransportPlanMr.setMtrlbName(dto.getMtrlNameB());
        logisticsTransportPlanMr.setStagea(dto.getStageA());
        logisticsTransportPlanMr.setStageaName(dto.getStageNameA());
        logisticsTransportPlanMr.setStageb(dto.getStageB());
        logisticsTransportPlanMr.setStagebName(dto.getStageNameB());
        logisticsTransportPlanMr.setCostCenter(dto.getCostCenter());
        logisticsTransportPlanMr.setCostCenterDesc(dto.getCostCenterDesc());
        logisticsTransportPlanMrService.updateById(logisticsTransportPlanMr);
        return AjaxResult.success();
    }

    /**
     * 物流管理-MR倒运计划删除
     * @param dto
     * @return
     */
    @Log(title = "物流管理-MR倒运计划删除",businessType = BusinessType.DELETE)
    @Operation(summary = "物流管理-MR倒运计划删除")
    @DeleteMapping(value = "/deleteLogisticsMrPlan")
    public Object delete(@RequestBody LogisticsTransportPlanMrDTO dto) {
        log.info("物流管理-MR倒运计划删除=>"+dto);
        LogisticsTransportPlanMr logisticsTransportPlanMr = logisticsTransportPlanMrService.getOne(
            new QueryWrapper<LogisticsTransportPlanMr>()
            .eq(StringUtils.isNotNull(dto.getPlanListNo()),"planlist_No",dto.getPlanListNo()));
        if (logisticsTransportPlanMr != null) {
            logisticsTransportPlanMrService.removeById(logisticsTransportPlanMr);
        }
        return AjaxResult.success();
    }

    /**
     * 物流管理-MR倒运计划条件查询
     * @param reptDate
     * @param qty
     * @param purposeId
     * @param seqNo
     * @param waterRate
     * @param mtrlNo
     * @return
     */
    @Log(title = "物流管理-MR倒运计划查询",businessType = BusinessType.OTHER)
    @Operation(summary = "物流管理-MR倒运计划查询")
    @GetMapping(value = "/getLogisticsMrPlan")
    public JSONObject getLogisticsMrPlan(@RequestParam(defaultValue = "",required = true) String reptDate,
                                         @RequestParam(defaultValue = "",required = true) BigDecimal qty,
                                         @RequestParam(defaultValue = "",required = true) String purposeId,
                                         @RequestParam(defaultValue = "",required = true) String seqNo,
                                         @RequestParam(defaultValue = "",required = true) BigDecimal waterRate,
                                         @RequestParam(defaultValue = "",required = true) String mtrlNo,
                                         @RequestParam(defaultValue = "",required = true) String carrierType) {
        JSONObject resultJson = new JSONObject();
        try {
            log.info("物流管理-MR倒运计划查询参数信息=》reptDate："+reptDate+"，qty："+qty+"，purposeId："+purposeId+"，seqNo："+seqNo+"，waterRate："+waterRate+"，mtrlNo："+mtrlNo+"，carrierType："+carrierType);
            // 查询字典获取料号
            String reptNo = getMrDictData(DICT_TYPE, mtrlNo, REPT,carrierType);
            // 部门编码
            String deptNo = "";
            // 查询字典获取部门编码
            if (J.equals(purposeId)) {
                deptNo = getMrDictData(DICT_TYPE,DEPTNO_J,DEPT,null);
            }
            if (M.equals(purposeId)) {
                deptNo = getMrDictData(DICT_TYPE,DEPTNO_M,DEPT,null);
            }
            // 查询对应料号数据
            LogisticsTransportPlanMr logisticsTransportPlanMr = logisticsTransportPlanMrService.getOne(
                    new QueryWrapper<LogisticsTransportPlanMr>()
                            .eq("planlist_No", reptNo));
            if (logisticsTransportPlanMr != null) {
                resultJson.put("matrlnoB",logisticsTransportPlanMr.getMtrlb());
                resultJson.put("matrlnoA",logisticsTransportPlanMr.getMtrla());
                resultJson.put("seqNo",seqNo);
                resultJson.put("planListNo",logisticsTransportPlanMr.getPlanlistNo());
                resultJson.put("costCenter",logisticsTransportPlanMr.getCostCenter());
                resultJson.put("purposeId",purposeId);
                resultJson.put("waterRate",waterRate);
                resultJson.put("deptNo",deptNo);
                resultJson.put("conveyance",logisticsTransportPlanMr.getCarrierType());
                resultJson.put("stgNoB",logisticsTransportPlanMr.getStageb());
                resultJson.put("stgNoA",logisticsTransportPlanMr.getStagea());
                resultJson.put("qty",qty);
                resultJson.put("reptDate",reptDate);
            }
        } catch (Exception e) {
            log.info("物流管理-MR倒运计划查询异常信息=》" + e);
        }
        log.info("获取结果resultJson =>" + resultJson);
        return resultJson;
    }

    /**
     * 参数查询
     * @param dictType
     * @param dictLabel
     * @param flag
     * @param field1
     * @return
     */
    private String getMrDictData(String dictType, String dictLabel, String flag, String field1){
        DictData data = new DictData();
        // 查询部门value
        if (DEPT.equals(flag)) {
            data = dictDataService.getOne(
                    new QueryWrapper<DictData>()
                            .eq("dict_type",dictType)
                            .eq("dict_label",dictLabel));
        }
        // 查询料号value
        if (REPT.equals(flag)) {
            // 查询料号
            DictData sysDictData = dictDataService.getOne(
                    new QueryWrapper<DictData>()
                            .eq("dict_type",dictType)
                            .eq("dict_label",dictLabel)
                            .eq(StringUtils.isNotNull(field1),"field1",field1));
            data = dictDataService.getOne(
                    new QueryWrapper<DictData>()
                            .eq("dict_type", dictType)
                            .eq("dict_label", sysDictData.getDictValue()));
        }
        return data.getDictValue();
    }
}
