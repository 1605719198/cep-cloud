package com.jlkj.quality.ts.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.quality.ts.domain.ElementTableDescription;
import com.jlkj.quality.ts.domain.EquipBasicInformation;
import com.jlkj.quality.ts.domain.SampleBasicDataA;
import com.jlkj.quality.ts.domain.SampleBasicDataB;
import com.jlkj.quality.ts.dto.TsSampleBasicDataaDTO;
import com.jlkj.quality.ts.dto.TsSampleBasicDatabDTO;
import com.jlkj.quality.ts.service.ElementTableDescriptionService;
import com.jlkj.quality.ts.service.EquipBasicInformationService;
import com.jlkj.quality.ts.service.SampleBasicDataaService;
import com.jlkj.quality.ts.service.SampleBasicDatabService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 智能研发室 黄兵
 * @Description 样品基本资料维护controller
 * @date 2022/5/21 - 8:31
 */
@RestController
@RequestMapping("/quality/chemicalTest/sampleBasicData")
public class SampleBasicDataController {

    @Autowired
    SampleBasicDataaService sampleBasicDataaService;

    @Autowired
    SampleBasicDatabService sampleBasicDatabService;

    @Autowired
    ElementTableDescriptionService elementTableDescriptionService;

    @Autowired
    EquipBasicInformationService equipBasicInformationService;

