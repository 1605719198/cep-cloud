package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.PayTable;
import com.jlkj.human.hs.domain.PayTableFormula;
import com.jlkj.human.hs.dto.PayTableDTO;
import com.jlkj.human.hs.dto.PayTableFormulaDTO;
import com.jlkj.human.hs.service.IPayTableFormulaService;
import com.jlkj.human.hs.service.IPayTableService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HuangBing
 * @Description
 * @create 2023-05-11 11:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/payTable")
public class PayTableController extends BaseController {

    private final IPayTableService iPayTableService;
    private final IPayTableFormulaService iPayTableFormulaService;

    /**
     * 查询公司薪资表项目列表
     */
    @RequiresPermissions("human:payTable:list")
    @Log(title = "查询公司薪资表项目列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询公司薪资表项目列表")
    @GetMapping("/list")
    public Object listPayTable(PayTable payTable) {
        startPage();
        List<PayTable> list = iPayTableService.lambdaQuery().eq(PayTable::getCompId, payTable.getCompId()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 保存公司薪资表项目列表
     */
    @RequiresPermissions("human:payTable:add")
    @Log(title = "保存公司薪资表项目列表", businessType = BusinessType.INSERT)
    @Operation(summary = "保存公司薪资表项目列表")
    @PostMapping
    public Object addPayTable(@RequestBody PayTableDTO payTableDTO) {
        for (PayTable item : payTableDTO.getPayTableList()) {
            item.setCreator(SecurityUtils.getNickName());
            item.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
        }
        return iPayTableService.saveOrUpdateBatch(payTableDTO.getPayTableList());
    }

    /**
     * 查询公司薪资表项目公式列表
     */
    @RequiresPermissions("human:payTableFormula:list")
    @Log(title = "查询公司薪资表项目公式列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询公司薪资表项目公式列表")
    @GetMapping("/listFormula")
    public Object listPayTableFormula(PayTableFormula payTableFormula) {
        startPage();
        List<PayTableFormula> list = iPayTableFormulaService.lambdaQuery().eq(PayTableFormula::getPayTabId, payTableFormula.getPayTabId()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 保存公司薪资表项目公式表
     */
    @RequiresPermissions("human:payTableFormula:add")
    @Log(title = "保存公司薪资表项目公式表", businessType = BusinessType.INSERT)
    @Operation(summary = "保存公司薪资表项目公式表")
    @PostMapping("/addPayTableFormula")
    public Object addPayTableFormula(@RequestBody PayTableFormulaDTO payTableFormulaDTO) {
        for (PayTableFormula item : payTableFormulaDTO.getTableData1()) {
            item.setCreator(SecurityUtils.getNickName());
            item.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
        }
        return iPayTableFormulaService.saveOrUpdateBatch(payTableFormulaDTO.getTableData1().stream().filter(item -> null != item.getConditions() && null != item.getFormula()).filter(item -> !item.getConditions().isEmpty() && !item.getFormula().isEmpty()).collect(Collectors.toList()));
    }

    /**
     * 删除公司薪资表项目列表
     */
    @RequiresPermissions("human:payTable:remove")
    @Operation(summary = "删除公司薪资表项目列表")
    @DeleteMapping("/{uuid}")
    @Log(title = "删除公司薪资表项目列表", businessType = BusinessType.DELETE)
    public Object delPayTable(@PathVariable String uuid) {
        iPayTableService.lambdaUpdate().eq(PayTable::getUuid, uuid).remove();
        return AjaxResult.success("删除成功");
    }
}
