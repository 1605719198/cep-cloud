package com.jlkj.human.hs.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 个人薪资核定明细对象 human_hs_salary_detail
 *
 * @author 266861
 * @date 2023-06-01
 */
public class PersonalSalaryDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 标识哪个薪资标准核定主项 */
    @Excel(name = "标识哪个薪资标准核定主项")
    private String salaryId;

    /** 该公司薪资项目ID */
    @Excel(name = "该公司薪资项目ID")
    private String payCheckRea;

    /** 该公司薪资项目名称 */
    @Excel(name = "该公司薪资项目名称")
    private String payProName;

    /** 第二列标题 */
    @Excel(name = "第二列标题")
    private String twoTitle;

    /** 第三列是否下拉选单 */
    @Excel(name = "第三列是否下拉选单")
    private String thrIsLov;

    /** 第三列值 */
    @Excel(name = "第三列值")
    private String thrSta;

    /** 第三列ID值 */
    @Excel(name = "第三列ID值")
    private String thrIdSta;

    /** 第四列标题 */
    @Excel(name = "第四列标题")
    private String foutTitle;

    /** 第五列是否下拉选单 */
    @Excel(name = "第五列是否下拉选单")
    private String fivIsLov;

    /** 第五列值 */
    @Excel(name = "第五列值")
    private String fivSta;

    /** 第五列ID值 */
    @Excel(name = "第五列ID值")
    private String fivIdSta;

    /** 第六列标题 */
    @Excel(name = "第六列标题")
    private String sixTitle;

    /** 第七列是否下拉选单 */
    @Excel(name = "第七列是否下拉选单")
    private String sevIsLov;

    /** 第七列值 */
    @Excel(name = "第七列值")
    private String sevSta;

    /** 第七列ID值 */
    @Excel(name = "第七列ID值")
    private String sevIdSta;

    /** 顺序号 */
    @Excel(name = "顺序号")
    private String serialNo;

    /** 岗位异动标识 */
    @Excel(name = "岗位异动标识")
    private String postFlag;

    /** 岗位异动生效日期 */
    @Excel(name = "岗位异动生效日期")
    private String postEffectDate;

    /** 岗位异动工作通知 */
    @Excel(name = "岗位异动工作通知")
    private String msgld;

    /** 是否扣税 */
    @Excel(name = "是否扣税")
    private String istax;

    /** 是否定薪项目 */
    @Excel(name = "是否定薪项目")
    private String isEmpPro;

    /** 下拉选单ID */
    @Excel(name = "下拉选单ID")
    private String lovConId;

    /** 是否基薪 */
    @Excel(name = "是否基薪")
    private String isSalaryBasis;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private String empId;

    /** 员工工号 */
    @Excel(name = "员工工号")
    private String empNo;

    /** 版本号 */
    @Excel(name = "版本号")
    private String versionNo;

    /** 是否定薪确认 */
    @Excel(name = "是否定薪确认")
    private String isCheck;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 输入人工号 */
    @Excel(name = "输入人工号")
    private String creatorNo;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createDate;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setSalaryId(String salaryId)
    {
        this.salaryId = salaryId;
    }

    public String getSalaryId()
    {
        return salaryId;
    }
    public void setPayCheckRea(String payCheckRea)
    {
        this.payCheckRea = payCheckRea;
    }

    public String getPayCheckRea()
    {
        return payCheckRea;
    }
    public void setPayProName(String payProName)
    {
        this.payProName = payProName;
    }

    public String getPayProName()
    {
        return payProName;
    }
    public void setTwoTitle(String twoTitle)
    {
        this.twoTitle = twoTitle;
    }

    public String getTwoTitle()
    {
        return twoTitle;
    }
    public void setThrIsLov(String thrIsLov)
    {
        this.thrIsLov = thrIsLov;
    }

    public String getThrIsLov()
    {
        return thrIsLov;
    }
    public void setThrSta(String thrSta)
    {
        this.thrSta = thrSta;
    }

    public String getThrSta()
    {
        return thrSta;
    }
    public void setThrIdSta(String thrIdSta)
    {
        this.thrIdSta = thrIdSta;
    }

    public String getThrIdSta()
    {
        return thrIdSta;
    }
    public void setFoutTitle(String foutTitle)
    {
        this.foutTitle = foutTitle;
    }

    public String getFoutTitle()
    {
        return foutTitle;
    }
    public void setFivIsLov(String fivIsLov)
    {
        this.fivIsLov = fivIsLov;
    }

    public String getFivIsLov()
    {
        return fivIsLov;
    }
    public void setFivSta(String fivSta)
    {
        this.fivSta = fivSta;
    }

    public String getFivSta()
    {
        return fivSta;
    }
    public void setFivIdSta(String fivIdSta)
    {
        this.fivIdSta = fivIdSta;
    }

    public String getFivIdSta()
    {
        return fivIdSta;
    }
    public void setSixTitle(String sixTitle)
    {
        this.sixTitle = sixTitle;
    }

    public String getSixTitle()
    {
        return sixTitle;
    }
    public void setSevIsLov(String sevIsLov)
    {
        this.sevIsLov = sevIsLov;
    }

    public String getSevIsLov()
    {
        return sevIsLov;
    }
    public void setSevSta(String sevSta)
    {
        this.sevSta = sevSta;
    }

    public String getSevSta()
    {
        return sevSta;
    }
    public void setSevIdSta(String sevIdSta)
    {
        this.sevIdSta = sevIdSta;
    }

    public String getSevIdSta()
    {
        return sevIdSta;
    }
    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }

    public String getSerialNo()
    {
        return serialNo;
    }
    public void setPostFlag(String postFlag)
    {
        this.postFlag = postFlag;
    }

    public String getPostFlag()
    {
        return postFlag;
    }
    public void setPostEffectDate(String postEffectDate)
    {
        this.postEffectDate = postEffectDate;
    }

    public String getPostEffectDate()
    {
        return postEffectDate;
    }
    public void setMsgld(String msgld)
    {
        this.msgld = msgld;
    }

    public String getMsgld()
    {
        return msgld;
    }
    public void setIstax(String istax)
    {
        this.istax = istax;
    }

    public String getIstax()
    {
        return istax;
    }
    public void setIsEmpPro(String isEmpPro)
    {
        this.isEmpPro = isEmpPro;
    }

    public String getIsEmpPro()
    {
        return isEmpPro;
    }
    public void setLovConId(String lovConId)
    {
        this.lovConId = lovConId;
    }

    public String getLovConId()
    {
        return lovConId;
    }
    public void setIsSalaryBasis(String isSalaryBasis)
    {
        this.isSalaryBasis = isSalaryBasis;
    }

    public String getIsSalaryBasis()
    {
        return isSalaryBasis;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getEmpId()
    {
        return empId;
    }
    public void setEmpNo(String empNo)
    {
        this.empNo = empNo;
    }

    public String getEmpNo()
    {
        return empNo;
    }
    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
    }

    public String getVersionNo()
    {
        return versionNo;
    }
    public void setIsCheck(String isCheck)
    {
        this.isCheck = isCheck;
    }

    public String getIsCheck()
    {
        return isCheck;
    }
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreator()
    {
        return creator;
    }
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getCreatorId()
    {
        return creatorId;
    }
    public void setCreatorNo(String creatorNo)
    {
        this.creatorNo = creatorNo;
    }

    public String getCreatorNo()
    {
        return creatorNo;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("salaryId", getSalaryId())
            .append("payCheckRea", getPayCheckRea())
            .append("payProName", getPayProName())
            .append("twoTitle", getTwoTitle())
            .append("thrIsLov", getThrIsLov())
            .append("thrSta", getThrSta())
            .append("thrIdSta", getThrIdSta())
            .append("foutTitle", getFoutTitle())
            .append("fivIsLov", getFivIsLov())
            .append("fivSta", getFivSta())
            .append("fivIdSta", getFivIdSta())
            .append("sixTitle", getSixTitle())
            .append("sevIsLov", getSevIsLov())
            .append("sevSta", getSevSta())
            .append("sevIdSta", getSevIdSta())
            .append("serialNo", getSerialNo())
            .append("postFlag", getPostFlag())
            .append("postEffectDate", getPostEffectDate())
            .append("msgld", getMsgld())
            .append("istax", getIstax())
            .append("isEmpPro", getIsEmpPro())
            .append("lovConId", getLovConId())
            .append("isSalaryBasis", getIsSalaryBasis())
            .append("compId", getCompId())
            .append("empId", getEmpId())
            .append("empNo", getEmpNo())
            .append("versionNo", getVersionNo())
            .append("isCheck", getIsCheck())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("creatorNo", getCreatorNo())
            .append("createDate", getCreateDate())
            .toString();
    }
}
