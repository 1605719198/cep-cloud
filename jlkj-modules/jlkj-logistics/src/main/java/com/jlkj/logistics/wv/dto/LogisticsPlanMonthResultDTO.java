package com.jlkj.logistics.wv.dto;

import java.math.BigDecimal;

/**
 * @author xin
 */
public class LogisticsPlanMonthResultDTO {

    /**
     * 运输方式名称
     */
    private String transportTypeName;

    /**
     * 计划年月
     */
    private String planMonth;

    /**
     * 起始地点名称
     */
    private String addressFromName;

    /**
     * 讫址名称
     */
    private String addressToName;

    /**
     * 货物名称
     */
    private String goodsName;
    /**
     * 用车部门代号名称
     */
    private String departmentName;
    /**
     * 运输方向名称
     */
    private String directionName;
    /**
     * 计划台时量
     */
    private Integer planMachineHourNum;
    /**
     * 完成台时数
     */
    private Integer completeMachineHourNum;
    /**
     * 剩余计划台时量=planMachineHourNum-completeMachineHourNum
     */
    private Integer planSurplusMachineHourNum;
    /**
     * 计划数量
     */
    private BigDecimal planPoundNum;
    /**
     * 完成数量
     */
    private BigDecimal completePoundNum;
    /**
     * 剩余称重计划量=planPoundNum-completePoundNum
     */
    private BigDecimal planSurplusPoundNum;
    /**
     * 车型名称
     */
    private String transportToolName;
    /**
     * 作业内容名称
     */
    private String workTypeName;
    /**
     * 成本中心
     */
    private String costBudg;
    /**
     * 成本中心名称
     */
    private String costBudgName;
    /**
     * 用车事由描述
     */
    private String reasonName;
    /**
     * 是否计量名称--结算方式
     */
    private String isMeasName;
    /**
     * 称重委托单号
     */
    private String planPoundListNo;
    /**
     * 备注
     */
    private String memo;

    public Integer getPlanSurplusMachineHourNum() {
        return planSurplusMachineHourNum;
    }

    public void setPlanSurplusMachineHourNum(Integer planSurplusMachineHourNum) {
        this.planSurplusMachineHourNum = planSurplusMachineHourNum;
    }

    public BigDecimal getPlanSurplusPoundNum() {
        return planSurplusPoundNum;
    }

    public void setPlanSurplusPoundNum(BigDecimal planSurplusPoundNum) {
        this.planSurplusPoundNum = planSurplusPoundNum;
    }

    public String getTransportTypeName() {
        return transportTypeName;
    }

    public void setTransportTypeName(String transportTypeName) {
        this.transportTypeName = transportTypeName;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
    }

    public String getAddressFromName() {
        return addressFromName;
    }

    public void setAddressFromName(String addressFromName) {
        this.addressFromName = addressFromName;
    }

    public String getAddressToName() {
        return addressToName;
    }

    public void setAddressToName(String addressToName) {
        this.addressToName = addressToName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public Integer getPlanMachineHourNum() {
        return planMachineHourNum;
    }

    public void setPlanMachineHourNum(Integer planMachineHourNum) {
        this.planMachineHourNum = planMachineHourNum;
    }

    public Integer getCompleteMachineHourNum() {
        return completeMachineHourNum;
    }

    public void setCompleteMachineHourNum(Integer completeMachineHourNum) {
        this.completeMachineHourNum = completeMachineHourNum;
    }

    public BigDecimal getPlanPoundNum() {
        return planPoundNum;
    }

    public void setPlanPoundNum(BigDecimal planPoundNum) {
        this.planPoundNum = planPoundNum;
    }

    public BigDecimal getCompletePoundNum() {
        return completePoundNum;
    }

    public void setCompletePoundNum(BigDecimal completePoundNum) {
        this.completePoundNum = completePoundNum;
    }

    public String getTransportToolName() {
        return transportToolName;
    }

    public void setTransportToolName(String transportToolName) {
        this.transportToolName = transportToolName;
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

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getIsMeasName() {
        return isMeasName;
    }

    public void setIsMeasName(String isMeasName) {
        this.isMeasName = isMeasName;
    }

    public String getPlanPoundListNo() {
        return planPoundListNo;
    }

    public void setPlanPoundListNo(String planPoundListNo) {
        this.planPoundListNo = planPoundListNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCostBudgName() {
        return costBudgName;
    }

    public void setCostBudgName(String costBudgName) {
        this.costBudgName = costBudgName;
    }

    @Override
    public String toString() {
        return "LogisticsPlanMonthResultDTO{" +
                "transportTypeName='" + transportTypeName + '\'' +
                ", planMonth='" + planMonth + '\'' +
                ", addressFromName='" + addressFromName + '\'' +
                ", addressToName='" + addressToName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", directionName='" + directionName + '\'' +
                ", planMachineHourNum=" + planMachineHourNum +
                ", completeMachineHourNum=" + completeMachineHourNum +
                ", planSurplusMachineHourNum=" + planSurplusMachineHourNum +
                ", planPoundNum=" + planPoundNum +
                ", completePoundNum=" + completePoundNum +
                ", planSurplusPoundNum=" + planSurplusPoundNum +
                ", transportToolName='" + transportToolName + '\'' +
                ", workTypeName='" + workTypeName + '\'' +
                ", costBudg='" + costBudg + '\'' +
                ", costBudgName='" + costBudgName + '\'' +
                ", reasonName='" + reasonName + '\'' +
                ", isMeasName='" + isMeasName + '\'' +
                ", planPoundListNo='" + planPoundListNo + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
