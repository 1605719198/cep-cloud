package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourceOrganizationTierDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourceOrganizationTier;
import com.jlkj.human.hm.service.IHumanresourceOrganizationTierService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/humanresource/organization/tier")
public class HumanresourceOrganizationTierController {

    @Autowired
    private IHumanresourceOrganizationTierService humanresourceOrganizationTierService;
    /**
     * 新增机构层级数据
     */
    @Log(title = "新增机构层级数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增机构层级数据")
    @PostMapping("/addOrganizationTierData")
    public AjaxResult addOrganizationTierData(@RequestBody HumanresourceOrganizationTierDTO humanresourceOrganizationTierDTO, @RequestHeader("token") String token){
        HumanresourceOrganizationTier humanresourceOrganizationTier = new HumanresourceOrganizationTier();
        BeanUtils.copyProperties(humanresourceOrganizationTierDTO,humanresourceOrganizationTier);
        boolean save = humanresourceOrganizationTierService.save(humanresourceOrganizationTier);
        return AjaxResult.success(save);
    }
    /**
     * 修改机构层级数据
     */
    @Log(title = "修改机构层级数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改机构层级数据")
    @PutMapping("/updateOrganizationTierData")
    public AjaxResult updateOrganizationTierData(@RequestBody HumanresourceOrganizationTierDTO humanresourceOrganizationTierDTO,@RequestHeader("token") String token){
        HumanresourceOrganizationTier humanresourceOrganizationTier = new HumanresourceOrganizationTier();
        BeanUtils.copyProperties(humanresourceOrganizationTierDTO,humanresourceOrganizationTier);
        HumanresourceOrganizationTier tierEntity = humanresourceOrganizationTierService.getById(humanresourceOrganizationTier.getId());
        if (StringUtils.isNotNull(tierEntity)) {
            boolean update = humanresourceOrganizationTierService.updateById(humanresourceOrganizationTier);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourceOrganizationTierService.save(humanresourceOrganizationTier);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除机构层级数据
     */
    @Log(title = "删除机构层级数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除机构层级数据")
    @DeleteMapping("/deleteOrganizationTierData")
    public AjaxResult deleteOrganizationTierData(@RequestBody HumanresourceOrganizationTierDTO humanresourceOrganizationTierDTO,@RequestHeader("token") String token){
        HumanresourceOrganizationTier humanresourceOrganizationTier = new HumanresourceOrganizationTier();
        BeanUtils.copyProperties(humanresourceOrganizationTierDTO,humanresourceOrganizationTier);
        boolean delete = humanresourceOrganizationTierService.removeById(humanresourceOrganizationTier);
        return AjaxResult.success(delete);
    }

}
