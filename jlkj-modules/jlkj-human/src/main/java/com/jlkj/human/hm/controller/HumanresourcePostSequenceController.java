package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.HumanresourcePostSequenceDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.HumanresourcePostSequence;
import com.jlkj.human.hm.service.IHumanresourcePostSequenceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 岗位序列数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/humanresource/post/sequence")
public class HumanresourcePostSequenceController {
    @Autowired
    private IHumanresourcePostSequenceService humanresourcePostSequenceService;

    /**
     * 新增岗位序列数据
     */
    @Log(title = "新增岗位序列数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增岗位序列数据")
    @PostMapping("/addPostSequenceData")
    public AjaxResult addPostSequenceData(@RequestBody HumanresourcePostSequenceDTO humanresourcePostSequenceDTO, @RequestHeader("token") String token){
        HumanresourcePostSequence humanresourcePostSequence = new HumanresourcePostSequence();
        BeanUtils.copyProperties(humanresourcePostSequenceDTO,humanresourcePostSequence);
        boolean save = humanresourcePostSequenceService.save(humanresourcePostSequence);
        return AjaxResult.success(save);
    }
    /**
     * 修改岗位序列数据
     */
    @Log(title = "修改岗位序列数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改岗位序列数据")
    @PutMapping("/updatePostSequenceData")
    public AjaxResult updatePostSequenceData(@RequestBody HumanresourcePostSequenceDTO humanresourcePostSequenceDTO,@RequestHeader("token") String token){
        HumanresourcePostSequence humanresourcePostSequence = new HumanresourcePostSequence();
        BeanUtils.copyProperties(humanresourcePostSequenceDTO,humanresourcePostSequence);
        HumanresourcePostSequence postEntity = humanresourcePostSequenceService.getById(humanresourcePostSequence.getId());
        if (StringUtils.isNotNull(postEntity)) {
            boolean update = humanresourcePostSequenceService.updateById(humanresourcePostSequence);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourcePostSequenceService.save(humanresourcePostSequence);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除岗位序列数据
     */
    @Log(title = "删除岗位序列数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除岗位序列数据")
    @DeleteMapping("/deletePostSequenceData")
    public AjaxResult deletePostSequenceData(@RequestBody HumanresourcePostSequenceDTO humanresourcePostSequenceDTO,@RequestHeader("token") String token){
        HumanresourcePostSequence humanresourcePostSequence = new HumanresourcePostSequence();
        BeanUtils.copyProperties(humanresourcePostSequenceDTO,humanresourcePostSequence);
        boolean delete = humanresourcePostSequenceService.removeById(humanresourcePostSequence);
        return AjaxResult.success(delete);
    }

}
