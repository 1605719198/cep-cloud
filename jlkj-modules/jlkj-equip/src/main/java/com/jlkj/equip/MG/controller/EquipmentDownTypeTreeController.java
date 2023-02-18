package com.jlkj.equip.MG.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.equip.MG.dto.EquipmentAddTreeNodeDTO;
import com.jlkj.equip.MG.dto.EquipmentDownTypeTreeDTO;
import com.jlkj.equip.MG.entity.EquipmentDownTypeTree;
import com.jlkj.equip.MG.service.IEquipmentDownTypeTreeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 设备停机类型维护  前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@RestController
@RequestMapping("/equipment/down/type/tree")
public class EquipmentDownTypeTreeController {

    @Autowired
    private IEquipmentDownTypeTreeService equipmentDownTypeTreeService;
    /**
     * 获取停机类型树结构资料
     */
    @Operation(summary = "获取停机类型树结构资料", description = "获取停机类型树结构资料")
    @GetMapping("/getDownTypeTreeList")
    @Log(title = "停机类型查询", businessType = BusinessType.OTHER)
    public AjaxResult getDownTypeTreeList(){
        List<EquipmentDownTypeTreeDTO> list = equipmentDownTypeTreeService.getEquipmentTypeTreeList();
        return AjaxResult.success(list);
    }
    /**
     * 新增树节点
     */
    @Operation(summary = "新增树节点", description = "新增树节点")
    @PostMapping("/addTreeNode")
    @Log(title = "停机类型新增", businessType = BusinessType.INSERT)
    public Object addTreeNode(@RequestBody EquipmentAddTreeNodeDTO equipmentAddTreeNodeDTO){
        QueryWrapper<EquipmentDownTypeTree> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotNull(equipmentAddTreeNodeDTO.getTreeName()),"equipment_down_type_name",equipmentAddTreeNodeDTO.getTreeName())
                .or().eq(StringUtils.isNotNull(equipmentAddTreeNodeDTO.getTreeCode()),"equipment_down_type_code",equipmentAddTreeNodeDTO.getTreeCode());
        List<EquipmentDownTypeTree> list = equipmentDownTypeTreeService.list(wrapper);
        if (list.size() > 0) {
            return AjaxResult.error("停机细类型名称或停机细类型代码已存在，请重新输入！");
        }
        EquipmentDownTypeTree typeTree = new EquipmentDownTypeTree();
        typeTree.setEquipmentDownTypeCode(equipmentAddTreeNodeDTO.getTreeCode());
        typeTree.setEquipmentDownTypeLevel(equipmentAddTreeNodeDTO.getLevel());
        typeTree.setEquipmentDownTypeName(equipmentAddTreeNodeDTO.getTreeName());
        typeTree.setEquipmentDownTypeParentCode(equipmentAddTreeNodeDTO.getParentId());
        typeTree.setCreateUser(equipmentAddTreeNodeDTO.getCreateUser());
        typeTree.setCreateUserName(equipmentAddTreeNodeDTO.getCreateUserName());
        boolean save = equipmentDownTypeTreeService.save(typeTree);
        return AjaxResult.success(save);
    }
    /**
     * 修改树节点操作
     */
    @Operation(summary = "修改树节点操作", description = "修改树节点操作")
    @PutMapping("/updateTreeNode")
    @Log(title = "停机类型修改", businessType = BusinessType.UPDATE)
    public Object updateTreeNode(@RequestBody EquipmentAddTreeNodeDTO equipmentAddTreeNodeDTO){
        QueryWrapper<EquipmentDownTypeTree> wrapper = new QueryWrapper<>();
        wrapper.ne(StringUtils.isNotNull(equipmentAddTreeNodeDTO.getId()),"id",equipmentAddTreeNodeDTO.getId());
        wrapper.and(wrapper1 ->
                wrapper1.eq(StringUtils.isNotNull(equipmentAddTreeNodeDTO.getTreeName()),"equipment_down_type_name",equipmentAddTreeNodeDTO.getTreeName())
                .or()
                .eq(StringUtils.isNotNull(equipmentAddTreeNodeDTO.getTreeCode()),"equipment_down_type_code",equipmentAddTreeNodeDTO.getTreeCode()));

        List<EquipmentDownTypeTree> list = equipmentDownTypeTreeService.list(wrapper);
        if (list.size() > 0) {
            return AjaxResult.error("停机细类型名称或停机细类型代码已存在，请重新输入！");
        }
        EquipmentDownTypeTree typeTree = new EquipmentDownTypeTree();
        typeTree.setId(equipmentAddTreeNodeDTO.getId());
        typeTree.setEquipmentDownTypeCode(equipmentAddTreeNodeDTO.getTreeCode());
        typeTree.setEquipmentDownTypeLevel(equipmentAddTreeNodeDTO.getLevel());
        typeTree.setEquipmentDownTypeName(equipmentAddTreeNodeDTO.getTreeName());
        typeTree.setEquipmentDownTypeParentCode(equipmentAddTreeNodeDTO.getParentId());
        typeTree.setUpdateUser(equipmentAddTreeNodeDTO.getUpdateUser());
        typeTree.setUpdateUserName(equipmentAddTreeNodeDTO.getUpdateUserName());
        boolean update = equipmentDownTypeTreeService.updateById(typeTree);
        return AjaxResult.success(update);
    }
    /**
     * 删除树节点操作
     */
    @Operation(summary = "删除树节点操作", description = "删除树节点操作")
    @DeleteMapping("/deleteTreeNode/{ids}")
    @Log(title = "停机类型删除", businessType = BusinessType.DELETE)
    public Object deleteTreeNode(@PathVariable String[] ids) {
        List<String> idList = Arrays.asList(ids);
        boolean delete = equipmentDownTypeTreeService.removeBatchByIds(idList);
        return AjaxResult.success(delete);
    }
}
