package com.jlkj.quality.ts.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.quality.TsEquipBasicInformationDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.quality.ts.domain.EquipBasicInformation;
import com.jlkj.quality.ts.service.EquipBasicInformationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 智能研发室 黄兵
 * @Description 试验仪器基本资料controller
 * @date 2022/5/9 - 14:10
 */
@RestController
@RequestMapping("/chemicalTest/instrumentBasicData")
public class EquipBasicInformationController {

    @Autowired
    EquipBasicInformationService equipBasicInformationService;

    /**
     * 搜索试验仪器基本资料
     */
    @Operation(summary = "搜索试验仪器基本资料")
    @PostMapping("/queryList")
    @Log(title = "试验仪器基本资料查询", businessType = BusinessType.OTHER)
    public Object queryListTsEquipBasicInformation(@RequestBody TsEquipBasicInformationDTO tsEquipBasicInformationDTO) {
        try {
            String equipNo = tsEquipBasicInformationDTO.getEquipNo();
            String equipDesc = tsEquipBasicInformationDTO.getEquipDesc();
            String a = "A";
            String m = "M";
            String open = "ON";
            String close = "OFF";
            Long pageNum = tsEquipBasicInformationDTO.getPageNum();
            Long pageSize = tsEquipBasicInformationDTO.getPageSize();
            LambdaQueryWrapper<EquipBasicInformation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.likeRight(StringUtils.isNotBlank(equipNo), EquipBasicInformation::getEquipNo, equipNo)
                        .like(StringUtils.isNotBlank(equipDesc), EquipBasicInformation::getEquipDesc, equipDesc)
                        .orderByDesc(EquipBasicInformation::getEquipNo);
            Page<EquipBasicInformation> page = equipBasicInformationService.page(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<EquipBasicInformation> records = page.getRecords();
            for (EquipBasicInformation item : records) {
                if (a.equals(item.getWorkType())) {
                    item.setWorkType("自动");
                } else if (m.equals(item.getWorkType())) {
                    item.setWorkType("手动");
                }
                if (open.equals(item.getReceiveStatus())) {
                    item.setReceiveStatus("开");
                } else if (close.equals(item.getReceiveStatus())) {
                    item.setReceiveStatus("关");
                }
            }
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
     * 添加试验仪器基本资料
     */
    @Operation(summary = "添加试验仪器基本资料")
    @PostMapping("/add")
    @Log(title = "试验仪器基本资料添加", businessType = BusinessType.INSERT)
    public Object addTsEquipBasicInformation(@RequestBody TsEquipBasicInformationDTO tsEquipBasicInformationDTO, @RequestHeader("token") String token) {
        try {
            String receivePort = tsEquipBasicInformationDTO.getReceivePort();
            List<EquipBasicInformation> list = equipBasicInformationService.query().eq("equip_no", tsEquipBasicInformationDTO.getEquipNo()).list();
            if (!list.isEmpty()) {
                return AjaxResult.error("您输入的" + tsEquipBasicInformationDTO.getEquipNo() + "系统中已存在，请重新输入！");
            }
            LambdaQueryWrapper<EquipBasicInformation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EquipBasicInformation::getReceivePort, receivePort);
            List<EquipBasicInformation> listA = equipBasicInformationService.list(queryWrapper);
            if (!listA.isEmpty()) {
                return AjaxResult.error("接收PORT不允许和其他仪器设置的接收PORT相同");
            }
            EquipBasicInformation equipBasicInformation = new EquipBasicInformation();
            BeanUtils.copyProperties(tsEquipBasicInformationDTO, equipBasicInformation);
            boolean result = equipBasicInformationService.save(equipBasicInformation);
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
     * 编辑试验仪器基本资料
     */
    @Operation(summary = "编辑试验仪器基本资料")
    @PostMapping("/update")
    @Log(title = "试验仪器基本资料编辑", businessType = BusinessType.UPDATE)
    public Object updateTsEquipBasicInformation(@RequestBody TsEquipBasicInformationDTO tsEquipBasicInformationDTO, @RequestHeader("token") String token) {
        try {
            String equipNo = tsEquipBasicInformationDTO.getEquipNo();
            EquipBasicInformation equipBasicInformation = new EquipBasicInformation();
            BeanUtils.copyProperties(tsEquipBasicInformationDTO, equipBasicInformation);
            LambdaUpdateWrapper<EquipBasicInformation> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(StringUtils.isNotBlank(equipNo), EquipBasicInformation::getEquipNo, equipNo);
            boolean result = equipBasicInformationService.update(equipBasicInformation, updateWrapper);
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
     * 删除试验仪器基本资料
     */
    @Operation(summary = "删除试验仪器基本资料")
    @DeleteMapping("/delete")
    @Log(title = "试验仪器基本资料删除", businessType = BusinessType.DELETE)
    public Object deleteTsEquipBasicInformation(@RequestParam String equipNo, @RequestHeader("token") String token) {
        try {
            LambdaQueryWrapper<EquipBasicInformation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EquipBasicInformation::getEquipNo, equipNo);
            boolean result = equipBasicInformationService.remove(queryWrapper);
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
     * 根据仪器代号查询
     */
    @Operation(summary = "根据仪器代号查询")
    @GetMapping("/query")
    @Log(title = "仪器代号查询", businessType = BusinessType.OTHER)
    public Object queryTsEquipBasicInformation(@RequestParam String equipNo) {
        try {
            List<EquipBasicInformation> list = equipBasicInformationService.query().eq("equip_no", equipNo).list();
            return AjaxResult.success("查询成功", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
