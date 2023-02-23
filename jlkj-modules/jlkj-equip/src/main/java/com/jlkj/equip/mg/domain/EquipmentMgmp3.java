package com.jlkj.equip.mg.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 三级机运转讯息纪录
 * </p>
 *
 * @author xin
 * @since 2022-05-16
 */
@TableName("equip_mg_mgmp3")
public class EquipmentMgmp3 implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统码
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 三级机编号
     */
    private String mgmp3No;

    /**
     * 三级机名称
     */
    private String mgmp3Name;

    /**
     * 产线编号
     */
    private String mgma1No;

    /**
     * 接收日期
     */
    private String receiveDate;

    /**
     * 接收时间
     */
    private String receiveTime;

    /**
     * 格式代号
     */
    private String mgmp3Type;

    /**
     * 纪录状态
     */
    private String mgmp3Stus;

    /**
     * 处理状态
     */
    private String processStus;

    /**
     * 建立单位
     */
    private String createDept;

    /**
     * 建立人员
     */
    private String createEmp;

    /**
     * 建立日期
     */
    private String createDate;

    /**
     * 建立时间
     */
    private String createTimes;

    /**
     * 修改人员
     */
    private String modifyEmp;

    /**
     * 修改日期
     */
    private String modifyDate;

    /**
     * 修改时间
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

    /**
     * 停机发生时间（YYYY-MM-dd HH:mm:ss）
     */
    private String mgmp3Time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getMgmp3No() {
        return mgmp3No;
    }

    public void setMgmp3No(String mgmp3No) {
        this.mgmp3No = mgmp3No;
    }
    public String getMgmp3Name() {
        return mgmp3Name;
    }

    public void setMgmp3Name(String mgmp3Name) {
        this.mgmp3Name = mgmp3Name;
    }
    public String getMgma1No() {
        return mgma1No;
    }

    public void setMgma1No(String mgma1No) {
        this.mgma1No = mgma1No;
    }
    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }
    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }
    public String getMgmp3Type() {
        return mgmp3Type;
    }

    public void setMgmp3Type(String mgmp3Type) {
        this.mgmp3Type = mgmp3Type;
    }
    public String getMgmp3Stus() {
        return mgmp3Stus;
    }

    public void setMgmp3Stus(String mgmp3Stus) {
        this.mgmp3Stus = mgmp3Stus;
    }
    public String getProcessStus() {
        return processStus;
    }

    public void setProcessStus(String processStus) {
        this.processStus = processStus;
    }
    public String getCreateDept() {
        return createDept;
    }

    public void setCreateDept(String createDept) {
        this.createDept = createDept;
    }
    public String getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(String createEmp) {
        this.createEmp = createEmp;
    }
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCreateTimes() {
        return createTimes;
    }

    public void setCreateTimes(String createTimes) {
        this.createTimes = createTimes;
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

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getMgmp3Time() {
        return mgmp3Time;
    }

    public void setMgmp3Time(String mgmp3Time) {
        this.mgmp3Time = mgmp3Time;
    }

    @Override
    public String toString() {
        return "EquipmentMgmp3{" +
                "id='" + id + '\'' +
                ", mgmp3No='" + mgmp3No + '\'' +
                ", mgmp3Name='" + mgmp3Name + '\'' +
                ", mgma1No='" + mgma1No + '\'' +
                ", receiveDate='" + receiveDate + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", mgmp3Type='" + mgmp3Type + '\'' +
                ", mgmp3Stus='" + mgmp3Stus + '\'' +
                ", processStus='" + processStus + '\'' +
                ", createDept='" + createDept + '\'' +
                ", createEmp='" + createEmp + '\'' +
                ", createDate='" + createDate + '\'' +
                ", createTimes='" + createTimes + '\'' +
                ", modifyEmp='" + modifyEmp + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUserName='" + updateUserName + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", delFlag=" + delFlag +
                ", mgmp3Time='" + mgmp3Time + '\'' +
                '}';
    }
}
