package com.jlkj.human.hm.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.Encourage;
import com.jlkj.human.hm.service.IEncourageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-27 13:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/employeeEncourage")
public class EmployeeEncourageController extends BaseController {
    
    private final IEncourageService iEncourageService;

    /**
     * 查询员工奖惩信息列表
     */
    @RequiresPermissions("human:employeeEncourage:list")
    @Log(title = "查询员工奖惩信息列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询员工奖惩信息列表")
    @GetMapping("/list")
    public Object listEmployeeEncourage(Encourage encourage) {
        startPage();
        List<Encourage> list = iEncourageService.lambdaQuery()
                .eq(StringUtils.isNotBlank(encourage.getCompId()), Encourage::getCompId, encourage.getCompId())
                .eq(Encourage::getEmpNo, encourage.getEmpNo()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 根据uuid查询员工奖惩详细信息
     */
    @RequiresPermissions("human:employeeEncourage:query")
    @Operation(summary = "根据uuid查询员工奖惩详细信息")
    @GetMapping("/{uuid}")
    @Log(title = "根据uuid查询员工奖惩详细信息", businessType = BusinessType.OTHER)
    public Object getEmployeeEncourage(@PathVariable String uuid) {
        Encourage encourage = iEncourageService.lambdaQuery().eq(Encourage::getUuid, uuid).one();
        return AjaxResult.success("查询成功", encourage);
    }

    /**
     * 添加员工奖惩信息
     */
    @RequiresPermissions("human:employeeEncourage:add")
    @Operation(summary = "添加员工奖惩信息")
    @PostMapping
    @Log(title = "添加员工奖惩信息", businessType = BusinessType.INSERT)
    public Object addEmployeeEncourage(@RequestBody Encourage encourage) {
        encourage.setCreator(SecurityUtils.getNickName());
        iEncourageService.save(encourage);
        return AjaxResult.success("新增成功");
    }

    /**
     * 修改员工奖惩信息
     */
    @RequiresPermissions("human:employeeEncourage:edit")
    @Operation(summary = "修改员工奖惩信息")
    @PutMapping
    @Log(title = "修改员工奖惩信息", businessType = BusinessType.UPDATE)
    public Object updateEmployeeEncourage(@RequestBody Encourage encourage) {
        iEncourageService.updateById(encourage);
        return AjaxResult.success("修改成功");
    }

    /**
     * 删除员工奖惩信息
     */
    @RequiresPermissions("human:employeeEncourage:remove")
    @Operation(summary = "删除员工奖惩信息")
    @DeleteMapping("/{uuid}")
    @Log(title = "删除员工奖惩信息", businessType = BusinessType.DELETE)
    public Object delEmployeeEncourage(@PathVariable String uuid) {
        iEncourageService.lambdaUpdate().eq(Encourage::getUuid, uuid).remove();
        return AjaxResult.success("删除成功");
    }
}
