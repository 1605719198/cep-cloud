package com.jlkj.logistics.wv.dto;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * <p>
 * 日运输计划表
 * </p>
 *
 * @author xin
 * @since 2022-05-07
 */
public class LogisticsPlanDayResultDTO {

    /**
     * id
     */
    private String id;
    /**
     * 标识
     */
    private String logicId;
    /**
     * 计划年月日
     */
    private String planDay;
    /**
     * 起始地点名称
     */
    private String addressFromName;
    /**
     * 到达地点名称
     */
    private String addressToName;
    /**
     * 货物名称
     */
    private String goodsName;
    /**
     * 作业内容名称
     */
    private String workTypeName;
    /**
     * 成本中心（预算编码）
     */
    private String costBudg;
    /**
     * 成本中心名称（预算编码）
     */
    private String costBudgName;
    /**
     * 物类名称
     */
    private String sortName;
    /**
     * 部门代号---用车单位
     */
    private String departmentName;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 计划台时量
     */
    private Integer planMachineHourNum;
    /**
     * 完成台时量
     */
    private Integer completeMachineHourNum;
    /**
     * 剩余计划台时量=planMachineHourNum-completeMachineHourNum
     */
    private Integer planSurplusMachineHourNum;
    /**
     * 称重计划量
     */
    private BigDecimal planPoundNum;
    /**
     * 称重完成量
     */
    private BigDecimal completePoundNum;
    /**
     * 剩余称重计划量=planPoundNum-completePoundNum
     */
    private BigDecimal planSurplusPoundNum;
    /**
     * 用车事由描述
     */
    private String reasonName;
    /**
     * 备注
     */
    private String memo;
    /**
     * 是否计量名称
     */
    private String isMeasName;
    /**
     * 车型名称
     */
    private String carTypeName;
    /**
     * 称重委托单号
     */
    private String planPoundListNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogicId() {
        return logicId;
    }

    public void setLogicId(String logicId) {
        this.logicId = logicId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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

    public String getPlanDay() {
        return planDay;
    }

    public void setPlanDay(String planDay) {
        this.planDay = planDay;
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

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
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

    public String getIsMeasName() {
        return isMeasName;
    }

    public void setIsMeasName(String isMeasName) {
        this.isMeasName = isMeasName;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getPlanPoundListNo() {
        return planPoundListNo;
    }

    public void setPlanPoundListNo(String planPoundListNo) {
        this.planPoundListNo = planPoundListNo;
    }

    public String getCostBudgName() {
        return costBudgName;
    }

    public void setCostBudgName(String costBudgName) {
        this.costBudgName = costBudgName;
    }

    @Override
    public String toString() {
        return "LogisticsPlanDayResultDTO{" +
                "id='" + id + '\'' +
                ", logicId='" + logicId + '\'' +
                ", planDay='" + planDay + '\'' +
                ", addressFromName='" + addressFromName + '\'' +
                ", addressToName='" + addressToName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", workTypeName='" + workTypeName + '\'' +
                ", costBudg='" + costBudg + '\'' +
                ", costBudgName='" + costBudgName + '\'' +
                ", sortName='" + sortName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", planMachineHourNum=" + planMachineHourNum +
                ", completeMachineHourNum=" + completeMachineHourNum +
                ", planSurplusMachineHourNum=" + planSurplusMachineHourNum +
                ", planPoundNum=" + planPoundNum +
                ", completePoundNum=" + completePoundNum +
                ", planSurplusPoundNum=" + planSurplusPoundNum +
                ", reasonName='" + reasonName + '\'' +
                ", memo='" + memo + '\'' +
                ", isMeasName='" + isMeasName + '\'' +
                ", carTypeName='" + carTypeName + '\'' +
                ", planPoundListNo='" + planPoundListNo + '\'' +
                '}';
    }
}
