package com.jlkj.common.dto.dto.logisticsprovider;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 月度运输计划表
 * </p>
 *
 * @author Xin
 * @since 2022-05-04
 */
@TableName("t_logistics_plan_month")
public class LogisticsPlanMonthDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 月度运输计划编号
     */
    private String id;

    /**
     * 运输方式代码
     */
    private String transportTypeNo;

    /**
     * 运输方式名称
     */
    private String transportTypeName;

    /**
     * 计划年月
     */
    private String planMonth;

    /**
     * 起始地点
     */
    private String addressFromNo;

    /**
     * 起始地点名称
     */
    private String addressFromName;

    /**
     * 讫址
     */
    private String addressToNo;

    /**
     * 讫址名称
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
     * 成本中心
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
     * 运输方向
     */
    private String directionNo;

    /**
     * 运输方向名称
     */
    private String directionName;

    /**
     * 计划台时量
     */
    private Integer planMachineHourNum;

    /**
     * 计划数量
     */
    private BigDecimal planPoundNum;

    /**
     * 完成台时数
     */
    private Integer completeMachineHourNum;

    /**
     * 完成数量
     */
    private BigDecimal completePoundNum;

    /**
     * 备注
     */
    private String memo;

    /**
     * 是否计量
     */
    private String isMeasNo;

    /**
     * 是否计量名称
     */
    private String isMeasName;

    /**
     * 车型
     */
    private String transportToolNo;

    /**
     * 车型名称
     */
    private String transportToolName;

    /**
     * 称重委托单号
     */
    private String planPoundListNo;

    /**
     * 用车事由
     */
    private String reasonNo;

    /**
     * 用车事由描述
     */
    private String reasonName;

    /**
     * 称重计划来源系统
     */
    private String sourceSysId;

    /**
     * 用车部门代号
     */
    private String departmentNo;

    /**
     * 用车部门代号名称
     */
    private String departmentName;

    /**
     * 运输类别
     */
    private String transportClassType;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
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
    public String getDirectionNo() {
        return directionNo;
    }

    public void setDirectionNo(String directionNo) {
        this.directionNo = directionNo;
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
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getIsMeasNo() {
        return isMeasNo;
    }

    public void setIsMeasNo(String isMeasNo) {
        this.isMeasNo = isMeasNo;
    }
    public String getIsMeasName() {
        return isMeasName;
    }

    public void setIsMeasName(String isMeasName) {
        this.isMeasName = isMeasName;
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
    public String getPlanPoundListNo() {
        return planPoundListNo;
    }

    public void setPlanPoundListNo(String planPoundListNo) {
        this.planPoundListNo = planPoundListNo;
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
    public String getTransportClassType() {
        return transportClassType;
    }

    public void setTransportClassType(String transportClassType) {
        this.transportClassType = transportClassType;
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

    public String getCostBudgName() {
        return costBudgName;
    }

    public void setCostBudgName(String costBudgName) {
        this.costBudgName = costBudgName;
    }

    @Override
    public String toString() {
        return "LogisticsPlanMonth{" +
            "id=" + id +
            ", transportTypeNo=" + transportTypeNo +
            ", transportTypeName=" + transportTypeName +
            ", planMonth=" + planMonth +
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
            ", directionNo=" + directionNo +
            ", directionName=" + directionName +
            ", planMachineHourNum=" + planMachineHourNum +
            ", planPoundNum=" + planPoundNum +
            ", completeMachineHourNum=" + completeMachineHourNum +
            ", completePoundNum=" + completePoundNum +
            ", memo=" + memo +
            ", isMeasNo=" + isMeasNo +
            ", isMeasName=" + isMeasName +
            ", transportToolNo=" + transportToolNo +
            ", transportToolName=" + transportToolName +
            ", planPoundListNo=" + planPoundListNo +
            ", reasonNo=" + reasonNo +
            ", reasonName=" + reasonName +
            ", sourceSysId=" + sourceSysId +
            ", departmentNo=" + departmentNo +
            ", departmentName=" + departmentName +
            ", transportClassType=" + transportClassType +
            ", updateTime=" + updateTime +
            ", createTime=" + createTime +
            ", createUser=" + createUser +
            ", updateUser=" + updateUser +
            ", updateUserName=" + updateUserName +
            ", createUserName=" + createUserName +
        "}";
    }
}
