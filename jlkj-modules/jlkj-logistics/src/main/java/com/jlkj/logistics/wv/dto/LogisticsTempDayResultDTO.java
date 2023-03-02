package com.jlkj.logistics.wv.dto;
/**
 * @author xin
 */
public class LogisticsTempDayResultDTO {

    /**
     * id
     */
    private String id;
    /**
     * 标识
     */
    private String logicId;
    /**
     * 临时用车申请单号
     */
    private String applyNo;

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
     * 成本中心(预算编码)
     */
    private String costBudg;

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
     * 车数
     */
    private Integer carNum;

    /**
     * 部门代号---用车单位
     */
    private String departmentName;

    /**
     * 运输工具名称
     */
    private String transportToolName;

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
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

    public Integer getPlanSurplusMachineHourNum() {
        return planSurplusMachineHourNum;
    }

    public void setPlanSurplusMachineHourNum(Integer planSurplusMachineHourNum) {
        this.planSurplusMachineHourNum = planSurplusMachineHourNum;
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

    public Integer getCarNum() {
        return carNum;
    }

    public void setCarNum(Integer carNum) {
        this.carNum = carNum;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getTransportToolName() {
        return transportToolName;
    }

    public void setTransportToolName(String transportToolName) {
        this.transportToolName = transportToolName;
    }

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

    @Override
    public String toString() {
        return "LogisticsTempDayResultDTO{" +
                "id='" + id + '\'' +
                ", logicId='" + logicId + '\'' +
                ", applyNo='" + applyNo + '\'' +
                ", addressFromName='" + addressFromName + '\'' +
                ", addressToName='" + addressToName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", workTypeName='" + workTypeName + '\'' +
                ", costBudg='" + costBudg + '\'' +
                ", planMachineHourNum=" + planMachineHourNum +
                ", completeMachineHourNum=" + completeMachineHourNum +
                ", planSurplusMachineHourNum=" + planSurplusMachineHourNum +
                ", reasonName='" + reasonName + '\'' +
                ", memo='" + memo + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", carNum=" + carNum +
                ", departmentName='" + departmentName + '\'' +
                ", transportToolName='" + transportToolName + '\'' +
                '}';
    }
}
