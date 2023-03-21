package com.jlkj.human.hm.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.PersonnelTrain;
import com.jlkj.human.hm.dto.PersonnelTrainDTO;
import com.jlkj.human.hm.service.IPersonnelTrainService;
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
@RequestMapping("/personnel/cultivate")
public class PersonnelTrainController {

    @Autowired
    private IPersonnelTrainService iPersonnelTrainService;

    /**
     * 新增培训经历信息
     */
    @Log(title = "新增培训经历信息",businessType = BusinessType.INSERT)
    @Operation(summary = "新增培训经历信息")
    @PostMapping("/addCultivateExperienceData")
    public Object addCultivateExperienceData(@RequestBody PersonnelTrainDTO personnelTrainDTO) {
        List<PersonnelTrain> cultivateExperienceList = personnelTrainDTO.getCultivateExperienceList();
        for (PersonnelTrain item : cultivateExperienceList) {
            item.setCreator(SecurityUtils.getNickName());
        }
        boolean result = iPersonnelTrainService.saveOrUpdateBatch(cultivateExperienceList);
        if (result) {
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 获取培训经历信息列表
     */
    @Log(title = "获取培训经历信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取培训经历信息列表")
    @GetMapping("/queryCultivateExperienceInfo")
    public Object queryCultivateExperienceInfo(HumanresourcePersonnelTrainDTO humanresourcePersonnelTrainDTO) {
        try {
            String empId = humanresourcePersonnelTrainDTO.getEmpId();
            LambdaQueryWrapper<HumanresourcePersonnelTrain> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(HumanresourcePersonnelTrain::getEmpId, empId);
            List<HumanresourcePersonnelTrain> list = humanresourcePersonnelTrainService.list(queryWrapper);
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
     * 获取培训经历信息列表
     */
    @Log(title = "获取培训经历信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "获取培训经历信息列表")
    @GetMapping("/queryCultivateInfo")
    public Object queryCultivateInfo(@RequestParam String empId) {
        try {
            LambdaQueryWrapper<HumanresourcePersonnelTrain> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(HumanresourcePersonnelTrain::getEmpId, empId);
            List<HumanresourcePersonnelTrain> list = humanresourcePersonnelTrainService.list(queryWrapper);
            return AjaxResult.success("查询成功！", list);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除培训经历信息
     */
    @Log(title = "删除培训经历信息", businessType = BusinessType.DELETE)
    @Operation(summary = "删除培训经历信息")
    @DeleteMapping("/delete")
    public AjaxResult delCultivateExperienceInfo(@RequestParam List<String> uuid)
    {
        try {
            boolean result = humanresourcePersonnelTrainService.removeBatchByIds(uuid);
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
