package com.jlkj.energy.ee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.dto.dto.energyprovider.EnergyCodeFoMaterialDTO;
import com.jlkj.common.dto.resp.Result;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.domain.EnergyCode;
import com.jlkj.energy.ee.domain.EnergyCodeFoMaterial;
import com.jlkj.energy.ee.service.EnergyCodeFoMaterialService;
import com.jlkj.energy.ee.service.EnergyCodeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 智能研发室 黄兵
 * @Description 固液体能源代码对应料号维护controller
 * @date 2022/4/28 - 11:26
 */
@RestController
@RequestMapping("/energy/code/material")
public class EnergyCodeFoMaterialController {

    @Autowired
    EnergyCodeFoMaterialService energyCodeFoMaterialService;

    @Autowired
    EnergyCodeService energyCodeService;

    /**
     * 新增固液体能源代码对应料号维护资料
     */
    @Log(title = "新增固液体能源代码对应料号维护资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增固液体能源代码对应料号维护资料")
    @PostMapping("/add")
    public Object addEnergyCodeFoMaterial(@RequestBody EnergyCodeFoMaterialDTO energyCodeFoMaterialDTO, @RequestHeader("token") String token) {
        try {
            EnergyCodeFoMaterial energyCodeFoMaterial = new EnergyCodeFoMaterial();
            BeanUtils.copyProperties(energyCodeFoMaterialDTO, energyCodeFoMaterial);
            LambdaQueryWrapper<EnergyCodeFoMaterial> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(EnergyCodeFoMaterial::getEngyId, energyCodeFoMaterial.getEngyId())
                    .eq(EnergyCodeFoMaterial::getCostCenter, energyCodeFoMaterial.getCostCenter())
                    .eq(EnergyCodeFoMaterial::getEngyClass, energyCodeFoMaterial.getEngyClass())
                    .eq(EnergyCodeFoMaterial::getEngySource, energyCodeFoMaterial.getEngySource());
            List<EnergyCodeFoMaterial> list = energyCodeFoMaterialService.list(queryWrapper);
            if(list.size() >= 1){
                return Result.failedOne("您输入的'能源代码、成本中心代号、能源量类型、来源系统'系统中已存在，请重新输入！");
            }
            boolean result = energyCodeFoMaterialService.save(energyCodeFoMaterial);
            if(result){
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("compId", energyCodeFoMaterial.getCompId());
                resultMap.put("engyId", energyCodeFoMaterial.getEngyId());
                resultMap.put("engyName", energyCodeFoMaterial.getEngyName());
                resultMap.put("costCenter", energyCodeFoMaterial.getCostCenter());
                resultMap.put("engyClass", energyCodeFoMaterial.getEngyClass());
                resultMap.put("engySource", energyCodeFoMaterial.getEngySource());
                resultMap.put("engyCmp1No", energyCodeFoMaterial.getEngyCmp1No());
                resultMap.put("engyCmp2No", energyCodeFoMaterial.getEngyCmp2No());
                resultMap.put("engyCmp3No", energyCodeFoMaterial.getEngyCmp3No());
                resultMap.put("engyCmp4No", energyCodeFoMaterial.getEngyCmp4No());
                List<Map<String, String>> resultList = new ArrayList<>();
                resultList.add(resultMap);
                return Result.successOne("新增成功", resultList);
            }else {
                return Result.failedTwo("新增失败，请重新提交");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 修改固液体能源代码对应料号维护资料
     */
    @Log(title = "修改固液体能源代码对应料号维护资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改固液体能源代码对应料号维护资料")
    @PostMapping("/update")
    public Object updateEnergyCodeFoMaterial(@RequestBody EnergyCodeFoMaterialDTO energyCodeFoMaterialDTO, @RequestHeader("token") String token) {
        try {
            EnergyCodeFoMaterial energyCodeFoMaterial = new EnergyCodeFoMaterial();
            BeanUtils.copyProperties(energyCodeFoMaterialDTO, energyCodeFoMaterial);
            String engyId = "engy_id";
            String engyId1 = energyCodeFoMaterial.getEngyId();
            LambdaQueryWrapper<EnergyCodeFoMaterial> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EnergyCodeFoMaterial::getEngyId, energyCodeFoMaterial.getEngyId())
                        .eq(EnergyCodeFoMaterial::getCostCenter, energyCodeFoMaterial.getCostCenter())
                        .eq(EnergyCodeFoMaterial::getEngyClass, energyCodeFoMaterial.getEngyClass())
                        .eq(EnergyCodeFoMaterial::getEngySource, energyCodeFoMaterial.getEngySource());
            LambdaUpdateWrapper<EnergyCodeFoMaterial> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(StringUtils.isNotBlank(engyId1), EnergyCodeFoMaterial::getEngyId, engyId1);
            Map<String, Object> map1 = energyCodeFoMaterialService.getMap(queryWrapper);
            if (map1 == null) {
                LambdaQueryWrapper<EnergyCode> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(StringUtils.isNotBlank(engyId1), EnergyCode::getEngyId, engyId1);
                Map<String, Object> map = energyCodeService.getMap(queryWrapper1);
                energyCodeFoMaterial.setEngyName(String.valueOf(map.get("engy_name")));
                energyCodeFoMaterialService.save(energyCodeFoMaterial);
                return Result.success("能源代码为空,已为您新增了一笔数据");
            } else {
                if(map1.get(engyId).equals(energyCodeFoMaterial.getEngyId())) {
                    boolean result = energyCodeFoMaterialService.update(energyCodeFoMaterial, updateWrapper);
                    if (result){
                        Map<String, String> resultMap = new HashMap<>(16);
                        resultMap.put("compId", energyCodeFoMaterial.getCompId());
                        resultMap.put("engyId", energyCodeFoMaterial.getEngyId());
                        resultMap.put("engyName", energyCodeFoMaterial.getEngyName());
                        resultMap.put("costCenter", energyCodeFoMaterial.getCostCenter());
                        resultMap.put("engyClass", energyCodeFoMaterial.getEngyClass());
                        resultMap.put("engySource", energyCodeFoMaterial.getEngySource());
                        resultMap.put("engyCmp1No", energyCodeFoMaterial.getEngyCmp1No());
                        resultMap.put("engyCmp2No", energyCodeFoMaterial.getEngyCmp2No());
                        resultMap.put("engyCmp3No", energyCodeFoMaterial.getEngyCmp3No());
                        resultMap.put("engyCmp4No", energyCodeFoMaterial.getEngyCmp4No());
                        List<Map<String, String>> resultList = new ArrayList<>();
                        resultList.add(resultMap);
                        return Result.successOne("修改成功", resultList);
                    } else {
                        return Result.failedTwo("修改失败，请重新提交");
                    }
                } else {
                    return Result.failedOne("'能源代码、成本中心代号、能源量类型、来源系统'，不允许修改");
                }
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 删除固液体能源代码对应料号维护资料
     */
    @Log(title = "删除固液体能源代码对应料号维护资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除固液体能源代码对应料号维护资料")
    @DeleteMapping("/delete")
    public Object deleteEnergyCodeFoMaterial(@RequestParam List<String> id) {
        try {
            boolean result = energyCodeFoMaterialService.removeBatchByIds(id);
            if (result) {
                return Result.success("删除成功");
            } else {
                return Result.failedTwo("删除失败，请重新提交");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 删除固液体能源代码对应料号维护资料(mq)
     */
    @Log(title = "删除固液体能源代码对应料号维护资料(mq)", businessType = BusinessType.DELETE)
    @Operation(summary = "删除固液体能源代码对应料号维护资料(mq)")
    @DeleteMapping("/deleteErp")
    public Object deleteEnergyCode(@RequestParam String engyId, @RequestHeader("token") String token) {
        try {
            LambdaQueryWrapper<EnergyCodeFoMaterial> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EnergyCodeFoMaterial::getEngyId, engyId);
            boolean result = energyCodeFoMaterialService.remove(queryWrapper);
            if (result) {
                return Result.success("删除成功");
            } else {
                return Result.failedTwo("删除失败，请重新提交");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 固液体能源代码对应料号资料查询与列表
     */
    @Log(title = "固液体能源代码对应料号资料查询与列表", businessType = BusinessType.OTHER)
    @Operation(summary = "固液体能源代码对应料号资料查询与列表")
    @GetMapping("/query")
    public Object queryEnergyCodeFoMaterial(EnergyCodeFoMaterialDTO energyCodeFoMaterialDTO) {
        try {
            String engyIdStart = energyCodeFoMaterialDTO.getEngyIdStart();
            String engyIdEnd = energyCodeFoMaterialDTO.getEngyIdEnd();
            Long pageNum = energyCodeFoMaterialDTO.getPageNum();
            Long pageSize = energyCodeFoMaterialDTO.getPageSize();
            LambdaQueryWrapper<EnergyCodeFoMaterial> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(engyIdStart) && StringUtils.isNotBlank(engyIdEnd)) {
                queryWrapper.between(EnergyCodeFoMaterial::getEngyId, engyIdStart, engyIdEnd);
            } else if (StringUtils.isNotBlank(engyIdStart)) {
                queryWrapper.eq(EnergyCodeFoMaterial::getEngyId, engyIdStart);
            } else if (StringUtils.isNotBlank(engyIdEnd)) {
                queryWrapper.eq(EnergyCodeFoMaterial::getEngyId, engyIdEnd);
            }
            Page<EnergyCodeFoMaterial> page = energyCodeFoMaterialService.page(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<EnergyCodeFoMaterial> records = page.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("total",total);
            dataMap.put("list",records);
            if (records.isEmpty()){
                return Result.successOne("查无资料", dataMap);
            } else {
                return Result.successOne("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 根据Id查询能源代码
     */
    @Log(title = "根据Id查询能源代码", businessType = BusinessType.OTHER)
    @Operation(summary = "根据Id查询能源代码")
    @GetMapping("/queryById")
    public Object queryEnergyCodeFoMaterialById(@RequestParam String id) {
        try {
            List<EnergyCodeFoMaterial> list = energyCodeFoMaterialService.query().eq("id", id).list();
            return Result.successOne("查询成功", list);
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 查询固液体能源代码下拉选单
     */
    @Log(title = "查询固液体能源代码下拉选单", businessType = BusinessType.OTHER)
    @Operation(summary = "查询固液体能源代码下拉选单")
    @GetMapping("/queryDropDownMenu")
    public Object queryDropDownMenu() {
        List<EnergyCodeFoMaterial> list = energyCodeFoMaterialService.list();
        List<Map<String, String>> resultList = new ArrayList<>();
        for (EnergyCodeFoMaterial energyCodeFoMaterial : list) {
            Map<String, String> resultMap = new HashMap<>(16);
            resultMap.put("value", energyCodeFoMaterial.getEngyId());
            resultMap.put("label", energyCodeFoMaterial.getEngyName());
            resultList.add(resultMap);
        }
        return Result.successOne("查询成功", resultList);
    }
}
