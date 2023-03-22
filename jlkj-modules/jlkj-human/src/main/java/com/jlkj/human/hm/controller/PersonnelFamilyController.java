package com.jlkj.human.hm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.PersonnelFamily;
import com.jlkj.human.hm.dto.PersonnelFamilyDTO;
import com.jlkj.human.hm.service.IPersonnelFamilyService;
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
@RequestMapping("/personnel/family")
public class PersonnelFamilyController {

    @Autowired
    private IPersonnelFamilyService iPersonnelFamilyService;

    /**
     * 新增家属信息
     */
    @Log(title = "新增家属信息",businessType = BusinessType.INSERT)
    @Operation(summary = "新增家属信息")
    @PostMapping("/addFamilyData")
    public Object addFamilyData(@RequestBody PersonnelFamilyDTO personnelFamilyDTO) {
        List<PersonnelFamily> familyList = personnelFamilyDTO.getFamilyList();
        for (PersonnelFamily item : familyList) {
            item.setCreator(SecurityUtils.getNickName());
        }
        boolean result = iPersonnelFamilyService.saveOrUpdateBatch(familyList);
        if (result) {
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 获取家属信息列表
     */
    @Log(title = "获取家属信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取家属信息列表")
    @GetMapping("/queryFamilyInfo")
    public Object queryFamilyInfo(PersonnelFamilyDTO personnelFamilyDTO) {
        try {
            String empId = personnelFamilyDTO.getEmpId();
            LambdaQueryWrapper<PersonnelFamily> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PersonnelFamily::getEmpId, empId);
            List<PersonnelFamily> list = iPersonnelFamilyService.list(queryWrapper);
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 获取家属信息列表
     */
    @Log(title = "获取家属信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取家属信息列表")
    @GetMapping("/queryFamily")
    public Object queryFamily(@RequestParam String empId) {
        try {
            LambdaQueryWrapper<PersonnelFamily> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PersonnelFamily::getEmpId, empId);
            List<PersonnelFamily> list = iPersonnelFamilyService.list(queryWrapper);
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除家属信息
     */
    @Log(title = "删除家属信息", businessType = BusinessType.DELETE)
    @Operation(summary = "删除家属信息")
    @DeleteMapping("/delete")
    public AjaxResult delFamilyInfo(@RequestParam List<String> uuid)
    {
        try {
            boolean result = iPersonnelFamilyService.removeBatchByIds(uuid);
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
