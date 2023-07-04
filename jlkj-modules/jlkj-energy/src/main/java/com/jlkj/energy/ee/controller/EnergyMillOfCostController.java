package com.jlkj.energy.ee.controller;

import com.alibaba.fastjson2.JSONArray;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.energy.ee.domain.EnergyMillOfCost;
import com.jlkj.energy.ee.dto.energymillofcost.EnergyMillOfCostDTO;
import com.jlkj.energy.ee.service.EnergyMillOfCostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 智能研发室 黄兵
 * @Description 产线代码对应成本中心资料维护controller
 * @date 2022/4/30 - 8:37
 */
@RestController
@RequestMapping("/energy/mill/cost")
public class EnergyMillOfCostController extends BaseController {

    @Autowired
    EnergyMillOfCostService energyMillOfCostService;

    /**
     * 新增产线代码对应成本中心资料
     */
    @Log(title = "新增产线代码对应成本中心资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增产线代码对应成本中心资料")
    @PostMapping("/add")
    @RequiresPermissions("mill:cost:add")
    public AjaxResult addEnergyMillOfCost(@Validated @RequestBody EnergyMillOfCost energyMillOfCost) {
        return toAjax(energyMillOfCostService.addEnergyMillOfCost(energyMillOfCost));
    }

    /**
     * 删除产线代码对应成本中心资料
     */
    @Log(title = "删除产线代码对应成本中心资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除产线代码对应成本中心资料")
    @DeleteMapping("/delete")
    @RequiresPermissions("mill:cost:delete")
    public Object deleteEnergyMillOfCost(@RequestParam List<String> id) {
        return  toAjax(energyMillOfCostService.removeBatchByIds(id));
    }

    /**
     * 产线代码对应成本中心资料查询与列表
     */
    @Log(title = "能源信息配置查询", businessType = BusinessType.OTHER)
    @Operation(summary = "产线代码对应成本中心资料查询与列表")
    @GetMapping("/query")
    @RequiresPermissions("mill:cost:query")
    public TableDataInfo queryEnergyMillOfCost(EnergyMillOfCostDTO energyMillOfCostDTO) {
        startPage();
        List<EnergyMillOfCost> list = energyMillOfCostService.queryEnergyMillOfCost(energyMillOfCostDTO);
        return getDataTable(list);
    }

    /**
     * 查询能源代码下拉选单带中文
     */
    @Log(title = "查询产线代码下拉选单带中文", businessType = BusinessType.OTHER)
    @Operation(summary = "查询产线代码下拉选单")
    @GetMapping("/selectsZh")
    public Object queryDropDownMenuZh() {
        JSONArray arrays = energyMillOfCostService.queryDropDownMenuZh();
        return AjaxResult.success(arrays);
    }

}
