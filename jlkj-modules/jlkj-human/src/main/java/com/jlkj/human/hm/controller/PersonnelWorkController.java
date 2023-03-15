package com.jlkj.human.hm.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.PersonnelWork;
import com.jlkj.human.hm.dto.PersonnelWorkDTO;
import com.jlkj.human.hm.service.IPersonnelWorkService;
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
}
