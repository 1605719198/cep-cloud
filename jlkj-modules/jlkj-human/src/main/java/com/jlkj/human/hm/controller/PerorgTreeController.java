package com.jlkj.human.hm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.PerorgTree;
import com.jlkj.human.hm.service.IPerorgTreeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 人员机构树型结构存储表 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/humanresource/perorg/tree")
public class PerorgTreeController {
    private final IPerorgTreeService humanresourcePerorgTreeService;
    /**
     * 获取机构及人员树结构资料
     */
    @Operation(summary = "获取机构及人员树结构资料")
    @GetMapping("/getOrganizationTreeList")
    @Log(title = "获取机构及人员树结构资料",businessType = BusinessType.OTHER)
    public AjaxResult getOrganizationTreeList(){
        List<PerorgTree> list = humanresourcePerorgTreeService.getOrganizationTreeList();
        return AjaxResult.success(list);
    }
    /**
     * 获取机构及人员树结构资料(去除无人员部门树结构)
     */
    @Operation(summary = "获取机构及人员树结构资料")
    @GetMapping("/getOrganizationDelTreeList")
    @Log(title = "获取机构及人员树结构资料",businessType = BusinessType.OTHER)
    public AjaxResult getOrganizationDelTreeList(){
        List<PerorgTree> list = humanresourcePerorgTreeService.getOrganizationDelTreeList();
        return AjaxResult.success(list);
    }
    /**
     * 获取部门机构树结构资料
     */
    @Operation(summary = "获取机构树结构资料")
    @GetMapping("/getOrganizationDeptTreeList")
    @Log(title = "获取机构树结构资料",businessType = BusinessType.OTHER)
    public AjaxResult getOrganizationDeptTreeList(){
        List<PerorgTree> list = humanresourcePerorgTreeService.getOrganizationDeptTreeList();
        return AjaxResult.success(list);
    }

    /**
     * 根据部门ID获取人员资料
     */
    @Operation(summary = "根据部门ID获取人员资料")
    @GetMapping("/getOrganizationUserList")
    @Log(title = "根据部门ID获取人员资料",businessType = BusinessType.OTHER)
    public AjaxResult getOrganizationUserList(@RequestParam(defaultValue = "") String id){
        QueryWrapper<PerorgTree> wrapper = new QueryWrapper<>();
        wrapper.eq("flag","user")
                .eq("parent_id",id);
        List<PerorgTree> userList = humanresourcePerorgTreeService.list(wrapper);
        return AjaxResult.success(userList);
    }
    /**
     * 获取所有人员信息列表
     */
    @Operation(summary = "获取所有人员信息列表")
    @GetMapping("/getPersonnerlInfoList")
    @Log(title = "根据部门ID获取人员资料",businessType = BusinessType.OTHER)
    public AjaxResult getPersonnerlInfoList() {
        List<PerorgTree> list = humanresourcePerorgTreeService.list(new QueryWrapper<PerorgTree>().eq("flag", "user"));
        List<Map<String, String>> collectList = list.stream().map(item -> {
            Map<String, String> map = new HashMap<>(2);
            map.put("value", item.getJobNumber());
            map.put("label", item.getJobNumber()+"_"+item.getLabel());
            return map;
        }).collect(Collectors.toList());
        return AjaxResult.success(collectList);
    }

}
