package com.jlkj.finance.ac.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.finance.aa.domain.FinanceAccountCode;
import com.jlkj.finance.aa.service.FinanceAccountCodeService;
import com.jlkj.finance.ac.dto.FinanceCostDownTypeTreeDTO;

import com.jlkj.finance.ac.service.FinanceCostTreeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/finance/cost/menu")
public class FinanceCostTreeController {
    @Autowired
    private FinanceCostTreeService financeCostTreeService;
    @Autowired
    private FinanceAccountCodeService financeAccountCodeService;
    /**
     * 获取成本科目类型树结构资料
     */
    @Log(title = "获取成本科目类型树结构资料", businessType = BusinessType.OTHER)
    @Operation(summary = "获取成本科目树结构资料")
    @GetMapping("/generateTree")
    public Object generateTree() {
        List<FinanceCostDownTypeTreeDTO> list = financeCostTreeService.getEquipmentTypeTreeList();
        return AjaxResult.success(list);
    }

    /**
     * 获取成本科目类型树结构资料
     */
    @Log(title = "获取成本科目类型树结构资料", businessType = BusinessType.OTHER)
    @Operation(summary = "获取成本科目树结构资料")
    @GetMapping("/getTreeCostNodeAll")
    public Object getTreeCostNodeAll() {
        List<FinanceCostDownTypeTreeDTO> list = financeCostTreeService.getEquipmentTypeAllTreeList();
        return AjaxResult.success(list);
    }

    /**
     * 获取成本科目树户号参号结构资料
     */
    @Log(title = "获取成本科目树户号参号结构资料", businessType = BusinessType.OTHER)
    @Operation(summary = "获取成本科目树户号参号结构资料")
    @GetMapping("/getListAccountTree/{accountCode}")
    public Object getListAccountTree(@PathVariable String accountCode) {

            LambdaQueryWrapper<FinanceAccountCode> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(StringUtils.isNotBlank(accountCode), FinanceAccountCode::getAccountCode, accountCode);
            FinanceAccountCode financeAccountCode = financeAccountCodeService.getOne(queryWrapper1);
            return financeAccountCode;


    }
}