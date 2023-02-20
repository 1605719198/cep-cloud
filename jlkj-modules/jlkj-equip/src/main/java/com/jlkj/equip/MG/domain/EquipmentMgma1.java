package com.jlkj.equip.MG.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 产线基本数据
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@TableName("equip_mg_mgma1")
@Data
public class EquipmentMgma1 implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统码
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 产线编号
     */
    private String mgma1no;

    /**
     * 产线名称
     */
    private String mgma1name;

    /**
     * 产线性质
     */
    private String mgma1type;

    /**
     * 重要等级
     */
    private String mgma1grade;

    /**
     * 停机原因群码
     */
    private String groupcode;

    /**
     * 修改人员
     */
    private String modifyemp;

    /**
     * 修改日期
     */
    private String modifydate;

    /**
     * 修改时间
     */
    private String modifytime;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getMgma1no() {
        return mgma1no;
    }

    public void setMgma1no(String mgma1no) {
        this.mgma1no = mgma1no;
    }
    public String getMgma1name() {
        return mgma1name;
    }

    public void setMgma1name(String mgma1name) {
        this.mgma1name = mgma1name;
    }
    public String getMgma1type() {
        return mgma1type;
    }

    public void setMgma1type(String mgma1type) {
        this.mgma1type = mgma1type;
    }
    public String getMgma1grade() {
        return mgma1grade;
    }

    public void setMgma1grade(String mgma1grade) {
        this.mgma1grade = mgma1grade;
    }
    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }
    public String getModifyemp() {
        return modifyemp;
    }

    public void setModifyemp(String modifyemp) {
        this.modifyemp = modifyemp;
    }
    public String getModifydate() {
        return modifydate;
    }

    public void setModifydate(String modifydate) {
        this.modifydate = modifydate;
    }
    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
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
        return "EquipmentMgma1{" +
            "id=" + id +
            ", mgma1no=" + mgma1no +
            ", mgma1name=" + mgma1name +
            ", mgma1type=" + mgma1type +
            ", mgma1grade=" + mgma1grade +
            ", groupcode=" + groupcode +
            ", modifyemp=" + modifyemp +
            ", modifydate=" + modifydate +
            ", modifytime=" + modifytime +
            ", createUser=" + createUser +
            ", createTime=" + createTime +
            ", updateUser=" + updateUser +
            ", updateTime=" + updateTime +
            ", updateUserName=" + updateUserName +
            ", createUserName=" + createUserName +
            ", delFlag=" + delFlag +
        "}";
    }
}
