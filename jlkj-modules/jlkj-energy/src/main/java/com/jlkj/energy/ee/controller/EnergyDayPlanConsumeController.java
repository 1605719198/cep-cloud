package com.jlkj.energy.ee.controller;


import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.energy.ee.EnergyDayPlanConsumeDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.domain.EnergyDayPlanConsume;
import com.jlkj.energy.ee.service.EnergyDayPlanConsumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author yzl
 * @Description 日计划能耗记录
 * @create 2022-09-02 13:32:21.117575
 */
@Tag(name = "日计划能耗记录")
@RestController
@RequestMapping("/energy/day/plan/consume")
@Slf4j
public class EnergyDayPlanConsumeController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    EnergyDayPlanConsumeService energyDayPlanConsumeService;

    /**
     * 接收报文 (EA04)档的新增
     */
    @Log(title = "新增日计划能耗资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增日计划能耗资料")
    @PostMapping("/ea04/xctl")
    public Object addEnergyDayPlanConsume(@RequestBody EnergyDayPlanConsumeDTO energyDayPlanConsumeDTO) {
        try {
                EnergyDayPlanConsume energyDayPlanConsume = new EnergyDayPlanConsume();
                BeanUtils.copyProperties(energyDayPlanConsumeDTO, energyDayPlanConsume);
                boolean result = energyDayPlanConsumeService.save(energyDayPlanConsume);
                if (result) {
                    return AjaxResult.success("新增成功");
                } else {
                    return AjaxResult.error("新增失败");
                }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }


    /**
     * 修改日计划能耗资料
     */
    @Log(title = "修改日计划能耗资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改日计划能耗资料")
    @PostMapping("/update")
    public Object updateEnergyDayPlanConsume(@RequestBody EnergyDayPlanConsumeDTO energyDayPlanConsumeDTO) {
        try {
            EnergyDayPlanConsume energyDayPlanConsume = new EnergyDayPlanConsume();
            BeanUtils.copyProperties(energyDayPlanConsumeDTO, energyDayPlanConsume);
            boolean result = energyDayPlanConsumeService.updateById(energyDayPlanConsume);
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
     * 删除日计划能耗资料
     */
    @Log(title = "删除日计划能耗资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除日计划能耗资料")
    @DeleteMapping("/delete")
    public Object deleteEnergyDayPlanConsume(@RequestParam List<String> id) {
        try {
            boolean result = energyDayPlanConsumeService.removeBatchByIds(id);
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

