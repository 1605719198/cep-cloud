package com.jlkj.finance.AA.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.finance.AA.domain.FinanceAccountCode;
import com.jlkj.finance.AA.domain.FinanceAccountTree;
import com.jlkj.finance.AA.dto.FinanceAccountCodeDTO;
import com.jlkj.finance.AA.dto.FinanceAccountingTreeDTO;
import com.jlkj.finance.AA.service.FinanceAccountCodeService;
import com.jlkj.finance.AA.service.FinanceAccountTreeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/finance/accountCode")
public class FinanceAccountSettingController {
    @Autowired
    private FinanceAccountTreeService financeAccountTreeService;
    @Autowired
    private FinanceAccountCodeService financeAccountCodeService;

    /**
     * 新增成本科目
     */
    @Operation(summary = "新增会计科目")
    @PostMapping("/doAdd")
    @Log(title = "会计科目新增", businessType = BusinessType.INSERT)
    public Object doAdd(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO) {
        FinanceAccountCode financeAccountCode = new FinanceAccountCode();
        QueryWrapper<FinanceAccountCode> wrapper = new QueryWrapper<>();
        BeanUtils.copyProperties(financeAccountCodeDTO, financeAccountCode);
        String accountCodeX = financeAccountCodeDTO.getAccountCodeX();
        financeAccountCode.setAccountCode(accountCodeX);
        wrapper.eq("account_code", accountCodeX);
        List<FinanceAccountCode> list = financeAccountCodeService.list(wrapper);
        if (list.size() >= 1) {
            return AjaxResult.error("您输入的会计科目系统中已存在，请重新输入！");
        }
        QueryWrapper<FinanceAccountCode> wrapperName = new QueryWrapper<>();
        String accountChineseName = financeAccountCodeDTO.getAccountChineseName();
        wrapperName.eq("account_chinese_name", accountChineseName);
        List<FinanceAccountCode> listName = financeAccountCodeService.list(wrapperName);

            if (listName.size()>0) {
                return AjaxResult.error("会计科目名称不可重复，请重新输入！");
            }

        boolean save = financeAccountCodeService.save(financeAccountCode);
        FinanceAccountTree financeAccountTree = new FinanceAccountTree();
        financeAccountTree.setCompId(financeAccountCodeDTO.getCompId());
        financeAccountTree.setParentId(financeAccountCodeDTO.getParentId());
        financeAccountTree.setAccountCode(financeAccountCodeDTO.getAccountCodeX());
        financeAccountTree.setAccountName(financeAccountCodeDTO.getAccountCode());
        financeAccountTreeService.save(financeAccountTree);
        return AjaxResult.success(save);
    }

    /**
     * 修改会计科目
     */
    @Operation(summary = "修改会计科目")
    @PutMapping("/doEdit")
    @Log(title = "会计科目修改", businessType = BusinessType.UPDATE)
    public Object doEdit(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO) {
        FinanceAccountCode financeAccountCode = new FinanceAccountCode();
        BeanUtils.copyProperties(financeAccountCodeDTO, financeAccountCode);
        String accountChineseName = financeAccountCodeDTO.getAccountChineseName();
        QueryWrapper<FinanceAccountCode> wrapper = new QueryWrapper<>();
        wrapper.eq("account_chinese_name", accountChineseName);
        List<FinanceAccountCode> list = financeAccountCodeService.list(wrapper);
        for(FinanceAccountCode financeAccountCode1 :list){
            if (!financeAccountCode1.getId().equals(financeAccountCodeDTO.getId())) {
                return AjaxResult.error("会计科目名称不可重复，请重新输入！");
            }
        }

        boolean update = financeAccountCodeService.updateById(financeAccountCode);
        return AjaxResult.success(update);

    }

    /**
     * 修改启用禁用
     */
    @Operation(summary = "修改启用禁用")
    @PutMapping("/doEditState")
    @Log(title = "启用禁用修改", businessType = BusinessType.UPDATE)
    public Object doEditState(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO) {
        FinanceAccountCode financeAccountCode = new FinanceAccountCode();
        BeanUtils.copyProperties(financeAccountCodeDTO, financeAccountCode);
        boolean update = financeAccountCodeService.updateById(financeAccountCode);
        return AjaxResult.success(update);
    }

