package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourceOrganizationTypeDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourceOrganizationType;
import com.jlkj.human.hm.service.IHumanresourceOrganizationTypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/humanresource/organization/type")
public class HumanresourceOrganizationTypeController {
    @Autowired
    private IHumanresourceOrganizationTypeService humanresourceOrganizationTypeService;
    /**
     * 新增机构类别数据
     */
    @Log(title = "新增机构类别数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增机构类别数据")
    @PostMapping("/addOrganizationTypeData")
    public AjaxResult addOrganizationTypeData(@RequestBody HumanresourceOrganizationTypeDTO humanresourceOrganizationTypeDTO, @RequestHeader("token") String token){
        HumanresourceOrganizationType humanresourceOrganizationType = new HumanresourceOrganizationType();
        BeanUtils.copyProperties(humanresourceOrganizationTypeDTO,humanresourceOrganizationType);
        boolean save = humanresourceOrganizationTypeService.save(humanresourceOrganizationType);
        return AjaxResult.success(save);
    }
    /**
     * 修改机构类别数据
     */
    @Log(title = "修改机构类别数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改机构类别数据")
    @PutMapping("/updateOrganizationTypeData")
    public AjaxResult updateOrganizationTypeData(@RequestBody HumanresourceOrganizationTypeDTO humanresourceOrganizationTypeDTO,@RequestHeader("token") String token){
        HumanresourceOrganizationType humanresourceOrganizationType = new HumanresourceOrganizationType();
        BeanUtils.copyProperties(humanresourceOrganizationTypeDTO,humanresourceOrganizationType);
        HumanresourceOrganizationType typeEntity = humanresourceOrganizationTypeService.getById(humanresourceOrganizationType.getId());
        if (StringUtils.isNotNull(typeEntity)) {
            boolean update = humanresourceOrganizationTypeService.updateById(humanresourceOrganizationType);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourceOrganizationTypeService.save(humanresourceOrganizationType);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除机构类别数据
     */
    @Operation(summary = "删除机构类别数据")
    @DeleteMapping("/deleteOrganizationTypeData")
    @Log(title = "删除机构类别数据",businessType = BusinessType.DELETE)
    public AjaxResult deleteOrganizationTypeData(@RequestBody HumanresourceOrganizationTypeDTO humanresourceOrganizationTypeDTO,@RequestHeader("token") String token){
        HumanresourceOrganizationType humanresourceOrganizationType = new HumanresourceOrganizationType();
        BeanUtils.copyProperties(humanresourceOrganizationTypeDTO,humanresourceOrganizationType);
        boolean delete = humanresourceOrganizationTypeService.removeById(humanresourceOrganizationType);
        return AjaxResult.success(delete);
    }

}
