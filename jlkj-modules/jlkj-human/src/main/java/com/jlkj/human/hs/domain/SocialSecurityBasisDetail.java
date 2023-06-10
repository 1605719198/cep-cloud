package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 社保公积金标准核定明细对象 human_hs_social_security_basis_detail
 *
 * @author 266861
 * @date 2023-05-31
 */
public class SocialSecurityBasisDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 标识哪个社保公积金标准核定主项 */
    @Excel(name = "标识哪个社保公积金标准核定主项")
    private String socialSecurityId;

    /** 该公司社保公积金项目 */
    @Excel(name = "该公司社保公积金项目")
    private String payProId;

    /** 该公司社保公积金项目名称 */
    @Excel(name = "该公司社保公积金项目名称")
    private String payProName;

    /** 该公司社保公积金项目编码 */
    @Excel(name = "该公司社保公积金项目编码")
    private String payProCode;

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

    /** 第4列标题 */
    @Excel(name = "第4列标题")
    private String fourTitle;

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

    /** 第八列标题 */
    @Excel(name = "第八列标题")
    private String eigTitle;

    /** 第九列是否下拉选单 */
    @Excel(name = "第九列是否下拉选单")
    private String ninIsLov;

    /** 第九列值 */
    @Excel(name = "第九列值")
    private String ninSta;

    /** 第九列ID值 */
    @Excel(name = "第九列ID值")
    private String ninIdSta;

    /** 第十列标题 */
    @Excel(name = "第十列标题")
    private String tenTitle;

    /** 第十一列是否下拉选单 */
    @Excel(name = "第十一列是否下拉选单")
    private String eleIsLov;

    /** 第十一列值 */
    @Excel(name = "第十一列值")
    private String eleSta;

    /** 第十一列ID值 */
    @Excel(name = "第十一列ID值")
    private String eleIdSta;

    /** 顺序号 */
    @Excel(name = "顺序号")
    private String serialNo;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 输入人工号 */
    @Excel(name = "输入人工号")
    private String creatorNo;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setSocialSecurityId(String socialSecurityId)
    {
        this.socialSecurityId = socialSecurityId;
    }

    public String getSocialSecurityId()
    {
        return socialSecurityId;
    }
    public void setPayProId(String payProId)
    {
        this.payProId = payProId;
    }

    public String getPayProId()
    {
        return payProId;
    }
    public void setPayProName(String payProName)
    {
        this.payProName = payProName;
    }

    public String getPayProName()
    {
        return payProName;
    }
    public void setPayProCode(String payProCode)
    {
        this.payProCode = payProCode;
    }

    public String getPayProCode()
    {
        return payProCode;
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
    public void setFourTitle(String fourTitle)
    {
        this.fourTitle = fourTitle;
    }

    public String getFourTitle()
    {
        return fourTitle;
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
    public void setEigTitle(String eigTitle)
    {
        this.eigTitle = eigTitle;
    }

    public String getEigTitle()
    {
        return eigTitle;
    }
    public void setNinIsLov(String ninIsLov)
    {
        this.ninIsLov = ninIsLov;
    }

    public String getNinIsLov()
    {
        return ninIsLov;
    }
    public void setNinSta(String ninSta)
    {
        this.ninSta = ninSta;
    }

    public String getNinSta()
    {
        return ninSta;
    }
    public void setNinIdSta(String ninIdSta)
    {
        this.ninIdSta = ninIdSta;
    }

    public String getNinIdSta()
    {
        return ninIdSta;
    }
    public void setTenTitle(String tenTitle)
    {
        this.tenTitle = tenTitle;
    }

    public String getTenTitle()
    {
        return tenTitle;
    }
    public void setEleIsLov(String eleIsLov)
    {
        this.eleIsLov = eleIsLov;
    }

    public String getEleIsLov()
    {
        return eleIsLov;
    }
    public void setEleSta(String eleSta)
    {
        this.eleSta = eleSta;
    }

    public String getEleSta()
    {
        return eleSta;
    }
    public void setEleIdSta(String eleIdSta)
    {
        this.eleIdSta = eleIdSta;
    }

    public String getEleIdSta()
    {
        return eleIdSta;
    }
    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }

    public String getSerialNo()
    {
        return serialNo;
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
            .append("socialSecurityId", getSocialSecurityId())
            .append("payProId", getPayProId())
            .append("payProName", getPayProName())
            .append("payProCode", getPayProCode())
            .append("twoTitle", getTwoTitle())
            .append("thrIsLov", getThrIsLov())
            .append("thrSta", getThrSta())
            .append("thrIdSta", getThrIdSta())
            .append("fourTitle", getFourTitle())
            .append("fivIsLov", getFivIsLov())
            .append("fivSta", getFivSta())
            .append("fivIdSta", getFivIdSta())
            .append("sixTitle", getSixTitle())
            .append("sevIsLov", getSevIsLov())
            .append("sevSta", getSevSta())
            .append("sevIdSta", getSevIdSta())
            .append("eigTitle", getEigTitle())
            .append("ninIsLov", getNinIsLov())
            .append("ninSta", getNinSta())
            .append("ninIdSta", getNinIdSta())
            .append("tenTitle", getTenTitle())
            .append("eleIsLov", getEleIsLov())
            .append("eleSta", getEleSta())
            .append("eleIdSta", getEleIdSta())
            .append("serialNo", getSerialNo())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("creatorNo", getCreatorNo())
            .append("createDate", getCreateDate())
            .toString();
    }
}
