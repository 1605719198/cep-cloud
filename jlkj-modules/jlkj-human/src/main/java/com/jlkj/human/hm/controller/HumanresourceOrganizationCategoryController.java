package com.jlkj.human.hm.controller;


import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourceOrganizationCategoryDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourceOrganizationCategory;
import com.jlkj.human.hm.service.IHumanresourceOrganizationCategoryService;
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
public class HumanresourceOrganizationCategoryController {
    @Autowired
    private IHumanresourceOrganizationCategoryService humanresourceOrganizationCategoryService;
    /**
     * 新增机构层级数据
     */
    @Log(title = "新增机构层级数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增机构层级数据")
    @PostMapping("/addOrganizationCategoryData")
    public AjaxResult addOrganizationCategoryData(@RequestBody HumanresourceOrganizationCategoryDTO humanresourceOrganizationCategoryDTO, @RequestHeader("token") String token){
        HumanresourceOrganizationCategory humanresourceOrganizationCategory = new HumanresourceOrganizationCategory();
        BeanUtils.copyProperties(humanresourceOrganizationCategoryDTO,humanresourceOrganizationCategory);
        boolean save = humanresourceOrganizationCategoryService.save(humanresourceOrganizationCategory);
        return AjaxResult.success(save);
    }
    /**
     * 修改机构层级数据
     */
    @Log(title = "修改机构层级数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改机构层级数据")
    @PutMapping("/updateOrganizationCategoryData")
    public AjaxResult updateOrganizationCategoryData(@RequestBody HumanresourceOrganizationCategoryDTO humanresourceOrganizationCategoryDTO,@RequestHeader("token") String token){
        HumanresourceOrganizationCategory humanresourceOrganizationCategory = new HumanresourceOrganizationCategory();
        BeanUtils.copyProperties(humanresourceOrganizationCategoryDTO,humanresourceOrganizationCategory);
        HumanresourceOrganizationCategory categoryEntity = humanresourceOrganizationCategoryService.getById(humanresourceOrganizationCategory.getId());
        if (StringUtils.isNotNull(categoryEntity)) {
            boolean update = humanresourceOrganizationCategoryService.updateById(humanresourceOrganizationCategory);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourceOrganizationCategoryService.save(humanresourceOrganizationCategory);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除机构层级数据
     */
    @Log(title = "修改机构层级数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除机构层级数据")
    @DeleteMapping("/deleteOrganizationCategoryData")
    public AjaxResult deleteOrganizationCategoryData(@RequestBody HumanresourceOrganizationCategoryDTO humanresourceOrganizationCategoryDTO, @RequestHeader("token") String token){
        HumanresourceOrganizationCategory humanresourceOrganizationCategory = new HumanresourceOrganizationCategory();
        BeanUtils.copyProperties(humanresourceOrganizationCategoryDTO,humanresourceOrganizationCategory);
        boolean delete = humanresourceOrganizationCategoryService.removeById(humanresourceOrganizationCategory);
        return AjaxResult.success(delete);
    }

}
