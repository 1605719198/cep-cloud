package com.jlkj.equip.MG.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.equip.MG.dto.EquipmentDomainTreeDTO;
import com.jlkj.equip.MG.domain.EquipmentDomainTree;
import com.jlkj.equip.MG.service.IEquipmentDomainTreeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 设备域维护 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-06-09
 */
@RestController
@RequestMapping("/equipment/domain/tree")
public class EquipmentDomainTreeController {
    private final IEquipmentDomainTreeService equipmentDomainTreeService;

    public EquipmentDomainTreeController(IEquipmentDomainTreeService equipmentDomainTreeService) {
        this.equipmentDomainTreeService = equipmentDomainTreeService;
    }

    /**
     * 获取设备域树结构资料
     */
    @Operation(summary = "获取设备域树结构资料", description = "获取设备域树结构资料")
    @GetMapping("/getDomainTreeList")
    @Log(title = "设备域查询", businessType = BusinessType.OTHER)
    public Object getDomainTreeList(){
        List<EquipmentDomainTreeDTO> list = equipmentDomainTreeService.getEquipmentDomainTreeList();
        return AjaxResult.success(list);
    }
    /**
     * 新增树节点
     */
    @Operation(summary = "新增树节点", description = "新增树节点")
    @PostMapping("/addTreeNode")
    @Log(title = "设备域新增", businessType = BusinessType.INSERT)
    public Object addTreeNode(@RequestBody EquipmentDomainTree equipmentDomainTree){
        QueryWrapper<EquipmentDomainTree> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotNull(equipmentDomainTree.getDomainNo()),"domain_no",equipmentDomainTree.getDomainNo())
                .or().eq(StringUtils.isNotNull(equipmentDomainTree.getDomainName()),"domain_name",equipmentDomainTree.getDomainName());
        List<EquipmentDomainTree> list = equipmentDomainTreeService.list(wrapper);
        if (list.size() > 0) {
            return AjaxResult.error("域编码或域名称已存在，请重新输入！");
        }
        boolean save = equipmentDomainTreeService.save(equipmentDomainTree);
        return AjaxResult.success(save);
    }
    /**
     * 修改树节点
     */
    @Operation(summary = "修改树节点", description = "修改树节点")
    @PutMapping("/updateTreeNode")
    @Log(title = "设备域修改", businessType = BusinessType.UPDATE)
    public Object updateTreeNode(@RequestBody EquipmentDomainTree equipmentDomainTree){
        QueryWrapper<EquipmentDomainTree> wrapper = new QueryWrapper<>();
        wrapper.ne(StringUtils.isNotNull(equipmentDomainTree.getId()),"id",equipmentDomainTree.getId());
        wrapper.and( wrapper1 -> wrapper1.eq(StringUtils.isNotNull(equipmentDomainTree.getDomainNo()),"domain_no",equipmentDomainTree.getDomainNo())
                .or().eq(StringUtils.isNotNull(equipmentDomainTree.getDomainName()),"domain_name",equipmentDomainTree.getDomainName()));
        List<EquipmentDomainTree> list = equipmentDomainTreeService.list(wrapper);
        if (list.size() > 0) {
            return AjaxResult.error("域编码或域名称已存在，请重新输入！");
        }
        boolean update = equipmentDomainTreeService.updateById(equipmentDomainTree);
        return AjaxResult.success(update);
    }
    /**
     * 删除树节点操作
     */
    @Operation(summary = "删除树节点操作", description = "删除树节点操作")
    @DeleteMapping("/deleteTreeNode/{ids}")
    @Log(title = "设备域删除", businessType = BusinessType.DELETE)
    public Object deleteTreeNode(@PathVariable String[] ids) {
        List<String> idList = Arrays.asList(ids);
        boolean delete = equipmentDomainTreeService.removeBatchByIds(idList);
        return AjaxResult.success(delete);
    }

    /**
     * 根据ID查询数据
     */
    @Operation(summary = "根据ID查询数据", description = "根据ID查询数据")
    @GetMapping("/getDomainById")
    @Log(title = "设备域根据ID查询数据", businessType = BusinessType.OTHER)
    public Object getDomainById(@RequestParam String id) {
        EquipmentDomainTree domainTree = equipmentDomainTreeService.getById(id);
        return AjaxResult.success(domainTree);
    }
}