    /**
     * 删除会计科目
     */
    @Operation(summary = "删除会计科目")
    @DeleteMapping("/doDelete/{ids}")
    @Log(title = "会计科目删除", businessType = BusinessType.DELETE)
    public Object doDelete(@PathVariable String ids) {
        QueryWrapper<FinanceAccountCode> wrapper = new QueryWrapper<>();
        wrapper.eq("id", ids);
        FinanceAccountCode financeAccountCode = financeAccountCodeService.getOne(wrapper);
        String accountCode = financeAccountCode.getAccountCode();
        String disabledCode = "D";
        if (disabledCode.equals(financeAccountCode.getDisabledCode())) {
            return AjaxResult.error("当前禁用状态禁止删除");
        }
        QueryWrapper<FinanceAccountTree> wrapperAccountTree = new QueryWrapper<>();
        wrapperAccountTree.eq("account_code", accountCode);
        FinanceAccountTree financeAccountTree = financeAccountTreeService.getOne(wrapperAccountTree);
        String id = financeAccountTree.getId();
        QueryWrapper<FinanceAccountTree> wrapperAccountTreeParentId = new QueryWrapper<>();
        wrapperAccountTreeParentId.eq("parent_id", id);
        List<FinanceAccountTree> list = financeAccountTreeService.list(wrapperAccountTreeParentId);
        if (list.size() > 0) {
            return AjaxResult.error("当前科目存在自己以外的下级科目！,删除失败");
        }
        financeAccountTreeService.removeById(id);
        boolean delete = financeAccountCodeService.removeById(ids);
        return AjaxResult.success(delete);
    }

    /**
     * 会计科目分页查询、根据条件查询信息
     */
    @Operation(summary = "分页查询、根据条件查询字典信息，名称")
    @GetMapping("/doQueryAll")
    @Log(title = "字典信息，名称查询", businessType = BusinessType.OTHER)
    public Object doQueryAll1(FinanceAccountCodeDTO financeAccountCodeDTO) {
        Long current = financeAccountCodeDTO.getPage();
        Long limit = financeAccountCodeDTO.getLimit();
        //创建page对象
        Page<FinanceAccountCode> pageData = new Page<>(current, limit);
        //构造条件
        QueryWrapper<FinanceAccountCode> wrapper = new QueryWrapper<>();
        //条件组合查询
        wrapper.likeRight(StringUtils.isNotEmpty(financeAccountCodeDTO.getAccountCodeX()), "account_code", financeAccountCodeDTO.getAccountCodeX());
        wrapper.orderByDesc("create_time");
        //实现分页查询
        financeAccountCodeService.page(pageData, wrapper);
        //总记录数
        long total = pageData.getTotal();
        //数据list集合
        List<FinanceAccountCode> records = pageData.getRecords();
        Map<String, Object> dataMap = new HashMap<>(5);
        dataMap.put("total", total);
        dataMap.put("list", records);
        if (records.size() == 0) {
            return AjaxResult.error("查无资料");
        } else {
            return AjaxResult.success("查询成功！", dataMap);
        }
    }

    /**
     * 根据ID查询会计科目数据
     */
    @Operation(summary = "根据ID查询会计科目数据")
    @GetMapping("/doQueryOne/{id}")
    @Log(title = "会计科目数据查询", businessType = BusinessType.OTHER)
    public Object doQueryOne(@PathVariable String id) {
        FinanceAccountCode financeAccountCode = financeAccountCodeService.getById(id);
        return AjaxResult.success(financeAccountCode);
    }

    /**
     * 获取会计科目树状资料结构资料
     */
    @Operation(summary = "获取会计科目树结构资料")
    @GetMapping("/generateTree")
    @Log(title = "会计科目树结构资料查询", businessType = BusinessType.OTHER)
    public Object generateTree(){
        List<FinanceAccountingTreeDTO> list = financeAccountTreeService.getEquipmentTypeTreeList();
        return AjaxResult.success(list);
    }
    /**
     * 获取会计科目树状资料结构资料
     */
    @Operation(summary = "获取会计科目树结构资料")
    @GetMapping("/generateAllTree")
    @Log(title = "会计科目树结构资料查询", businessType = BusinessType.OTHER)
    public Object generateAllTree(){
        List<FinanceAccountingTreeDTO> list = financeAccountTreeService.getEquipmentTypeAllTreeList();
        return AjaxResult.success(list);
    }

}
