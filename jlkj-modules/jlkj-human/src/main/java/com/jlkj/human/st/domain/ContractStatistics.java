package com.jlkj.human.st.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 劳动合同统计分析对象 human_hr_contract
 *
 * @author 267383
 * @date 2023-07-17
 */
public class ContractStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公司 */
    private String compId;

    /** 序号 */
    @Excel(name = "序号")
    private String num;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date1;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date2;

    /** 劳动关系状态 */
    private String relStatus;

    /** 签订合同日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签订合同日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signDate;

    /** 合同起始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同起始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 合同期限 */
    @Excel(name = "合同期限")
    private String conPeriId;

    /** 合同到期日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同到期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long versionNo;

    /** 更新人员ID */
    @Excel(name = "更新人员ID")
    private String creatorId;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 工号 */
    @Excel(name = "工号")
    private String jobNumber;

    /** 姓名 */
    @Excel(name = "姓名")
    private String fullName;

    /** 岗位id */
    private String postId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postName;

    /** 职工来源ID */
    private String sourceOfEmployees;

    /** 岗位类别id */
    private String postTypeId;

    /** 岗位序列id */
    private String postSequenceId;

    /** 所属公司/机构id */
    private Long orgId;

    /** 祖级列表 */
    private String ancestors;

    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setNum(String num)
    {
        this.num = num;
    }

    public String getNum()
    {
        return num;
    }
    public void setDate1(Date date1)
    {
        this.date1 = date1;
    }
    public Date getDate1()
    {
        return date1;
    }
    public void setDate2(Date date2)
    {
        this.date2 = date2;
    }
    public Date getDate2()
    {
        return date2;
    }
    public void setRelStatus(String relStatus)
    {
        this.relStatus = relStatus;
    }

    public String getRelStatus()
    {
        return relStatus;
    }
    public void setSignDate(Date signDate)
    {
        this.signDate = signDate;
    }

    public Date getSignDate()
    {
        return signDate;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setConPeriId(String conPeriId)
    {
        this.conPeriId = conPeriId;
    }

    public String getConPeriId()
    {
        return conPeriId;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    public void setVersionNo(Long versionNo)
    {
        this.versionNo = versionNo;
    }

    public Long getVersionNo()
    {
        return versionNo;
    }
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getCreatorId()
    {
        return creatorId;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }
    public void setJobNumber(String jobNumber)
    {
        this.jobNumber = jobNumber;
    }

    public String getJobNumber()
    {
        return jobNumber;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getFullName()
    {
        return fullName;
    }
    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    public String getPostId()
    {
        return postId;
    }
    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    public String getPostName()
    {
        return postName;
    }
    public void setSourceOfEmployees(String sourceOfEmployees)
    {
        this.sourceOfEmployees = sourceOfEmployees;
    }

    public String getSourceOfEmployees()
    {
        return sourceOfEmployees;
    }
    public void setPostTypeId(String postTypeId)
    {
        this.postTypeId = postTypeId;
    }

    public String getPostTypeId()
    {
        return postTypeId;
    }
    public void setPostSequenceId(String postSequenceId)
    {
        this.postSequenceId = postSequenceId;
    }

    public String getPostSequenceId()
    {
        return postSequenceId;
    }
    public void setOrgId(Long orgId)
    {
        this.orgId = orgId;
    }

    public Long getOrgId()
    {
        return orgId;
    }
    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("compId", getCompId())
            .append("relStatus", getRelStatus())
            .append("signDate", getSignDate())
            .append("startDate", getStartDate())
            .append("conPeriId", getConPeriId())
            .append("endDate", getEndDate())
            .append("versionNo", getVersionNo())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .append("jobNumber", getJobNumber())
            .append("fullName", getFullName())
            .append("postId", getPostId())
            .append("postName", getPostName())
            .append("sourceOfEmployees", getSourceOfEmployees())
            .append("postTypeId", getPostTypeId())
            .append("postSequenceId", getPostSequenceId())
            .append("orgId", getOrgId())
            .append("ancestors", getAncestors())
                .append("num", getNum())
                .append("date1", getDate1())
                .append("date2", getDate2())
            .toString();
    }
}
