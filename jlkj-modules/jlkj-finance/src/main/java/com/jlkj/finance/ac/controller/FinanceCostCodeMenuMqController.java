package com.jlkj.finance.ac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.dto.dto.financeperdto.FinanceperMenuDTO;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.finance.ac.domain.FinanceCostTree;
import com.jlkj.finance.ac.service.FinanceCostTreeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/financepr/menu")
public class FinanceCostCodeMenuMqController {
    @Autowired
    private FinanceCostTreeService financeCostTreeService;
    /**
     * 成本科目新增板块资料
     */
    @Log(title = "成本科目新增板块资料", businessType = BusinessType.INSERT)
    @Operation(summary = "成本科目树新增板块资料")
    @PostMapping("/addMenu")
    public Object addMenu(@RequestBody FinanceperMenuDTO financeperMenuDTO, @RequestHeader("token") String token){
        FinanceCostTree financeCostTree = new FinanceCostTree();
        BeanUtils.copyProperties(financeperMenuDTO,financeCostTree);
        boolean save = financeCostTreeService.save(financeCostTree);
        //TODO 获取登录用户名
        return AjaxResult.success(save);
    }
    /**
     * 成本科目修改板块资料
     */
    @Log(title = "成本科目修改板块资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "成本科目树修改板块资料")
    @PostMapping("/updateBoardMenu")
    public Object updateBoardMenu(@RequestBody  FinanceperMenuDTO financeperMenuDTO, @RequestHeader("token") String token){
        FinanceCostTree financeCostTree = new FinanceCostTree();
        BeanUtils.copyProperties(financeperMenuDTO,financeCostTree);
        QueryWrapper<FinanceCostTree> wrapper = new QueryWrapper<>();
        wrapper.eq("comp_id",financeCostTree.getCompId())
                .eq("cost_code_level_code ",financeCostTree.getCostCodeLevelCode());
        financeCostTreeService.remove(wrapper);
        boolean save = financeCostTreeService.save(financeCostTree);
        return AjaxResult.success(save);
    }
    /**
     * 成本科目删除板块资料
     */
    @Log(title = "成本科目删除板块资料", businessType = BusinessType.DELETE)
    @Operation(summary = "成本科目树删除板块资料")
    @PostMapping("/deleteBoardMenu")
    public Object deleteBoardMenu(@RequestBody FinanceperMenuDTO financeperMenuDTO, @RequestHeader("token") String token){
        FinanceCostTree financeCostTree = new FinanceCostTree();
        BeanUtils.copyProperties(financeperMenuDTO,financeCostTree);
        QueryWrapper<FinanceCostTree> wrapper = new QueryWrapper<>();
        wrapper.eq("comp_id",financeCostTree.getCompId())
               .eq("cost_code_level_code",financeCostTree.getCostCodeLevelCode());
        boolean delete = financeCostTreeService.remove(wrapper);
        //TODO 获取登录用户名
        return AjaxResult.success(delete);
    }
}
