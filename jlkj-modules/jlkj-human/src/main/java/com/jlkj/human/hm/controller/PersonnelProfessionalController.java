package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.PersonnelProfessional;
import com.jlkj.human.hm.dto.PersonnelProfessionalDTO;
import com.jlkj.human.hm.service.IPersonnelProfessionalService;
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
@RequestMapping("/personnel/professional")
public class PersonnelProfessionalController {

    private final IPersonnelProfessionalService iPersonnelProfessionalService;

    /**
     * 新增职称信息
     */
    @RequiresPermissions("human:professionalInfo:save")
    @Log(title = "新增职称信息",businessType = BusinessType.INSERT)
    @Operation(summary = "新增职称信息")
    @PostMapping("/addProfessionalData")
    public Object addProfessionalData(@RequestBody PersonnelProfessionalDTO personnelProfessionalDTO) {
        List<PersonnelProfessional> professionalList = personnelProfessionalDTO.getProfessionalList();
        for (PersonnelProfessional item : professionalList) {
            item.setCreator(SecurityUtils.getNickName());
        }
        boolean result = iPersonnelProfessionalService.saveOrUpdateBatch(professionalList);
        if (result) {
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error();
        }
    }


    /**
     * 获取职称信息列表
     */
    @Log(title = "获取职称信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取职称信息列表")
    @GetMapping("/queryProfessionalInfo")
    public Object queryProfessionalInfo(PersonnelProfessionalDTO personnelProfessionalDTO) {
        try {
            String empId = personnelProfessionalDTO.getEmpId();
            LambdaQueryWrapper<PersonnelProfessional> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PersonnelProfessional::getEmpId, empId);
            List<PersonnelProfessional> list = iPersonnelProfessionalService.list(queryWrapper);
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 获取职称信息列表
     */
    @Log(title = "获取职称信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取职称信息列表")
    @GetMapping("/queryProfessional")
    public Object queryProfessional(@RequestParam String empId) {
        try {
            LambdaQueryWrapper<PersonnelProfessional> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PersonnelProfessional::getEmpId, empId);
            List<PersonnelProfessional> list = iPersonnelProfessionalService.list(queryWrapper);
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除职称信息
     */
    @RequiresPermissions("human:professionalInfo:remove")
    @Log(title = "删除职称信息", businessType = BusinessType.DELETE)
    @Operation(summary = "删除职称信息")
    @DeleteMapping("/delete")
    public AjaxResult delProfessionalInfo(@RequestParam List<String> uuid)
    {
        try {
            boolean result = iPersonnelProfessionalService.removeBatchByIds(uuid);
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
