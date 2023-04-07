package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.PostDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.Post;
import com.jlkj.human.hm.service.IPostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 岗位数据 前端控制器
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/humanresource/post/base")
public class PostController {
    private final IPostService humanresourcePostService;
    /**
     * 新增岗位资料
     */
    @Log(title = "新增岗位资料",businessType = BusinessType.INSERT)
    @Operation(summary = "新增岗位资料")
    @PostMapping("/addPostData")
    public AjaxResult addPostData(@RequestBody PostDTO postDTO, @RequestHeader("token") String token){
        Post post = new Post();
        BeanUtils.copyProperties(postDTO, post);
        boolean save = humanresourcePostService.save(post);
        return AjaxResult.success(save);
    }
    /**
     * 修改岗位资料
     */
    @Log(title = "修改岗位资料",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改岗位资料")
    @PutMapping("/updatePostData")
    public AjaxResult updatePostData(@RequestBody PostDTO postDTO, @RequestHeader("token") String token){
        Post humanresourcePost = new Post();
        BeanUtils.copyProperties(postDTO,humanresourcePost);
        Post post = humanresourcePostService.getById(humanresourcePost.getId());
        if (StringUtils.isNotNull(post)) {
            boolean update = humanresourcePostService.updateById(humanresourcePost);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourcePostService.save(humanresourcePost);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除岗位资料
     */
    @Log(title = "删除岗位资料",businessType = BusinessType.DELETE)
    @Operation(summary = "删除岗位资料")
    @DeleteMapping("/deletePostData")
    public AjaxResult deletePostData(@RequestBody PostDTO postDTO, @RequestHeader("token") String token){
        Post post = new Post();
        BeanUtils.copyProperties(postDTO, post);
        boolean delete = humanresourcePostService.removeById(post);
        return AjaxResult.success(delete);
    }
}