    /**
     * 搜索样品基本资料
     */
    @Operation(summary = "搜索样品基本资料")
    @PostMapping("/queryList")
    @Log(title = "样品基本资料查询", businessType = BusinessType.OTHER)
    public Object queryListTsSampleBasicData(@RequestBody TsSampleBasicDataaDTO tsSampleBasicDataaDTO) {
        try {
            String sampleClass = tsSampleBasicDataaDTO.getSampleClass();
            String sampleDesc = tsSampleBasicDataaDTO.getSampleDesc();
            Long pageNum = tsSampleBasicDataaDTO.getPageNum();
            Long pageSize = tsSampleBasicDataaDTO.getPageSize();
            LambdaQueryWrapper<SampleBasicDataA> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.likeRight(StringUtils.isNotBlank(sampleClass), SampleBasicDataA::getSampleClass, sampleClass)
                        .likeRight(StringUtils.isNotBlank(sampleDesc), SampleBasicDataA::getSampleDesc, sampleDesc)
                        .orderByDesc(SampleBasicDataA::getCreateTime, SampleBasicDataA::getUpdateTime);
            Page<SampleBasicDataA> page = sampleBasicDataaService.page(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<SampleBasicDataA> records = page.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("total",total);
            dataMap.put("list",records);
            if (records.isEmpty()){
                return AjaxResult.success("查无资料", dataMap);
            } else {
                return AjaxResult.success("搜索成功,为您找到" + total + "笔资料", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 添加样品基本资料
     */
    @Operation(summary = "添加样品基本资料")
    @PostMapping("/add")
    @Log(title = "样品基本资料添加", businessType = BusinessType.INSERT)
    public Object addTsSampleBasicData(@RequestBody TsSampleBasicDataaDTO tsSampleBasicDataaDTO) {
        try {
            String sampleClass = tsSampleBasicDataaDTO.getSampleClass();
            String sampleDesc = tsSampleBasicDataaDTO.getSampleDesc();
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String format = dateFormat.format(date);
            List<SampleBasicDataB> sendValues = tsSampleBasicDataaDTO.getSendValues();
            SampleBasicDataA sampleBasicDataA = new SampleBasicDataA();
            BeanUtils.copyProperties(tsSampleBasicDataaDTO, sampleBasicDataA);
            sampleBasicDataA.setCreateDate(format);
            LambdaQueryWrapper<SampleBasicDataA> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SampleBasicDataA::getSampleClass, sampleClass)
                        .eq(SampleBasicDataA::getSampleDesc, sampleDesc);
            List<SampleBasicDataA> list = sampleBasicDataaService.list(queryWrapper);
            if (!list.isEmpty()) {
                return AjaxResult.error("样品类别不允许重复！");
            }
            boolean resultA = sampleBasicDataaService.save(sampleBasicDataA);
            sampleBasicDatabService.saveBatch(sendValues);
            if (resultA) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 添加样品基本资料主表数据同步
     */
    @Operation(summary = "添加样品基本资料主表数据同步")
    @PostMapping("/addMaster")
    @Log(title = "样品基本资料主表数据添加", businessType = BusinessType.INSERT)
    public Object addTsSampleBasicMasterData(@RequestBody TsSampleBasicDataaDTO tsSampleBasicDataaDTO, @RequestHeader("token") String token) {
        try {
            SampleBasicDataA sampleBasicDataA = new SampleBasicDataA();
            BeanUtils.copyProperties(tsSampleBasicDataaDTO, sampleBasicDataA);
            boolean result = sampleBasicDataaService.save(sampleBasicDataA);
            if (result) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 添加样品基本资料从表数据同步
     */
    @Operation(summary = "添加样品基本资料从表数据同步")
    @PostMapping("/addSlave")
    @Log(title = "样品基本资料从表数据添加", businessType = BusinessType.INSERT)
    public Object addTsSampleBasicSlaveData(@RequestBody TsSampleBasicDatabDTO tsSampleBasicDatabDTO, @RequestHeader("token") String token) {
        try {
            SampleBasicDataB sampleBasicDataB = new SampleBasicDataB();
            BeanUtils.copyProperties(tsSampleBasicDatabDTO, sampleBasicDataB);
            boolean result = sampleBasicDatabService.save(sampleBasicDataB);
            if (result) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 编辑样品基本资料
     */
    @Operation(summary = "编辑样品基本资料")
    @PostMapping("/update")
    @Log(title = "样品基本资料编辑", businessType = BusinessType.UPDATE)
    public Object updateTsSampleBasicData(@RequestBody TsSampleBasicDataaDTO tsSampleBasicDataaDTO) {
        try {
            String sampleClass = tsSampleBasicDataaDTO.getSampleClass();
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String format = dateFormat.format(date);
            List<SampleBasicDataB> sendValues = tsSampleBasicDataaDTO.getSendValues();
            SampleBasicDataA sampleBasicDataA = new SampleBasicDataA();
            BeanUtils.copyProperties(tsSampleBasicDataaDTO, sampleBasicDataA);
            LambdaUpdateWrapper<SampleBasicDataA> updateWrapperA = new LambdaUpdateWrapper<>();
            updateWrapperA.eq(SampleBasicDataA::getSampleClass, sampleClass);
            LambdaUpdateWrapper<SampleBasicDataB> updateWrapperB = new LambdaUpdateWrapper<>();
            updateWrapperB.eq(SampleBasicDataB::getSampleClass, sampleClass);
            sampleBasicDataA.setModifyDate(format);
            boolean resultA = sampleBasicDataaService.saveOrUpdate(sampleBasicDataA, updateWrapperA);
            sampleBasicDatabService.saveOrUpdateBatch(sendValues);
            if (resultA) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 修改样品基本资料主表数据同步
     */
    @Operation(summary = "修改样品基本资料主表数据同步")
    @PostMapping("/updateMaster")
    @Log(title = "样品基本资料主表数据编辑", businessType = BusinessType.UPDATE)
    public Object updateTsSampleBasicMasterData(@RequestBody TsSampleBasicDataaDTO tsSampleBasicDataaDTO, @RequestHeader("token") String token) {
        try {
            String sampleClass = tsSampleBasicDataaDTO.getSampleClass();
            SampleBasicDataA sampleBasicDataA = new SampleBasicDataA();
            BeanUtils.copyProperties(tsSampleBasicDataaDTO, sampleBasicDataA);
            LambdaUpdateWrapper<SampleBasicDataA> updateWrapperA = new LambdaUpdateWrapper<>();
            updateWrapperA.eq(SampleBasicDataA::getSampleClass, sampleClass);
            boolean result = sampleBasicDataaService.saveOrUpdate(sampleBasicDataA, updateWrapperA);
            if (result) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 修改样品基本资料从表数据同步
     */
    @Operation(summary = "修改样品基本资料从表数据同步")
    @PostMapping("/updateSlave")
    @Log(title = "样品基本资料从表数据编辑", businessType = BusinessType.UPDATE)
    public Object updateTsSampleBasicSlaveData(@RequestBody TsSampleBasicDatabDTO tsSampleBasicDatabDTO, @RequestHeader("token") String token) {
        try {
            String sampleClass = "";
            List<SampleBasicDataB> sendValues = tsSampleBasicDatabDTO.getSendValues();
            for (SampleBasicDataB item : sendValues) {
                sampleClass = item.getSampleClass();
            }
            LambdaQueryWrapper<SampleBasicDataB> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SampleBasicDataB::getSampleClass, sampleClass);
            sampleBasicDatabService.remove(queryWrapper);
            boolean result = sampleBasicDatabService.saveBatch(sendValues);
            if (result) {
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除样品基本资料
     */
    @Operation(summary = "删除样品基本资料")
    @DeleteMapping("/delete")
    @Log(title = "样品基本资料删除", businessType = BusinessType.DELETE)
    public Object deleteTsSampleBasicData(@RequestParam String sampleClass) {
        try {
            LambdaQueryWrapper<SampleBasicDataA> queryWrapperA = new LambdaQueryWrapper<>();
            queryWrapperA.eq(SampleBasicDataA::getSampleClass, sampleClass);
            LambdaQueryWrapper<SampleBasicDataB> queryWrapperB = new LambdaQueryWrapper<>();
            queryWrapperB.eq(SampleBasicDataB::getSampleClass, sampleClass);
            boolean resultA = sampleBasicDataaService.remove(queryWrapperA);
            sampleBasicDatabService.remove(queryWrapperB);
            if (resultA) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除样品基本资料主表数据同步
     */
    @Operation(summary = "删除样品基本资料主表数据同步")
    @DeleteMapping("/deleteMaster")
    @Log(title = "样品基本资料主表数据删除", businessType = BusinessType.DELETE)
    public Object deleteTsSampleBasicMasterData(@RequestParam String sampleClass, @RequestHeader("token") String token) {
        try {
            LambdaQueryWrapper<SampleBasicDataA> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SampleBasicDataA::getSampleClass, sampleClass);
            boolean result = sampleBasicDataaService.remove(queryWrapper);
            if (result) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除样品基本资料从表数据同步
     */
    @Operation(summary = "删除样品基本资料从表数据同步")
    @DeleteMapping("/deleteSlave")
    @Log(title = "样品基本资料从表数据删除", businessType = BusinessType.DELETE)
    public Object deleteTsSampleBasicSlaveData(@RequestParam String sampleClass, @RequestHeader("token") String token) {
        try {
            LambdaQueryWrapper<SampleBasicDataB> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SampleBasicDataB::getSampleClass, sampleClass);
            boolean result = sampleBasicDatabService.remove(queryWrapper);
            if (result) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 根据样品类别查询
     */
    @Operation(summary = "根据样品类别查询")
    @GetMapping("/query")
    @Log(title = "样品类别查询", businessType = BusinessType.OTHER)
    public Object queryTsSampleBasicData(@RequestParam String sampleClass) {
        try {
            List<SampleBasicDataA> dataaList = sampleBasicDataaService.query().eq("sample_class", sampleClass).list();
            List<SampleBasicDataB> databList = sampleBasicDatabService.query().eq("sample_class", sampleClass).list();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("dataaList", dataaList);
            dataMap.put("databList", databList);
            if (dataaList.isEmpty() && databList.isEmpty()) {
                return AjaxResult.success("查无资料");
            } else {
                return AjaxResult.success("查询成功", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 根据试验元素表格前两码查询试验元素表格代码
     */
    @Operation(summary = "根据试验元素表格前两码查询试验元素表格代码")
    @PostMapping("/queryTsTable")
    @Log(title = "试验元素表格前两码查询", businessType = BusinessType.OTHER)
    public Object queryTsElementTableDescription(@RequestBody TsSampleBasicDataaDTO tsSampleBasicDataaDTO) {
        try {
            String formatId = tsSampleBasicDataaDTO.getFormatId();
            Long pageNum = tsSampleBasicDataaDTO.getPageNum();
            Long pageSize = tsSampleBasicDataaDTO.getPageSize();
            Page<ElementTableDescription> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<ElementTableDescription> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.likeRight(StringUtils.isNotBlank(formatId), ElementTableDescription::getFormatId, formatId);
            Page<ElementTableDescription> descriptionPage = elementTableDescriptionService.page(page, queryWrapper);
            //总记录数
            long total = descriptionPage.getTotal();
            //数据list集合
            List<ElementTableDescription> records = descriptionPage.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("total",total);
            dataMap.put("list",records);
            if (records.isEmpty()){
                return AjaxResult.success("查无资料", dataMap);
            } else {
                return AjaxResult.success("搜索成功,为您找到" + total + "笔资料", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 查询试验仪器基本资料下拉选单
     */
    @Operation(summary = "查询试验仪器基本资料下拉选单")
    @GetMapping("/queryDropDownMenu")
    @Log(title = "试验仪器基本资料下拉选单查询", businessType = BusinessType.OTHER)
    public Object queryDropDownMenu() {
        LambdaQueryWrapper<EquipBasicInformation> queryWrapperA = new LambdaQueryWrapper<>();
        queryWrapperA.likeRight(EquipBasicInformation::getEquipNo, "E");
        LambdaQueryWrapper<EquipBasicInformation> queryWrapperB = new LambdaQueryWrapper<>();
        queryWrapperB.likeRight(EquipBasicInformation::getEquipNo, "X");
        LambdaQueryWrapper<EquipBasicInformation> queryWrapperC = new LambdaQueryWrapper<>();
        queryWrapperC.likeRight(EquipBasicInformation::getEquipNo, "C");
        LambdaQueryWrapper<EquipBasicInformation> queryWrapperD = new LambdaQueryWrapper<>();
        queryWrapperD.likeRight(EquipBasicInformation::getEquipNo, "N");
        List<EquipBasicInformation> listA = equipBasicInformationService.list(queryWrapperA);
        List<EquipBasicInformation> listB = equipBasicInformationService.list(queryWrapperB);
        List<EquipBasicInformation> listC = equipBasicInformationService.list(queryWrapperC);
        List<EquipBasicInformation> listD = equipBasicInformationService.list(queryWrapperD);
        Map<String,Object> dataMap = new HashMap<>(16);
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<String> list4 = new ArrayList<>();
        for (EquipBasicInformation item : listA) {
            list1.add(item.getEquipNo());
            dataMap.put("listA", list1);
        }
        for (EquipBasicInformation item : listB) {
            list2.add(item.getEquipNo());
            dataMap.put("listB", list2);
        }
        for (EquipBasicInformation item : listC) {
            list3.add(item.getEquipNo());
            dataMap.put("listC", list3);
        }
        for (EquipBasicInformation item : listD) {
            list4.add(item.getEquipNo());
            dataMap.put("listD", list4);
        }
        return AjaxResult.success(dataMap);
    }
}
