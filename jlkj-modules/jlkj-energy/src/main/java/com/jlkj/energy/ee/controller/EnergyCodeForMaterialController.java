package com.jlkj.energy.ee.controller;

import com.alibaba.fastjson2.JSONArray;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.dto.energy.ee.EnergyCodeForMaterialDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.domain.EnergyCodeForMaterial;
import com.jlkj.energy.ee.service.EnergyCodeForMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 固液体能源代码对应料号维护controller
 *
 * @Author: 智能研发室 黄兵
 * @Date: 2023年7月7日, 0007 上午 09:43:40
 */
@RestController
@RequestMapping("/energy/code/material")
public class EnergyCodeForMaterialController extends BaseController {

    @Autowired
    EnergyCodeForMaterialService energyCodeForMaterialService;

    /**
     * 新增固液体能源代码对应料号维护资料
     *
     * @param energyCodeForMaterial  固液体能源代码对应料号维护资料
     * @return com.jlkj.common.core.web.domain.AjaxResult 返回操作成功/失败信息及数据结果
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:45:24
     */
    @Log(title = "新增固液体能源代码对应料号维护资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增固液体能源代码对应料号维护资料")
    @PostMapping("/add")
    public AjaxResult addEnergyCodeForMaterial(@Validated @RequestBody EnergyCodeForMaterial energyCodeForMaterial) {
        return toAjax(energyCodeForMaterialService.addEnergyCodeForMaterial(energyCodeForMaterial));
    }

    /**
     * 修改固液体能源代码对应料号维护资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:51:38
     * @param energyCodeForMaterial 固液体能源代码对应料号
     * @return com.jlkj.common.core.web.domain.AjaxResult 返回操作成功/失败信息及数据结果
     */
    @Log(title = "修改固液体能源代码对应料号维护资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改固液体能源代码对应料号维护资料")
    @PostMapping("/update")
    public AjaxResult updateEnergyCodeForMaterial(@Validated @RequestBody EnergyCodeForMaterial energyCodeForMaterial) {
        return toAjax(energyCodeForMaterialService.updateEnergyCodeForMaterial(energyCodeForMaterial));
    }

    /**
     * 删除固液体能源代码对应料号维护资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:52:17
     * @param ids 能源代码ids 多笔
     * @return com.jlkj.common.core.web.domain.AjaxResult 返回操作成功/失败信息及数据结果
     */
    @Log(title = "删除固液体能源代码对应料号维护资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除固液体能源代码对应料号维护资料")
    @DeleteMapping("/delete/{ids}")
    public AjaxResult deleteEnergyCodeForMaterial(@PathVariable String[] ids) {
        return toAjax(energyCodeForMaterialService.deleteEnergyCodeForMaterial(ids));
    }

    /**
     * 删除固液体能源代码对应料号维护资料(mq)
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:52:44
     * @param engyId  能源代码id
     * @return com.jlkj.common.core.web.domain.AjaxResult 返回操作成功/失败信息及数据结果
     */
    @Log(title = "删除固液体能源代码对应料号维护资料(mq)", businessType = BusinessType.DELETE)
    @Operation(summary = "删除固液体能源代码对应料号维护资料(mq)")
    @DeleteMapping("/deleteErp/{engyId}")
    public AjaxResult deleteEnergyCode(@PathVariable String engyId) {
        return AjaxResult.success(energyCodeForMaterialService.deleteEnergyCode(engyId));
    }

    /**
     * 固液体能源代码对应料号资料查询与列表
     *
     * @param energyCodeForMaterialDTO 查询条伯
     * @return com.jlkj.common.core.web.page.TableDataInfo 固液体能源代码对应料号资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:32:41
     */
    @Log(title = "固液体能源代码对应料号资料查询与列表", businessType = BusinessType.OTHER)
    @Operation(summary = "固液体能源代码对应料号资料查询与列表")
    @GetMapping("/query")
    public TableDataInfo queryEnergyCodeForMaterial(EnergyCodeForMaterialDTO energyCodeForMaterialDTO) {
        startPage();
        return getDataTable(energyCodeForMaterialService.queryEnergyCodeForMaterial(energyCodeForMaterialDTO));
    }

    /**
     * 根据Id查询能源代码
     *
     * @param id 能源代码
     * @return com.jlkj.common.core.web.domain.AjaxResult 分页反回结果
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:30:49
     */
    @Log(title = "根据Id查询能源代码", businessType = BusinessType.OTHER)
    @Operation(summary = "根据Id查询能源代码")
    @GetMapping("/queryById/{id}")
    public AjaxResult queryEnergyCodeForMaterialById(@PathVariable String id) {
        EnergyCodeForMaterial list = energyCodeForMaterialService.queryEnergyCodeForMaterialById(id);
        return AjaxResult.success(list);
    }

    /**
     * 查询固液体能源代码下拉选单
     *
     * @return com.jlkj.common.core.web.domain.AjaxResult
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:30:34
     */
    @Log(title = "查询固液体能源代码下拉选单", businessType = BusinessType.OTHER)
    @Operation(summary = "查询固液体能源代码下拉选单")
    @GetMapping("/queryDropDownMenu")
    public AjaxResult queryDropDownMenu() {
        JSONArray arrays = energyCodeForMaterialService.queryDropDownMenuZh();
        return AjaxResult.success(arrays);
    }
}
