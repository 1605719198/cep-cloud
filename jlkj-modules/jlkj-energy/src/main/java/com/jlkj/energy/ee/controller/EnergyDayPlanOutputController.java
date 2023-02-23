package com.jlkj.energy.ee.controller;

import com.jlkj.common.dto.aop.permission.RoleAndPermission;
import com.jlkj.common.dto.aop.syslog.SysLogTypeEnum;
 
import com.jlkj.common.dto.dto.energyprovider.EnergyDayPlanOutputDTO;
import com.jlkj.common.dto.resp.Result;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.domain.EnergyDayPlanOutput;
import com.jlkj.energy.ee.service.EnergyDayPlanOutputService;
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
 * @Description 日计划产量记录
 * @create 2022-09-02 13:33:26.757127
 */
@Tag(name = "日计划产量记录")
@RestController
@RequestMapping("/energy/day/Plan/OutPut")
@Slf4j
public class EnergyDayPlanOutputController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    EnergyDayPlanOutputService energyDayPlanOutputService;

    /**
     * 接收报文 (EA05)新增
     */
    @Log(title = "新增月计划产量资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增月计划产量资料")
    @PostMapping("/ea05/xctl")
    @RoleAndPermission(permission = "day_Plan_OutPut_ea05_xctl")
    public Object addEnergyDayPlanOutput(@RequestBody EnergyDayPlanOutputDTO energyDayPlanOutputDTO) {
        try {
                EnergyDayPlanOutput energyDayPlanOutput = new EnergyDayPlanOutput();
                BeanUtils.copyProperties(energyDayPlanOutputDTO, energyDayPlanOutput);
                boolean result = energyDayPlanOutputService.save(energyDayPlanOutput);
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
     * 修改日计划产量资料
     */
    @Log(title = "修改日计划产量资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改日计划产量资料")
    @PostMapping("/update")
    @RoleAndPermission(permission = "day_Plan_OutPut_update")
    public Object updateEnergyDayPlanOutput(@RequestBody EnergyDayPlanOutputDTO energyDayPlanOutputDTO) {
        try {
            EnergyDayPlanOutput energyDayPlanOutput = new EnergyDayPlanOutput();
            BeanUtils.copyProperties(energyDayPlanOutputDTO, energyDayPlanOutput);
            boolean result = energyDayPlanOutputService.updateById(energyDayPlanOutput);
            if (result) {
                return Result.success("修改成功");
            } else {
                return Result.failedTwo("修改失败，请重新提交");
            }
        } catch (Exception e) {
            return Result.failed();
        }
    }

    /**
     * 删除日计划产量资料
     */
    @Log(title = "删除日计划产量资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除日计划产量资料")
    @DeleteMapping("/delete")
    @RoleAndPermission(permission = "day_Plan_OutPut_delete")
    public Object deleteEnergyDayPlanOutput(@RequestParam List<String> id) {
        try {
            boolean result = energyDayPlanOutputService.removeBatchByIds(id);
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

