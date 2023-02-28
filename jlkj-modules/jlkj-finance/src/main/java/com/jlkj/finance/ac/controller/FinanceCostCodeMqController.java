package com.jlkj.finance.ac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.dto.finance.ip.FinanceperCodeDTO;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.finance.ac.domain.FinanceCostCode;
import com.jlkj.finance.ac.service.FinanceCostCodeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/financepr/code")
public class FinanceCostCodeMqController {
     @Autowired
    private FinanceCostCodeService financeCostCodeService;
    /**
     * 成本科目新增板块资料
     */
    @Log(title = "成本科目新增板块资料", businessType = BusinessType.INSERT)
    @Operation(summary = "成本科目新增板块资料")
    @PostMapping("/addCode")
    public Object addCode(@RequestBody FinanceperCodeDTO financeperCodeDTO, @RequestHeader("token") String token){
        FinanceCostCode financeCostCode = new FinanceCostCode();
        BeanUtils.copyProperties(financeperCodeDTO,financeCostCode);
        boolean save = financeCostCodeService.save(financeCostCode);
        //TODO 获取登录用户名
        return AjaxResult.success(save);
    }
    /**
     * 成本科目修改板块资料
     */
    @Log(title = "成本科目修改板块资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "成本科目修改板块资料")
    @PostMapping("/updateBoardCode")
    public Object updateBoardCode(@RequestBody FinanceperCodeDTO financeperCodeDTO, @RequestHeader("token") String token){
        FinanceCostCode financeCostCode = new FinanceCostCode();
        BeanUtils.copyProperties(financeperCodeDTO,financeCostCode);
        QueryWrapper<FinanceCostCode> wrapper = new QueryWrapper<>();
        wrapper.eq("comp_id",financeCostCode.getCompId())
                .eq("cost_code ",financeCostCode.getCostCode());
        financeCostCodeService.remove(wrapper);
        boolean save = financeCostCodeService.save(financeCostCode);
        return AjaxResult.success(save);
    }
    /**
     * 成本科目删除板块资料
     */
    @Log(title = "成本科目删除板块资料", businessType = BusinessType.DELETE)
    @Operation(summary = "成本科目删除板块资料")
    @PostMapping("/deleteBoardCode")
    public Object deleteBoardCode(@RequestBody FinanceperCodeDTO financeperCodeDTO, @RequestHeader("token") String token){
        FinanceCostCode financeCostCode = new FinanceCostCode();
        BeanUtils.copyProperties(financeperCodeDTO,financeCostCode);
        QueryWrapper<FinanceCostCode> wrapper = new QueryWrapper<>();
        wrapper.eq("comp_id",financeCostCode.getCompId())
                .eq("cost_code ",financeCostCode.getCostCode());
        boolean delete = financeCostCodeService.remove(wrapper);
        //TODO 获取登录用户名
        return AjaxResult.success(delete);
    }
}
