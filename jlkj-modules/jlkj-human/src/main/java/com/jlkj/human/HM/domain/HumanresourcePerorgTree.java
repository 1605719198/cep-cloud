package com.jlkj.human.HM.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 人员机构树型结构存储表
 * </p>
 *
 * @author xin
 * @since 2022-05-17
 */
@TableName("human_hm_perorg_tree")
public class HumanresourcePerorgTree implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 名称(机构名称、人员名称)
     */
    private String label;

    /**
     * 上级机构、部门ID
     */
    private String parentId;

    /**
     * 上级机构、部门名称
     */
    private String parentName;

    /**
     * dept部门、user人员
     */
    private String flag;

    /**
     * 人员工号
     */
    private String jobNumber;

    /**
     * 人员、部门图标
     */
    private String icon;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 岗位id
     */
    private String postId;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;
    /**
     * 用来封装子节点数据
     */
    @TableField(exist = false)
    private List<HumanresourcePerorgTree> children = new ArrayList<>();

    public HumanresourcePerorgTree() {
    }

    public HumanresourcePerorgTree(String id, String label, String parentId, String parentName, String flag, String jobNumber, String icon, String postName, String postId, Integer delFlag, List<HumanresourcePerorgTree> children) {
        this.id = id;
        this.label = label;
        this.parentId = parentId;
        this.parentName = parentName;
        this.flag = flag;
        this.jobNumber = jobNumber;
        this.icon = icon;
        this.postName = postName;
        this.postId = postId;
        this.delFlag = delFlag;
        this.children = children;
    }

    public HumanresourcePerorgTree(HumanresourcePerorgTree humanresourcePerorgTree) {
        this.id = humanresourcePerorgTree.getId();
        this.label = humanresourcePerorgTree.getLabel();
        this.parentId = humanresourcePerorgTree.getParentId();
        this.parentName = humanresourcePerorgTree.getParentName();
        this.flag = humanresourcePerorgTree.getFlag();
        this.jobNumber = humanresourcePerorgTree.getJobNumber();
        this.icon = humanresourcePerorgTree.getIcon();
        this.children = humanresourcePerorgTree.getChildren().stream().map(HumanresourcePerorgTree::new).collect(Collectors.toList());
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
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public List<HumanresourcePerorgTree> getChildren() {
        return children;
    }

    public void setChildren(List<HumanresourcePerorgTree> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "HumanresourcePerorgTree{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", flag='" + flag + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", icon='" + icon + '\'' +
                ", postName='" + postName + '\'' +
                ", postId='" + postId + '\'' +
                ", delFlag=" + delFlag +
                ", children=" + children +
                '}';
    }
}
