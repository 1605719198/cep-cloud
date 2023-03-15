package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.PostTypeDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.PostType;
import com.jlkj.human.hm.service.IPostTypeService;
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
public class PostTypeController {

    @Autowired
    private IPostTypeService humanresourcePostTypeService;
    /**
     * 新增岗位类别数据
     */
    @Log(title = "新增岗位类别数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增岗位类别数据")
    @PostMapping("/addPostTypeData")
    public AjaxResult addPostTypeData(@RequestBody PostTypeDTO postTypeDTO, @RequestHeader("token") String token){
        PostType postType = new PostType();
        BeanUtils.copyProperties(postTypeDTO, postType);
        boolean save = humanresourcePostTypeService.save(postType);
        return AjaxResult.success(save);
    }
    /**
     * 修改岗位类别数据
     */
    @Log(title = "修改岗位类别数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改岗位类别数据")
    @PutMapping("/updatePostTypeData")
    public AjaxResult updatePostTypeData(@RequestBody PostTypeDTO postTypeDTO, @RequestHeader("token") String token){
        PostType postType = new PostType();
        BeanUtils.copyProperties(postTypeDTO, postType);
        PostType postEntity = humanresourcePostTypeService.getById(postType.getId());
        if (StringUtils.isNotNull(postEntity)) {
            boolean update = humanresourcePostTypeService.updateById(postType);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourcePostTypeService.save(postType);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除岗位类别数据
     */
    @Log(title = "删除岗位类别数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除岗位类别数据")
    @DeleteMapping("/deletePostTypeData")
    public AjaxResult deletePostTypeData(@RequestBody PostTypeDTO postTypeDTO, @RequestHeader("token") String token){
        PostType postType = new PostType();
        BeanUtils.copyProperties(postTypeDTO, postType);
        boolean delete = humanresourcePostTypeService.removeById(postType);
        return AjaxResult.success(delete);
    }
}
