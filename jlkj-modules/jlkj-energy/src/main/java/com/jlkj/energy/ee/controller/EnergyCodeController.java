package com.jlkj.energy.ee.controller;

import com.alibaba.fastjson.JSONArray;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 265675
 * @Description: 能源代码维护controller
 * @Date:  2022-04-24
 */
@RestController
@RequestMapping("/energy/code")
public class EnergyCodeController extends BaseController {

    @Resource
    EnergyCodeService energyCodeService;

    /**
     * @Description: 新增能源代码资料
     * @Author: 111191
     * @Date: 2023年6月30日, 0030 上午 11:41:16
     * @Param: energyCode 能源代码资料
     * @Return: AjaxResult
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
    public AjaxResult queryDropDownMenu() {
        JSONArray arrays = energyCodeService.queryDropDownMenu();
        return AjaxResult.success(arrays);
    }

    /**
     * 查询能源代码下拉选单带中文
     */
    @Log(title = "查询能源代码下拉选单带中文", businessType = BusinessType.OTHER)
    @Operation(summary = "查询能源代码下拉选单")
    @GetMapping("/queryDropDownMenuZh")
    public AjaxResult queryDropDownMenuZh() {
        JSONArray arrays = energyCodeService.queryDropDownMenuZh();
        return AjaxResult.success(arrays);
    }

    /**
     * 新增固液体能源代码资料
     */
    @Log(title = "新增固液体能源代码资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增固液体能源代码资料")
    @PostMapping("/addSolid")
    @RequiresPermissions("ee:code:add")
    public AjaxResult addSolidLiquidEnergyCode(@Validated @RequestBody EnergyCode energyCode) {
        return toAjax(energyCodeService.addSolidLiquidEnergyCode(energyCode));
    }

    /**
     * 修改固液体能源代码资料
     */
    @Log(title = "修改固液体能源代码资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改固液体能源代码资料")
    @PostMapping("/updateSolid")
    @RequiresPermissions("ee:code:update")
    public AjaxResult updateSolidLiquidEnergyCode(@Validated @RequestBody EnergyCode energyCode) {
        return toAjax(energyCodeService.updateSolidLiquidEnergyCode(energyCode));
    }

    /**
     * 删除固液体能源代码资料
     */
    @Log(title = "删除固液体能源代码资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除固液体能源代码资料")
    @DeleteMapping("/deleteSolid/{ids}")
    @RequiresPermissions("ee:code:delete")
    public AjaxResult deleteSolidLiquidEnergyCode(@PathVariable String[] ids) {
        return toAjax(energyCodeService.deleteSolidLiquidEnergyCode(ids));
    }

    /**
     * 固液体能源代码资料查询与列表
     */
    @Log(title = "固液体能源代码资料查询与列表", businessType = BusinessType.OTHER)
    @Operation(summary = "能源代码资料查询与列表")
    @GetMapping("/querySolid")
    @RequiresPermissions("ee:energy:query")
    public TableDataInfo querySolidLiquidEnergyCode(EnergyCodeDTO energyCodeDTO) {
        startPage();
        List<EnergyCode> list = energyCodeService.querySolidLiquidEnergyCode(energyCodeDTO);
        return getDataTable(list);
    }

    /**
     * 查询固液体能源代码下拉选单
     */
    @Log(title = "查询固液体能源代码下拉选单", businessType = BusinessType.OTHER)
    @Operation(summary = "查询能源代码下拉选单")
    @GetMapping("/querySolidLiquidDropDownMenu")
    public AjaxResult querySolidLiquidDropDownMenu() {
        JSONArray arrays = energyCodeService.querySolidLiquidDropDownMenu();
        return AjaxResult.success(arrays);
    }
}
