package com.jlkj.energy.ee.controller;


 
import com.jlkj.common.dto.dto.energyprovider.EnergyMonthPlanConsumeDTO;
import com.jlkj.common.dto.resp.Result;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.domain.EnergyMonthPlanConsume;
import com.jlkj.energy.ee.service.EnergyMonthPlanConsumeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 智能研发室 黄兵
 * @Description 月计划能耗controller
 * @date 2022/5/5 - 11:10
 */
@RestController
@RequestMapping("/energy/month/plan/consume")
public class EnergyMonthPlanConsumeController {

    @Autowired
    EnergyMonthPlanConsumeService energyMonthPlanConsumeService;

    /**
     * 接收报文 (EA01)档的新增
     */
    @Log(title = "接收报文 (EA01)档的新增", businessType = BusinessType.INSERT)
    @Operation(summary = "新增月计划能耗资料")
    @PostMapping("/ea01/xctl")
    public Object addEnergyMonthPlanConsume(@RequestBody EnergyMonthPlanConsumeDTO energyMonthPlanConsumeDTO) {
        try {
                EnergyMonthPlanConsume energyMonthPlanConsume = new EnergyMonthPlanConsume();
                BeanUtils.copyProperties(energyMonthPlanConsumeDTO, energyMonthPlanConsume);
                boolean result = energyMonthPlanConsumeService.save(energyMonthPlanConsume);
                if (result) {
                    return Result.success("新增成功");
                } else {
                    return Result.failedTwo("新增失败");
                }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 修改月计划能耗资料
     */
    @Log(title = "修改月计划能耗资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改月计划能耗资料")
    @PostMapping("/update")
    public Object updateEnergyMonthPlanConsume(@RequestBody EnergyMonthPlanConsumeDTO energyMonthPlanConsumeDTO) {
        try {
            EnergyMonthPlanConsume energyMonthPlanConsume = new EnergyMonthPlanConsume();
            BeanUtils.copyProperties(energyMonthPlanConsumeDTO, energyMonthPlanConsume);
            boolean result = energyMonthPlanConsumeService.updateById(energyMonthPlanConsume);
            if (result){
                return Result.success("修改成功");
            } else {
                return Result.failedTwo("修改失败，请重新提交");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 删除月计划能耗资料
     */
    @Log(title = "删除月计划能耗资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除月计划能耗资料")
    @DeleteMapping("/delete")
    public Object deleteEnergyMonthPlanConsume(@RequestParam List<String> id) {
        try {
            boolean result = energyMonthPlanConsumeService.removeBatchByIds(id);
            if (result) {
                return Result.success("删除成功");
            } else {
                return Result.failedTwo("删除失败，请重新提交");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }
}
