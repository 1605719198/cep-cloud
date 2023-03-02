package com.jlkj.common.dto.logistics.wv;

/**
 * 物流管理-MR倒运计划
 * @author Xin
 * @TableName t_logistics_transport_plan_mr
 */
public class LogisticsTransportPlanMrDTO {

    /**
     * 运输工具
     */
    private String carrierType;
    /**
     * 成本中心
     */
    private String costCenter;
    /**
     * 成本中心名称
     */
    private String costCenterDesc;
    /**
     * 日期
     */
    private String issueDate;
    /**
     * 成本中心代码
     */
    private String issueDept;
    /**
     * 成本中心名称
     */
    private String issueDeptName;
    /**
     * 姓名
     */
    private String issueEmpName;
    /**
     * 工号
     */
    private String issueEmpNo;
    /**
     * 料号1中文
     */
    private String mtrlNameA;
    /**
     * 料号1
     */
    private String mtrlNoA;
    /**
     * 料号2中文
     */
    private String mtrlNameB;
    /**
     * 料号2
     */
    private String mtrlNoB;
    /**
     * 计划单号
     */
    private String planListNo;
    /**
     * 储位1
     */
    private String stageA;
    /**
     * 储位1中文
     */
    private String stageNameA;
    /**
     * 储位2
     */
    private String stageB;
    /**
     * 储位1中文
     */
    private String stageNameB;

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssueDept() {
        return issueDept;
    }

    public void setIssueDept(String issueDept) {
        this.issueDept = issueDept;
    }

    public String getIssueDeptName() {
        return issueDeptName;
    }

    public void setIssueDeptName(String issueDeptName) {
        this.issueDeptName = issueDeptName;
    }

    public String getIssueEmpName() {
        return issueEmpName;
    }

    public void setIssueEmpName(String issueEmpName) {
        this.issueEmpName = issueEmpName;
    }

    public String getIssueEmpNo() {
        return issueEmpNo;
    }

    public void setIssueEmpNo(String issueEmpNo) {
        this.issueEmpNo = issueEmpNo;
    }

    public String getMtrlNameA() {
        return mtrlNameA;
    }

    public void setMtrlNameA(String mtrlNameA) {
        this.mtrlNameA = mtrlNameA;
    }

    public String getMtrlNoA() {
        return mtrlNoA;
    }

    public void setMtrlNoA(String mtrlNoA) {
        this.mtrlNoA = mtrlNoA;
    }

    public String getMtrlNameB() {
        return mtrlNameB;
    }

    public void setMtrlNameB(String mtrlNameB) {
        this.mtrlNameB = mtrlNameB;
    }

    public String getMtrlNoB() {
        return mtrlNoB;
    }

    public void setMtrlNoB(String mtrlNoB) {
        this.mtrlNoB = mtrlNoB;
    }

    public String getPlanListNo() {
        return planListNo;
    }

    public void setPlanListNo(String planListNo) {
        this.planListNo = planListNo;
    }

    public String getStageA() {
        return stageA;
    }

    public void setStageA(String stageA) {
        this.stageA = stageA;
    }

    public String getStageNameA() {
        return stageNameA;
    }

    public void setStageNameA(String stageNameA) {
        this.stageNameA = stageNameA;
    }

    public String getStageB() {
        return stageB;
    }

    public void setStageB(String stageB) {
        this.stageB = stageB;
    }

    public String getStageNameB() {
        return stageNameB;
    }

    public void setStageNameB(String stageNameB) {
        this.stageNameB = stageNameB;
    }

    @Override
    public String toString() {
        return "LogisticsTransportPlanMrDTO{" +
                "carrierType='" + carrierType + '\'' +
                ", costCenter='" + costCenter + '\'' +
                ", costCenterDesc='" + costCenterDesc + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", issueDept='" + issueDept + '\'' +
                ", issueDeptName='" + issueDeptName + '\'' +
                ", issueEmpName='" + issueEmpName + '\'' +
                ", issueEmpNo='" + issueEmpNo + '\'' +
                ", mtrlNameA='" + mtrlNameA + '\'' +
                ", mtrlNoA='" + mtrlNoA + '\'' +
                ", mtrlNameB='" + mtrlNameB + '\'' +
                ", mtrlNoB='" + mtrlNoB + '\'' +
                ", planListNo='" + planListNo + '\'' +
                ", stageA='" + stageA + '\'' +
                ", stageNameA='" + stageNameA + '\'' +
                ", stageB='" + stageB + '\'' +
                ", stageNameB='" + stageNameB + '\'' +
                '}';
    }
}

