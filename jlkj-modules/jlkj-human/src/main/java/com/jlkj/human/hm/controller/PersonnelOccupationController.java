package com.jlkj.human.hm.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.PersonnelOccupation;
import com.jlkj.human.hm.dto.PersonnelOccupationDTO;
import com.jlkj.human.hm.service.IPersonnelOccupationService;
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
}
