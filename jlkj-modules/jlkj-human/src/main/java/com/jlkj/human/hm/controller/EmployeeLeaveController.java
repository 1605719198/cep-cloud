package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.Leave;
import com.jlkj.human.hm.service.ILeaveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-23 13:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/employeeLeave")
public class EmployeeLeaveController extends BaseController {

    private final ILeaveService iLeaveService;

    /**
     * 查询人员离职信息列表
     */
    @RequiresPermissions("human:employeeLeave:list")
    @Log(title = "查询人员离职信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询人员离职信息列表")
    @GetMapping("/list")
    public Object listEmployeeLeave(Leave leave) {
        startPage();
        List<Leave> list = iLeaveService.lambdaQuery()
                                        .eq(StringUtils.isNotBlank(leave.getCompId()), Leave::getCompId, leave.getCompId())
                                        .eq(Leave::getEmpNo, leave.getEmpNo()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 根据uuid查询员工离职详细信息
     */
    @RequiresPermissions("human:employeeLeave:query")
    @Operation(summary = "根据uuid查询员工离职详细信息")
    @GetMapping("/{uuid}")
    @Log(title = "根据uuid查询员工离职详细信息", businessType = BusinessType.OTHER)
    public Object getEmployeeLeave(@PathVariable String uuid) {
        Leave leave = iLeaveService.lambdaQuery().eq(Leave::getUuid, uuid).one();
        return AjaxResult.success("查询成功", leave);
    }

    /**
     * 添加员工离职信息
     */
    @RequiresPermissions("human:employeeLeave:add")
    @Operation(summary = "添加员工离职信息")
    @PostMapping
    @Log(title = "添加员工离职信息", businessType = BusinessType.INSERT)
    public Object addEmployeeLeave(@RequestBody Leave leave) {
        leave.setCreator(SecurityUtils.getNickName());
        iLeaveService.save(leave);
        return AjaxResult.success("新增成功");
    }

    /**
     * 修改员工离职信息
     */
    @RequiresPermissions("human:employeeLeave:edit")
    @Operation(summary = "修改员工离职信息")
    @PutMapping
    @Log(title = "修改员工离职信息", businessType = BusinessType.UPDATE)
    public Object updateEmployeeLeave(@RequestBody Leave leave) {
        iLeaveService.updateById(leave);
        return AjaxResult.success("修改成功");
    }

    /**
     * 删除员工离职信息
     */
    @RequiresPermissions("human:employeeLeave:remove")
    @Operation(summary = "删除员工离职信息")
    @DeleteMapping("/{uuid}")
    @Log(title = "删除员工离职信息", businessType = BusinessType.DELETE)
    public Object delEmployeeLeave(@PathVariable String uuid) {
        iLeaveService.lambdaUpdate().eq(Leave::getUuid, uuid).remove();
        return AjaxResult.success("删除成功");
    }
}
