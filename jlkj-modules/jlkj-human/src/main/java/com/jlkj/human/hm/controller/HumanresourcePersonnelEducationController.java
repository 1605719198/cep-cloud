package com.jlkj.human.hm.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.HumanresourcePersonnelEducation;
import com.jlkj.human.hm.dto.HumanresourcePersonnelEducationDTO;
import com.jlkj.human.hm.service.HumanresourcePersonnelEducationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-14 17:47
 */
@RestController
@RequestMapping("/personnel/education")
public class HumanresourcePersonnelEducationController {

    @Autowired
    private HumanresourcePersonnelEducationService humanresourcePersonnelEducationService;

    /**
     * 新增教育经历信息
     */
    @Log(title = "新增教育经历信息",businessType = BusinessType.INSERT)
    @Operation(summary = "新增教育经历信息")
    @PostMapping("/addEducationExperienceData")
    public Object addEducationExperienceData(@RequestBody HumanresourcePersonnelEducationDTO humanresourcePersonnelEducationDTO) {
        List<HumanresourcePersonnelEducation> educationalExperienceList = humanresourcePersonnelEducationDTO.getEducationalExperienceList();
        for (HumanresourcePersonnelEducation item : educationalExperienceList) {
            item.setCreator(SecurityUtils.getNickName());
        }
        boolean result = humanresourcePersonnelEducationService.saveOrUpdateBatch(educationalExperienceList);
        if (result) {
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error();
        }
    }
}
