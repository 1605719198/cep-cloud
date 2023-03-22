package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.PersonnelWork;
import com.jlkj.human.hm.dto.PersonnelWorkDTO;
import com.jlkj.human.hm.service.IPersonnelWorkService;
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
@RequestMapping("/personnel/work")
public class PersonnelWorkController {

    @Autowired
    private IPersonnelWorkService iPersonnelWorkService;

    /**
     * 新增工作经历信息
     */
    @Log(title = "新增工作经历信息",businessType = BusinessType.INSERT)
    @Operation(summary = "新增工作经历信息")
    @PostMapping("/addWorkExperienceData")
    public Object addWorkExperienceData(@RequestBody PersonnelWorkDTO personnelWorkDTO) {
        List<PersonnelWork> workExperienceList = personnelWorkDTO.getWorkExperienceList();
        for (PersonnelWork item : workExperienceList) {
            item.setCreator(SecurityUtils.getNickName());
        }
        boolean result = iPersonnelWorkService.saveOrUpdateBatch(workExperienceList);
        if (result) {
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 获取工作经历信息列表
     */
    @Log(title = "获取工作经历信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取工作经历信息列表")
    @GetMapping("/queryWorkExperienceInfo")
    public Object queryWorkExperienceInfo(PersonnelWorkDTO personnelWorkDTO) {
        try {
            String empId = personnelWorkDTO.getEmpId();
            LambdaQueryWrapper<PersonnelWork> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PersonnelWork::getEmpId, empId);
            List<PersonnelWork> list = iPersonnelWorkService.list(queryWrapper);
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 获取工作经历信息列表
     */
    @Log(title = "获取工作经历信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取工作经历信息列表")
    @GetMapping("/queryWorkInfo")
    public Object queryWorkInfo(@RequestParam String empId) {
        try {
            LambdaQueryWrapper<PersonnelWork> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PersonnelWork::getEmpId, empId);
            List<PersonnelWork> list = iPersonnelWorkService.list(queryWrapper);
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除工作经历信息
     */
    @Log(title = "删除工作经历信息", businessType = BusinessType.DELETE)
    @Operation(summary = "删除工作经历信息")
    @DeleteMapping("/delete")
    public AjaxResult delWorkExperienceInfo(@RequestParam List<String> uuid)
    {
        try {
            boolean result = iPersonnelWorkService.removeBatchByIds(uuid);
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
