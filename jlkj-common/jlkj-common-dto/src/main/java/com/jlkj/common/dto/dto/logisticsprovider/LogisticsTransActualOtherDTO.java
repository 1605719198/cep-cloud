package com.jlkj.common.dto.dto.logisticsprovider;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 运输实绩表
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
public class LogisticsTransActualOtherDTO {


    /**
     * 临时用车申请编号
     */
    private String id;

    /**
     * 标识
     */
    private String logicId;

    /**
     * 派车日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date carSendDate;

    /**
     * 车行编号
     */
    private String venderNo;

    /**
     * 车行名称
     */
    private String venderName;

    /**
     * 车型
     */
    private String carTypeNo;

    /**
     * 车型名称
     */
    private String carTypeName;

    /**
     * 作业内容名称
     */
    private String workTypeName;

    /**
     * 作业内容
     */
    private String workTypeNo;

    /**
     * 称重计划号
     */
    private String planPoundListNo;

    /**
     * 派车履运单号
     */
    private String carExecuteNo;

    /**
     * 车号
     */
    private String carNo;

    /**
     * 称重完成量
     */
    private BigDecimal completePoundNum;

    /**
     * 台时完成量
     */
    private BigDecimal completeMachineHourNum;

    /**
     * 称重日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date poundDate;

    /**
     * 起始地点编号
     */
    private String addressFromNo;

    /**
     * 起始地点名称
     */
    private String addressFromName;

    /**
     * 到达地点编号
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
    /**
     * 计算属性（1或-1）
     */
    private BigDecimal computeFlag;

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
    public Date getCarSendDate() {
        return carSendDate;
    }

    public void setCarSendDate(Date carSendDate) {
        this.carSendDate = carSendDate;
    }
    public String getVenderNo() {
        return venderNo;
    }

    public void setVenderNo(String venderNo) {
        this.venderNo = venderNo;
    }
    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
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
    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }
    public String getWorkTypeNo() {
        return workTypeNo;
    }

    public void setWorkTypeNo(String workTypeNo) {
        this.workTypeNo = workTypeNo;
    }
    public String getPlanPoundListNo() {
        return planPoundListNo;
    }

    public void setPlanPoundListNo(String planPoundListNo) {
        this.planPoundListNo = planPoundListNo;
    }
    public String getCarExecuteNo() {
        return carExecuteNo;
    }

    public void setCarExecuteNo(String carExecuteNo) {
        this.carExecuteNo = carExecuteNo;
    }
    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
    public BigDecimal getCompletePoundNum() {
        return completePoundNum;
    }

    public void setCompletePoundNum(BigDecimal completePoundNum) {
        this.completePoundNum = completePoundNum;
    }
    public BigDecimal getCompleteMachineHourNum() {
        return completeMachineHourNum;
    }

    public void setCompleteMachineHourNum(BigDecimal completeMachineHourNum) {
        this.completeMachineHourNum = completeMachineHourNum;
    }
    public Date getPoundDate() {
        return poundDate;
    }

    public void setPoundDate(Date poundDate) {
        this.poundDate = poundDate;
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

    public BigDecimal getComputeFlag() {
        return computeFlag;
    }

    public void setComputeFlag(BigDecimal computeFlag) {
        this.computeFlag = computeFlag;
    }

    @Override
    public String toString() {
        return "LogisticsTransActualOtherDTO{" +
                "id='" + id + '\'' +
                ", logicId='" + logicId + '\'' +
                ", carSendDate=" + carSendDate +
                ", venderNo='" + venderNo + '\'' +
                ", venderName='" + venderName + '\'' +
                ", carTypeNo='" + carTypeNo + '\'' +
                ", carTypeName='" + carTypeName + '\'' +
                ", workTypeName='" + workTypeName + '\'' +
                ", workTypeNo='" + workTypeNo + '\'' +
                ", planPoundListNo='" + planPoundListNo + '\'' +
                ", carExecuteNo='" + carExecuteNo + '\'' +
                ", carNo='" + carNo + '\'' +
                ", completePoundNum=" + completePoundNum +
                ", completeMachineHourNum=" + completeMachineHourNum +
                ", poundDate=" + poundDate +
                ", addressFromNo='" + addressFromNo + '\'' +
                ", addressFromName='" + addressFromName + '\'' +
                ", addressToNo='" + addressToNo + '\'' +
                ", addressToName='" + addressToName + '\'' +
                ", goodsNo='" + goodsNo + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", computeFlag=" + computeFlag +
                '}';
    }
}
