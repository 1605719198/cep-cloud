package com.jlkj.energy.ee.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.dto.energy.ee.EnergyConsumeOutputDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.domain.EnergyConsumeOutput;
import com.jlkj.energy.ee.service.EnergyConsumeOutputService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 智能研发室 黄兵
 * 能源耗用产出收集作业报文程序controller
 * @Date: 2022/5/5 - 8:34
 */
@RestController
@RequestMapping("/energy/consume/output")
@Slf4j
public class EnergyConsumeOutputController extends BaseController {

    @Autowired
    EnergyConsumeOutputService energyConsumeOutputService;

    /**
     * 接收报文执行每日能源产耗资料(t_energy_consume_output)档的新增
     */
    @Log(title = "接收报文执行每日能源产耗资料(t_energy_consume_output)档的新增", businessType = BusinessType.INSERT)
    @Operation(summary = "新增每日能源产耗资料")
    @PostMapping("/eeozc1/xctl")
    public AjaxResult addEnergyConsumeOutput(@Validated  @RequestBody EnergyConsumeOutput energyConsumeOutput) {
        return toAjax(energyConsumeOutputService.addEnergyConsumeOutput(energyConsumeOutput));
    }

    /**
     * 成本中心日能耗资料查询
     * @Author: 111191
     * @Date: 2023年7月8日, 0008 上午 07:35:39
     * @param energyConsumeOutputDTO
     * @return com.jlkj.common.core.web.page.TableDataInfo
     */
    @Log(title = "成本中心日能耗资料查询", businessType = BusinessType.OTHER)
    @Operation(summary = "查询成本中心日能耗资料")
    @GetMapping("/query")
    public TableDataInfo queryEnergyConsumeOutput(EnergyConsumeOutputDTO energyConsumeOutputDTO) {
        startPage();
        List<EnergyConsumeOutput> list = energyConsumeOutputService.queryEnergyConsumeOutput(energyConsumeOutputDTO);
        return getDataTable(list);
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
