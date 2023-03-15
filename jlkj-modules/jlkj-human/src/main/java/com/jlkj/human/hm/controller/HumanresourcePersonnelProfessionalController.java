package com.jlkj.human.hm.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.HumanresourcePersonnelProfessional;
import com.jlkj.human.hm.dto.HumanresourcePersonnelProfessionalDTO;
import com.jlkj.human.hm.service.HumanresourcePersonnelProfessionalService;
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
@RequestMapping("/personnel/professional")
public class HumanresourcePersonnelProfessionalController {

    @Autowired
    private HumanresourcePersonnelProfessionalService humanresourcePersonnelProfessionalService;

    /**
     * 新增职称信息
     */
    @Log(title = "新增职称信息",businessType = BusinessType.INSERT)
    @Operation(summary = "新增职称信息")
    @PostMapping("/addProfessionalData")
    public Object addProfessionalData(@RequestBody HumanresourcePersonnelProfessionalDTO humanresourcePersonnelProfessionalDTO) {
        List<HumanresourcePersonnelProfessional> professionalList = humanresourcePersonnelProfessionalDTO.getProfessionalList();
        for (HumanresourcePersonnelProfessional item : professionalList) {
            item.setCreator(SecurityUtils.getNickName());
        }
        boolean result = humanresourcePersonnelProfessionalService.saveOrUpdateBatch(professionalList);
        if (result) {
            return AjaxResult.success("保存成功");
        } else {
            return AjaxResult.error();
        }
    }
}
