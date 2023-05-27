package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 各公司所得税起征点设定对象 human_hs_income_tax
 *
 * @author 116519
 * @date 2023-05-16
 */
public class IncomeTax extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 员工类别ID */
    @Excel(name = "员工类别ID")
    private String empTypeId;

    /** 月扣除数 */
    @Excel(name = "月扣除数")
    private String deductNum;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long version;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String resvAttr1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String resvAttr2;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 输入人姓名 */
    @Excel(name = "输入人姓名")
    private String creatorName;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    private List<IncomeTax> incometaxDetailListList = new ArrayList<IncomeTax>();



    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setEmpTypeId(String empTypeId)
    {
        this.empTypeId = empTypeId;
    }

    public String getEmpTypeId()
    {
        return empTypeId;
    }
    public void setDeductNum(String deductNum)
    {
        this.deductNum = deductNum;
    }

    public String getDeductNum()
    {
        return deductNum;
    }
    public void setEffectDate(Date effectDate)
    {
        this.effectDate = effectDate;
    }

    public Date getEffectDate()
    {
        return effectDate;
    }
    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
    }
    public void setResvAttr1(String resvAttr1)
    {
        this.resvAttr1 = resvAttr1;
    }

    public String getResvAttr1()
    {
        return resvAttr1;
    }
    public void setResvAttr2(String resvAttr2)
    {
        this.resvAttr2 = resvAttr2;
    }

    public String getResvAttr2()
    {
        return resvAttr2;
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
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }

    public String getCreatorName()
    {
        return creatorName;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public List<IncomeTax> getIncometaxDetailListList() {
        return incometaxDetailListList;
    }

    public void setIncometaxDetailListList(List<IncomeTax> incometaxDetailListList) {
        this.incometaxDetailListList = incometaxDetailListList;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uuid", getUuid())
            .append("compId", getCompId())
            .append("empTypeId", getEmpTypeId())
            .append("deductNum", getDeductNum())
            .append("remark", getRemark())
            .append("effectDate", getEffectDate())
            .append("version", getVersion())
            .append("resvAttr1", getResvAttr1())
            .append("resvAttr2", getResvAttr2())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("creatorName", getCreatorName())
            .append("createDate", getCreateDate())
            .toString();
    }
}
