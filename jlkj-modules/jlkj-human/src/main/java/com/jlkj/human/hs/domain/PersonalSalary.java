package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 薪资核定对象 human_hs_salary
 *
 * @author 266861
 * @date 2023-06-01
 */
public class PersonalSalary extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 薪酬核定原因ID
     */
    private String payCheckReaId;

    /**
     * 薪酬核定原因
     */
    @Excel(name = "薪酬核定原因")
    private String payCheckRea;

    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /**
     * 主岗位名称
     */
    @Excel(name = "主岗位名称")
    private String mainPostName;

    /**
     * 职位等级
     */
    @Excel(name = "职位等级")
    private String postLevel;

    /**
     * 止薪日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "止薪日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endPayDate;

    /**
     * 定薪原因说明
     */
    private String payCheckDesc;

    /**
     * 工资支付银行
     */
    private String salBank;

    /**
     * 工资支付银行ID
     */
    private String salBankId;

    /**
     * 工资支付账号
     */
    private String salBankNo;

    /**
     * 奖金支付银行
     */
    private String bonBank;

    /**
     * 奖金支付银行ID
     */
    private String bonBankId;

    /**
     * 奖金支付账号
     */
    private String bonBankNo;

    /**
     * 公司ID
     */
    private String compId;

    /**
     * 公司名称
     */
    private String compName;

    /**
     * 岗位异动前
     */
    private String postId;

    /**
     * 岗位异动标识
     */
    private String postFlag;

    /**
     * 岗位异动生效日期
     */
    private Date postEffectDate;

    /**
     * 岗位异动工作通知
     */
    private String msgld;

    /**
     * 是否扣税
     */
    private String istax;

    /**
     * 员工ID
     */
    private String empId;

    /**
     * 员工工号
     */
    private String empNo;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 版本号
     */
    private Long versionNo;

    /**
     * 是否定薪确认
     */
    private String isCheck;

    /**
     * 绩效类别
     */
    private String achievementType;

    /**
     * 是否最新
     */
    private String isNew;

    /**
     * 输入人
     */
    @Excel(name = "输入人")
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入人工号
     */
    private String creatorNo;

    /**
     * 输入日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPayCheckReaId(String payCheckReaId) {
        this.payCheckReaId = payCheckReaId;
    }

    public String getPayCheckReaId() {
        return payCheckReaId;
    }

    public void setPayCheckRea(String payCheckRea) {
        this.payCheckRea = payCheckRea;
    }

    public String getPayCheckRea() {
        return payCheckRea;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setMainPostName(String mainPostName) {
        this.mainPostName = mainPostName;
    }

    public String getMainPostName() {
        return mainPostName;
    }

    public void setPostLevel(String postLevel) {
        this.postLevel = postLevel;
    }

    public String getPostLevel() {
        return postLevel;
    }

    public void setEndPayDate(Date endPayDate) {
        this.endPayDate = endPayDate;
    }

    public Date getEndPayDate() {
        return endPayDate;
    }

    public void setPayCheckDesc(String payCheckDesc) {
        this.payCheckDesc = payCheckDesc;
    }

    public String getPayCheckDesc() {
        return payCheckDesc;
    }

    public void setSalBank(String salBank) {
        this.salBank = salBank;
    }

    public String getSalBank() {
        return salBank;
    }

    public void setSalBankId(String salBankId) {
        this.salBankId = salBankId;
    }

    public String getSalBankId() {
        return salBankId;
    }

    public void setSalBankNo(String salBankNo) {
        this.salBankNo = salBankNo;
    }

    public String getSalBankNo() {
        return salBankNo;
    }

    public void setBonBank(String bonBank) {
        this.bonBank = bonBank;
    }

    public String getBonBank() {
        return bonBank;
    }

    public void setBonBankId(String bonBankId) {
        this.bonBankId = bonBankId;
    }

    public String getBonBankId() {
        return bonBankId;
    }

    public void setBonBankNo(String bonBankNo) {
        this.bonBankNo = bonBankNo;
    }

    public String getBonBankNo() {
        return bonBankNo;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompName() {
        return compName;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostFlag(String postFlag) {
        this.postFlag = postFlag;
    }

    public String getPostFlag() {
        return postFlag;
    }

    public void setPostEffectDate(Date postEffectDate) {
        this.postEffectDate = postEffectDate;
    }

    public Date getPostEffectDate() {
        return postEffectDate;
    }

    public void setMsgld(String msgld) {
        this.msgld = msgld;
    }

    public String getMsgld() {
        return msgld;
    }

    public void setIstax(String istax) {
        this.istax = istax;
    }

    public String getIstax() {
        return istax;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setAchievementType(String achievementType) {
        this.achievementType = achievementType;
    }

    public String getAchievementType() {
        return achievementType;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorNo(String creatorNo) {
        this.creatorNo = creatorNo;
    }

    public String getCreatorNo() {
        return creatorNo;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("payCheckReaId", getPayCheckReaId())
                .append("payCheckRea", getPayCheckRea())
                .append("effectDate", getEffectDate())
                .append("mainPostName", getMainPostName())
                .append("postLevel", getPostLevel())
                .append("endPayDate", getEndPayDate())
                .append("payCheckDesc", getPayCheckDesc())
                .append("salBank", getSalBank())
                .append("salBankId", getSalBankId())
                .append("salBankNo", getSalBankNo())
                .append("bonBank", getBonBank())
                .append("bonBankId", getBonBankId())
                .append("bonBankNo", getBonBankNo())
                .append("compId", getCompId())
                .append("compName", getCompName())
                .append("postId", getPostId())
                .append("postFlag", getPostFlag())
                .append("postEffectDate", getPostEffectDate())
                .append("msgld", getMsgld())
                .append("istax", getIstax())
                .append("empId", getEmpId())
                .append("empNo", getEmpNo())
                .append("empName", getEmpName())
                .append("versionNo", getVersionNo())
                .append("isCheck", getIsCheck())
                .append("isNew", getIsNew())
                .append("achievementType", getAchievementType())
                .append("creator", getCreator())
                .append("creatorId", getCreatorId())
                .append("creatorNo", getCreatorNo())
                .append("createDate", getCreateDate())
                .toString();
    }
}
