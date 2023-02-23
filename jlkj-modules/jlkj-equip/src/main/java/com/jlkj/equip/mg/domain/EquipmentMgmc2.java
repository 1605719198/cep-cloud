package com.jlkj.equip.mg.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 停机原因数据
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@TableName("equip_mg_mgmc2")
public class EquipmentMgmc2 implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统码
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 停机原因编号
     */
    private String mgmc2no;

    /**
     * 停机原因名称
     */
    private String mgmc2name;

    /**
     * 停机原因群码
     */
    private String groupcode;

    /**
     * 停机类型编号
     */
    private String mgmc1no;

    /**
     * 备注
     */
    private String memo;

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
    public String getMgmc2no() {
        return mgmc2no;
    }

    public void setMgmc2no(String mgmc2no) {
        this.mgmc2no = mgmc2no;
    }
    public String getMgmc2name() {
        return mgmc2name;
    }

    public void setMgmc2name(String mgmc2name) {
        this.mgmc2name = mgmc2name;
    }
    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }
    public String getMgmc1no() {
        return mgmc1no;
    }

    public void setMgmc1no(String mgmc1no) {
        this.mgmc1no = mgmc1no;
    }
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
        return "EquipmentMgmc2{" +
            "id=" + id +
            ", mgmc2no=" + mgmc2no +
            ", mgmc2name=" + mgmc2name +
            ", groupcode=" + groupcode +
            ", mgmc1no=" + mgmc1no +
            ", memo=" + memo +
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
