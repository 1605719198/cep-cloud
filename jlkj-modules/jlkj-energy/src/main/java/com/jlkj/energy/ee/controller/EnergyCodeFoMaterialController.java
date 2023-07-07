package com.jlkj.energy.ee.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.energy.ee.EnergyCodeFoMaterialDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.domain.EnergyCodeFoMaterial;
import com.jlkj.energy.ee.service.EnergyCodeFoMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 智能研发室 黄兵
 * @Description 固液体能源代码对应料号维护controller
 * @date 2022/4/28 - 11:26
 */
@RestController
@RequestMapping("/energy/code/material")
public class EnergyCodeFoMaterialController extends BaseController {

    @Autowired
    EnergyCodeFoMaterialService energyCodeFoMaterialService;

    /**
     * 新增固液体能源代码对应料号维护资料
     */
    @Log(title = "新增固液体能源代码对应料号维护资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增固液体能源代码对应料号维护资料")
    @PostMapping("/add")
    public AjaxResult addEnergyCodeFoMaterial(@Validated @RequestBody EnergyCodeFoMaterial energyCodeFoMaterial) {
        return toAjax(energyCodeFoMaterialService.addEnergyCodeFoMaterial(energyCodeFoMaterial));
    }

    /**
     * 修改固液体能源代码对应料号维护资料
     */
    @Log(title = "修改固液体能源代码对应料号维护资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改固液体能源代码对应料号维护资料")
    @PostMapping("/update")
    public AjaxResult updateEnergyCodeFoMaterial(@RequestBody EnergyCodeFoMaterial energyCodeFoMaterial) {
        return energyCodeFoMaterialService.updateEnergyCodeFoMaterial(energyCodeFoMaterial);
    }

    /**
     * 删除固液体能源代码对应料号维护资料
     */
    @Log(title = "删除固液体能源代码对应料号维护资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除固液体能源代码对应料号维护资料")
    @DeleteMapping("/delete")
    public AjaxResult deleteEnergyCodeFoMaterial(@RequestParam List<String> id) {
        return energyCodeFoMaterialService.deleteEnergyCodeFoMaterial(id);
    }

    /**
     * 删除固液体能源代码对应料号维护资料(mq)
     */
    @Log(title = "删除固液体能源代码对应料号维护资料(mq)", businessType = BusinessType.DELETE)
    @Operation(summary = "删除固液体能源代码对应料号维护资料(mq)")
    @DeleteMapping("/deleteErp")
    public AjaxResult deleteEnergyCode(@RequestParam String engyId) {
        return energyCodeFoMaterialService.deleteEnergyCode(engyId);
    }

    /**
     * 固液体能源代码对应料号资料查询与列表
     */
    @Log(title = "固液体能源代码对应料号资料查询与列表", businessType = BusinessType.OTHER)
    @Operation(summary = "固液体能源代码对应料号资料查询与列表")
    @GetMapping("/query")
    public AjaxResult queryEnergyCodeFoMaterial(EnergyCodeFoMaterialDTO energyCodeFoMaterialDTO) {
        return energyCodeFoMaterialService.queryEnergyCodeFoMaterial(energyCodeFoMaterialDTO);
    }

    /**
     * 根据Id查询能源代码
     */
    @Log(title = "根据Id查询能源代码", businessType = BusinessType.OTHER)
    @Operation(summary = "根据Id查询能源代码")
    @GetMapping("/queryById")
    public AjaxResult queryEnergyCodeFoMaterialById(@RequestParam String id) {
        try {
            List<EnergyCodeFoMaterial> list = energyCodeFoMaterialService.query().eq("id", id).list();
            return AjaxResult.success("查询成功", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 查询固液体能源代码下拉选单
     */
    @Log(title = "查询固液体能源代码下拉选单", businessType = BusinessType.OTHER)
    @Operation(summary = "查询固液体能源代码下拉选单")
    @GetMapping("/queryDropDownMenu")
    public AjaxResult queryDropDownMenu() {
        List<Map<String, String>> list = energyCodeFoMaterialService.queryDropDownMenuZh();
        return AjaxResult.success(list);
    }
}
