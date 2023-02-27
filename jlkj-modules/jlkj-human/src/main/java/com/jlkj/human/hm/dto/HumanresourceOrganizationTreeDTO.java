package com.jlkj.human.hm.dto;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 机构数据
 * </p>
 *
 * @author xin
 * @since 2022-05-05
 */
@TableName("human_hm_organization")
public class HumanresourceOrganizationTreeDTO {


    /**
     * 主键
     */
    private String id;
    /**
     * 机构名称
     */
    private String label;
    /**
     * 上级机构ID
     */
    private String parentId;
    /**
     * 上级机构名称
     */
    private String parentName;
    /**
     * 用来区分部门和人员：dept[部门] user[人员]
     */
    private String flag;
    /**
     * 人员工号
     */
    private String jobNumber;
    /**
     * icon图标，机构和人员
     */
    private String icon;
    /**
     * 用来封装子节点数据
     */
    private List<HumanresourceOrganizationTreeDTO> children;

    public HumanresourceOrganizationTreeDTO() {
    }

    public HumanresourceOrganizationTreeDTO(String id, String label, String parentId, String parentName, String flag, String jobNumber, String icon, List<HumanresourceOrganizationTreeDTO> children) {
        this.id = id;
        this.label = label;
        this.parentId = parentId;
        this.parentName = parentName;
        this.flag = flag;
        this.jobNumber = jobNumber;
        this.icon = icon;
        this.children = children;
    }

    public HumanresourceOrganizationTreeDTO(HumanresourceOrganizationTreeDTO humanresourceOrganization) {
        this.id = humanresourceOrganization.getId();
        this.label = humanresourceOrganization.getLabel();
        this.parentId = humanresourceOrganization.getParentId();
        this.parentName = humanresourceOrganization.getParentName();
        this.flag = humanresourceOrganization.getFlag();
        this.jobNumber = humanresourceOrganization.getJobNumber();
        this.icon = humanresourceOrganization.getIcon();
        this.children = humanresourceOrganization.getChildren().stream().map(HumanresourceOrganizationTreeDTO::new).collect(Collectors.toList());
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<HumanresourceOrganizationTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<HumanresourceOrganizationTreeDTO> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    @Override
    public String toString() {
        return "HumanresourceOrganizationTreeDTO{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", flag='" + flag + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", icon='" + icon + '\'' +
                ", children=" + children +
                '}';
    }
}
