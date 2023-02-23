package com.jlkj.equip.mg.dto;
/**
 * @author xin
 */
public class EquipmentAddTreeNodeDTO {

    /**
     * id
     */
    private String id;
    /**
     * 父节点ID
     */
    private String parentId;
    /**
     * 停机细类型代码
     */
    private String treeCode;
    /**
     * 停机细类型名称
     */
    private String treeName;
    /**
     * 停机细类型层级
     */
    private String level;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 创建人姓名
     */
    private String createUserName;

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

    public String getTreeCode() {
        return treeCode;
    }

    public void setTreeCode(String treeCode) {
        this.treeCode = treeCode;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public String toString() {
        return "EquipmentAddTreeNodeDTO{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", treeCode='" + treeCode + '\'' +
                ", treeName='" + treeName + '\'' +
                ", level='" + level + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", createUserName='" + createUserName + '\'' +
                '}';
    }
}
