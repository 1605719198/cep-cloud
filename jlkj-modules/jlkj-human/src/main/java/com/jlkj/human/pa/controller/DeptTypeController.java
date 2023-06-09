package com.jlkj.human.pa.controller;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.pa.domain.DeptType;
import com.jlkj.human.pa.domain.DeptTypeDetail;
import com.jlkj.human.pa.service.IDeptTypeDetailService;
import com.jlkj.human.pa.service.IDeptTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-06-8
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/deptType")
public class DeptTypeController extends BaseController {

    private final IDeptTypeService iDeptTypeService;
    private final IDeptTypeDetailService iDeptTypeDetailService;

    /**
     * 查询人员类别考评项目设定主档列表
     */
    @RequiresPermissions("human:deptType:list")
    @Log(title = "查询人员类别考评项目设定主档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询人员类别考评项目设定主档列表")
    @GetMapping("/list")
    public Object listDeptType(DeptType deptType) {
        startPage();
        List<DeptType> list = iDeptTypeService.lambdaQuery()
                .eq(DeptType::getCompId, deptType.getCompId())
                .eq(DeptType::getEffcMonth, deptType.getEffcMonth())
                .eq(StringUtils.isNotBlank(deptType.getDeptType()),DeptType::getDeptType, deptType.getDeptType()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 查询人员类别考评项目设定明细档列表
     */
    @Log(title = "查询人员类别考评项目设定明细档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询人员类别考评项目设定明细档列表")
    @GetMapping("/listDeptTypeDetail")
    public Object listDeptTypeDetail(DeptType deptType) {
        startPage();
        List<DeptTypeDetail> list = iDeptTypeDetailService.lambdaQuery()
                .eq(DeptTypeDetail::getDeptTypeId, deptType.getId()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 新增人员类别考评项目设定主档列表
     */
    @RequiresPermissions("human:deptType:add")
    @Log(title = "新增人员类别考评项目设定主档列表", businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员类别考评项目设定主档列表")
    @PostMapping
    public Object addDeptType(@RequestBody DeptType deptType) {
        List<DeptType> list = iDeptTypeService.lambdaQuery()
                .eq(DeptType::getDeptType, deptType.getDeptType())
                .eq(DeptType::getEffcMonth, deptType.getEffcMonth()).list();
        if (list.isEmpty()) {
            if (deptType.getEffcMonth().compareTo(DateUtils.dateTime1().substring(0,7)) < 0) {
                return AjaxResult.error("生效年月不能小于当前月");
            }
            deptType.setCreator(SecurityUtils.getNickName());
            deptType.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
            return iDeptTypeService.save(deptType);
        } else {
            return AjaxResult.error("生效月别、人员类别不能重复");
        }
    }

    /**
     * 修改人员类别考评项目设定主档列表
     */
    @RequiresPermissions("human:deptType:edit")
    @Log(title = "新增人员类别考评项目设定主档列表", businessType = BusinessType.UPDATE)
    @Operation(summary = "新增人员类别考评项目设定主档列表")
    @PutMapping
    public Object updateDeptType(@RequestBody DeptType deptType) {
        List<DeptType> list = iDeptTypeService.lambdaQuery()
                .eq(DeptType::getDeptType, deptType.getDeptType())
                .eq(DeptType::getEffcMonth, deptType.getEffcMonth()).list();
        if (list.isEmpty()) {
            if (deptType.getEffcMonth().compareTo(DateUtils.dateTime1().substring(0,7)) < 0) {
                return AjaxResult.error("生效年月不能小于当前月");
            }
            return iDeptTypeService.updateById(deptType);
        } else {
            return AjaxResult.error("生效月别、人员类别不能重复");
        }
    }

    /**
     * 删除人员类别考评项目设定主档
     */
    @RequiresPermissions("human:deptType:remove")
    @Operation(summary = "删除人员类别考评项目设定主档")
    @PostMapping("/delDeptType")
    @Log(title = "删除人员类别考评项目设定主档", businessType = BusinessType.DELETE)
    public Object delDeptType(@RequestBody DeptType deptType) {
        if (deptType.getEffcMonth().compareTo(DateUtils.dateTime1().substring(0,7)) < 0) {
            return AjaxResult.error("生效年月小于当前月时不能删除");
        }
        return iDeptTypeService.removeById(deptType.getId());
    }

    /**
     * 新增人员类别考评项目设定明细档列表
     */
    @RequiresPermissions("human:deptType:addDetail")
    @Log(title = "新增人员类别考评项目设定明细档列表", businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员类别考评项目设定明细档列表")
    @PostMapping("/addDeptTypeDetail")
    public Object addDeptTypeDetail(@RequestBody DeptTypeDetail deptTypeDetail) {
        if (deptTypeDetail.getWeight().compareTo(new BigDecimal(100)) > 0) {
            return AjaxResult.error("明细项的权重不能超过100%");
        }
        deptTypeDetail.setCreator(SecurityUtils.getNickName());
        deptTypeDetail.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
        return iDeptTypeDetailService.save(deptTypeDetail);
    }
}
