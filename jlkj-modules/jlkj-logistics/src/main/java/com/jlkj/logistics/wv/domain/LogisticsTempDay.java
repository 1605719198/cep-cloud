package com.jlkj.logistics.wv.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 临时用车申请表
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@TableName("logistics_wv_temp_day")
public class LogisticsTempDay implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 临时用车申请编号
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 临时用车申请单号
     */
    private String applyNo;

    /**
     * 起始地点编码
     */
    private String addressFromNo;

    /**
     * 起始地点名称
     */
    private String addressFromName;

    /**
     * 到达地点编码
     */
    private String addressToNo;

    /**
     * 到达地点名称
     */
    private String addressToName;

    /**
     * 货物编码
     */
    private String goodsNo;

    /**
     * 货物名称
     */
    private String goodsName;

    /**
     * 作业内容
     */
    private String workTypeNo;

    /**
     * 作业内容名称
     */
    private String workTypeName;

    /**
     * 成本中心(预算编码)
     */
    private String costBudg;

    /**
     * 成本中心名称（预算编码）
     */
    private String costBudgName;

    /**
     * 标识
     */
    private String logicId;

    /**
     * 物类代码
     */
    private String sortNo;

    /**
     * 物类名称
     */
    private String sortName;

    /**
     * 单位
     */
    private String unitNo;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 计划台时量
     */
    private Integer planMachineHourNum;

    /**
     * 称重计划量
     */
    private BigDecimal planPoundNum;

    /**
     * 完成台时量
     */
    private Integer completeMachineHourNum;

    /**
     * 称重完成量
     */
    private BigDecimal completePoundNum;

    /**
     * 用车事由
     */
    private String reasonNo;

    /**
     * 用车事由描述
     */
    private String reasonName;

    /**
     * 备注
     */
    private String memo;

    /**
     * 电话号码
     */
    private String phoneNo;

    /**
     * 是否计量
     */
    private String ismeas;

    /**
     * 是否计量名称
     */
    private String ismeasName;

    /**
     * 车型
     */
    private String carTypeNo;

    /**
     * 车型名称
     */
    private String carTypeName;

    /**
     * 车数
     */
    private Integer carNum;

    /**
     * 称重委托单号
     */
    private String planPoundListNo;

    /**
     * 种类
     */
    private String issueTypeNo;

    /**
     * 种类描述
     */
    private String issueTypeName;

    /**
     * 称重计划来源系统
     */
    private String sourceSysId;

    /**
     * 部门代号
     */
    private String departmentNo;

    /**
     * 部门代号
     */
    private String departmentName;

    /**
     * 运输类别
     */
    private String transportTypeNo;

    /**
     * 运输类别描述
     */
    private String transportTypeName;

    /**
     * 运输工具
     */
    private String transportToolNo;

    /**
     * 运输工具名称
     */
    private String transportToolName;

    /**
     * 类别
     */
    private String kind;

    /**
     * 类别名称
     */
    private String kindName;

    /**
     * 料号1代码
     */
    private String mtrla;

    /**
     * 料号1说明
     */
    private String mtrlaDesc;

    /**
     * 料号2代码
     */
    private String mtrlb;

    /**
     * 料号2说明
     */
    private String mtrlbDesc;

    /**
     * 储位1代码
     */
    private String stgnoa;

    /**
     * 储位1说明
     */
    private String stgnoaDesc;

    /**
     * 储位2代码
     */
    private String stgnob;

    /**
     * 储位2说明
     */
    private String stgnobDesc;

    /**
     * 成本中心说明
     */
    private String costCenterDesc;

    /**
     * 申请人员编码
     */
    private String applyEmployeeNo;

    /**
     * 申请人员名称
     */
    private String applyEmployeeName;

    /**
     * 申请用车日期
     */
    private Date applyCarUseDate;

    /**
     * 计划下达日期
     */
    private Date planPublishDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    /**
     * 成本中心（试车预算号）
     */
    private String costCenterNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }
    public String getAddressFromNo() {
        return addressFromNo;
    }

    public void setAddressFromNo(String addressFromNo) {
        this.addressFromNo = addressFromNo;
    }
    public String getAddressFromName() {
        return addressFromName;
    }

    public void setAddressFromName(String addressFromName) {
        this.addressFromName = addressFromName;
    }
    public String getAddressToNo() {
        return addressToNo;
    }

    public void setAddressToNo(String addressToNo) {
        this.addressToNo = addressToNo;
    }
    public String getAddressToName() {
        return addressToName;
    }

    public void setAddressToName(String addressToName) {
        this.addressToName = addressToName;
    }
    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getWorkTypeNo() {
        return workTypeNo;
    }

    public void setWorkTypeNo(String workTypeNo) {
        this.workTypeNo = workTypeNo;
    }
    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }
    public String getCostBudg() {
        return costBudg;
    }

    public void setCostBudg(String costBudg) {
        this.costBudg = costBudg;
    }
    public String getLogicId() {
        return logicId;
    }

    public void setLogicId(String logicId) {
        this.logicId = logicId;
    }
    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }
    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    public Integer getPlanMachineHourNum() {
        return planMachineHourNum;
    }

    public void setPlanMachineHourNum(Integer planMachineHourNum) {
        this.planMachineHourNum = planMachineHourNum;
    }
    public BigDecimal getPlanPoundNum() {
        return planPoundNum;
    }

    public void setPlanPoundNum(BigDecimal planPoundNum) {
        this.planPoundNum = planPoundNum;
    }
    public Integer getCompleteMachineHourNum() {
        return completeMachineHourNum;
    }

    public void setCompleteMachineHourNum(Integer completeMachineHourNum) {
        this.completeMachineHourNum = completeMachineHourNum;
    }
    public BigDecimal getCompletePoundNum() {
        return completePoundNum;
    }

    public void setCompletePoundNum(BigDecimal completePoundNum) {
        this.completePoundNum = completePoundNum;
    }
    public String getReasonNo() {
        return reasonNo;
    }

    public void setReasonNo(String reasonNo) {
        this.reasonNo = reasonNo;
    }
    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getIsmeas() {
        return ismeas;
    }

    public void setIsmeas(String ismeas) {
        this.ismeas = ismeas;
    }
    public String getIsmeasName() {
        return ismeasName;
    }

    public void setIsmeasName(String ismeasName) {
        this.ismeasName = ismeasName;
    }
    public String getCarTypeNo() {
        return carTypeNo;
    }

    public void setCarTypeNo(String carTypeNo) {
        this.carTypeNo = carTypeNo;
    }
    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }
    public Integer getCarNum() {
        return carNum;
    }

    public void setCarNum(Integer carNum) {
        this.carNum = carNum;
    }
    public String getPlanPoundListNo() {
        return planPoundListNo;
    }

    public void setPlanPoundListNo(String planPoundListNo) {
        this.planPoundListNo = planPoundListNo;
    }
    public String getIssueTypeNo() {
        return issueTypeNo;
    }

    public void setIssueTypeNo(String issueTypeNo) {
        this.issueTypeNo = issueTypeNo;
    }
    public String getIssueTypeName() {
        return issueTypeName;
    }

    public void setIssueTypeName(String issueTypeName) {
        this.issueTypeName = issueTypeName;
    }
    public String getSourceSysId() {
        return sourceSysId;
    }

    public void setSourceSysId(String sourceSysId) {
        this.sourceSysId = sourceSysId;
    }
    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getTransportTypeNo() {
        return transportTypeNo;
    }

    public void setTransportTypeNo(String transportTypeNo) {
        this.transportTypeNo = transportTypeNo;
    }
    public String getTransportTypeName() {
        return transportTypeName;
    }

    public void setTransportTypeName(String transportTypeName) {
        this.transportTypeName = transportTypeName;
    }
    public String getTransportToolNo() {
        return transportToolNo;
    }

    public void setTransportToolNo(String transportToolNo) {
        this.transportToolNo = transportToolNo;
    }
    public String getTransportToolName() {
        return transportToolName;
    }

    public void setTransportToolName(String transportToolName) {
        this.transportToolName = transportToolName;
    }
    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }
    public String getMtrla() {
        return mtrla;
    }

    public void setMtrla(String mtrla) {
        this.mtrla = mtrla;
    }
    public String getMtrlaDesc() {
        return mtrlaDesc;
    }

    public void setMtrlaDesc(String mtrlaDesc) {
        this.mtrlaDesc = mtrlaDesc;
    }
    public String getMtrlb() {
        return mtrlb;
    }

    public void setMtrlb(String mtrlb) {
        this.mtrlb = mtrlb;
    }
    public String getMtrlbDesc() {
        return mtrlbDesc;
    }

    public void setMtrlbDesc(String mtrlbDesc) {
        this.mtrlbDesc = mtrlbDesc;
    }
    public String getStgnoa() {
        return stgnoa;
    }

    public void setStgnoa(String stgnoa) {
        this.stgnoa = stgnoa;
    }
    public String getStgnoaDesc() {
        return stgnoaDesc;
    }

    public void setStgnoaDesc(String stgnoaDesc) {
        this.stgnoaDesc = stgnoaDesc;
    }
    public String getStgnob() {
        return stgnob;
    }

    public void setStgnob(String stgnob) {
        this.stgnob = stgnob;
    }
    public String getStgnobDesc() {
        return stgnobDesc;
    }

    public void setStgnobDesc(String stgnobDesc) {
        this.stgnobDesc = stgnobDesc;
    }
    public String getCostCenterDesc() {
        return costCenterDesc;
    }

    public void setCostCenterDesc(String costCenterDesc) {
        this.costCenterDesc = costCenterDesc;
    }
    public String getApplyEmployeeNo() {
        return applyEmployeeNo;
    }

    public void setApplyEmployeeNo(String applyEmployeeNo) {
        this.applyEmployeeNo = applyEmployeeNo;
    }
    public String getApplyEmployeeName() {
        return applyEmployeeName;
    }

    public void setApplyEmployeeName(String applyEmployeeName) {
        this.applyEmployeeName = applyEmployeeName;
    }
    public Date getApplyCarUseDate() {
        return applyCarUseDate;
    }

    public void setApplyCarUseDate(Date applyCarUseDate) {
        this.applyCarUseDate = applyCarUseDate;
    }
    public Date getPlanPublishDate() {
        return planPublishDate;
    }

    public void setPlanPublishDate(Date planPublishDate) {
        this.planPublishDate = planPublishDate;
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
    public String getCostCenterNo() {
        return costCenterNo;
    }

    public void setCostCenterNo(String costCenterNo) {
        this.costCenterNo = costCenterNo;
    }

    public String getCostBudgName() {
        return costBudgName;
    }

    public void setCostBudgName(String costBudgName) {
        this.costBudgName = costBudgName;
    }

    @Override
    public String toString() {
        return "LogisticsTempDay{" +
            "id=" + id +
            ", applyNo=" + applyNo +
            ", addressFromNo=" + addressFromNo +
            ", addressFromName=" + addressFromName +
            ", addressToNo=" + addressToNo +
            ", addressToName=" + addressToName +
            ", goodsNo=" + goodsNo +
            ", goodsName=" + goodsName +
            ", workTypeNo=" + workTypeNo +
            ", workTypeName=" + workTypeName +
            ", costBudg=" + costBudg +
            ", costBudgName=" + costBudgName +
            ", logicId=" + logicId +
            ", sortNo=" + sortNo +
            ", sortName=" + sortName +
            ", unitNo=" + unitNo +
            ", unitName=" + unitName +
            ", planMachineHourNum=" + planMachineHourNum +
            ", planPoundNum=" + planPoundNum +
            ", completeMachineHourNum=" + completeMachineHourNum +
            ", completePoundNum=" + completePoundNum +
            ", reasonNo=" + reasonNo +
            ", reasonName=" + reasonName +
            ", memo=" + memo +
            ", phoneNo=" + phoneNo +
            ", ismeas=" + ismeas +
            ", ismeasName=" + ismeasName +
            ", carTypeNo=" + carTypeNo +
            ", carTypeName=" + carTypeName +
            ", carNum=" + carNum +
            ", planPoundListNo=" + planPoundListNo +
            ", issueTypeNo=" + issueTypeNo +
            ", issueTypeName=" + issueTypeName +
            ", sourceSysId=" + sourceSysId +
            ", departmentNo=" + departmentNo +
            ", departmentName=" + departmentName +
            ", transportTypeNo=" + transportTypeNo +
            ", transportTypeName=" + transportTypeName +
            ", transportToolNo=" + transportToolNo +
            ", transportToolName=" + transportToolName +
            ", kind=" + kind +
            ", kindName=" + kindName +
            ", mtrla=" + mtrla +
            ", mtrlaDesc=" + mtrlaDesc +
            ", mtrlb=" + mtrlb +
            ", mtrlbDesc=" + mtrlbDesc +
            ", stgnoa=" + stgnoa +
            ", stgnoaDesc=" + stgnoaDesc +
            ", stgnob=" + stgnob +
            ", stgnobDesc=" + stgnobDesc +
            ", costCenterDesc=" + costCenterDesc +
            ", applyEmployeeNo=" + applyEmployeeNo +
            ", applyEmployeeName=" + applyEmployeeName +
            ", applyCarUseDate=" + applyCarUseDate +
            ", planPublishDate=" + planPublishDate +
            ", updateTime=" + updateTime +
            ", createTime=" + createTime +
            ", createUser=" + createUser +
            ", updateUser=" + updateUser +
            ", updateUserName=" + updateUserName +
            ", createUserName=" + createUserName +
            ", costCenterNo=" + costCenterNo +
        "}";
    }
}
