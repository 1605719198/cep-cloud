package com.jlkj.human.hm.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.PersonnelFamily;
import com.jlkj.human.hm.dto.PersonnelFamilyDTO;
import com.jlkj.human.hm.service.IPersonnelFamilyService;
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
}
