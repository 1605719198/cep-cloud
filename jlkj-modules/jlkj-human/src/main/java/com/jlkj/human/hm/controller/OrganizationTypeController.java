package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.OrganizationTypeDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.OrganizationType;
import com.jlkj.human.hm.service.IOrganizationTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 机构类别数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/humanresource/organization/type")
public class OrganizationTypeController {
    private final IOrganizationTypeService humanresourceOrganizationTypeService;
    /**
     * 新增机构类别数据
     */
    @Log(title = "新增机构类别数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增机构类别数据")
    @PostMapping("/addOrganizationTypeData")
    public AjaxResult addOrganizationTypeData(@RequestBody OrganizationTypeDTO organizationTypeDTO, @RequestHeader("token") String token){
        OrganizationType organizationType = new OrganizationType();
        BeanUtils.copyProperties(organizationTypeDTO, organizationType);
        boolean save = humanresourceOrganizationTypeService.save(organizationType);
        return AjaxResult.success(save);
    }
    /**
     * 修改机构类别数据
     */
    @Log(title = "修改机构类别数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改机构类别数据")
    @PutMapping("/updateOrganizationTypeData")
    public AjaxResult updateOrganizationTypeData(@RequestBody OrganizationTypeDTO organizationTypeDTO, @RequestHeader("token") String token){
        OrganizationType organizationType = new OrganizationType();
        BeanUtils.copyProperties(organizationTypeDTO, organizationType);
        OrganizationType typeEntity = humanresourceOrganizationTypeService.getById(organizationType.getId());
        if (StringUtils.isNotNull(typeEntity)) {
            boolean update = humanresourceOrganizationTypeService.updateById(organizationType);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourceOrganizationTypeService.save(organizationType);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除机构类别数据
     */
    @Operation(summary = "删除机构类别数据")
    @DeleteMapping("/deleteOrganizationTypeData")
    @Log(title = "删除机构类别数据",businessType = BusinessType.DELETE)
    public AjaxResult deleteOrganizationTypeData(@RequestBody OrganizationTypeDTO organizationTypeDTO, @RequestHeader("token") String token){
        OrganizationType organizationType = new OrganizationType();
        BeanUtils.copyProperties(organizationTypeDTO, organizationType);
        boolean delete = humanresourceOrganizationTypeService.removeById(organizationType);
        return AjaxResult.success(delete);
    }

}
