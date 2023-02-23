package com.jlkj.equip.mg.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 设备停机类型维护
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@TableName("equip_mg_down_type_tree")
public class EquipmentDownTypeTree implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 停机类型名称
     */
    private String equipmentDownTypeName;

    /**
     * 停机类型代码
     */
    private String equipmentDownTypeCode;

    /**
     * 父类型代码
     */
    private String equipmentDownTypeParentCode;

    /**
     * 类型层级属性
     */
    private String equipmentDownTypeLevel;

    /**
     * 备注
     */
    private String equipmentDownTypeRemark;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;


    public EquipmentDownTypeTree() {
    }

    public EquipmentDownTypeTree(String id, String equipmentDownTypeName, String equipmentDownTypeCode, String equipmentDownTypeParentCode, String equipmentDownTypeLevel, String equipmentDownTypeRemark, String createUser, Date createTime, String updateUser, Date updateTime, String updateUserName, String createUserName, Integer delFlag, List<EquipmentDownTypeTree> children) {
        this.id = id;
        this.equipmentDownTypeName = equipmentDownTypeName;
        this.equipmentDownTypeCode = equipmentDownTypeCode;
        this.equipmentDownTypeParentCode = equipmentDownTypeParentCode;
        this.equipmentDownTypeLevel = equipmentDownTypeLevel;
        this.equipmentDownTypeRemark = equipmentDownTypeRemark;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.updateUserName = updateUserName;
        this.createUserName = createUserName;
        this.delFlag = delFlag;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipmentDownTypeName() {
        return equipmentDownTypeName;
    }

    public void setEquipmentDownTypeName(String equipmentDownTypeName) {
        this.equipmentDownTypeName = equipmentDownTypeName;
    }

    public String getEquipmentDownTypeCode() {
        return equipmentDownTypeCode;
    }

    public void setEquipmentDownTypeCode(String equipmentDownTypeCode) {
        this.equipmentDownTypeCode = equipmentDownTypeCode;
    }

    public String getEquipmentDownTypeParentCode() {
        return equipmentDownTypeParentCode;
    }

    public void setEquipmentDownTypeParentCode(String equipmentDownTypeParentCode) {
        this.equipmentDownTypeParentCode = equipmentDownTypeParentCode;
    }

    public String getEquipmentDownTypeLevel() {
        return equipmentDownTypeLevel;
    }

    public void setEquipmentDownTypeLevel(String equipmentDownTypeLevel) {
        this.equipmentDownTypeLevel = equipmentDownTypeLevel;
    }

    public String getEquipmentDownTypeRemark() {
        return equipmentDownTypeRemark;
    }

    public void setEquipmentDownTypeRemark(String equipmentDownTypeRemark) {
        this.equipmentDownTypeRemark = equipmentDownTypeRemark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "EquipmentDownTypeTree{" +
                "id='" + id + '\'' +
                ", equipmentDownTypeName='" + equipmentDownTypeName + '\'' +
                ", equipmentDownTypeCode='" + equipmentDownTypeCode + '\'' +
                ", equipmentDownTypeParentCode='" + equipmentDownTypeParentCode + '\'' +
                ", equipmentDownTypeLevel='" + equipmentDownTypeLevel + '\'' +
                ", equipmentDownTypeRemark='" + equipmentDownTypeRemark + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUserName='" + updateUserName + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }
}
