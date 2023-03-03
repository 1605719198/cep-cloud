package com.jlkj.quality.ts.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.quality.TsExperimentRecordDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.quality.ts.domain.ExperimentRecord;
import com.jlkj.quality.ts.domain.ExperimentRecordDetail;
import com.jlkj.quality.ts.domain.QualityTsRecord;
import com.jlkj.quality.ts.domain.QualityTsRecordBoiler;
import com.jlkj.quality.ts.service.ExperimentRecordDetailService;
import com.jlkj.quality.ts.service.ExperimentRecordService;
import com.jlkj.quality.ts.service.QualityTsRecordBoilerService;
import com.jlkj.quality.ts.service.QualityTsRecordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 265675
 * @Description 质量记录
 * @date 2022/09/13/8:29
 */
@RestController
@RequestMapping("/quality/chemicalTest/experimentRecord")
public class ExperimentRecordController {
    @Autowired
    ExperimentRecordService experimentRecordService;

    @Autowired
    ExperimentRecordDetailService experimentRecordDetailService;

    @Autowired
    QualityTsRecordService qualityTsRecordService;

    @Autowired
    QualityTsRecordBoilerService qualityTsRecordBoilerService;

    /**
     * 接收mq队列中的数据存入表中
     */
    @Operation(summary = "新增质量记录")
    @PostMapping("/add")
    @Log(title = "质量记录新增", businessType = BusinessType.INSERT)
    public Object addTsExperimentRecord(@RequestBody TsExperimentRecordDTO tsExperimentRecordDTO) {
        try {
            ExperimentRecord experimentRecord = new ExperimentRecord();
            ExperimentRecordDetail experimentRecordDetail = new ExperimentRecordDetail();
            BeanUtils.copyProperties(tsExperimentRecordDTO, experimentRecord);
            BeanUtils.copyProperties(tsExperimentRecordDTO, experimentRecordDetail);
            experimentRecord.setId(IdUtils.randomUUID());
            experimentRecordDetail.setParentId(experimentRecord.getId());
            boolean save = experimentRecordService.save(experimentRecord);
            for ( Map<String, String> item : tsExperimentRecordDTO.getSampleValues()) {
                experimentRecordDetail.setId(IdUtils.randomUUID());
                experimentRecordDetail.setElement(item.get("element"));
                experimentRecordDetail.setElementId(item.get("elementId"));
                experimentRecordDetail.setElementValue(item.get("value"));
                experimentRecordDetailService.save(experimentRecordDetail);
            }
            if (save) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 查询焦炭质量记录表数据
     */
    @Operation(summary = "查询焦炭质量记录")
    @GetMapping("/queryListMc")
    @Log(title = "焦炭质量记录查询", businessType = BusinessType.OTHER)
    public Object queryTsExperimentRecordMc(TsExperimentRecordDTO tsExperimentRecordDTO) {
        try {
            String startDate = tsExperimentRecordDTO.getStartDate();
            String endDate = tsExperimentRecordDTO.getEndDate();
            String shift = tsExperimentRecordDTO.getShift();
            String type = tsExperimentRecordDTO.getType();
            Long pageNum = tsExperimentRecordDTO.getPageNum();
            Long pageSize = tsExperimentRecordDTO.getPageSize();
            Page<QualityTsRecord> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<QualityTsRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(shift), QualityTsRecord::getShift, shift)
                        .eq(StringUtils.isNotBlank(type), QualityTsRecord::getRecordtype, type);
            if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
                queryWrapper.between(QualityTsRecord::getSendTime, startDate.substring(0,10).replace("-", "") + "000000", endDate.substring(0,10).replace("-", "") + "235959");
            } else if (StringUtils.isNotBlank(startDate)) {
                queryWrapper.eq(QualityTsRecord::getSendTime, startDate.substring(0,10).replace("-", "") + "000000");
            } else if (StringUtils.isNotBlank(endDate)) {
                queryWrapper.eq(QualityTsRecord::getSendTime, endDate.substring(0,10).replace("-", "") + "235959");
            }
            Page<QualityTsRecord> recordPage = qualityTsRecordService.page(page, queryWrapper);
            List<QualityTsRecord> list = qualityTsRecordService.list(queryWrapper);
            //总记录数
            long total = recordPage.getTotal();
            //数据list集合
            List<QualityTsRecord> records = recordPage.getRecords();
            if (records.isEmpty()){
                Map<String,Object> dataMap = new HashMap<>(16);
                dataMap.put("total",total);
                dataMap.put("list",records);
                return AjaxResult.success("查无资料", dataMap);
            } else {
                //计算平均值、最大值、最小值
                List<BigDecimal> averageList = new ArrayList<>();
                List<BigDecimal> maxList = new ArrayList<>();
                List<BigDecimal> minList = new ArrayList<>();
                List<BigDecimal> waterList = new ArrayList<>();
                List<BigDecimal> crozzleList = new ArrayList<>();
                List<BigDecimal> volatileFluxList = new ArrayList<>();
                List<BigDecimal> sulfurationList = new ArrayList<>();
                List<BigDecimal> m40List = new ArrayList<>();
                List<BigDecimal> m10List = new ArrayList<>();
                List<BigDecimal> criList = new ArrayList<>();
                List<BigDecimal> csrList = new ArrayList<>();
                BigDecimal waterSum = BigDecimal.ZERO;
                BigDecimal waterValue = BigDecimal.ZERO;
                BigDecimal crozzleSum = BigDecimal.ZERO;
                BigDecimal crozzleValue = BigDecimal.ZERO;
                BigDecimal volatileFluxSum = BigDecimal.ZERO;
                BigDecimal volatileFluxValue = BigDecimal.ZERO;
                BigDecimal sulfurationSum = BigDecimal.ZERO;
                BigDecimal sulfurationValue = BigDecimal.ZERO;
                BigDecimal m40Sum = BigDecimal.ZERO;
                BigDecimal m40Value = BigDecimal.ZERO;
                BigDecimal m10Sum = BigDecimal.ZERO;
                BigDecimal m10Value = BigDecimal.ZERO;
                BigDecimal criSum = BigDecimal.ZERO;
                BigDecimal criValue = BigDecimal.ZERO;
                BigDecimal csrSum = BigDecimal.ZERO;
                BigDecimal csrValue = BigDecimal.ZERO;
                for (QualityTsRecord item : list) {
                    waterList.add(item.getMt());
                    crozzleList.add(item.getAd());
                    volatileFluxList.add(item.getVdaf());
                    sulfurationList.add(item.getStad());
                    m40List.add(item.getM40());
                    m10List.add(item.getM10());
                    criList.add(item.getCri());
                    csrList.add(item.getCsr());
                    if (item.getMt() != null && !item.getMt().equals(new BigDecimal(0))) {
                        waterValue = waterValue.add(new BigDecimal(1));
                        waterSum = waterSum.add(item.getMt());
                    }
                    if (item.getAd() != null && !item.getAd().equals(new BigDecimal(0))) {
                        crozzleValue = crozzleValue.add(new BigDecimal(1));
                        crozzleSum = crozzleSum.add(item.getAd());
                    }
                    if (item.getVdaf() != null && !item.getVdaf().equals(new BigDecimal(0))) {
                        volatileFluxValue = volatileFluxValue.add(new BigDecimal(1));
                        volatileFluxSum = volatileFluxSum.add(item.getVdaf());
                    }
                    if (item.getStad() != null && !item.getStad().equals(new BigDecimal(0))) {
                        sulfurationValue = sulfurationValue.add(new BigDecimal(1));
                        sulfurationSum = sulfurationSum.add(item.getStad());
                    }
                    if (item.getM40() != null && !item.getM40().equals(new BigDecimal(0))) {
                        m40Value = m40Value.add(new BigDecimal(1));
                        m40Sum = m40Sum.add(item.getM40());
                    }
                    if (item.getM10() != null && !item.getM10().equals(new BigDecimal(0))) {
                        m10Value = m10Value.add(new BigDecimal(1));
                        m10Sum = m10Sum.add(item.getM10());
                    }
                    if (item.getCri() != null && !item.getCri().equals(new BigDecimal(0))) {
                        criValue = criValue.add(new BigDecimal(1));
                        criSum = criSum.add(item.getCri());
                    }
                    if (item.getCsr() != null && !item.getCsr().equals(new BigDecimal(0))) {
                        csrValue = csrValue.add(new BigDecimal(1));
                        csrSum = csrSum.add(item.getCsr());
                    }
                }
                BigDecimal waterMax = waterList.get(0);
                BigDecimal crozzleMax = crozzleList.get(0);
                BigDecimal volatileFluxMax = volatileFluxList.get(0);
                BigDecimal sulfurationMax = sulfurationList.get(0);
                BigDecimal m40Max = m40List.get(0);
                BigDecimal m10Max = m10List.get(0);
                BigDecimal criMax = criList.get(0);
                BigDecimal csrMax = csrList.get(0);
                BigDecimal waterMin = waterList.get(0);
                BigDecimal crozzleMin = crozzleList.get(0);
                BigDecimal volatileFluxMin = volatileFluxList.get(0);
                BigDecimal sulfurationMin = sulfurationList.get(0);
                BigDecimal m40Min = m40List.get(0);
                BigDecimal m10Min = m10List.get(0);
                BigDecimal criMin = criList.get(0);
                BigDecimal csrMin = csrList.get(0);
                for (QualityTsRecord item : list) {
                    if (item.getMt() != null) {
                        waterMax = item.getMt().max(waterMax);
                        waterMin = item.getMt().min(waterMin);
                    }
                    if (item.getAd() != null) {
                        crozzleMax = item.getAd().max(crozzleMax);
                        crozzleMin = item.getAd().min(crozzleMin);
                    }
                    if (item.getVdaf() != null) {
                        volatileFluxMax = item.getVdaf().max(volatileFluxMax);
                        volatileFluxMin = item.getVdaf().min(volatileFluxMin);
                    }
                    if (item.getStad() != null) {
                        sulfurationMax = item.getStad().max(sulfurationMax);
                        sulfurationMin = item.getStad().min(sulfurationMin);
                    }
                    if (item.getM40() != null) {
                        m40Max = item.getM40().max(m40Max);
                        m40Min = item.getM40().min(m40Min);
                    }
                    if (item.getM10() != null) {
                        m10Max = item.getM10().max(m10Max);
                        m10Min = item.getM10().min(m10Min);
                    }
                    if (item.getCri() != null) {
                        criMax = item.getCri().max(criMax);
                        criMin = item.getCri().min(criMin);
                    }
                    if (item.getCsr() != null) {
                        csrMax = item.getCsr().max(csrMax);
                        csrMin = item.getCsr().min(csrMin);
                    }
                }
                    if (waterValue.equals(BigDecimal.ZERO)) {
                        waterSum = waterSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        waterSum = waterSum.divide(waterValue, 2, RoundingMode.HALF_UP);
                    }
                    if (crozzleValue.equals(BigDecimal.ZERO)) {
                        crozzleSum = crozzleSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        crozzleSum = crozzleSum.divide(crozzleValue, 2, RoundingMode.HALF_UP);
                    }
                    if (volatileFluxValue.equals(BigDecimal.ZERO)) {
                        volatileFluxSum = volatileFluxSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        volatileFluxSum = volatileFluxSum.divide(volatileFluxValue, 2, RoundingMode.HALF_UP);
                    }
                    if (sulfurationValue.equals(BigDecimal.ZERO)) {
                        sulfurationSum = sulfurationSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        sulfurationSum = sulfurationSum.divide(sulfurationValue, 2, RoundingMode.HALF_UP);
                    }
                    if (m40Value.equals(BigDecimal.ZERO)) {
                        m40Sum = m40Sum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        m40Sum = m40Sum.divide(m40Value, 2, RoundingMode.HALF_UP);
                    }
                    if (m10Value.equals(BigDecimal.ZERO)) {
                        m10Sum = m10Sum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        m10Sum = m10Sum.divide(m10Value, 2, RoundingMode.HALF_UP);
                    }
                    if (criValue.equals(BigDecimal.ZERO)) {
                        criSum = criSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        criSum = criSum.divide(criValue, 2, RoundingMode.HALF_UP);
                    }
                    if (csrValue.equals(BigDecimal.ZERO)) {
                        csrSum = csrSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        csrSum = csrSum.divide(csrValue, 2, RoundingMode.HALF_UP);
                    }
                averageList.add(waterSum);
                averageList.add(crozzleSum);
                averageList.add(volatileFluxSum);
                averageList.add(sulfurationSum);
                averageList.add(m40Sum);
                averageList.add(m10Sum);
                averageList.add(criSum);
                averageList.add(csrSum);
                maxList.add(waterMax);
                maxList.add(crozzleMax);
                maxList.add(volatileFluxMax);
                maxList.add(sulfurationMax);
                maxList.add(m40Max);
                maxList.add(m10Max);
                maxList.add(criMax);
                maxList.add(csrMax);
                minList.add(waterMin);
                minList.add(crozzleMin);
                minList.add(volatileFluxMin);
                minList.add(sulfurationMin);
                minList.add(m40Min);
                minList.add(m10Min);
                minList.add(criMin);
                minList.add(csrMin);
                Map<String,Object> dataMap = new HashMap<>(16);
                dataMap.put("total",total);
                dataMap.put("list",records);
                dataMap.put("averageList", averageList);
                dataMap.put("maxList", maxList);
                dataMap.put("minList", minList);
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 查询煤质量记录表数据
     */
    @Operation(summary = "查询煤质量记录")
    @GetMapping("/queryList")
    @Log(title = "煤质量记录查询", businessType = BusinessType.OTHER)
    public Object queryTsExperimentRecord(TsExperimentRecordDTO tsExperimentRecordDTO) {
        try {
            String startDate = tsExperimentRecordDTO.getStartDate();
            String endDate = tsExperimentRecordDTO.getEndDate();
            String shift = tsExperimentRecordDTO.getShift();
            String type = tsExperimentRecordDTO.getType();
            String typeA = tsExperimentRecordDTO.getTypeA();
            Long pageNum = tsExperimentRecordDTO.getPageNum();
            Long pageSize = tsExperimentRecordDTO.getPageSize();
            Page<QualityTsRecord> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<QualityTsRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(shift), QualityTsRecord::getShift, shift)
                        .eq(StringUtils.isNotBlank(type), QualityTsRecord::getRecordtype, type)
                        .eq(StringUtils.isNotBlank(typeA), QualityTsRecord::getCoaltype, typeA);
            if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
                queryWrapper.between(QualityTsRecord::getSendTime, startDate.substring(0,10).replace("-", "") + "000000", endDate.substring(0,10).replace("-", "") + "235959");
            } else if (StringUtils.isNotBlank(startDate)) {
                queryWrapper.eq(QualityTsRecord::getSendTime, startDate.substring(0,10).replace("-", "") + "000000");
            } else if (StringUtils.isNotBlank(endDate)) {
                queryWrapper.eq(QualityTsRecord::getSendTime, endDate.substring(0,10).replace("-", "") + "235959");
            }
            Page<QualityTsRecord> recordPage = qualityTsRecordService.page(page, queryWrapper);
            List<QualityTsRecord> list = qualityTsRecordService.list(queryWrapper);
            //总记录数
            long total = recordPage.getTotal();
            //数据list集合
            List<QualityTsRecord> records = recordPage.getRecords();
            if (records.isEmpty()){
                Map<String,Object> dataMap = new HashMap<>(16);
                dataMap.put("total",total);
                dataMap.put("list",records);
                return AjaxResult.success("查无资料", dataMap);
            } else {
                //计算平均值、最大值、最小值
                List<BigDecimal> averageList = new ArrayList<>();
                List<BigDecimal> maxList = new ArrayList<>();
                List<BigDecimal> minList = new ArrayList<>();
                List<BigDecimal> waterList = new ArrayList<>();
                List<BigDecimal> crozzleList = new ArrayList<>();
                List<BigDecimal> volatileFluxList = new ArrayList<>();
                List<BigDecimal> sulfurationList = new ArrayList<>();
                List<BigDecimal> finenessList = new ArrayList<>();
                List<BigDecimal> gHemList = new ArrayList<>();
                List<BigDecimal> xHemList = new ArrayList<>();
                List<BigDecimal> yHemList = new ArrayList<>();
                BigDecimal waterSum = BigDecimal.ZERO;
                BigDecimal waterValue = BigDecimal.ZERO;
                BigDecimal crozzleSum = BigDecimal.ZERO;
                BigDecimal crozzleValue = BigDecimal.ZERO;
                BigDecimal volatileFluxSum = BigDecimal.ZERO;
                BigDecimal volatileFluxValue = BigDecimal.ZERO;
                BigDecimal sulfurationSum = BigDecimal.ZERO;
                BigDecimal sulfurationValue = BigDecimal.ZERO;
                BigDecimal finenessSum = BigDecimal.ZERO;
                BigDecimal finenessValue = BigDecimal.ZERO;
                BigDecimal xHemSum = BigDecimal.ZERO;
                BigDecimal xHemValue = BigDecimal.ZERO;
                BigDecimal yHemSum = BigDecimal.ZERO;
                BigDecimal yHemValue = BigDecimal.ZERO;
                BigDecimal gHemSum = BigDecimal.ZERO;
                BigDecimal gHemValue = BigDecimal.ZERO;
                for (QualityTsRecord item : list) {
                    waterList.add(item.getMt());
                    crozzleList.add(item.getAd());
                    volatileFluxList.add(item.getVdaf());
                    sulfurationList.add(item.getStad());
                    finenessList.add(item.getXd());
                    xHemList.add(item.getX());
                    yHemList.add(item.getY());
                    gHemList.add(item.getG());
                    if (item.getMt() != null && !item.getMt().equals(new BigDecimal(0))) {
                        waterValue = waterValue.add(new BigDecimal(1));
                        waterSum = waterSum.add(item.getMt());
                    }
                    if (item.getAd() != null && !item.getAd().equals(new BigDecimal(0))) {
                        crozzleValue = crozzleValue.add(new BigDecimal(1));
                        crozzleSum = crozzleSum.add(item.getAd());
                    }
                    if (item.getVdaf() != null && !item.getVdaf().equals(new BigDecimal(0))) {
                        volatileFluxValue = volatileFluxValue.add(new BigDecimal(1));
                        volatileFluxSum = volatileFluxSum.add(item.getVdaf());
                    }
                    if (item.getStad() != null && !item.getStad().equals(new BigDecimal(0))) {
                        sulfurationValue = sulfurationValue.add(new BigDecimal(1));
                        sulfurationSum = sulfurationSum.add(item.getStad());
                    }
                    if (item.getXd() != null && !item.getXd().equals(new BigDecimal(0))) {
                        finenessValue = finenessValue.add(new BigDecimal(1));
                        finenessSum = finenessSum.add(item.getXd());
                    }
                    if (item.getX() != null && !item.getX().equals(new BigDecimal(0))) {
                        xHemValue =  xHemValue.add(new BigDecimal(1));
                        xHemSum = xHemSum.add(item.getX());
                    }
                    if (item.getY() != null && !item.getY().equals(new BigDecimal(0))) {
                        yHemValue = yHemValue.add(new BigDecimal(1));
                        yHemSum = yHemSum.add(item.getY());
                    }
                    if (item.getG() != null && !item.getG().equals(new BigDecimal(0))) {
                        gHemValue = gHemValue.add(new BigDecimal(1));
                        gHemSum = gHemSum.add(item.getG());
                    }
                }
                BigDecimal waterMax = waterList.get(0);
                BigDecimal crozzleMax = crozzleList.get(0);
                BigDecimal volatileFluxMax = volatileFluxList.get(0);
                BigDecimal sulfurationMax = sulfurationList.get(0);
                BigDecimal finenessMax = finenessList.get(0);
                BigDecimal xHemMax = xHemList.get(0);
                BigDecimal yHemMax = yHemList.get(0);
                BigDecimal gHemMax = gHemList.get(0);
                BigDecimal waterMin = waterList.get(0);
                BigDecimal crozzleMin = crozzleList.get(0);
                BigDecimal volatileFluxMin = volatileFluxList.get(0);
                BigDecimal sulfurationMin = sulfurationList.get(0);
                BigDecimal finenessMin = finenessList.get(0);
                BigDecimal xHemMin = xHemList.get(0);
                BigDecimal yHemMin = yHemList.get(0);
                BigDecimal gHemMin = gHemList.get(0);
                for (QualityTsRecord item : list) {
                    if (item.getMt() != null) {
                        waterMax = item.getMt().max(waterMax);
                        waterMin = item.getMt().min(waterMin);
                    }
                    if (item.getAd() != null) {
                        crozzleMax = item.getAd().max(crozzleMax);
                        crozzleMin = item.getAd().min(crozzleMin);
                    }
                    if (item.getVdaf() != null) {
                        volatileFluxMax = item.getVdaf().max(volatileFluxMax);
                        volatileFluxMin = item.getVdaf().min(volatileFluxMin);
                    }
                    if (item.getStad() != null) {
                        sulfurationMax = item.getStad().max(sulfurationMax);
                        sulfurationMin = item.getStad().min(sulfurationMin);
                    }
                    if (item.getXd() != null) {
                        finenessMax = item.getXd().max(finenessMax);
                        finenessMin = item.getXd().min(finenessMin);
                    }
                    if (item.getX() != null) {
                        xHemMax = item.getX().max(xHemMax);
                        xHemMin = item.getX().min(xHemMin);
                    }
                    if (item.getY() != null) {
                        yHemMax = item.getY().max(yHemMax);
                        yHemMin = item.getY().min(yHemMin);
                    }
                    if (item.getG() != null) {
                        gHemMax = item.getG().max(gHemMax);
                        gHemMin = item.getG().min(gHemMin);
                    }
                }
                    if (waterValue.equals(BigDecimal.ZERO)) {
                        waterSum = waterSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        waterSum = waterSum.divide(waterValue, 2, RoundingMode.HALF_UP);
                    }
                    if (crozzleValue.equals(BigDecimal.ZERO)) {
                        crozzleSum = crozzleSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        crozzleSum = crozzleSum.divide(crozzleValue, 2, RoundingMode.HALF_UP);
                    }
                    if (volatileFluxValue.equals(BigDecimal.ZERO)) {
                        volatileFluxSum = volatileFluxSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        volatileFluxSum = volatileFluxSum.divide(volatileFluxValue, 2, RoundingMode.HALF_UP);
                    }
                    if (sulfurationValue.equals(BigDecimal.ZERO)) {
                        sulfurationSum = sulfurationSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        sulfurationSum = sulfurationSum.divide(sulfurationValue, 2, RoundingMode.HALF_UP);
                    }
                    if (finenessValue.equals(BigDecimal.ZERO)) {
                        finenessSum = finenessSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        finenessSum = finenessSum.divide(finenessValue, 2, RoundingMode.HALF_UP);
                    }
                    if (xHemValue.equals(BigDecimal.ZERO)) {
                        xHemSum = xHemSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        xHemSum = xHemSum.divide(xHemValue, 2, RoundingMode.HALF_UP);
                    }
                    if (yHemValue.equals(BigDecimal.ZERO)) {
                        yHemSum = yHemSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        yHemSum = yHemSum.divide(yHemValue, 2, RoundingMode.HALF_UP);
                    }
                    if (gHemValue.equals(BigDecimal.ZERO)) {
                        gHemSum = gHemSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        gHemSum = gHemSum.divide(gHemValue, 2, RoundingMode.HALF_UP);
                    }
                averageList.add(waterSum);
                averageList.add(crozzleSum);
                averageList.add(volatileFluxSum);
                averageList.add(sulfurationSum);
                averageList.add(finenessSum);
                averageList.add(xHemSum);
                averageList.add(yHemSum);
                averageList.add(gHemSum);
                maxList.add(waterMax);
                maxList.add(crozzleMax);
                maxList.add(volatileFluxMax);
                maxList.add(sulfurationMax);
                maxList.add(finenessMax);
                maxList.add(xHemMax);
                maxList.add(yHemMax);
                maxList.add(gHemMax);
                minList.add(waterMin);
                minList.add(crozzleMin);
                minList.add(volatileFluxMin);
                minList.add(sulfurationMin);
                minList.add(finenessMin);
                minList.add(xHemMin);
                minList.add(yHemMin);
                minList.add(gHemMin);
                Map<String,Object> dataMap = new HashMap<>(16);
                dataMap.put("total",total);
                dataMap.put("list",records);
                dataMap.put("averageList", averageList);
                dataMap.put("maxList", maxList);
                dataMap.put("minList", minList);
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 查询锅炉水质量记录表数据
     */
    @Operation(summary = "查询锅炉水质量记录")
    @GetMapping("/queryListBoiler")
    @Log(title = "锅炉水质量记录查询", businessType = BusinessType.OTHER)
    public Object queryTsExperimentRecordBoiler(TsExperimentRecordDTO tsExperimentRecordDTO) {
        try {
            String startDate = tsExperimentRecordDTO.getStartDate();
            String endDate = tsExperimentRecordDTO.getEndDate();
            String shift = tsExperimentRecordDTO.getShift();
            String type = tsExperimentRecordDTO.getType();
            String typeA = tsExperimentRecordDTO.getTypeA();
            Long pageNum = tsExperimentRecordDTO.getPageNum();
            Long pageSize = tsExperimentRecordDTO.getPageSize();
            Page<QualityTsRecordBoiler> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<QualityTsRecordBoiler> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(shift), QualityTsRecordBoiler::getShift, shift)
                        .eq(StringUtils.isNotBlank(type), QualityTsRecordBoiler::getCode, type)
                        .eq(StringUtils.isNotBlank(typeA), QualityTsRecordBoiler::getCoaltype2, typeA);
            if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
                queryWrapper.between(QualityTsRecordBoiler::getSendTime, startDate.substring(0,10).replace("-", "") + "000000", endDate.substring(0,10).replace("-", "") + "235959");
            } else if (StringUtils.isNotBlank(startDate)) {
                queryWrapper.eq(QualityTsRecordBoiler::getSendTime, startDate.substring(0,10).replace("-", "") + "000000");
            } else if (StringUtils.isNotBlank(endDate)) {
                queryWrapper.eq(QualityTsRecordBoiler::getSendTime, endDate.substring(0,10).replace("-", "") + "235959");
            }
            Page<QualityTsRecordBoiler> recordPage = qualityTsRecordBoilerService.page(page, queryWrapper);
            List<QualityTsRecordBoiler> list = qualityTsRecordBoilerService.list(queryWrapper);
            //总记录数
            long total = recordPage.getTotal();
            //数据list集合
            List<QualityTsRecordBoiler> records = recordPage.getRecords();
            if (records.isEmpty()){
                Map<String,Object> dataMap = new HashMap<>(16);
                dataMap.put("total",total);
                dataMap.put("list",records);
                return AjaxResult.success("查无资料", dataMap);
            } else {
                //计算平均值、最大值、最小值
                List<BigDecimal> averageList = new ArrayList<>();
                List<BigDecimal> maxList = new ArrayList<>();
                List<BigDecimal> minList = new ArrayList<>();
                List<BigDecimal> pHList = new ArrayList<>();
                List<BigDecimal> conductivityList = new ArrayList<>();
                List<BigDecimal> siliconDioxideList = new ArrayList<>();
                List<BigDecimal> totalHardnessList = new ArrayList<>();
                List<BigDecimal> cuList = new ArrayList<>();
                List<BigDecimal> feList = new ArrayList<>();
                List<BigDecimal> poList = new ArrayList<>();
                BigDecimal pHSum = BigDecimal.ZERO;
                BigDecimal pHValue = BigDecimal.ZERO;
                BigDecimal conductivitySum = BigDecimal.ZERO;
                BigDecimal conductivityValue = BigDecimal.ZERO;
                BigDecimal siliconDioxideSum = BigDecimal.ZERO;
                BigDecimal siliconDioxideValue = BigDecimal.ZERO;
                BigDecimal totalHardnessSum = BigDecimal.ZERO;
                BigDecimal totalHardnessValue = BigDecimal.ZERO;
                BigDecimal cuSum = BigDecimal.ZERO;
                BigDecimal cuValue = BigDecimal.ZERO;
                BigDecimal feSum = BigDecimal.ZERO;
                BigDecimal feValue = BigDecimal.ZERO;
                BigDecimal poSum = BigDecimal.ZERO;
                BigDecimal poValue = BigDecimal.ZERO;
                for (QualityTsRecordBoiler item : list) {
                    pHList.add(item.getPh());
                    conductivityList.add(item.get电导率());
                    siliconDioxideList.add(item.get二氧化硅锅炉());
                    totalHardnessList.add(item.get总硬度());
                    cuList.add(item.getCu());
                    feList.add(item.getFe());
                    poList.add(item.get磷酸根离子());
                    if (item.getPh() != null && !item.getPh().equals(new BigDecimal(0))) {
                        pHValue = pHValue.add(new BigDecimal(1));
                        pHSum = pHSum.add(item.getPh());
                    }
                    if (item.get电导率() != null && !item.get电导率().equals(new BigDecimal(0))) {
                        conductivityValue = conductivityValue.add(new BigDecimal(1));
                        conductivitySum = conductivitySum.add(item.get电导率());
                    }
                    if (item.get二氧化硅锅炉() != null && !item.get二氧化硅锅炉().equals(new BigDecimal(0))) {
                        siliconDioxideValue = siliconDioxideValue.add(new BigDecimal(1));
                        siliconDioxideSum = siliconDioxideSum.add(item.get二氧化硅锅炉());
                    }
                    if (item.get总硬度() != null && !item.get总硬度().equals(new BigDecimal(0))) {
                        totalHardnessValue = totalHardnessValue.add(new BigDecimal(1));
                        totalHardnessSum = totalHardnessSum.add(item.get总硬度());
                    }
                    if (item.getCu() != null && !item.getCu().equals(new BigDecimal(0))) {
                        cuValue = cuValue.add(new BigDecimal(1));
                        cuSum = cuSum.add(item.getCu());
                    }
                    if (item.getFe() != null && !item.getFe().equals(new BigDecimal(0))) {
                        feValue = feValue.add(new BigDecimal(1));
                        feSum = feSum.add(item.getFe());
                    }
                    if (item.get磷酸根离子() != null && !item.get磷酸根离子().equals(new BigDecimal(0))) {
                        poValue = poValue.add(new BigDecimal(1));
                        poSum = poSum.add(item.get磷酸根离子());
                    }
                }
                BigDecimal pHMax = pHList.get(0);
                BigDecimal conductivityMax = conductivityList.get(0);
                BigDecimal siliconDioxideMax = siliconDioxideList.get(0);
                BigDecimal totalHardnessMax = totalHardnessList.get(0);
                BigDecimal cuMax = cuList.get(0);
                BigDecimal feMax = feList.get(0);
                BigDecimal poMax = poList.get(0);
                BigDecimal pHMin = pHList.get(0);
                BigDecimal conductivityMin = conductivityList.get(0);
                BigDecimal siliconDioxideMin = siliconDioxideList.get(0);
                BigDecimal totalHardnessMin = totalHardnessList.get(0);
                BigDecimal cuMin = cuList.get(0);
                BigDecimal feMin = feList.get(0);
                BigDecimal poMin = poList.get(0);
                for (QualityTsRecordBoiler item : list) {
                    if (item.getPh() != null) {
                        pHMax = item.getPh().max(pHMax);
                        pHMin = item.getPh().min(pHMin);
                    }
                    if (item.get电导率() != null) {
                        conductivityMax = item.get电导率().max(conductivityMax);
                        conductivityMin = item.get电导率().min(conductivityMin);
                    }
                    if (item.get二氧化硅锅炉() != null) {
                        siliconDioxideMax = item.get二氧化硅锅炉().max(siliconDioxideMax);
                        siliconDioxideMin = item.get二氧化硅锅炉().min(siliconDioxideMin);
                    }
                    if (item.get总硬度() != null) {
                        totalHardnessMax = item.get总硬度().max(totalHardnessMax);
                        totalHardnessMin = item.get总硬度().min(totalHardnessMin);
                    }
                    if (item.getCu() != null) {
                        cuMax = item.getCu().max(cuMax);
                        cuMin = item.getCu().min(cuMin);
                    }
                    if (item.getFe() != null) {
                        feMax = item.getFe().max(feMax);
                        feMin = item.getFe().min(feMin);
                    }
                    if (item.get磷酸根离子() != null) {
                        poMax = item.get磷酸根离子().max(poMax);
                        poMin = item.get磷酸根离子().min(poMin);
                    }
                }
                    if (pHValue.equals(BigDecimal.ZERO)) {
                        pHSum = pHSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        pHSum = pHSum.divide(pHValue, 2, RoundingMode.HALF_UP);
                    }
                    if (conductivityValue.equals(BigDecimal.ZERO)) {
                        conductivitySum = conductivitySum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        conductivitySum = conductivitySum.divide(conductivityValue, 2, RoundingMode.HALF_UP);
                    }
                    if (siliconDioxideValue.equals(BigDecimal.ZERO)) {
                        siliconDioxideSum = siliconDioxideSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        siliconDioxideSum = siliconDioxideSum.divide(siliconDioxideValue, 2, RoundingMode.HALF_UP);
                    }
                    if (totalHardnessValue.equals(BigDecimal.ZERO)) {
                        totalHardnessSum = totalHardnessSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        totalHardnessSum = totalHardnessSum.divide(totalHardnessValue, 2, RoundingMode.HALF_UP);
                    }
                    if (cuValue.equals(BigDecimal.ZERO)) {
                        cuSum = cuSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        cuSum = cuSum.divide(cuValue, 2, RoundingMode.HALF_UP);
                    }
                    if (feValue.equals(BigDecimal.ZERO)) {
                        feSum = feSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        feSum = feSum.divide(feValue, 2, RoundingMode.HALF_UP);
                    }
                    if (poValue.equals(BigDecimal.ZERO)) {
                        poSum = poSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                    } else {
                        poSum = poSum.divide(poValue, 2, RoundingMode.HALF_UP);
                    }
                averageList.add(poSum);
                averageList.add(pHSum);
                averageList.add(conductivitySum);
                averageList.add(siliconDioxideSum);
                averageList.add(totalHardnessSum);
                averageList.add(cuSum);
                averageList.add(feSum);
                maxList.add(poMax);
                maxList.add(pHMax);
                maxList.add(conductivityMax);
                maxList.add(siliconDioxideMax);
                maxList.add(totalHardnessMax);
                maxList.add(cuMax);
                maxList.add(feMax);
                minList.add(poMin);
                minList.add(pHMin);
                minList.add(conductivityMin);
                minList.add(siliconDioxideMin);
                minList.add(totalHardnessMin);
                minList.add(cuMin);
                minList.add(feMin);
                Map<String,Object> dataMap = new HashMap<>(16);
                dataMap.put("total",total);
                dataMap.put("list",records);
                dataMap.put("averageList", averageList);
                dataMap.put("maxList", maxList);
                dataMap.put("minList", minList);
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 查询干熄焦质量记录表数据
     */
    @Operation(summary = "查询干熄焦质量记录")
    @GetMapping("/queryListCdq")
    @Log(title = "干熄焦质量记录查询", businessType = BusinessType.OTHER)
    public Object queryTsExperimentRecordCdq(TsExperimentRecordDTO tsExperimentRecordDTO) {
        try {
            String startDate = tsExperimentRecordDTO.getStartDate();
            String endDate = tsExperimentRecordDTO.getEndDate();
            String shift = tsExperimentRecordDTO.getShift();
            String type = tsExperimentRecordDTO.getType();
            String typeA = tsExperimentRecordDTO.getTypeA();
            Long pageNum = tsExperimentRecordDTO.getPageNum();
            Long pageSize = tsExperimentRecordDTO.getPageSize();
            Page<QualityTsRecordBoiler> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<QualityTsRecordBoiler> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(shift), QualityTsRecordBoiler::getShift, shift)
                        .eq(StringUtils.isNotBlank(type), QualityTsRecordBoiler::getCode, type)
                        .eq(StringUtils.isNotBlank(typeA), QualityTsRecordBoiler::getCoaltype, typeA);
            if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
                queryWrapper.between(QualityTsRecordBoiler::getSendTime, startDate.substring(0,10).replace("-", "") + "000000", endDate.substring(0,10).replace("-", "") + "235959");
            } else if (StringUtils.isNotBlank(startDate)) {
                queryWrapper.eq(QualityTsRecordBoiler::getSendTime, startDate.substring(0,10).replace("-", "") + "000000");
            } else if (StringUtils.isNotBlank(endDate)) {
                queryWrapper.eq(QualityTsRecordBoiler::getSendTime, endDate.substring(0,10).replace("-", "") + "235959");
            }
            Page<QualityTsRecordBoiler> recordPage = qualityTsRecordBoilerService.page(page, queryWrapper);
            List<QualityTsRecordBoiler> list = qualityTsRecordBoilerService.list(queryWrapper);
            //总记录数
            long total = recordPage.getTotal();
            //数据list集合
            List<QualityTsRecordBoiler> records = recordPage.getRecords();
            if (records.isEmpty()){
                Map<String,Object> dataMap = new HashMap<>(16);
                dataMap.put("total",total);
                dataMap.put("list",records);
                return AjaxResult.success("查无资料", dataMap);
            } else {
                //计算平均值、最大值、最小值
                List<BigDecimal> averageList = new ArrayList<>();
                List<BigDecimal> maxList = new ArrayList<>();
                List<BigDecimal> minList = new ArrayList<>();
                List<BigDecimal> pHList = new ArrayList<>();
                List<BigDecimal> conductivityList = new ArrayList<>();
                List<BigDecimal> siliconDioxideList = new ArrayList<>();
                List<BigDecimal> totalHardnessList = new ArrayList<>();
                List<BigDecimal> orthophosphateList = new ArrayList<>();
                List<BigDecimal> chlorideIonList = new ArrayList<>();
                List<BigDecimal> sodiumIonList = new ArrayList<>();
                List<BigDecimal> carbonDioxideList = new ArrayList<>();
                List<BigDecimal> oxygenList = new ArrayList<>();
                List<BigDecimal> carbonMonoxideList = new ArrayList<>();
                BigDecimal pHSum = BigDecimal.ZERO;
                BigDecimal pHValue = BigDecimal.ZERO;
                BigDecimal conductivitySum = BigDecimal.ZERO;
                BigDecimal conductivityValue = BigDecimal.ZERO;
                BigDecimal siliconDioxideSum = BigDecimal.ZERO;
                BigDecimal siliconDioxideValue = BigDecimal.ZERO;
                BigDecimal totalHardnessSum = BigDecimal.ZERO;
                BigDecimal totalHardnessValue = BigDecimal.ZERO;
                BigDecimal orthophosphateSum = BigDecimal.ZERO;
                BigDecimal orthophosphateValue = BigDecimal.ZERO;
                BigDecimal chlorideIonSum = BigDecimal.ZERO;
                BigDecimal chlorideIonValue = BigDecimal.ZERO;
                BigDecimal sodiumIonSum = BigDecimal.ZERO;
                BigDecimal sodiumIonValue = BigDecimal.ZERO;
                BigDecimal carbonDioxideSum = BigDecimal.ZERO;
                BigDecimal carbonDioxideValue = BigDecimal.ZERO;
                BigDecimal oxygenSum = BigDecimal.ZERO;
                BigDecimal oxygenValue = BigDecimal.ZERO;
                BigDecimal carbonMonoxideSum = BigDecimal.ZERO;
                BigDecimal carbonMonoxideValue = BigDecimal.ZERO;
                for (QualityTsRecordBoiler item : list) {
                    pHList.add(item.getPh());
                    conductivityList.add(item.get电导率());
                    siliconDioxideList.add(item.get二氧化硅());
                    totalHardnessList.add(item.get总硬度());
                    orthophosphateList.add(item.get正磷酸盐());
                    chlorideIonList.add(item.get氯离子());
                    sodiumIonList.add(item.get钠离子());
                    carbonDioxideList.add(item.get二氧化碳());
                    oxygenList.add(item.get氧气());
                    carbonMonoxideList.add(item.get一氧化碳());
                    if (item.getPh() != null && !item.getPh().equals(new BigDecimal(0))) {
                        pHValue = pHValue.add(new BigDecimal(1));
                        pHSum = pHSum.add(item.getPh());
                    }
                    if (item.get电导率() != null && !item.get电导率().equals(new BigDecimal(0))) {
                        conductivityValue = conductivityValue.add(new BigDecimal(1));
                        conductivitySum = conductivitySum.add(item.get电导率());
                    }
                    if (item.get二氧化硅() != null && !item.get二氧化硅().equals(new BigDecimal(0))) {
                        siliconDioxideValue = siliconDioxideValue.add(new BigDecimal(1));
                        siliconDioxideSum = siliconDioxideSum.add(item.get二氧化硅());
                    }
                    if (item.get总硬度() != null && !item.get总硬度().equals(new BigDecimal(0))) {
                        totalHardnessValue = totalHardnessValue.add(new BigDecimal(1));
                        totalHardnessSum = totalHardnessSum.add(item.get总硬度());
                    }
                    if (item.get正磷酸盐() != null && !item.get正磷酸盐().equals(new BigDecimal(0))) {
                        orthophosphateValue = orthophosphateValue.add(new BigDecimal(1));
                        orthophosphateSum = orthophosphateSum.add(item.get正磷酸盐());
                    }
                    if (item.get氯离子() != null && !item.get氯离子().equals(new BigDecimal(0))) {
                        chlorideIonValue = chlorideIonValue.add(new BigDecimal(1));
                        chlorideIonSum = chlorideIonSum.add(item.get氯离子());
                    }
                    if (item.get钠离子() != null && !item.get钠离子().equals(new BigDecimal(0))) {
                        sodiumIonValue = sodiumIonValue.add(new BigDecimal(1));
                        sodiumIonSum = sodiumIonSum.add(item.get钠离子());
                    }
                    if (item.get二氧化碳() != null && !item.get二氧化碳().equals(new BigDecimal(0))) {
                        carbonDioxideValue = carbonDioxideValue.add(new BigDecimal(1));
                        carbonDioxideSum = carbonDioxideSum.add(item.get二氧化碳());
                    }
                    if (item.get氧气() != null && !item.get氧气().equals(new BigDecimal(0))) {
                        oxygenValue = oxygenValue.add(new BigDecimal(1));
                        oxygenSum = oxygenSum.add(item.get氧气());
                    }
                    if (item.get一氧化碳() != null && !item.get一氧化碳().equals(new BigDecimal(0))) {
                        carbonMonoxideValue = carbonMonoxideValue.add(new BigDecimal(1));
                        carbonMonoxideSum = carbonMonoxideSum.add(item.get一氧化碳());
                    }
                }
                BigDecimal pHMax = pHList.get(0);
                BigDecimal conductivityMax = conductivityList.get(0);
                BigDecimal siliconDioxideMax = siliconDioxideList.get(0);
                BigDecimal totalHardnessMax = totalHardnessList.get(0);
                BigDecimal orthophosphateMax = orthophosphateList.get(0);
                BigDecimal chlorideIonMax = chlorideIonList.get(0);
                BigDecimal sodiumIonMax = sodiumIonList.get(0);
                BigDecimal carbonDioxideMax = carbonDioxideList.get(0);
                BigDecimal oxygenMax = oxygenList.get(0);
                BigDecimal carbonMonoxideMax = carbonMonoxideList.get(0);
                BigDecimal pHMin = pHList.get(0);
                BigDecimal conductivityMin = conductivityList.get(0);
                BigDecimal siliconDioxideMin = siliconDioxideList.get(0);
                BigDecimal totalHardnessMin = totalHardnessList.get(0);
                BigDecimal orthophosphateMin = orthophosphateList.get(0);
                BigDecimal chlorideIonMin = chlorideIonList.get(0);
                BigDecimal sodiumIonMin = sodiumIonList.get(0);
                BigDecimal carbonDioxideMin = carbonDioxideList.get(0);
                BigDecimal oxygenMin = oxygenList.get(0);
                BigDecimal carbonMonoxideMin = carbonMonoxideList.get(0);
                for (QualityTsRecordBoiler item : list) {
                    if (item.getPh() != null) {
                        pHMax = item.getPh().max(pHMax);
                        pHMin = item.getPh().min(pHMin);
                    }
                    if (item.get电导率() != null) {
                        conductivityMax = item.get电导率().max(conductivityMax);
                        conductivityMin = item.get电导率().min(conductivityMin);
                    }
                    if (item.get二氧化硅() != null) {
                        siliconDioxideMax = item.get二氧化硅().max(siliconDioxideMax);
                        siliconDioxideMin = item.get二氧化硅().min(siliconDioxideMin);
                    }
                    if (item.get总硬度() != null) {
                        totalHardnessMax = item.get总硬度().max(totalHardnessMax);
                        totalHardnessMin = item.get总硬度().min(totalHardnessMin);
                    }
                    if (item.get正磷酸盐() != null) {
                        orthophosphateMax = item.get正磷酸盐().max(orthophosphateMax);
                        orthophosphateMin = item.get正磷酸盐().min(orthophosphateMin);
                    }
                    if (item.get氯离子() != null) {
                        chlorideIonMax = item.get氯离子().max(chlorideIonMax);
                        chlorideIonMin = item.get氯离子().min(chlorideIonMin);
                    }
                    if (item.get钠离子() != null) {
                        sodiumIonMax = item.get钠离子().max(sodiumIonMax);
                        sodiumIonMin = item.get钠离子().min(sodiumIonMin);
                    }
                    if (item.get二氧化碳() != null) {
                        carbonDioxideMax = item.get二氧化碳().max(carbonDioxideMax);
                        carbonDioxideMin = item.get二氧化碳().min(carbonDioxideMin);
                    }
                    if (item.get氧气() != null) {
                        oxygenMax = item.get氧气().max(oxygenMax);
                        oxygenMin = item.get氧气().min(oxygenMin);
                    }
                    if (item.get一氧化碳() != null) {
                        carbonMonoxideMax = item.get一氧化碳().max(carbonMonoxideMax);
                        carbonMonoxideMin = item.get一氧化碳().min(carbonMonoxideMin);
                    }
                }
                if (pHValue.equals(BigDecimal.ZERO)) {
                    pHSum = pHSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                } else {
                    pHSum = pHSum.divide(pHValue, 2, RoundingMode.HALF_UP);
                }
                if (conductivityValue.equals(BigDecimal.ZERO)) {
                    conductivitySum = conductivitySum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                } else {
                    conductivitySum = conductivitySum.divide(conductivityValue, 2, RoundingMode.HALF_UP);
                }
                if (siliconDioxideValue.equals(BigDecimal.ZERO)) {
                    siliconDioxideSum = siliconDioxideSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                } else {
                    siliconDioxideSum = siliconDioxideSum.divide(siliconDioxideValue, 2, RoundingMode.HALF_UP);
                }
                if (totalHardnessValue.equals(BigDecimal.ZERO)) {
                    totalHardnessSum = totalHardnessSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                } else {
                    totalHardnessSum = totalHardnessSum.divide(totalHardnessValue, 2, RoundingMode.HALF_UP);
                }
                if (orthophosphateValue.equals(BigDecimal.ZERO)) {
                    orthophosphateSum = orthophosphateSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                } else {
                    orthophosphateSum = orthophosphateSum.divide(orthophosphateValue, 2, RoundingMode.HALF_UP);
                }
                if (chlorideIonValue.equals(BigDecimal.ZERO)) {
                    chlorideIonSum = chlorideIonSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                } else {
                    chlorideIonSum = chlorideIonSum.divide(chlorideIonValue, 2, RoundingMode.HALF_UP);
                }
                if (sodiumIonValue.equals(BigDecimal.ZERO)) {
                    sodiumIonSum = sodiumIonSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                } else {
                    sodiumIonSum = sodiumIonSum.divide(sodiumIonValue, 2, RoundingMode.HALF_UP);
                }
                if (carbonDioxideValue.equals(BigDecimal.ZERO)) {
                    carbonDioxideSum = carbonDioxideSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                } else {
                    carbonDioxideSum = carbonDioxideSum.divide(carbonDioxideValue, 2, RoundingMode.HALF_UP);
                }
                if (oxygenValue.equals(BigDecimal.ZERO)) {
                    oxygenSum = oxygenSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                } else {
                    oxygenSum = oxygenSum.divide(oxygenValue, 2, RoundingMode.HALF_UP);
                }
                if (carbonMonoxideValue.equals(BigDecimal.ZERO)) {
                    carbonMonoxideSum = carbonMonoxideSum.divide(BigDecimal.valueOf(pageSize), 2, RoundingMode.HALF_UP);
                } else {
                    carbonMonoxideSum = carbonMonoxideSum.divide(carbonMonoxideValue, 2, RoundingMode.HALF_UP);
                }
                averageList.add(carbonDioxideSum);
                averageList.add(oxygenSum);
                averageList.add(carbonMonoxideSum);
                averageList.add(chlorideIonSum);
                averageList.add(orthophosphateSum);
                averageList.add(pHSum);
                averageList.add(conductivitySum);
                averageList.add(siliconDioxideSum);
                averageList.add(totalHardnessSum);
                averageList.add(sodiumIonSum);
                maxList.add(carbonDioxideMax);
                maxList.add(oxygenMax);
                maxList.add(carbonMonoxideMax);
                maxList.add(chlorideIonMax);
                maxList.add(orthophosphateMax);
                maxList.add(pHMax);
                maxList.add(conductivityMax);
                maxList.add(siliconDioxideMax);
                maxList.add(totalHardnessMax);
                maxList.add(sodiumIonMax);
                minList.add(carbonDioxideMin);
                minList.add(oxygenMin);
                minList.add(carbonMonoxideMin);
                minList.add(chlorideIonMin);
                minList.add(orthophosphateMin);
                minList.add(pHMin);
                minList.add(conductivityMin);
                minList.add(siliconDioxideMin);
                minList.add(totalHardnessMin);
                minList.add(sodiumIonMin);
                Map<String,Object> dataMap = new HashMap<>(16);
                dataMap.put("total",total);
                dataMap.put("list",records);
                dataMap.put("averageList", averageList);
                dataMap.put("maxList", maxList);
                dataMap.put("minList", minList);
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
