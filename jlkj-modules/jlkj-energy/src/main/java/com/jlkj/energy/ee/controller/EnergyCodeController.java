package com.jlkj.energy.ee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.dto.dto.energyprovider.EnergyCodeDTO;
import com.jlkj.common.dto.resp.Result;
import com.jlkj.energy.ee.domain.EnergyCode;
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
 * @author 265675
 * @Description 能源代码维护controller
 * @create 2022-04-24
 */
@RestController
@RequestMapping("/energy/code")
public class EnergyCodeController {

    @Autowired
    EnergyCodeService energyCodeService;

    /**
     * 新增能源代码资料
     */
    @Log(title = "新增能源代码资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增能源代码资料")
    @PostMapping("/add")
    public Object addEnergyCode(@RequestBody EnergyCodeDTO energyCodeDTO, @RequestHeader("token") String token) {
        try {
            EnergyCode energyCode = new EnergyCode();
            BeanUtils.copyProperties(energyCodeDTO, energyCode);
            List<EnergyCode> list = energyCodeService.query().eq("engy_id", energyCode.getEngyId()).list();
            if(!list.isEmpty()){
                return Result.failedOne("您输入的能源代码系统中已存在，请重新输入！");
            }
            boolean result = energyCodeService.save(energyCode);
            if(result){
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("comp_id", energyCode.getCompId());
                resultMap.put("engy_id", energyCode.getEngyId());
                resultMap.put("engy_name", energyCode.getEngyName());
                resultMap.put("engy_ac", energyCode.getEngyAc());
                resultMap.put("engy_unit", energyCode.getEngyUnit());
                resultMap.put("cal_value", energyCode.getCalValue().toString());
                resultMap.put("cal_unit", energyCode.getCalUnit());
                resultMap.put("engy_type", energyCode.getEngyType());
                resultMap.put("src_type", energyCode.getSrcType());
                resultMap.put("acct_sys", energyCode.getAcctSys());
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
     * 修改能源代码资料
     */
    @Log(title = "修改能源代码资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改能源代码资料")
    @PostMapping("/update")
    public Object updateEnergyCode(@RequestBody EnergyCodeDTO energyCodeDTO, @RequestHeader("token") String token) {
        try {
            EnergyCode energyCode = new EnergyCode();
            BeanUtils.copyProperties(energyCodeDTO, energyCode);
            String engyId1 = energyCode.getEngyId();
            LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EnergyCode::getEngyId, energyCode.getEngyId());
            LambdaUpdateWrapper<EnergyCode> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(StringUtils.isNotBlank(engyId1), EnergyCode::getEngyId, engyId1);
            EnergyCode code = energyCodeService.getOne(queryWrapper);
            if (code == null){
                energyCodeService.save(energyCode);
                return Result.success("能源代码为空,已为您新增了一笔数据");
            } else if(code.getEngyId().equals(energyCode.getEngyId())) {
                boolean result = energyCodeService.update(energyCode, updateWrapper);
                if (result){
                    Map<String, String> resultMap = new HashMap<>(16);
                    resultMap.put("comp_id", energyCode.getCompId());
                    resultMap.put("uuid", energyCode.getId());
                    resultMap.put("engy_id", energyCode.getEngyId());
                    resultMap.put("engy_name", energyCode.getEngyName());
                    resultMap.put("engy_ac", energyCode.getEngyAc());
                    resultMap.put("engy_unit", energyCode.getEngyUnit());
                    resultMap.put("cal_value", energyCode.getCalValue().toString());
                    resultMap.put("cal_unit", energyCode.getCalUnit());
                    resultMap.put("engy_type", energyCode.getEngyType());
                    resultMap.put("src_type", energyCode.getSrcType());
                    resultMap.put("acct_sys", energyCode.getAcctSys());
                    List<Map<String, String>> resultList = new ArrayList<>();
                    resultList.add(resultMap);
                    return Result.successOne("修改成功", resultList);
                } else {
                    return Result.failedTwo("修改失败，请重新提交");
                }
            } else {
                return Result.failedOne("能源代码，不允许修改");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 删除能源代码资料
     */
    @Log(title = "删除能源代码资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除能源代码资料")
    @DeleteMapping("/delete")
    public Object deleteEnergyCode(@RequestParam List<String> id) {
        try {
                boolean result = energyCodeService.removeBatchByIds(id);
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
     * 删除能源代码资料(mq)
     */
    @Log(title = "删除能源代码资料(mq)", businessType = BusinessType.DELETE)
    @Operation(summary = "删除能源代码资料(mq)")
    @DeleteMapping("/deleteErp")
    public Object deleteEnergyCode(@RequestParam String engyId, @RequestHeader("token") String token) {
        try {
            LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EnergyCode::getEngyId, engyId);
            boolean result = energyCodeService.remove(queryWrapper);
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
     * 能源代码资料查询与列表
     */
    @Log(title = "查询厂商基本资料", businessType = BusinessType.OTHER)
    @Operation(summary = "能源代码资料查询与列表")
    @GetMapping("/query")
    public Object queryEnergyCode(EnergyCodeDTO energyCodeDTO) {
        try {
            String engyIdStart = energyCodeDTO.getEngyIdStart();
            String engyIdEnd = energyCodeDTO.getEngyIdEnd();
            Long pageNum = energyCodeDTO.getPageNum();
            Long pageSize = energyCodeDTO.getPageSize();
            String[] solidLiquid = {"G000", "Y000"};
            LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.notIn(EnergyCode::getEngyType, solidLiquid)
                        .orderByAsc(EnergyCode::getEngyId);
            if (StringUtils.isNotBlank(engyIdStart) && StringUtils.isNotBlank(engyIdEnd)) {
                queryWrapper.between(EnergyCode::getEngyId, engyIdStart, engyIdEnd);
            } else if (StringUtils.isNotBlank(engyIdStart)) {
                queryWrapper.eq(EnergyCode::getEngyId, engyIdStart);
            } else if (StringUtils.isNotBlank(engyIdEnd)) {
                queryWrapper.eq(EnergyCode::getEngyId, engyIdEnd);
            }
            Page<EnergyCode> page = energyCodeService.page(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<EnergyCode> records = page.getRecords();
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
    public Object queryEnergyCodeById(@RequestParam String id) {
        try {
            List<EnergyCode> list = energyCodeService.query().eq("id", id).list();
            return Result.successOne("查询成功", list);
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 查询能源代码下拉选单
     */
    @Log(title = "查询能源代码下拉选单", businessType = BusinessType.OTHER)
    @Operation(summary = "查询能源代码下拉选单")
    @GetMapping("/queryDropDownMenu")
    public Object queryDropDownMenu() {
        String[] solidLiquid = {"G000", "Y000"};
        LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.notIn(EnergyCode::getEngyType, solidLiquid)
                    .orderByAsc(EnergyCode::getEngyId);
        List<EnergyCode> list = energyCodeService.list(queryWrapper);
        List<String> list1 = new ArrayList<>();
        for (EnergyCode energyCode : list) {
            list1.add(energyCode.getEngyId());
        }
        return Result.success(list1);
    }

    /**
     * 查询能源代码下拉选单带中文
     */
    @Log(title = "查询能源代码下拉选单带中文", businessType = BusinessType.OTHER)
    @Operation(summary = "查询能源代码下拉选单")
    @GetMapping("/queryDropDownMenuZh")
    public Object queryDropDownMenuZh() {
        String[] solidLiquid = {"G000", "Y000"};
        LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.notIn(EnergyCode::getEngyType, solidLiquid)
                .orderByAsc(EnergyCode::getEngyId);
        List<EnergyCode> list = energyCodeService.list(queryWrapper);
        List<Map<String, String>> list1 = new ArrayList<>();
        for (EnergyCode energyCode : list) {
            Map<String, String> param = new HashMap<>(1);
            param.put("EngyId",energyCode.getEngyId());
            param.put("EngyName",energyCode.getEngyName());
            list1.add(param);
        }
        return Result.success(list1);
    }

    /**
     * 新增固液体能源代码资料
     */
    @Log(title = "新增固液体能源代码资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增固液体能源代码资料")
    @PostMapping("/addSolid")
    public Object addSolidLiquidEnergyCode(@RequestBody EnergyCodeDTO energyCodeDTO) {
        try {
            EnergyCode energyCode = new EnergyCode();
            BeanUtils.copyProperties(energyCodeDTO, energyCode);
            List<EnergyCode> list = energyCodeService.query().eq("engy_id", energyCode.getEngyId()).list();
            if(list.size() >= 1){
                return Result.failedOne("您输入的固液体能源代码系统中已存在，请重新输入！");
            }
            boolean result = energyCodeService.save(energyCode);
            if(result){
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("comp_id", energyCode.getCompId());
                resultMap.put("engy_id", energyCode.getEngyId());
                resultMap.put("engy_name", energyCode.getEngyName());
                resultMap.put("engy_ac", energyCode.getEngyAc());
                resultMap.put("engy_unit", energyCode.getEngyUnit());
                resultMap.put("cal_value", energyCode.getCalValue().toString());
                resultMap.put("cal_unit", energyCode.getCalUnit());
                resultMap.put("engy_type", energyCode.getEngyType());
                resultMap.put("src_type", energyCode.getSrcType());
                resultMap.put("acct_sys", energyCode.getAcctSys());
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
     * 修改固液体能源代码资料
     */
    @Log(title = "修改固液体能源代码资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改固液体能源代码资料")
    @PostMapping("/updateSolid")
    public Object updateSolidLiquidEnergyCode(@RequestBody EnergyCodeDTO energyCodeDTO) {
        try {
            EnergyCode energyCode = new EnergyCode();
            BeanUtils.copyProperties(energyCodeDTO, energyCode);
            LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EnergyCode::getId, energyCode.getId());
            Map<String, Object> map = energyCodeService.getMap(queryWrapper);
            Object engyId = map.get("engy_id");
            if(engyId.equals(energyCode.getEngyId())) {
                boolean result = energyCodeService.updateById(energyCode);
                if (result){
                    Map<String, String> resultMap = new HashMap<>(16);
                    resultMap.put("comp_id", energyCode.getCompId());
                    resultMap.put("uuid", energyCode.getId());
                    resultMap.put("engy_id", energyCode.getEngyId());
                    resultMap.put("engy_name", energyCode.getEngyName());
                    resultMap.put("engy_ac", energyCode.getEngyAc());
                    resultMap.put("engy_unit", energyCode.getEngyUnit());
                    resultMap.put("cal_value", energyCode.getCalValue().toString());
                    resultMap.put("cal_unit", energyCode.getCalUnit());
                    resultMap.put("engy_type", energyCode.getEngyType());
                    resultMap.put("src_type", energyCode.getSrcType());
                    resultMap.put("acct_sys", energyCode.getAcctSys());
                    List<Map<String, String>> resultList = new ArrayList<>();
                    resultList.add(resultMap);
                    return Result.successOne("修改成功", resultList);
                } else {
                    return Result.failedTwo("修改失败，请重新提交");
                }
            } else {
                return Result.failedOne("能源代码，不允许修改");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 删除固液体能源代码资料
     */
    @Log(title = "删除固液体能源代码资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除固液体能源代码资料")
    @DeleteMapping("/deleteSolid")
    public Object deleteSolidLiquidEnergyCode(@RequestParam List<String> id) {
        try {
                boolean result = energyCodeService.removeBatchByIds(id);
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
     * 固液体能源代码资料查询与列表
     */
    @Log(title = "固液体能源代码资料查询与列表", businessType = BusinessType.OTHER)
    @Operation(summary = "能源代码资料查询与列表")
    @GetMapping("/querySolid")
    public Object querySolidLiquidEnergyCode(EnergyCodeDTO energyCodeDTO) {
        try {
            String engyIdStart = energyCodeDTO.getEngyIdStart();
            String engyIdEnd = energyCodeDTO.getEngyIdEnd();
            Long pageNum = energyCodeDTO.getPageNum();
            Long pageSize = energyCodeDTO.getPageSize();
            String[] solidLiquid = {"G000", "Y000"};
            LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(EnergyCode::getEngyType, solidLiquid);
            if (StringUtils.isNotBlank(engyIdStart) && StringUtils.isNotBlank(engyIdEnd)) {
                queryWrapper.between(EnergyCode::getEngyId, engyIdStart, engyIdEnd);
            } else if (StringUtils.isNotBlank(engyIdStart)) {
                queryWrapper.eq(EnergyCode::getEngyId, engyIdStart);
            } else if (StringUtils.isNotBlank(engyIdEnd)) {
                queryWrapper.eq(EnergyCode::getEngyId, engyIdEnd);
            }
            Page<EnergyCode> page = energyCodeService.page(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<EnergyCode> records = page.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("total",total);
            dataMap.put("list",records);
            if (page.getRecords().isEmpty()){
                return Result.failedOne("查无资料");
            } else {
                return Result.successOne("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 查询固液体能源代码下拉选单
     */
    @Log(title = "查询固液体能源代码下拉选单", businessType = BusinessType.OTHER)
    @Operation(summary = "查询能源代码下拉选单")
    @GetMapping("/querySolidLiquidDropDownMenu")
    public Object querySolidLiquidDropDownMenu() {
        String[] solidLiquid = {"G000", "Y000"};
        LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(EnergyCode::getEngyType, solidLiquid)
                    .orderByAsc(EnergyCode::getEngyId);
        List<EnergyCode> list = energyCodeService.list(queryWrapper);
        List<Map<String, String>> resultList = new ArrayList<>();
        for (EnergyCode energyCode : list) {
            Map<String, String> resultMap = new HashMap<>(16);
            resultMap.put("value", energyCode.getEngyId());
            resultMap.put("label", energyCode.getEngyName());
            resultList.add(resultMap);
        }
        return Result.successOne("查询成功", resultList);
    }
}
