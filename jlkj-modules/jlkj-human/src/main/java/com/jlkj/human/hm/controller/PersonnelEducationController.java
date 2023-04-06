package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.PersonnelEducation;
import com.jlkj.human.hm.dto.PersonnelEducationDTO;
import com.jlkj.human.hm.service.IPersonnelEducationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-14 17:47
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/personnel/education")
public class PersonnelEducationController {

    private final IPersonnelEducationService personnelEducationService;

    /**
     * 新增教育经历信息
     */
    @RequiresPermissions("human:educationalExperienceInfo:save")
    @Log(title = "新增教育经历信息",businessType = BusinessType.INSERT)
    @Operation(summary = "新增教育经历信息")
    @PostMapping("/addEducationExperienceData")
    public Object addEducationExperienceData(@RequestBody PersonnelEducationDTO personnelEducationDTO) {
        List<PersonnelEducation> educationalExperienceList = personnelEducationDTO.getEducationalExperienceList();
        for (PersonnelEducation item : educationalExperienceList) {
            item.setCreator(SecurityUtils.getNickName());
        }
        boolean result = personnelEducationService.saveOrUpdateBatch(educationalExperienceList);
        if (result) {
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 获取教育经历信息列表
     */
    @Log(title = "获取教育经历信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取教育经历信息列表")
    @GetMapping("/queryEducationalExperienceInfo")
    public Object queryEducationalExperienceInfo(PersonnelEducationDTO personnelEducationDTO) {
        try {
            String empId = personnelEducationDTO.getEmpId();
            LambdaQueryWrapper<PersonnelEducation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PersonnelEducation::getEmpId, empId);
            List<PersonnelEducation> list = personnelEducationService.list(queryWrapper);
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }


    /**
     * 获取教育经历信息列表
     */
    @Log(title = "获取教育经历信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取教育经历信息列表")
    @GetMapping("/queryEducationalInfo")
    public Object queryEducationalInfo(@RequestParam String empId) {
        try {
            LambdaQueryWrapper<PersonnelEducation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PersonnelEducation::getEmpId, empId);
            List<PersonnelEducation> list = personnelEducationService.list(queryWrapper);
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除教育经历信息
     */
    @RequiresPermissions("human:educationalExperienceInfo:remove")
    @Log(title = "删除教育经历信息", businessType = BusinessType.DELETE)
    @Operation(summary = "删除教育经历信息")
    @DeleteMapping("/delete")
    public AjaxResult delEducationalExperienceInfo(@RequestParam List<String> uuid)
    {
        try {
            boolean result = personnelEducationService.removeBatchByIds(uuid);
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
