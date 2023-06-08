package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 社保公积金标准核定对象 human_hs_social_security_basis
 *
 * @author 266861
 * @date 2023-05-30
 */
public class SocialSecurityBasis extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 社保核定原因
     */
    @Excel(name = "社保核定原因")
    private String insureRea;

    /**
     * 社保核定原因ID
     */
    private String insureReaId;

    /**
     * 保险编号
     */
    private String insureNo;

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
    private String posLevel;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;

    /**
     * 最高学历
     */
    private String lastExper;

    /**
     * 参加工作日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date joinWorkDate;

    /**
     * 民族
     */
    private String nation;

    /**
     * 户籍所在地
     */
    private String reg;

    /**
     * 户口性质
     */
    private String regFea;

    /**
     * 证件号码
     */
    private String cerNo;

    /**
     * 入企日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String entCorDate;

    /**
     * 现居住地
     */
    private String nowAddr;

    /**
     * 联系电话（手机）
     */
    private String mobPhone;

    /**
     * 基本工资
     */
    private BigDecimal basicPay;

    /**
     * 离职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaveEffectDate;

    /**
     * 职工身份
     */
    private String empIde;

    /**
     * 长期工作地
     */
    private String workAddr;

    /**
     * 用工性质
     */
    private String empFea;

    /**
     * 社保缴费地类别
     */
    private String payAreaType;

    /**
     * 社保缴费地类别ID
     */
    private String payAreaTypeId;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 员工姓名
     */
    @Excel(name = "员工姓名")
    private String empName;

    /**
     * 员工工号
     */
    @Excel(name = "员工工号")
    private String empNo;

    /**
     * 员工ID
     */
    @Excel(name = "员工ID")
    private String empId;

    /**
     * 版本号
     */
    private Long versionNo;

    /**
     * 信息通知ID
     */
    private String msgid;

    /**
     * 医保编号
     */
    private String medicalInsuranceNo;

    /**
     * 公司保险手册编号
     */
    private String compInsureBookNo;

    /**
     * 住房公积金账号
     */
    private String houseAccount;

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
    @Excel(name = "输入人ID")
    private String creatorId;

    /**
     * 输入人工号
     */
    @Excel(name = "输入人工号")
    private String creatorNo;

    /**
     * 输入日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 社保公积金明细
     */
    private List<SocialSecurityBasisDetail> detailList = new ArrayList<SocialSecurityBasisDetail>();

    public void setDetailList(List<SocialSecurityBasisDetail> detailList) {
        this.detailList = detailList;
    }

    public List<SocialSecurityBasisDetail> getDetailList() {
        return detailList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setInsureRea(String insureRea) {
        this.insureRea = insureRea;
    }

    public String getInsureRea() {
        return insureRea;
    }

    public void setInsureReaId(String insureReaId) {
        this.insureReaId = insureReaId;
    }

    public String getInsureReaId() {
        return insureReaId;
    }

    public void setInsureNo(String insureNo) {
        this.insureNo = insureNo;
    }

    public String getInsureNo() {
        return insureNo;
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

    public void setPosLevel(String posLevel) {
        this.posLevel = posLevel;
    }

    public String getPosLevel() {
        return posLevel;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setLastExper(String lastExper) {
        this.lastExper = lastExper;
    }

    public String getLastExper() {
        return lastExper;
    }

    public void setJoinWorkDate(Date joinWorkDate) {
        this.joinWorkDate = joinWorkDate;
    }

    public Date getJoinWorkDate() {
        return joinWorkDate;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNation() {
        return nation;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getReg() {
        return reg;
    }

    public void setRegFea(String regFea) {
        this.regFea = regFea;
    }

    public String getRegFea() {
        return regFea;
    }

    public void setCerNo(String cerNo) {
        this.cerNo = cerNo;
    }

    public String getCerNo() {
        return cerNo;
    }

    public void setEntCorDate(String entCorDate) {
        this.entCorDate = entCorDate;
    }

    public String getEntCorDate() {
        return entCorDate;
    }

    public void setNowAddr(String nowAddr) {
        this.nowAddr = nowAddr;
    }

    public String getNowAddr() {
        return nowAddr;
    }

    public void setMobPhone(String mobPhone) {
        this.mobPhone = mobPhone;
    }

    public String getMobPhone() {
        return mobPhone;
    }

    public void setBasicPay(BigDecimal basicPay) {
        this.basicPay = basicPay;
    }

    public BigDecimal getBasicPay() {
        return basicPay;
    }

    public void setLeaveEffectDate(Date leaveEffectDate) {
        this.leaveEffectDate = leaveEffectDate;
    }

    public Date getLeaveEffectDate() {
        return leaveEffectDate;
    }

    public void setEmpIde(String empIde) {
        this.empIde = empIde;
    }

    public String getEmpIde() {
        return empIde;
    }

    public void setWorkAddr(String workAddr) {
        this.workAddr = workAddr;
    }

    public String getWorkAddr() {
        return workAddr;
    }

    public void setEmpFea(String empFea) {
        this.empFea = empFea;
    }

    public String getEmpFea() {
        return empFea;
    }

    public void setPayAreaType(String payAreaType) {
        this.payAreaType = payAreaType;
    }

    public String getPayAreaType() {
        return payAreaType;
    }

    public void setPayAreaTypeId(String payAreaTypeId) {
        this.payAreaTypeId = payAreaTypeId;
    }

    public String getPayAreaTypeId() {
        return payAreaTypeId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompId() {
        return compId;
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

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMedicalInsuranceNo(String medicalInsuranceNo) {
        this.medicalInsuranceNo = medicalInsuranceNo;
    }

    public String getMedicalInsuranceNo() {
        return medicalInsuranceNo;
    }

    public void setCompInsureBookNo(String compInsureBookNo) {
        this.compInsureBookNo = compInsureBookNo;
    }

    public String getCompInsureBookNo() {
        return compInsureBookNo;
    }

    public void setHouseAccount(String houseAccount) {
        this.houseAccount = houseAccount;
    }

    public String getHouseAccount() {
        return houseAccount;
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
                .append("insureRea", getInsureRea())
                .append("insureReaId", getInsureReaId())
                .append("insureNo", getInsureNo())
                .append("effectDate", getEffectDate())
                .append("mainPostName", getMainPostName())
                .append("posLevel", getPosLevel())
                .append("birthDate", getBirthDate())
                .append("lastExper", getLastExper())
                .append("joinWorkDate", getJoinWorkDate())
                .append("nation", getNation())
                .append("reg", getReg())
                .append("regFea", getRegFea())
                .append("cerNo", getCerNo())
                .append("entCorDate", getEntCorDate())
                .append("nowAddr", getNowAddr())
                .append("mobPhone", getMobPhone())
                .append("basicPay", getBasicPay())
                .append("leaveEffectDate", getLeaveEffectDate())
                .append("empIde", getEmpIde())
                .append("workAddr", getWorkAddr())
                .append("empFea", getEmpFea())
                .append("payAreaType", getPayAreaType())
                .append("payAreaTypeId", getPayAreaTypeId())
                .append("compId", getCompId())
                .append("empNo", getEmpNo())
                .append("empId", getEmpId())
                .append("empName", getEmpName())
                .append("versionNo", getVersionNo())
                .append("msgid", getMsgid())
                .append("medicalInsuranceNo", getMedicalInsuranceNo())
                .append("compInsureBookNo", getCompInsureBookNo())
                .append("houseAccount", getHouseAccount())
                .append("isNew", getIsNew())
                .append("creator", getCreator())
                .append("creatorId", getCreatorId())
                .append("creatorNo", getCreatorNo())
                .append("createDate", getCreateDate())
                .append("detailList", getDetailList())
                .toString();
    }
}
