package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourceJobTitleDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourceJobTitle;
import com.jlkj.human.hm.service.IHumanresourceJobTitleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 职位名称数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-20
 */
@RestController
@RequestMapping("/humanresource/job/title")
public class HumanresourceJobTitleController {
    @Autowired
    private IHumanresourceJobTitleService humanresourceJobTitleService;

    /**
     * 新增职位名称数据
     */
    @Operation(summary = "新增职位名称数据")
    @PostMapping("/addJobTitleData")
    @Log(title = "新增职位名称数据",businessType = BusinessType.INSERT)
    public AjaxResult addJobTitleData(@RequestBody HumanresourceJobTitleDTO humanresourceJobTitleDTO, @RequestHeader("token") String token){
        HumanresourceJobTitle humanresourceJobTitle = new HumanresourceJobTitle();
        BeanUtils.copyProperties(humanresourceJobTitleDTO,humanresourceJobTitle);
        boolean save = humanresourceJobTitleService.save(humanresourceJobTitle);
        return AjaxResult.success(save);
    }
    /**
     * 修改职位名称数据
     */
    @Log(title = "修改职位名称数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改职位名称数据")
    @PutMapping("/updateJobTitleData")
    public AjaxResult updateJobTitleData(@RequestBody HumanresourceJobTitleDTO humanresourceJobTitleDTO,@RequestHeader("token") String token){
        HumanresourceJobTitle humanresourceJobTitle = new HumanresourceJobTitle();
        BeanUtils.copyProperties(humanresourceJobTitleDTO,humanresourceJobTitle);
        HumanresourceJobTitle postEntity = humanresourceJobTitleService.getById(humanresourceJobTitle.getId());
        if (StringUtils.isNotNull(postEntity)) {
            boolean update = humanresourceJobTitleService.updateById(humanresourceJobTitle);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourceJobTitleService.save(humanresourceJobTitle);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除职位名称数据
     */
    @Log(title = "修改职位名称数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除职位名称数据")
    @DeleteMapping("/deleteJobTitleData")
    public AjaxResult deleteJobTitleData(@RequestBody HumanresourceJobTitleDTO humanresourceJobTitleDTO,@RequestHeader("token") String token){
        HumanresourceJobTitle humanresourceJobTitle = new HumanresourceJobTitle();
        BeanUtils.copyProperties(humanresourceJobTitleDTO,humanresourceJobTitle);
        boolean delete = humanresourceJobTitleService.removeById(humanresourceJobTitle);
        return AjaxResult.success(delete);
    }

}
