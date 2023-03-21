package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.PersonnelOccupation;
import com.jlkj.human.hm.dto.PersonnelOccupationDTO;
import com.jlkj.human.hm.service.IPersonnelOccupationService;
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
public class PersonnelOccupationController {

    @Autowired
    private IPersonnelOccupationService iPersonnelOccupationService;

    /**
     * 新增职业资格信息
     */
    @Log(title = "新增职业资格信息",businessType = BusinessType.INSERT)
    @Operation(summary = "新增职业资格信息")
    @PostMapping("/addOccupationData")
    public Object addOccupationData(@RequestBody PersonnelOccupationDTO personnelOccupationDTO) {
        List<PersonnelOccupation> occupationList = personnelOccupationDTO.getOccupationList();
        for (PersonnelOccupation item : occupationList) {
            item.setCreator(SecurityUtils.getNickName());
        }
        boolean result = iPersonnelOccupationService.saveOrUpdateBatch(occupationList);
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
    public Object queryOccupationInfo(PersonnelOccupationDTO humanresourcePersonnelOccupationDTO) {
        try {
            String empId = humanresourcePersonnelOccupationDTO.getEmpId();
            LambdaQueryWrapper<PersonnelOccupation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PersonnelOccupation::getEmpId, empId);
            List<PersonnelOccupation> list = iPersonnelOccupationService.list(queryWrapper);
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
            LambdaQueryWrapper<PersonnelOccupation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PersonnelOccupation::getEmpId, empId);
            List<PersonnelOccupation> list = iPersonnelOccupationService.list(queryWrapper);
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
            boolean result = iPersonnelOccupationService.removeBatchByIds(uuid);
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
