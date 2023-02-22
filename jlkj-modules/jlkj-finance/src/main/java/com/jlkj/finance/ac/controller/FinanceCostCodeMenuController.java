package com.jlkj.finance.ac.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.finance.ac.dto.FinanceCostAddNodeDTO;
import com.jlkj.finance.ac.dto.FinanceCostAddTreeNodeDTO;
import com.jlkj.finance.ac.domain.FinanceCostCode;
import com.jlkj.finance.ac.domain.FinanceCostTree;
import com.jlkj.finance.feign.system.SysDictDataFeignService;
import com.jlkj.finance.ac.service.FinanceCostCodeService;
import com.jlkj.finance.ac.service.FinanceCostTreeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/finance/cost/menu")
public class FinanceCostCodeMenuController {
    @Autowired
    private FinanceCostTreeService financeCostTreeService;
    @Autowired
    private FinanceCostCodeService financeCostCodeService;
    @Autowired
    private SysDictDataFeignService sysDictDataFeignService;

    /**
     * 新增树
     */
    @Log(title = "新增树", businessType = BusinessType.INSERT)
    @Operation(summary = "新增树")
    @PostMapping("/doAdd")
    public Object doAdd(@RequestBody FinanceCostAddTreeNodeDTO financeCostAddTreeNodeDTO) {
        FinanceCostTree financeCostTree = new FinanceCostTree();
        BeanUtils.copyProperties(financeCostAddTreeNodeDTO, financeCostTree);
        financeCostTree.setParentId(financeCostAddTreeNodeDTO.getId());
        QueryWrapper<FinanceCostTree> wrapper = new QueryWrapper<>();
        String levelCode = financeCostAddTreeNodeDTO.getUpperCodex();
        String parentId = financeCostTree.getParentId();
        wrapper.eq("level_code", levelCode)
                .eq("parent_id", parentId);
        List<FinanceCostTree> list = financeCostTreeService.list(wrapper);
        if (list.size() >= 1) {
            return AjaxResult.error("您输入的阶层编码、阶层名称系统中已存在，请重新输入！");
        }
        QueryWrapper<FinanceCostTree> wrapperName = new QueryWrapper<>();
        String upperName = financeCostAddTreeNodeDTO.getUpperName();
        wrapperName.eq("level_name", upperName);
        List<FinanceCostTree> listName = financeCostTreeService.list(wrapperName);

            if (listName.size()>0) {
                return AjaxResult.error("阶层名称不可重复，请重新输入！");
            }

        financeCostTree.setId("");
        financeCostTree.setUpperCode(financeCostAddTreeNodeDTO.getLevelCode());
        financeCostTree.setLevelName(financeCostAddTreeNodeDTO.getUpperName());
        financeCostTree.setNodeType(financeCostAddTreeNodeDTO.getNodeType());
        String compIdIdValue = "finance_compId";
        List<Map<String, Object>> compIdValue = sysDictDataFeignService.getMainCompIdValue(compIdIdValue);
        Map<String, Object> compIdMap = compIdValue.get(0);
        String compId = null;
        for (String key : compIdMap.keySet()) {
            compId = (String) compIdMap.get(key);
        }
        financeCostTree.setCompId(compId);
        financeCostTree.setLevelCode(financeCostAddTreeNodeDTO.getUpperCodex());
        financeCostTree.setCostCodeLevelCode(financeCostAddTreeNodeDTO.getCostCodeLevelCode());
        financeCostTree.setCreateUser(financeCostAddTreeNodeDTO.getCreateUser());

        boolean save = financeCostTreeService.save(financeCostTree);
        return AjaxResult.success(save);
    }

    /**
     * 修改树
     */
    @Log(title = "修改树", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改树")
    @PutMapping("/doEdit")
    public Object doEdit(@RequestBody FinanceCostAddTreeNodeDTO financeCostAddTreeNodeDTO) {
        FinanceCostTree financeCostTree = new FinanceCostTree();
        BeanUtils.copyProperties(financeCostAddTreeNodeDTO, financeCostTree);
        financeCostTree.setLevelName(financeCostAddTreeNodeDTO.getUpperName());
        LambdaQueryWrapper<FinanceCostTree> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinanceCostTree::getLevelName, financeCostTree.getLevelName());
        List<FinanceCostTree> list = financeCostTreeService.list(queryWrapper);
        for(FinanceCostTree financeCostTree1 :list){
            if (!financeCostTree1.getId().equals(financeCostAddTreeNodeDTO.getId())) {
                return AjaxResult.error("阶层名称不可重复，请重新输入！");
            }
        }
            boolean update = financeCostTreeService.updateById(financeCostTree);
            return AjaxResult.success(update);

    }

