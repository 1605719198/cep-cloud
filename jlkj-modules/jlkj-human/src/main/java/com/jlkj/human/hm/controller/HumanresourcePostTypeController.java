package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourcePostTypeDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourcePostType;
import com.jlkj.human.hm.service.IHumanresourcePostTypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 岗位类别数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/humanresource/post/type")
public class HumanresourcePostTypeController {

    @Autowired
    private IHumanresourcePostTypeService humanresourcePostTypeService;
    /**
     * 新增岗位类别数据
     */
    @Log(title = "新增岗位类别数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增岗位类别数据")
    @PostMapping("/addPostTypeData")
    public AjaxResult addPostTypeData(@RequestBody HumanresourcePostTypeDTO humanresourcePostTypeDTO, @RequestHeader("token") String token){
        HumanresourcePostType humanresourcePostType = new HumanresourcePostType();
        BeanUtils.copyProperties(humanresourcePostTypeDTO,humanresourcePostType);
        boolean save = humanresourcePostTypeService.save(humanresourcePostType);
        return AjaxResult.success(save);
    }
    /**
     * 修改岗位类别数据
     */
    @Log(title = "修改岗位类别数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改岗位类别数据")
    @PutMapping("/updatePostTypeData")
    public AjaxResult updatePostTypeData(@RequestBody HumanresourcePostTypeDTO humanresourcePostTypeDTO,@RequestHeader("token") String token){
        HumanresourcePostType humanresourcePostType = new HumanresourcePostType();
        BeanUtils.copyProperties(humanresourcePostTypeDTO,humanresourcePostType);
        HumanresourcePostType postEntity = humanresourcePostTypeService.getById(humanresourcePostType.getId());
        if (StringUtils.isNotNull(postEntity)) {
            boolean update = humanresourcePostTypeService.updateById(humanresourcePostType);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourcePostTypeService.save(humanresourcePostType);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除岗位类别数据
     */
    @Log(title = "删除岗位类别数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除岗位类别数据")
    @DeleteMapping("/deletePostTypeData")
    public AjaxResult deletePostTypeData(@RequestBody HumanresourcePostTypeDTO humanresourcePostTypeDTO,@RequestHeader("token") String token){
        HumanresourcePostType humanresourcePostType = new HumanresourcePostType();
        BeanUtils.copyProperties(humanresourcePostTypeDTO,humanresourcePostType);
        boolean delete = humanresourcePostTypeService.removeById(humanresourcePostType);
        return AjaxResult.success(delete);
    }
}
