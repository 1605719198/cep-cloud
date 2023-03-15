package com.jlkj.human.hm.controller;


import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.PersonnelTrain;
import com.jlkj.human.hm.dto.PersonnelTrainDTO;
import com.jlkj.human.hm.service.IPersonnelTrainService;
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
}
