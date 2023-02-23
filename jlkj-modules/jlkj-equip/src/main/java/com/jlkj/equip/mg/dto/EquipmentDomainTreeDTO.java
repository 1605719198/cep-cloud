package com.jlkj.equip.mg.dto;

import java.util.List;

/**
 * @author xin
 */
public class EquipmentDomainTreeDTO {
    /**
     * 系统码uuid
     */
    private String id;

    /**
     * 上级域id（父id）
     */
    private String parentId;

    /**
     * 域编码
     */
    private String domainNo;

    /**
     * 域名称
     */
    private String domainName;

    /**
     * 域描述
     */
    private String domainDescription;
    /**
     * 用来封装子节点数据
     */
    List<EquipmentDomainTreeDTO> children;

    public EquipmentDomainTreeDTO(EquipmentDomainTreeDTO equipmentDomainTreeDTO) {
        this.id = equipmentDomainTreeDTO.getId();
        this.parentId = equipmentDomainTreeDTO.getParentId();
        this.domainNo = equipmentDomainTreeDTO.getDomainNo();
        this.domainName = equipmentDomainTreeDTO.getDomainName();
        this.domainDescription = equipmentDomainTreeDTO.getDomainDescription();
        this.children = equipmentDomainTreeDTO.getChildren();
    }

    public EquipmentDomainTreeDTO() {
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

    public String getDomainNo() {
        return domainNo;
    }

    public void setDomainNo(String domainNo) {
        this.domainNo = domainNo;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainDescription() {
        return domainDescription;
    }

    public void setDomainDescription(String domainDescription) {
        this.domainDescription = domainDescription;
    }

    public List<EquipmentDomainTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<EquipmentDomainTreeDTO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "EquipmentDomainTreeDTO{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", domainNo='" + domainNo + '\'' +
                ", domainName='" + domainName + '\'' +
                ", domainDescription='" + domainDescription + '\'' +
                ", children=" + children +
                '}';
    }
}
