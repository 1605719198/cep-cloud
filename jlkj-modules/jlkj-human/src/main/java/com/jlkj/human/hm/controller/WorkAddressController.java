package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.human.hm.WorkAddressDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hm.domain.WorkAddress;
import com.jlkj.human.hm.service.IWorkAddressService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 岗位工作地点数据 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-20
 */
@RestController
@RequestMapping("/humanresource/work/address")
public class WorkAddressController {
    @Autowired
    private IWorkAddressService humanresourceWorkAddressService;

    /**
     * 新增岗位工作地点数据
     */
    @Log(title = "新增岗位工作地点数据",businessType = BusinessType.INSERT)
    @Operation(summary = "新增岗位工作地点数据")
    @PostMapping("/addWorkAddressData")
    public AjaxResult addWorkAddressData(@RequestBody WorkAddressDTO workAddressDTO, @RequestHeader("token") String token){
        WorkAddress workAddress = new WorkAddress();
        BeanUtils.copyProperties(workAddressDTO, workAddress);
        boolean save = humanresourceWorkAddressService.save(workAddress);
        return AjaxResult.success(save);
    }
    /**
     * 修改岗位工作地点数据
     */
    @Log(title = "修改岗位工作地点数据",businessType = BusinessType.UPDATE)
    @Operation(summary = "修改岗位工作地点数据")
    @PutMapping("/updateWorkAddressData")
    public AjaxResult updateWorkAddressData(@RequestBody WorkAddressDTO workAddressDTO, @RequestHeader("token") String token){
        WorkAddress workAddress = new WorkAddress();
        BeanUtils.copyProperties(workAddressDTO, workAddress);
        WorkAddress postEntity = humanresourceWorkAddressService.getById(workAddress.getId());
        if (StringUtils.isNotNull(postEntity)) {
            boolean update = humanresourceWorkAddressService.updateById(workAddress);
            return AjaxResult.success(update);
        } else {
            boolean save = humanresourceWorkAddressService.save(workAddress);
            return AjaxResult.success(save);
        }
    }

    /**
     * 删除岗位工作地点数据
     */
    @Log(title = "删除岗位工作地点数据",businessType = BusinessType.DELETE)
    @Operation(summary = "删除岗位工作地点数据")
    @DeleteMapping("/deleteWorkAddressData")
    public AjaxResult deleteWorkAddressData(@RequestBody WorkAddressDTO workAddressDTO, @RequestHeader("token") String token){
        WorkAddress workAddress = new WorkAddress();
        BeanUtils.copyProperties(workAddressDTO, workAddress);
        boolean delete = humanresourceWorkAddressService.removeById(workAddress);
        return AjaxResult.success(delete);
    }

}
