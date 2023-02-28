package com.jlkj.energy.ee.controller;


 
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.energy.ee.EnergyMonthPlanOutputDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.domain.EnergyMonthPlanOutput;
import com.jlkj.energy.ee.service.EnergyMonthPlanOutputService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 智能研发室 黄兵
 * @Description 月计划产量controller
 * @date 2022/5/5 - 14:16
 */
@RestController
@RequestMapping("/energy/month/Plan/OutPut")
public class EnergyMonthPlanOutputController {

    @Autowired
    EnergyMonthPlanOutputService energyMonthPlanOutputService;

    /**
     * 接收报文 (EA02)新增
     */
    @Log(title = "新增月计划产量资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增月计划产量资料")
    @PostMapping("/ea02/xctl")
    public Object addEnergyMonthPlanOutput(@RequestBody EnergyMonthPlanOutputDTO energyMonthPlanOutputDTO) {
        try {
                EnergyMonthPlanOutput energyMonthPlanOutput = new EnergyMonthPlanOutput();
                BeanUtils.copyProperties(energyMonthPlanOutputDTO, energyMonthPlanOutput);
                boolean result = energyMonthPlanOutputService.save(energyMonthPlanOutput);
                if(result){
                    return AjaxResult.success("新增成功");
                }else {
                    return AjaxResult.error("新增失败");
                }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 修改月计划产量资料
     */
    @Log(title = "修改月计划产量资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改月计划产量资料")
    @PostMapping("/update")
    public Object updateEnergyMonthPlanOutput(@RequestBody EnergyMonthPlanOutputDTO energyMonthPlanOutputDTO) {
        try {
            EnergyMonthPlanOutput energyMonthPlanOutput = new EnergyMonthPlanOutput();
            BeanUtils.copyProperties(energyMonthPlanOutputDTO, energyMonthPlanOutput);
            boolean result = energyMonthPlanOutputService.updateById(energyMonthPlanOutput);
            if (result){
                return AjaxResult.success("修改成功");
            } else {
                return AjaxResult.error("修改失败，请重新提交");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除月计划产量资料
     */
    @Log(title = "删除月计划产量资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除月计划产量资料")
    @DeleteMapping("/delete")
    public Object deleteEnergyMonthPlanOutput(@RequestParam List<String> id) {
        try {
            boolean result = energyMonthPlanOutputService.removeBatchByIds(id);
            if (result) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败，请重新提交");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
