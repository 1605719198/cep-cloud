package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.HumanresourcePersonnelOccupation;
import com.jlkj.human.hm.dto.HumanresourcePersonnelOccupationDTO;
import com.jlkj.human.hm.service.HumanresourcePersonnelOccupationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-14 17:47
 */
@RestController
@RequestMapping("/personnel/occupation")
public class HumanresourcePersonnelOccupationController {

    @Autowired
    private HumanresourcePersonnelOccupationService humanresourcePersonnelOccupationService;

    /**
     * 新增职业资格信息
     */
    @Log(title = "新增职业资格信息",businessType = BusinessType.INSERT)
    @Operation(summary = "新增职业资格信息")
    @PostMapping("/addOccupationData")
    public Object addOccupationData(@RequestBody HumanresourcePersonnelOccupationDTO humanresourcePersonnelOccupationDTO) {
        List<HumanresourcePersonnelOccupation> occupationList = humanresourcePersonnelOccupationDTO.getOccupationList();
        for (HumanresourcePersonnelOccupation item : occupationList) {
            item.setCreator(SecurityUtils.getNickName());
        }
        boolean result = humanresourcePersonnelOccupationService.saveOrUpdateBatch(occupationList);
        if (result) {
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 获取职业资格信息列表
     */
    @Log(title = "获取职业资格信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取职业资格信息列表")
    @GetMapping("/queryOccupationInfo")
    public Object queryOccupationInfo(HumanresourcePersonnelOccupationDTO humanresourcePersonnelOccupationDTO) {
        try {
            String empId = humanresourcePersonnelOccupationDTO.getEmpId();
            LambdaQueryWrapper<HumanresourcePersonnelOccupation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(HumanresourcePersonnelOccupation::getEmpId, empId);
            List<HumanresourcePersonnelOccupation> list = humanresourcePersonnelOccupationService.list(queryWrapper);
            if (list.isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", list);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 获取职业资格信息列表
     */
    @Log(title = "获取职业资格信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取职业资格信息列表")
    @GetMapping("/queryOccupation")
    public Object queryOccupation(@RequestParam String empId) {
        try {
            LambdaQueryWrapper<HumanresourcePersonnelOccupation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(HumanresourcePersonnelOccupation::getEmpId, empId);
            List<HumanresourcePersonnelOccupation> list = humanresourcePersonnelOccupationService.list(queryWrapper);
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除职业资格信息
     */
    @Log(title = "删除职业资格信息", businessType = BusinessType.DELETE)
    @Operation(summary = "删除职业资格信息")
    @DeleteMapping("/delete")
    public AjaxResult delOccupationInfo(@RequestParam List<String> uuid)
    {
        try {
            boolean result = humanresourcePersonnelOccupationService.removeBatchByIds(uuid);
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
