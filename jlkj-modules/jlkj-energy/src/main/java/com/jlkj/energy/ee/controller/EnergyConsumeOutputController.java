package com.jlkj.energy.ee.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.energy.ee.EnergyConsumeOutputDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.service.EnergyConsumeOutputService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 智能研发室 黄兵
 * @Description 能源耗用产出收集作业报文程序controller
 * @date 2022/5/5 - 8:34
 */
@RestController
@RequestMapping("/energy/consume/output")
@Slf4j
public class EnergyConsumeOutputController {

    @Autowired
    EnergyConsumeOutputService energyConsumeOutputService;

    /**
     * 接收报文执行每日能源产耗资料(t_energy_consume_output)档的新增
     */
    @Log(title = "接收报文执行每日能源产耗资料(t_energy_consume_output)档的新增", businessType = BusinessType.INSERT)
    @Operation(summary = "新增每日能源产耗资料")
    @PostMapping("/eeozc1/xctl")
    public AjaxResult addEnergyConsumeOutput(@RequestBody EnergyConsumeOutputDTO energyConsumeOutputDTO) {
        return energyConsumeOutputService.addEnergyConsumeOutput(energyConsumeOutputDTO);
    }

    /**
     * 成本中心日能耗资料查询
     */
    @Log(title = "成本中心日能耗资料查询", businessType = BusinessType.OTHER)
    @Operation(summary = "查询成本中心日能耗资料")
    @GetMapping("/query")
    public AjaxResult queryEnergyConsumeOutput(EnergyConsumeOutputDTO energyConsumeOutputDTO) {
        return energyConsumeOutputService.queryEnergyConsumeOutput(energyConsumeOutputDTO);
    }

    /**
     * 查询成本中心日能耗资料能源代码下拉选单
     */
    @Log(title = "查询成本中心日能耗资料能源代码下拉选单", businessType = BusinessType.OTHER)
    @Operation(summary = "查询能源代码下拉选单")
    @GetMapping("/queryDropDownMenu")
    public Object queryDropDownMenu() {
        List<String> list1 = energyConsumeOutputService.queryDropDownMenu();
        return AjaxResult.success(list1);
    }
}
