package com.jlkj.equip.mg.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 停机责任分摊纪录
 * </p>
 *
 * @author xin
 * @since 2022-05-23
 */
@TableName("equip_mg_mgmsa")
public class EquipmentMgmsa implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  系统码uuid
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     *  停机责任分摊编号
     */
    private String mgmsaNo;

    /**
     *  停机责任分摊名称
     */
    private String mgmsaName;

    /**
     *  停机纪录编号
     */
    private String mgms1No;

    /**
     *  产线编号
     */
    private String mgma1No;

    /**
     *  停机类型编号
     */
    private String mgmc1No;
    /**
     *  停机类型名称
     */
    private String mgmc1Name;

    /**
     *  停机原因编号
     */
    private String mgmc2No;

    /**
     *  停机原因名称
     */
    private String mgmc2Name;

    /**
     *  停机责任时间
     */
    private BigDecimal shareTime;

    /**
     *  停机责任百分比
     */
    private BigDecimal sharePercent;

    /**
     *  修改人员
     */
    private String modifyEmp;

    /**
     *  修改日期
     */
    private String modifyDate;

    /**
     *  修改时间
     */
    private String modifyTime;

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
    public String getMgmsaNo() {
        return mgmsaNo;
    }

    public void setMgmsaNo(String mgmsaNo) {
        this.mgmsaNo = mgmsaNo;
    }
    public String getMgmsaName() {
        return mgmsaName;
    }

    public void setMgmsaName(String mgmsaName) {
        this.mgmsaName = mgmsaName;
    }
    public String getMgms1No() {
        return mgms1No;
    }

    public void setMgms1No(String mgms1No) {
        this.mgms1No = mgms1No;
    }
    public String getMgma1No() {
        return mgma1No;
    }

    public void setMgma1No(String mgma1No) {
        this.mgma1No = mgma1No;
    }
    public String getMgmc1No() {
        return mgmc1No;
    }

    public void setMgmc1No(String mgmc1No) {
        this.mgmc1No = mgmc1No;
    }
    public String getMgmc2No() {
        return mgmc2No;
    }

    public void setMgmc2No(String mgmc2No) {
        this.mgmc2No = mgmc2No;
    }
    public BigDecimal getShareTime() {
        return shareTime;
    }

    public void setShareTime(BigDecimal shareTime) {
        this.shareTime = shareTime;
    }
    public BigDecimal getSharePercent() {
        return sharePercent;
    }

    public void setSharePercent(BigDecimal sharePercent) {
        this.sharePercent = sharePercent;
    }
    public String getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(String modifyEmp) {
        this.modifyEmp = modifyEmp;
    }
    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
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

    public String getMgmc1Name() { return mgmc1Name;}
    public void setMgmc1Name(String mgmc1Name) { this.mgmc1Name = mgmc1Name; }

    public String getMgmc2Name() { return mgmc2Name; }
    public void setMgmc2Name(String mgmc2Name) { this.mgmc2Name = mgmc2Name; }

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
        return "EquipmentMgmsa{" +
            "id=" + id +
            ", mgmsaNo=" + mgmsaNo +
            ", mgmsaName=" + mgmsaName +
            ", mgms1No=" + mgms1No +
            ", mgma1No=" + mgma1No +
            ", mgmc1No=" + mgmc1No +
                ", mgmc1Name=" + mgmc1Name +
            ", mgmc2No=" + mgmc2No + "," +
                " mgmc2Name=" + mgmc2Name +
            ", shareTime=" + shareTime +
            ", sharePercent=" + sharePercent +
            ", modifyEmp=" + modifyEmp +
            ", modifyDate=" + modifyDate +
            ", modifyTime=" + modifyTime +
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
