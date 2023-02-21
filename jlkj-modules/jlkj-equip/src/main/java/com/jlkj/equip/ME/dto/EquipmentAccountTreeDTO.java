package com.jlkj.equip.ME.dto;

import java.util.List;

/**
 * @author xin
 */
public class EquipmentAccountTreeDTO {

    /**
     * 设备ID
     */
    private String id;
    /**
     * 上级设备ID upper_level
     */
    private String parentId;
    /**
     * 设备名称
     */
    private String label;
    /**
     * 用来封装子节点数据
     */
    List<EquipmentAccountTreeDTO> children;

    public EquipmentAccountTreeDTO(EquipmentAccountTreeDTO equipmentAccountTreeDTO) {
        this.id = equipmentAccountTreeDTO.getId();
        this.parentId = equipmentAccountTreeDTO.getParentId();
        this.label = equipmentAccountTreeDTO.getLabel();
        this.children = equipmentAccountTreeDTO.getChildren();
    }

    public EquipmentAccountTreeDTO() {
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<EquipmentAccountTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<EquipmentAccountTreeDTO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "EquipmentAccountTreeDTO{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
