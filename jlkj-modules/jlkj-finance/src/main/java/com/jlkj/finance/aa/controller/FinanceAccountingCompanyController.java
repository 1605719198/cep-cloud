package com.jlkj.finance.aa.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.finance.aa.domain.FinanceCompany;
import com.jlkj.finance.aa.dto.FinanceCompanyDTO;
import com.jlkj.finance.aa.service.FinanceCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/acct/company")
public class FinanceAccountingCompanyController {
    @Autowired
    private FinanceCompanyService financeCompanyService;
    /**
     * 根据公司编码、公司名称
     */
    @Operation(summary = "根据公司编码、公司名称")
    @GetMapping("/queryAll")
    @Log(title = "公司编码、公司名称", businessType = BusinessType.OTHER)
    public Object queryAll(FinanceCompanyDTO financeCompanyDTO) {
        try {
            String compId = financeCompanyDTO.getCompId();
            String companyChineseName = financeCompanyDTO.getCompanyChineseName();
            Long pageNum = financeCompanyDTO.getPageNum();
            Long pageSize = financeCompanyDTO.getPageSize();
            LambdaQueryWrapper<FinanceCompany> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(StringUtils.isNotBlank(compId), FinanceCompany::getCompId, compId)
                    .like(StringUtils.isNotBlank(companyChineseName), FinanceCompany::getCompanyChineseName, companyChineseName);
            Page<FinanceCompany> page = financeCompanyService.page(new Page<>(pageNum, pageSize), queryWrapper);
            long total = page.getTotal();
            //数据list集合
            List<FinanceCompany> records = page.getRecords();
            Map<String, Object> dataMap = new HashMap<>(5);
            dataMap.put("total", total);
            dataMap.put("list", records);
            if (records.size()==0) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
    /**
     * 会计公司新增
     */
    @Operation(summary = "会计公司新增")
    @PostMapping("/doAdd")
    @Log(title = "会计公司新增", businessType = BusinessType.INSERT)
    public Object addFinanceCost(@RequestBody FinanceCompanyDTO financeCompanyDTO) {
        try {
            QueryWrapper<FinanceCompany> wrapper = new QueryWrapper<>();
            FinanceCompany financeCompany = new FinanceCompany();
            BeanUtils.copyProperties(financeCompanyDTO, financeCompany);
            String compId = financeCompanyDTO.getCompId();
            wrapper.eq("comp_id", compId);
            List<FinanceCompany> list = financeCompanyService.list(wrapper);
            if (list.size() >= 1) {
                return AjaxResult.error("您输入的会计公司系统中已存在，请重新输入！");
            }

            boolean result = financeCompanyService.save(financeCompany);

            if (result ) {
                Map<String, String> resultMap = new HashMap<>(16);
                resultMap.put("id", financeCompany.getId());
                resultMap.put("create_user", financeCompany.getCreatUser());
                List<Map<String, String>> resultList = new ArrayList<>();
                resultList.add(resultMap);
                return AjaxResult.success("新增成功", resultList);

            } else {
                return AjaxResult.error("新增失败，请重新提交");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
    /**
     * 详情查询
     */
    @Operation(summary = "根据公司编码，id")
    @GetMapping("/queryOne")
    @Log(title = "公司编码，id详情查询", businessType = BusinessType.OTHER)
    public Object queryOne(@RequestParam String compId, String id) {
        QueryWrapper<FinanceCompany> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(compId)) {
            wrapper.eq("comp_id", compId)
                    .eq("id", id);
        }
        FinanceCompany one = financeCompanyService.getOne(wrapper);
        return AjaxResult.success(one);
    }
    /**
     * 会计公司修改
     *
     */
    @Operation(summary = "会计公司修改")
    @PostMapping("/doEdit")
    @Log(title = "会计公司修改", businessType = BusinessType.UPDATE)
    public Object updateEnergyCode(@RequestBody FinanceCompanyDTO financeCompanyDTO) {
        try {
            FinanceCompany financeCompany = new FinanceCompany();
            BeanUtils.copyProperties(financeCompanyDTO, financeCompany);
            boolean result = financeCompanyService.updateById(financeCompany);
            if (result){
                Map<String, String> resultMap;
                resultMap = new HashMap<>(16);
                resultMap.put("id", financeCompany.getId());
                resultMap.put("update_user", financeCompany.getUpdateUser());
                List<Map<String, String>> resultList = new ArrayList<>();
                resultList.add(resultMap);
                return AjaxResult.success("修改成功", resultList);
            } else {
                return AjaxResult.error("修改失败，请重新提交");
            }

        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
    /**
     * 会计公司删除
     */
    @Operation(summary = "会计公司删除")
    @DeleteMapping("/delete")
    @Log(title = "会计公司删除", businessType = BusinessType.DELETE)
    public Object deleteEnergyCode(@RequestParam String id) {
        try {
            QueryWrapper<FinanceCompany> wrapper = new QueryWrapper<>();
            wrapper.eq("id",id);
            boolean delete = financeCompanyService.remove(wrapper);
            //TODO 获取登录用户名
            return AjaxResult.success(delete);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
