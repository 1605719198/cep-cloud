package com.jlkj.human.hm.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.OrganizationDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.dto.eOrganizationTreeDTO;
import com.jlkj.human.hm.service.IOrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 机构数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-05
 */
@RestController
@RequestMapping("/humanresource/organization/base")
public class OrganizationController {
    @Autowired
    private IOrganizationService humanresourceOrganizationService;

    /**
     * 新增机构资料
     */
    @Log(title = "新增机构资料",businessType = BusinessType.INSERT)
    @Operation(summary = "新增机构资料")
    @PostMapping("/addOrganizationData")
    public void addOrganizationData(@RequestBody OrganizationDTO organizationDTO, @RequestHeader("token") String token){
        humanresourceOrganizationService.saveData(organizationDTO);
    }
    /**
     * 修改岗位资料
     */
    @Log(title = "修改机构资料",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改机构资料")
    @PutMapping("/updateOrganizationData")
    public void updateOrganizationData(@RequestBody OrganizationDTO organizationDTO, @RequestHeader("token") String token){
        humanresourceOrganizationService.updateData(organizationDTO);
    }
    /**
     * 删除机构资料
     */
    @Log(title = "删除机构资料",businessType = BusinessType.DELETE)
    @Operation(summary = "删除机构资料")
    @DeleteMapping("/deleteOrganizationData")
    public void deleteOrganizationData(@RequestBody OrganizationDTO organizationDTO, @RequestHeader("token") String token){
        humanresourceOrganizationService.removeData(organizationDTO);
    }

    /**
     * 获取机构树结构资料
     * @return
     */
    @Log(title = "获取机构树结构资料",businessType = BusinessType.OTHER)
    @Operation(summary = "获取机构树结构资料")
    @GetMapping("/getOrganizationTreeList")
    public AjaxResult getOrganizationTreeList(){
        List<eOrganizationTreeDTO> list = humanresourceOrganizationService.getOrganizationTreeList();
        return AjaxResult.success(list);
    }

}
