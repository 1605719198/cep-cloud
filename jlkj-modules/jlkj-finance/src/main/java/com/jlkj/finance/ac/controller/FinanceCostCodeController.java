package com.jlkj.finance.ac.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.finance.aa.domain.FinanceAccountCode;
import com.jlkj.finance.aa.service.FinanceAccountCodeService;
import com.jlkj.finance.ac.dto.FinanceCostCodeDTO;
import com.jlkj.finance.ac.domain.FinanceCostCode;
import com.jlkj.finance.ac.service.FinanceCostCodeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/finance/cost/code")
public class FinanceCostCodeController {

    @Autowired
    private FinanceCostCodeService financeCostCodeService;
    @Autowired
    private FinanceAccountCodeService financeAccountCodeService;

    /**
     * 新增成本科目
     */
    @Log(title = "新增成本科目", businessType = BusinessType.INSERT)
    @Operation(summary = "新增成本科目")
    @PostMapping("/doAdd")
    public Object doAdd(@RequestBody FinanceCostCodeDTO financeCostCodeDTO){
        FinanceCostCode financeCostCode = new FinanceCostCode();
        QueryWrapper<FinanceCostCode> wrapper = new QueryWrapper<>();
        BeanUtils.copyProperties(financeCostCodeDTO, financeCostCode);
        String costCode = financeCostCodeDTO.getCostCode();
        wrapper.eq("cost_code", costCode);
        List<FinanceCostCode> list = financeCostCodeService.list(wrapper);
        if (list.size() >= 1) {
            return AjaxResult.error("您输入的成本科目系统中已存在，请重新输入！");
        }
        QueryWrapper<FinanceCostCode> wrapperName = new QueryWrapper<>();
        String costDesc = financeCostCodeDTO.getCostDesc();
        wrapperName.eq("cost_desc", costDesc);
        List<FinanceCostCode> listName = financeCostCodeService.list(wrapperName);
            if (listName.size()>0) {
                return AjaxResult.error("成本科目名称不可重复，请重新输入！");
            }

        boolean save = financeCostCodeService.save(financeCostCode);
        return AjaxResult.success(save);
    }
    /**
     * 修改成本科目
     */
    @Log(title = "修改成本科目", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改成本科目")
    @PutMapping("/doEdit")
    public Object doEdit(@RequestBody  FinanceCostCodeDTO financeCostCodeDTO){
        FinanceCostCode financeCostCode = new FinanceCostCode();
        BeanUtils.copyProperties(financeCostCodeDTO, financeCostCode);
        LambdaQueryWrapper<FinanceCostCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinanceCostCode::getId, financeCostCode.getId());
        Map<String, Object> map = financeCostCodeService.getMap(queryWrapper);
        String costCode = "cost_code";
        QueryWrapper<FinanceCostCode> wrapperName = new QueryWrapper<>();
        String costDesc = financeCostCodeDTO.getCostDesc();
        wrapperName.eq("cost_desc", costDesc);
        List<FinanceCostCode> listName = financeCostCodeService.list(wrapperName);
        for(FinanceCostCode financeCostCode1 :listName){
            if (!financeCostCode1.getId().equals(financeCostCodeDTO.getId())) {
                return AjaxResult.error("成本科目名称不可重复，请重新输入！");
            }
        }
        if(map.get(costCode).equals(financeCostCode.getCostCode())) {
            boolean update = financeCostCodeService.updateById(financeCostCode);
            return AjaxResult.success(update);
        }else {
            return AjaxResult.error("成本科目编码，不允许修改");
        }
    }
    /**
     * 删除成本科目
     */
    @Log(title = "删除成本科目", businessType = BusinessType.DELETE)
    @Operation(summary = "删除成本科目")
    @DeleteMapping("/doDelete/{ids}")
    public Object doDelete(@PathVariable String[] ids){
        List<String> idList = Arrays.asList(ids);
        boolean delete = financeCostCodeService.removeBatchByIds(idList);
        return AjaxResult.success(delete);
    }
    /**
     * 成本科目分页查询、根据条件查询信息
     */
    @Log(title = "成本科目分页查询、根据条件查询信息", businessType = BusinessType.OTHER)
    @Operation(summary = "分页查询、根据条件查询字典信息，名称")
    @GetMapping("/doQueryAll")
    public Object doQueryAll(FinanceCostCodeDTO financeCostCodeDTO) {
        Long current = financeCostCodeDTO.getPage();
        Long limit = financeCostCodeDTO.getLimit();
        //创建page对象
        Page<FinanceCostCode> pageData = new Page<>(current, limit);
        //构造条件
        QueryWrapper<FinanceCostCode> wrapper = new QueryWrapper<>();
        //条件组合查询
        wrapper.likeRight(StringUtils.isNotEmpty(financeCostCodeDTO.getCostCode()),"cost_code",financeCostCodeDTO.getCostCode());
        wrapper.orderByDesc("create_time");
        //实现分页查询
        financeCostCodeService.page(pageData,wrapper);
        //总记录数
        long total = pageData.getTotal();
        //数据list集合
        List<FinanceCostCode> records = pageData.getRecords();
        List<FinanceCostCodeDTO> list = new ArrayList<>();
        for (FinanceCostCode financeCostCodeAttr : records) {
            FinanceCostCodeDTO costdto = new FinanceCostCodeDTO();
            BeanUtils.copyProperties(financeCostCodeAttr, costdto);
                String accountCode = costdto.getAccountCode();
                    QueryWrapper<FinanceAccountCode> wrapperAccountCode = new QueryWrapper<>();
                    wrapperAccountCode.eq(StringUtils.isNotEmpty(accountCode),"account_code",accountCode);
                    FinanceAccountCode financeAccountCode =  financeAccountCodeService.getOne(wrapperAccountCode);
                    if(financeAccountCode !=null){
                        costdto.setAccountName(financeAccountCode.getAccountChineseName());
                    }else {
                        costdto.setAccountName("");
                    }
            list.add(costdto);
        }
        Map<String,Object> dataMap = new HashMap<>(16);
        dataMap.put("total",total);
        dataMap.put("list",list);
        if (pageData.getRecords().isEmpty()) {
            return AjaxResult.error("查无资料");
        } else {
            return AjaxResult.success("查询成功！", dataMap);
        }

    }

    /**
     * 根据ID查询科目数据
     */
    @Log(title = "根据ID查询科目数据", businessType = BusinessType.OTHER)
    @Operation(summary = "根据ID查询科目数据")
    @GetMapping("/doQueryOne/{id}")
    public Object doQueryOne(@PathVariable String id){
            FinanceCostCode financeCostCode = financeCostCodeService.getById(id);
            return AjaxResult.success(financeCostCode);
    }


}
