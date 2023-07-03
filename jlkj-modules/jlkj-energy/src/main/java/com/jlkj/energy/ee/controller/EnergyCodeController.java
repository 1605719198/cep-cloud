package com.jlkj.energy.ee.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.dto.energy.ee.EnergyCodeDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.energy.ee.domain.EnergyCode;
import com.jlkj.energy.ee.service.EnergyCodeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
public class EnergyCodeController extends BaseController {

    @Autowired
    EnergyCodeService energyCodeService;

    /**
     * @description 新增能源代码资料
     * @author: 111191
     * @date: 2023年6月30日, 0030 上午 11:41:16
     * @param: energyCode
     * @return: java.lang.Object
     * @throws:
     */
    @Log(title = "新增能源代码资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增能源代码资料")
    @PostMapping("/add")
    @RequiresPermissions("ee:energy:add")
    public AjaxResult addEnergyCode(@Validated @RequestBody EnergyCode energyCode) {
        List<EnergyCode> list = energyCodeService.query().eq("engy_id", energyCode.getEngyId()).list();
        if (!list.isEmpty()) {
            return AjaxResult.error("您输入的能源代码系统中已存在，请重新输入！");
        }
        return toAjax(energyCodeService.addEnergyCode(energyCode));
    }

    /**
     * 修改能源代码资料
     */
    @Log(title = "修改能源代码资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改能源代码资料")
    @PostMapping("/update")
    @RequiresPermissions("ee:energy:update")
    public AjaxResult updateEnergyCode(@Validated @RequestBody EnergyCode energyCode) {
        return toAjax(energyCodeService.updateEnergyCode(energyCode));
    }

    /**
     * 删除能源代码资料
     */
    @Log(title = "删除能源代码资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除能源代码资料")
    @DeleteMapping("/delete/{ids}")
    @RequiresPermissions("ee:energy:delete")
    public AjaxResult deleteEnergyCode(@PathVariable String[] ids) {
        return toAjax(energyCodeService.deleteEnergyCode(ids));
    }

    /**
     * 能源代码资料查询与列表
     */
    @Log(title = "能源代码资料查询与列表", businessType = BusinessType.OTHER)
    @Operation(summary = "能源代码资料查询与列表")
    @GetMapping("/query")
    public TableDataInfo queryEnergyCode(EnergyCodeDTO energyCodeDTO) {
        startPage();
        List<EnergyCode> list = energyCodeService.queryEnergyCode(energyCodeDTO);
        return getDataTable(list);
    }

    /**
     * 删除能源代码资料(mq)
     */
    @Log(title = "删除能源代码资料(mq)", businessType = BusinessType.DELETE)
    @Operation(summary = "删除能源代码资料(mq)")
    @DeleteMapping("/deleteErp")
    public AjaxResult deleteEnergyCode(@RequestParam String engyId) {
        return toAjax(energyCodeService.removeEnergyCode(engyId));
    }


    /**
     * 根据Id查询能源代码
     */
    @Log(title = "根据Id查询能源代码", businessType = BusinessType.OTHER)
    @Operation(summary = "根据Id查询能源代码")
    @GetMapping("/queryById/{id}")
    public AjaxResult queryEnergyCodeById(@PathVariable String id) {
        return AjaxResult.success(energyCodeService.queryEnergyCodeById(id));
    }

    /**
     * 查询能源代码下拉选单
     */
    @Log(title = "查询能源代码下拉选单", businessType = BusinessType.OTHER)
    @Operation(summary = "查询能源代码下拉选单")
    @GetMapping("/queryDropDownMenu")
    public Object queryDropDownMenu() {
        JSONArray arrays = energyCodeService.queryDropDownMenu();
        return AjaxResult.success(arrays);
    }

    /**
     * 查询能源代码下拉选单带中文
     */
    @Log(title = "查询能源代码下拉选单带中文", businessType = BusinessType.OTHER)
    @Operation(summary = "查询能源代码下拉选单")
    @GetMapping("/queryDropDownMenuZh")
    public Object queryDropDownMenuZh() {
        JSONArray arrays = energyCodeService.queryDropDownMenuZh();
        return AjaxResult.success(arrays);
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
            if (list.size() >= 1) {
                return AjaxResult.error("您输入的固液体能源代码系统中已存在，请重新输入！");
            }
            boolean result = energyCodeService.save(energyCode);
            if (result) {
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
                return AjaxResult.success("新增成功", resultList);
            } else {
                return AjaxResult.error("新增失败，请重新提交");
            }
        } catch (Exception e) {
            return AjaxResult.error();
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
            if (engyId.equals(energyCode.getEngyId())) {
                boolean result = energyCodeService.updateById(energyCode);
                if (result) {
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
                    return AjaxResult.success("修改成功", resultList);
                } else {
                    return AjaxResult.error("修改失败，请重新提交");
                }
            } else {
                return AjaxResult.error("能源代码，不允许修改");
            }
        } catch (Exception e) {
            return AjaxResult.error();
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
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败，请重新提交");
            }
        } catch (Exception e) {
            return AjaxResult.error();
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
            Map<String, Object> dataMap = new HashMap<>(16);
            dataMap.put("total", total);
            dataMap.put("list", records);
            if (page.getRecords().isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
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
        return AjaxResult.success("查询成功", resultList);
    }
}
