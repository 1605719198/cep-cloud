package com.jlkj.human.pa.controller;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.pa.domain.DeptKpi;
import com.jlkj.human.pa.domain.DeptKpiDetail;
import com.jlkj.human.pa.dto.DeptKpiDetailDTO;
import com.jlkj.human.pa.service.IDeptKpiDetailService;
import com.jlkj.human.pa.service.IDeptKpiService;
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
@RequestMapping("/deptKpi")
public class DeptKpiController extends BaseController {

    private final IDeptKpiService iDeptKpiService;
    private final IDeptKpiDetailService iDeptKpiDetailService;

    /**
     * 查询单位KPI指标主档列表
     */
    @RequiresPermissions("human:deptKpi:list")
    @Log(title = "查询单位KPI指标主档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询单位KPI指标主档列表")
    @GetMapping("/list")
    public Object listDeptKpi(DeptKpi deptKpi) {
        startPage();
        List<DeptKpi> list = iDeptKpiService.lambdaQuery()
                .eq(DeptKpi::getCompId, deptKpi.getCompId())
                .eq(DeptKpi::getEffcMonth, deptKpi.getEffcMonth())
                .eq(StringUtils.isNotBlank(deptKpi.getDept()),DeptKpi::getDept, deptKpi.getDept()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 查询单位KPI指标明细档列表
     */
    @Log(title = "查询单位KPI指标明细档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询单位KPI指标明细档列表")
    @GetMapping("/listDeptKpiDetail")
    public Object listDeptKpiDetail(DeptKpi deptKpi) {
        startPage();
        List<DeptKpiDetail> list = iDeptKpiDetailService.lambdaQuery()
                .eq(DeptKpiDetail::getKpiId, deptKpi.getId()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 新增单位KPI指标主档列表
     */
    @RequiresPermissions("human:deptKpi:add")
    @Log(title = "新增单位KPI指标主档列表", businessType = BusinessType.INSERT)
    @Operation(summary = "新增单位KPI指标主档列表")
    @PostMapping
    public Object addDeptKpi(@RequestBody DeptKpi deptKpi) {
        List<DeptKpi> list = iDeptKpiService.lambdaQuery()
                .eq(DeptKpi::getDept, deptKpi.getDept())
                .eq(DeptKpi::getEffcMonth, deptKpi.getEffcMonth()).list();
        if (list.isEmpty()) {
            if (deptKpi.getEffcMonth().compareTo(DateUtils.dateTime1().substring(0,7)) < 0) {
                return AjaxResult.error("指标年月不能小于当前月");
            }
            deptKpi.setCreator(SecurityUtils.getNickName());
            deptKpi.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
            deptKpi.setStatus("1");
            return iDeptKpiService.save(deptKpi);
        } else {
            return AjaxResult.error("同一个部门，指标年月不能重复");
        }
    }

    /**
     * 删除单位KPI指标主档同时一起删除明细档
     */
    @RequiresPermissions("human:deptKpi:remove")
    @Operation(summary = "删除单位KPI指标主档同时一起删除明细档")
    @DeleteMapping("/{id}")
    @Log(title = "删除单位KPI指标主档同时一起删除明细档", businessType = BusinessType.DELETE)
    public Object delDeptKpiAndDeptKpiDetail(@PathVariable String id) {
        String two = "2";
        DeptKpi deptKpi = iDeptKpiService.lambdaQuery()
                .select(DeptKpi::getStatus)
                .eq(DeptKpi::getId, id).one();
        if (two.equals(deptKpi.getStatus())){
            return AjaxResult.error("已确认数据不能删除");
        } else {
            iDeptKpiService.lambdaUpdate().eq(DeptKpi::getId, id).remove();
            iDeptKpiDetailService.lambdaUpdate().eq(DeptKpiDetail::getKpiId, id).remove();
            return AjaxResult.success("删除成功");
        }
    }

    /**
     * 删除单位KPI指标明细档
     */
    @RequiresPermissions("human:deptKpi:removeDetail")
    @Operation(summary = "删除单位KPI指标明细档")
    @DeleteMapping("/removeDetail/{detailId}")
    @Log(title = "删除单位KPI指标明细档", businessType = BusinessType.DELETE)
    public Object delDeptKpiDetail(@PathVariable String detailId) {
        iDeptKpiDetailService.lambdaUpdate().eq(DeptKpiDetail::getId, detailId).remove();
        return AjaxResult.success("删除成功");
    }

    /**
     * 新增单位KPI指标明细档列表
     */
    @RequiresPermissions("human:deptKpi:addDetail")
    @Log(title = "新增单位KPI指标明细档列表", businessType = BusinessType.INSERT)
    @Operation(summary = "新增单位KPI指标明细档列表")
    @PostMapping("/addDeptKpiDetail")
    public Object addDeptKpiDetail(@RequestBody DeptKpiDetail deptKpiDetail) {
        deptKpiDetail.setCreator(SecurityUtils.getNickName());
        deptKpiDetail.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
        return iDeptKpiDetailService.save(deptKpiDetail);
    }

    /**
     * 更新KPI指标主档状态
     */
    @RequiresPermissions("human:deptKpi:confirm")
    @Log(title = "更新KPI指标主档状态", businessType = BusinessType.UPDATE)
    @Operation(summary = "更新KPI指标主档状态")
    @PostMapping("/updateDeptKpiStatus")
    public Object updateDeptKpiStatus(@RequestBody DeptKpiDetailDTO deptKpiDetailDTO) {
        for (DeptKpiDetail item : deptKpiDetailDTO.getDeptKpiDetailList()){
            if (item.getWeight().compareTo(new BigDecimal(100)) == 0) {
                continue;
            } else {
                return AjaxResult.error("明细项的权重必须为100%");
            }
        }
        return iDeptKpiService.lambdaUpdate().set(DeptKpi::getStatus, "2").eq(DeptKpi::getId, deptKpiDetailDTO.getId()).update();
    }
}
