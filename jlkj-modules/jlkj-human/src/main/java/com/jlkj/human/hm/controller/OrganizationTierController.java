package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.OrganizationTierDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.OrganizationTier;
import com.jlkj.human.hm.service.IOrganizationTierService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 机构层级数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/humanresource/organization/tier")
public class OrganizationTierController {

    private final IOrganizationTierService humanresourceOrganizationTierService;
    /**
     * 新增机构层级数据
     */
    @Log(title = "新增机构层级数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增机构层级数据")
    @PostMapping("/addOrganizationTierData")
    public AjaxResult addOrganizationTierData(@RequestBody OrganizationTierDTO organizationTierDTO, @RequestHeader("token") String token){
        OrganizationTier organizationTier = new OrganizationTier();
        BeanUtils.copyProperties(organizationTierDTO, organizationTier);
        boolean save = humanresourceOrganizationTierService.save(organizationTier);
        return AjaxResult.success(save);
    }
    /**
     * 修改机构层级数据
     */
    @Log(title = "修改机构层级数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改机构层级数据")
    @PutMapping("/updateOrganizationTierData")
    public AjaxResult updateOrganizationTierData(@RequestBody OrganizationTierDTO organizationTierDTO, @RequestHeader("token") String token){
        OrganizationTier organizationTier = new OrganizationTier();
        BeanUtils.copyProperties(organizationTierDTO, organizationTier);
        OrganizationTier tierEntity = humanresourceOrganizationTierService.getById(organizationTier.getId());
        if (StringUtils.isNotNull(tierEntity)) {
            boolean update = humanresourceOrganizationTierService.updateById(organizationTier);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourceOrganizationTierService.save(organizationTier);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除机构层级数据
     */
    @Log(title = "删除机构层级数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除机构层级数据")
    @DeleteMapping("/deleteOrganizationTierData")
    public AjaxResult deleteOrganizationTierData(@RequestBody OrganizationTierDTO organizationTierDTO, @RequestHeader("token") String token){
        OrganizationTier organizationTier = new OrganizationTier();
        BeanUtils.copyProperties(organizationTierDTO, organizationTier);
        boolean delete = humanresourceOrganizationTierService.removeById(organizationTier);
        return AjaxResult.success(delete);
    }

}
