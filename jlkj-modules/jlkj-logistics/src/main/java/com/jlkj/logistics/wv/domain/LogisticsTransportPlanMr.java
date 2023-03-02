package com.jlkj.logistics.wv.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 物流管理-MR倒运计划
 * @author Xin
 * @TableName t_logistics_transport_plan_mr
 */
@TableName(value ="logistics_wv_transport_plan_mr")
public class LogisticsTransportPlanMr implements Serializable {
    /**
     * 主键标识
     */
    @TableId
    private String id;

    /**
     * 计划单号
     */
    private String planlistNo;

    /**
     * 运输工具
     */
    private String carrierType;

    /**
     * 料号1
     */
    private String mtrla;

    /**
     * 料号1中文
     */
    private String mtrlaName;

    /**
     * 料号2
     */
    private String mtrlb;

    /**
     * 料号2中文
     */
    private String mtrlbName;

    /**
     * 储位1
     */
    private String stagea;

    /**
     * 储位1中文
     */
    private String stageaName;

    /**
     * 储位2
     */
    private String stageb;

    /**
     * 储位2中文
     */
    private String stagebName;

    /**
     * 成本中心
     */
    private String costCenter;

    /**
     * 成本中心名称
     */
    private String costCenterDesc;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanlistNo() {
        return planlistNo;
    }

    public void setPlanlistNo(String planlistNo) {
        this.planlistNo = planlistNo;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public String getMtrla() {
        return mtrla;
    }

    public void setMtrla(String mtrla) {
        this.mtrla = mtrla;
    }

    public String getMtrlaName() {
        return mtrlaName;
    }

    public void setMtrlaName(String mtrlaName) {
        this.mtrlaName = mtrlaName;
    }

    public String getMtrlb() {
        return mtrlb;
    }

    public void setMtrlb(String mtrlb) {
        this.mtrlb = mtrlb;
    }

    public String getMtrlbName() {
        return mtrlbName;
    }

    public void setMtrlbName(String mtrlbName) {
        this.mtrlbName = mtrlbName;
    }

    public String getStagea() {
        return stagea;
    }

    public void setStagea(String stagea) {
        this.stagea = stagea;
    }

    public String getStageaName() {
        return stageaName;
    }

    public void setStageaName(String stageaName) {
        this.stageaName = stageaName;
    }

    public String getStageb() {
        return stageb;
    }

    public void setStageb(String stageb) {
        this.stageb = stageb;
    }

    public String getStagebName() {
        return stagebName;
    }

    public void setStagebName(String stagebName) {
        this.stagebName = stagebName;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getCostCenterDesc() {
        return costCenterDesc;
    }

    public void setCostCenterDesc(String costCenterDesc) {
        this.costCenterDesc = costCenterDesc;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        return "LogisticsTransportPlanMr{" +
                "id='" + id + '\'' +
                ", planlistNo='" + planlistNo + '\'' +
                ", carrierType='" + carrierType + '\'' +
                ", mtrla='" + mtrla + '\'' +
                ", mtrlaName='" + mtrlaName + '\'' +
                ", mtrlb='" + mtrlb + '\'' +
                ", mtrlbName='" + mtrlbName + '\'' +
                ", stagea='" + stagea + '\'' +
                ", stageaName='" + stageaName + '\'' +
                ", stageb='" + stageb + '\'' +
                ", stagebName='" + stagebName + '\'' +
                ", costCenter='" + costCenter + '\'' +
                ", costCenterDesc='" + costCenterDesc + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", createUserName='" + createUserName + '\'' +
                '}';
    }
}
