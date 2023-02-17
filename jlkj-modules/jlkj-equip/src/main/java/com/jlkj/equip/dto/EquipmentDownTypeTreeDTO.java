package com.jlkj.equip.dto;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * <p>
 * 设备停机类型维护
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@TableName("t_equipment_down_type_tree")
public class EquipmentDownTypeTreeDTO {

    /**
     * 停机类型ID
     */
    private String id;

    /**
     * 停机类型名称
     */
    private String label;

    /**
     * 父类型代码
     */
    private String parentId;

    /**
     * 类型层级属性
     */
    private String level;
    /**
     * 停机类型代码
     */
    private String code;
    /**
     * 用来封装子节点数据
     */
    List<EquipmentDownTypeTreeDTO> children;

    public EquipmentDownTypeTreeDTO() {
    }

    public EquipmentDownTypeTreeDTO(String label, String id, String parentId, String level, String code, List<EquipmentDownTypeTreeDTO> children) {
        this.label = label;
        this.id = id;
        this.parentId = parentId;
        this.level = level;
        this.code = code;
        this.children = children;
    }

    public EquipmentDownTypeTreeDTO(EquipmentDownTypeTreeDTO equipmentDownTypeTreeDTO) {
        this.id = equipmentDownTypeTreeDTO.getId();
        this.parentId = equipmentDownTypeTreeDTO.getParentId();
        this.level = equipmentDownTypeTreeDTO.getLevel();
        this.label = equipmentDownTypeTreeDTO.getLabel();
        this.code = equipmentDownTypeTreeDTO.getCode();
        this.children = equipmentDownTypeTreeDTO.getChildren();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<EquipmentDownTypeTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<EquipmentDownTypeTreeDTO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "EquipmentDownTypeTreeDTO{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", parentId='" + parentId + '\'' +
                ", level='" + level + '\'' +
                ", code='" + code + '\'' +
                ", children=" + children +
                '}';
    }
}
