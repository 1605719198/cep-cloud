package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourceSpecialityTypeDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourceSpecialityType;
import com.jlkj.human.hm.service.IHumanresourceSpecialityTypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 专长类别数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-20
 */
@RestController
@RequestMapping("/humanresource/speciality/type")
public class HumanresourceSpecialityTypeController {
    @Autowired
    private IHumanresourceSpecialityTypeService humanresourceSpecialityTypeService;

    /**
     * 新增专长类别数据
     */
    @Log(title = "新增专长类别数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增专长类别数据")
    @PostMapping("/addSpecialityTypeData")
    public AjaxResult addSpecialityTypeData(@RequestBody HumanresourceSpecialityTypeDTO humanresourceSpecialityTypeDTO, @RequestHeader("token") String token){
        HumanresourceSpecialityType humanresourceSpecialityType = new HumanresourceSpecialityType();
        BeanUtils.copyProperties(humanresourceSpecialityTypeDTO,humanresourceSpecialityType);
        boolean save = humanresourceSpecialityTypeService.save(humanresourceSpecialityType);
        return AjaxResult.success(save);
    }
    /**
     * 修改专长类别数据
     */
    @Log(title = "修改专长类别数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改专长类别数据")
    @PutMapping("/updateSpecialityTypeData")
    public AjaxResult updateSpecialityTypeData(@RequestBody HumanresourceSpecialityTypeDTO humanresourceSpecialityTypeDTO,@RequestHeader("token") String token){
        HumanresourceSpecialityType humanresourceSpecialityType = new HumanresourceSpecialityType();
        BeanUtils.copyProperties(humanresourceSpecialityTypeDTO,humanresourceSpecialityType);
        HumanresourceSpecialityType postEntity = humanresourceSpecialityTypeService.getById(humanresourceSpecialityType.getId());
        if (StringUtils.isNotNull(postEntity)) {
            boolean update = humanresourceSpecialityTypeService.updateById(humanresourceSpecialityType);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourceSpecialityTypeService.save(humanresourceSpecialityType);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除专长类别数据
     */
    @Log(title = "删除专长类别数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除专长类别数据")
    @DeleteMapping("/deleteSpecialityTypeData")
    public AjaxResult deleteSpecialityTypeData(@RequestBody HumanresourceSpecialityTypeDTO humanresourceSpecialityTypeDTO,@RequestHeader("token") String token){
        HumanresourceSpecialityType humanresourceSpecialityType = new HumanresourceSpecialityType();
        BeanUtils.copyProperties(humanresourceSpecialityTypeDTO,humanresourceSpecialityType);
        boolean delete = humanresourceSpecialityTypeService.removeById(humanresourceSpecialityType);
        return AjaxResult.success(delete);
    }

}
