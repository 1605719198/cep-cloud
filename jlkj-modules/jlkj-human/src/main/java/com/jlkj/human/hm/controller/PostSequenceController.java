package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.PostSequenceDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.PostSequence;
import com.jlkj.human.hm.service.IPostSequenceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@RequiredArgsConstructor
@RequestMapping("/humanresource/post/sequence")
public class PostSequenceController {
    private final IPostSequenceService humanresourcePostSequenceService;

    /**
     * 新增岗位序列数据
     */
    @Log(title = "新增岗位序列数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增岗位序列数据")
    @PostMapping("/addPostSequenceData")
    public AjaxResult addPostSequenceData(@RequestBody PostSequenceDTO postSequenceDTO, @RequestHeader("token") String token){
        PostSequence postSequence = new PostSequence();
        BeanUtils.copyProperties(postSequenceDTO, postSequence);
        boolean save = humanresourcePostSequenceService.save(postSequence);
        return AjaxResult.success(save);
    }
    /**
     * 修改岗位序列数据
     */
    @Log(title = "修改岗位序列数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改岗位序列数据")
    @PutMapping("/updatePostSequenceData")
    public AjaxResult updatePostSequenceData(@RequestBody PostSequenceDTO postSequenceDTO, @RequestHeader("token") String token){
        PostSequence postSequence = new PostSequence();
        BeanUtils.copyProperties(postSequenceDTO, postSequence);
        PostSequence postEntity = humanresourcePostSequenceService.getById(postSequence.getId());
        if (StringUtils.isNotNull(postEntity)) {
            boolean update = humanresourcePostSequenceService.updateById(postSequence);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourcePostSequenceService.save(postSequence);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除岗位序列数据
     */
    @Log(title = "删除岗位序列数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除岗位序列数据")
    @DeleteMapping("/deletePostSequenceData")
    public AjaxResult deletePostSequenceData(@RequestBody PostSequenceDTO postSequenceDTO, @RequestHeader("token") String token){
        PostSequence postSequence = new PostSequence();
        BeanUtils.copyProperties(postSequenceDTO, postSequence);
        boolean delete = humanresourcePostSequenceService.removeById(postSequence);
        return AjaxResult.success(delete);
    }

}
