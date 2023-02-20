package com.jlkj.equip.MG.dto;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 停机纪录
 * </p>
 *
 * @author xin
 * @since 2022-05-16
 */
public class EquipmentMgms1DTO {


    /**
     * 系统码UUID
     */
    private String id;

    /**
     * 停机纪录编号
     */
    private String mgms1No;

    /**
     * 停机纪录名称
     */
    private String mgms1Name;

    /**
     * 产线编号
     */
    private String mgma1No;
    /**
     * 产线名称
     */
    private String mgma1Name;

    /**
     * 停机纪录状态
     */
    private String mgms1Stus;

    /**
     * 停机主类型编号
     */
    private String mgmc1No;
    /**
     * 停机主类型名称
     */
    private String mgmc1Name;

    /**
     * 停机细类型编号
     */
    private String mgmc2No;

    /**
     * 记录来源
     */
    private String mgms1Type;

    /**
     * 停机开始日期
     */
    private String beginTime;

    /**
     * 停机结束日期
     */
    private String endTime;

    /**
     * 停机总时间
     */
    private BigDecimal totalTime;

    /**
     * 责任单位
     */
    private String respDeptNo;

    /**
     * 责任单位名称
     */
    private String respDeptName;

    /**
     * 责任设备编号
     */
    private String memSysNo;

    /**
     * 设备故障纪录编号
     */
    private String memf1No;

    /**
     * 备注
     */
    private String memo;

    /**
     * 立案人员
     */
    private String createEmp;

    /**
     * 立案日期时间
     */
    private String createDate;

    /**
     * 重启人员
     */
    private String modifyEmp;

    /**
     * 重启日期时间
     */
    private String modifyDate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
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
     * 未分摊责任时间
     */
    private BigDecimal unshareTime;
    /**
     * 单位班别
     */
    private String deptclassMgms1No;

    /**
     * 停机原因
     */
    private String reasonNo;


    public BigDecimal getUnshareTime() {
        return unshareTime;
    }

    public void setUnshareTime(BigDecimal unshareTime) {
        this.unshareTime = unshareTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getMgms1No() {
        return mgms1No;
    }

    public void setMgms1No(String mgms1No) {
        this.mgms1No = mgms1No;
    }
    public String getMgms1Name() {
        return mgms1Name;
    }

    public void setMgms1Name(String mgms1Name) {
        this.mgms1Name = mgms1Name;
    }
    public String getMgma1No() {
        return mgma1No;
    }

    public void setMgma1Name(String mgma1Name) {
        this.mgma1Name = mgma1Name;
    }
    public String getMgma1Name() {
        return mgma1Name;
    }

    public void setMgma1No(String mgma1No) {
        this.mgma1No = mgma1No;
    }
    public String getMgms1Stus() {
        return mgms1Stus;
    }

    public void setMgms1Stus(String mgms1Stus) {
        this.mgms1Stus = mgms1Stus;
    }
    public String getMgmc1No() {
        return mgmc1No;
    }

    public void setMgmc1No(String mgmc1No) {
        this.mgmc1No = mgmc1No;
    }
    public String getMgmc1Name() {
        return mgmc1Name;
    }

    public void setMgmc1Name(String mgmc1Name) {
        this.mgmc1Name = mgmc1Name;
    }
    public String getMgmc2No() {
        return mgmc2No;
    }

    public void setMgmc2No(String mgmc2No) {
        this.mgmc2No = mgmc2No;
    }
    public String getMgms1Type() {
        return mgms1Type;
    }

    public void setMgms1Type(String mgms1Type) {
        this.mgms1Type = mgms1Type;
    }
    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public BigDecimal getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(BigDecimal totalTime) {
        this.totalTime = totalTime;
    }
    public String getRespDeptNo() {
        return respDeptNo;
    }

    public void setRespDeptNo(String respDeptNo) {
        this.respDeptNo = respDeptNo;
    }
    public String getMemSysNo() {
        return memSysNo;
    }

    public void setMemSysNo(String memSysNo) {
        this.memSysNo = memSysNo;
    }
    public String getMemf1No() {
        return memf1No;
    }

    public void setMemf1No(String memf1No) {
        this.memf1No = memf1No;
    }
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getRespDeptName() {
        return respDeptName;
    }

    public void setRespDeptName(String respDeptName) {
        this.respDeptName = respDeptName;
    }

    public String getDeptclassMgms1No() {
        return deptclassMgms1No;
    }

    public void setDeptclassMgms1No(String deptclassMgms1No) {
        this.deptclassMgms1No = deptclassMgms1No;
    }

    public String getReasonNo() {
        return reasonNo;
    }

    public void setReasonNo(String reasonNo) {
        this.reasonNo = reasonNo;
    }

    @Override
    public String toString() {
        return "EquipmentMgms1DTO{" +
                "id='" + id + '\'' +
                ", mgms1No='" + mgms1No + '\'' +
                ", mgms1Name='" + mgms1Name + '\'' +
                ", mgma1No='" + mgma1No + '\'' +
                ", mgma1Name='" + mgma1Name + '\'' +
                ", mgms1Stus='" + mgms1Stus + '\'' +
                ", mgmc1No='" + mgmc1No + '\'' +
                ", mgmc1Name='" + mgmc1Name + '\'' +
                ", mgmc2No='" + mgmc2No + '\'' +
                ", mgms1Type='" + mgms1Type + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", totalTime=" + totalTime +
                ", respDeptNo='" + respDeptNo + '\'' +
                ", respDeptName='" + respDeptName + '\'' +
                ", memSysNo='" + memSysNo + '\'' +
                ", memf1No='" + memf1No + '\'' +
                ", memo='" + memo + '\'' +
                ", createEmp='" + createEmp + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifyEmp='" + modifyEmp + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUserName='" + updateUserName + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", delFlag=" + delFlag +
                ", unshareTime=" + unshareTime +
                ", deptclassMgms1No='" + deptclassMgms1No + '\'' +
                ", reasonNo='" + reasonNo + '\'' +
                '}';
    }
}
