package com.jlkj.human.hm.controller;


import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.OrganizationCategoryDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.OrganizationCategory;
import com.jlkj.human.hm.service.IOrganizationCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 机构细类数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/humanresource/organization/category")
public class OrganizationCategoryController {
    @Autowired
    private IOrganizationCategoryService humanresourceOrganizationCategoryService;
    /**
     * 新增机构层级数据
     */
    @Log(title = "新增机构层级数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增机构层级数据")
    @PostMapping("/addOrganizationCategoryData")
    public AjaxResult addOrganizationCategoryData(@RequestBody OrganizationCategoryDTO organizationCategoryDTO, @RequestHeader("token") String token){
        OrganizationCategory organizationCategory = new OrganizationCategory();
        BeanUtils.copyProperties(organizationCategoryDTO, organizationCategory);
        boolean save = humanresourceOrganizationCategoryService.save(organizationCategory);
        return AjaxResult.success(save);
    }
    /**
     * 修改机构层级数据
     */
    @Log(title = "修改机构层级数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改机构层级数据")
    @PutMapping("/updateOrganizationCategoryData")
    public AjaxResult updateOrganizationCategoryData(@RequestBody OrganizationCategoryDTO organizationCategoryDTO, @RequestHeader("token") String token){
        OrganizationCategory organizationCategory = new OrganizationCategory();
        BeanUtils.copyProperties(organizationCategoryDTO, organizationCategory);
        OrganizationCategory categoryEntity = humanresourceOrganizationCategoryService.getById(organizationCategory.getId());
        if (StringUtils.isNotNull(categoryEntity)) {
            boolean update = humanresourceOrganizationCategoryService.updateById(organizationCategory);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourceOrganizationCategoryService.save(organizationCategory);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除机构层级数据
     */
    @Log(title = "修改机构层级数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除机构层级数据")
    @DeleteMapping("/deleteOrganizationCategoryData")
    public AjaxResult deleteOrganizationCategoryData(@RequestBody OrganizationCategoryDTO organizationCategoryDTO, @RequestHeader("token") String token){
        OrganizationCategory organizationCategory = new OrganizationCategory();
        BeanUtils.copyProperties(organizationCategoryDTO, organizationCategory);
        boolean delete = humanresourceOrganizationCategoryService.removeById(organizationCategory);
        return AjaxResult.success(delete);
    }

}
