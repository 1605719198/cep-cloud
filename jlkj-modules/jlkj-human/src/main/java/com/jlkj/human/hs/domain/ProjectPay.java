package com.jlkj.human.hs.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 薪酬项目对象 human_hs_salary_project_basis
 *
 * @author 266861
 * @date 2023-05-11
 */
public class ProjectPay extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 薪酬项目编码 */
    @Excel(name = "薪酬项目编码")
    private String payProCode;

    /** 薪酬项目名称 */
    @Excel(name = "薪酬项目名称")
    private String payProName;

    /** 是否需设定标准 */
    @Excel(name = "是否需设定标准")
    private String isSta;

    /** 标准的内容 */
    @Excel(name = "标准的内容")
    private String staCon;

    /** 是否岗位定薪项目 */
    @Excel(name = "是否岗位定薪项目")
    private Long isPostPro;

    /** 是否员工定薪项目 */
    @Excel(name = "是否员工定薪项目")
    private String isEmpPro;

    /** 是否下拉选单 */
    @Excel(name = "是否下拉选单")
    private String isLov;

    /** 下拉选单的内容ID */
    @Excel(name = "下拉选单的内容ID")
    private String lovConId;

    /** 说明 */
    @Excel(name = "说明")
    private String salaryDescribe;

    /** 父结点ID */
    private Long parentid;

    /** 祖籍关系 */
    private String parents;

    /** 薪酬项目类别 */
    private String payType;

    /** 是否显示编码 */
    private String isShowno;

    /** 排序 */
    private String num;

    /** 默认值 */
    private String defaultValue;

    /** 状态 */
    private String status;

    /** 该代码是否在使用 */
    private String ifUsed;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 子节点 */
    private List<ProjectPay> children = new ArrayList<ProjectPay>();

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setPayProCode(String payProCode)
    {
        this.payProCode = payProCode;
    }

    public String getPayProCode()
    {
        return payProCode;
    }
    public void setPayProName(String payProName)
    {
        this.payProName = payProName;
    }

    public String getPayProName()
    {
        return payProName;
    }
    public void setIsSta(String isSta)
    {
        this.isSta = isSta;
    }

    public String getIsSta()
    {
        return isSta;
    }
    public void setStaCon(String staCon)
    {
        this.staCon = staCon;
    }

    public String getStaCon()
    {
        return staCon;
    }
    public void setIsPostPro(Long isPostPro)
    {
        this.isPostPro = isPostPro;
    }

    public Long getIsPostPro()
    {
        return isPostPro;
    }
    public void setIsEmpPro(String isEmpPro)
    {
        this.isEmpPro = isEmpPro;
    }

    public String getIsEmpPro()
    {
        return isEmpPro;
    }
    public void setIsLov(String isLov)
    {
        this.isLov = isLov;
    }

    public String getIsLov()
    {
        return isLov;
    }
    public void setLovConId(String lovConId)
    {
        this.lovConId = lovConId;
    }

    public String getLovConId()
    {
        return lovConId;
    }
    public void setSalaryDescribe(String salaryDescribe)
    {
        this.salaryDescribe = salaryDescribe;
    }

    public String getSalaryDescribe()
    {
        return salaryDescribe;
    }
    public void setParentid(Long parentid)
    {
        this.parentid = parentid;
    }

    public Long getParentid()
    {
        return parentid;
    }
    public void setParents(String parents)
    {
        this.parents = parents;
    }

    public String getParents()
    {
        return parents;
    }
    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayType()
    {
        return payType;
    }
    public void setIsShowno(String isShowno)
    {
        this.isShowno = isShowno;
    }

    public String getIsShowno()
    {
        return isShowno;
    }
    public void setNum(String num)
    {
        this.num = num;
    }

    public String getNum()
    {
        return num;
    }
    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue()
    {
        return defaultValue;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setIfUsed(String ifUsed)
    {
        this.ifUsed = ifUsed;
    }

    public String getIfUsed()
    {
        return ifUsed;
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
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }
    public List<ProjectPay> getChildren()
    {
        return children;
    }

    public void setChildren(List<ProjectPay> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("payProCode", getPayProCode())
            .append("payProName", getPayProName())
            .append("isSta", getIsSta())
            .append("staCon", getStaCon())
            .append("isPostPro", getIsPostPro())
            .append("isEmpPro", getIsEmpPro())
            .append("isLov", getIsLov())
            .append("lovConId", getLovConId())
            .append("salaryDescribe", getSalaryDescribe())
            .append("parentid", getParentid())
            .append("parents", getParents())
            .append("payType", getPayType())
            .append("isShowno", getIsShowno())
            .append("num", getNum())
            .append("defaultValue", getDefaultValue())
            .append("status", getStatus())
            .append("ifUsed", getIfUsed())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