    /**
     * 删除树
     */
    @Log(title = "删除树", businessType = BusinessType.DELETE)
    @Operation(summary = "删除树")
    @DeleteMapping("/doDelete")
    public Object doDelete(@RequestParam String ids, String upperCodex) {
        FinanceCostTree financeCostTree = new FinanceCostTree();
        FinanceCostCode financeCostCode = new FinanceCostCode();
        financeCostTree.setParentId(ids);
        LambdaQueryWrapper<FinanceCostTree> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinanceCostTree::getParentId, financeCostTree.getParentId());
        List<FinanceCostTree> list = financeCostTreeService.list(queryWrapper);
        if (list.isEmpty()) {
            financeCostCode.setCostCode(upperCodex);
            QueryWrapper<FinanceCostCode> wrapper = new QueryWrapper<>();
            wrapper.likeRight("cost_code", upperCodex);
            List<FinanceCostCode> list1 = financeCostCodeService.list(wrapper);
            if (list1.size() >= 1) {
                return AjaxResult.error("该子节点存在成科目信息！");
            }
            boolean delete = financeCostTreeService.removeById(ids);
            return AjaxResult.success(delete);
        } else {
            return AjaxResult.error("存在子节点");
        }

    }

    /**
     * 成本科目索引分页查询、根据条件查询信息
     */
    @Log(title = "成本科目索引分页查询、根据条件查询信息", businessType = BusinessType.OTHER)
    @Operation(summary = "分页查询、根据条件查询字典信息，名称")
    @GetMapping("/queryMenuList")
    public Object queryMenuList(FinanceCostAddTreeNodeDTO financeCostAddTreeNodeDTO) {
        Long current = financeCostAddTreeNodeDTO.getPage();
        Long limit = financeCostAddTreeNodeDTO.getLimit();
        //创建page对象
        Page<FinanceCostTree> pageData = new Page<>(current, limit);
        //构造条件
        QueryWrapper<FinanceCostTree> wrapper = new QueryWrapper<>();
        //条件组合查询
        wrapper.eq(StringUtils.isNotEmpty(financeCostAddTreeNodeDTO.getLevelCode()), "level_code", financeCostAddTreeNodeDTO.getLevelCode());
        wrapper.orderByDesc("create_time");
        //实现分页查询
        financeCostTreeService.page(pageData, wrapper);
        //总记录数
        long total = pageData.getTotal();
        //数据list集合
        List<FinanceCostTree> records = pageData.getRecords();
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("total", total);
        dataMap.put("list", records);
        return AjaxResult.success(dataMap);
    }

    /**
     * 成本科目索引分页查询、根据条件查询信息
     */
    @Log(title = "成本科目索引分页查询、根据条件查询信息", businessType = BusinessType.OTHER)
    @Operation(summary = "分页查询、根据条件查询字典信息，名称")
    @GetMapping("/queryMenu")
    public Object queryMenu(FinanceCostAddNodeDTO financeCostAddNodeDTO) {
        Long current = financeCostAddNodeDTO.getPage();
        Long limit = financeCostAddNodeDTO.getLimit();
        //创建page对象
        Page<FinanceCostTree> pageData = new Page<>(current, limit);
        //构造条件
        QueryWrapper<FinanceCostTree> wrapper = new QueryWrapper<>();
        //条件组合查询
        wrapper.eq(StringUtils.isNotEmpty(financeCostAddNodeDTO.getParentId()), "parent_id", financeCostAddNodeDTO.getParentId());
        wrapper.orderByDesc("create_time");
        //实现分页查询
        financeCostTreeService.page(pageData, wrapper);
        //总记录数
        long total = pageData.getTotal();
        //数据list集合
        List<FinanceCostTree> records = pageData.getRecords();
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("total", total);
        dataMap.put("list", records);
        return AjaxResult.success(dataMap);

    }

    /**
     * 根据ID查询成本科目索引数据
     */
    @Log(title = "根据ID查询成本科目索引数据", businessType = BusinessType.OTHER)
    @Operation(summary = "根据ID查询成本科目索引数据")
    @GetMapping("/queryOne/{id}")
    public Object queryOne(@PathVariable String id) {
        LambdaQueryWrapper<FinanceCostTree> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(id), FinanceCostTree::getId, id);
        FinanceCostTree attr = financeCostTreeService.getOne(queryWrapper);
        FinanceCostAddNodeDTO costdto = new FinanceCostAddNodeDTO();
        BeanUtils.copyProperties(attr, costdto);
        costdto.setUpperCodex(attr.getCostCodeLevelCode());
        costdto.setUpperName(attr.getLevelName());
        costdto.setParentId(attr.getParentId());

        return AjaxResult.success(costdto);
    }
}
