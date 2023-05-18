package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 社保公积金缴费比例设定对象 human_hs_social_security
 *
 * @author jiangbingchen
 * @date 2023-05-11
 */
public class SocialSecurity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 社保公积金项目ID */
    @Excel(name = "社保公积金项目ID")
    private Long salaryProjectId;

    /** 单位缴纳比例（%） */
    @Excel(name = "单位缴纳比例", readConverterExp = "%=")
    private String comPro;

    /** 单位附加金额 */
    @Excel(name = "单位附加金额")
    private String comMon;

    /** 个人缴纳比例（%） */
    @Excel(name = "个人缴纳比例", readConverterExp = "%=")
    private String perPro;

    /** 个人附加金额 */
    @Excel(name = "个人附加金额")
    private String perMon;

    /** 基数上限 */
    @Excel(name = "基数上限")
    private String baseUcl;

    /** 基数下限 */
    @Excel(name = "基数下限")
    private String baseLl;

    /** 小数位数 */
    @Excel(name = "小数位数")
    private String deciBit;

    /** 进位方式 */
    @Excel(name = "进位方式")
    private String carryMode;

    /** 缴费地区ID */
    private String payAreaId;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /** 版本号 */
    private String version;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

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
    public void setSalaryProjectId(Long salaryProjectId)
    {
        this.salaryProjectId = salaryProjectId;
    }

    public Long getSalaryProjectId()
    {
        return salaryProjectId;
    }
    public void setComPro(String comPro)
    {
        this.comPro = comPro;
    }

    public String getComPro()
    {
        return comPro;
    }
    public void setComMon(String comMon)
    {
        this.comMon = comMon;
    }

    public String getComMon()
    {
        return comMon;
    }
    public void setPerPro(String perPro)
    {
        this.perPro = perPro;
    }

    public String getPerPro()
    {
        return perPro;
    }
    public void setPerMon(String perMon)
    {
        this.perMon = perMon;
    }

    public String getPerMon()
    {
        return perMon;
    }
    public void setBaseUcl(String baseUcl)
    {
        this.baseUcl = baseUcl;
    }

    public String getBaseUcl()
    {
        return baseUcl;
    }
    public void setBaseLl(String baseLl)
    {
        this.baseLl = baseLl;
    }

    public String getBaseLl()
    {
        return baseLl;
    }
    public void setDeciBit(String deciBit)
    {
        this.deciBit = deciBit;
    }

    public String getDeciBit()
    {
        return deciBit;
    }
    public void setCarryMode(String carryMode)
    {
        this.carryMode = carryMode;
    }

    public String getCarryMode()
    {
        return carryMode;
    }
    public void setPayAreaId(String payAreaId)
    {
        this.payAreaId = payAreaId;
    }

    public String getPayAreaId()
    {
        return payAreaId;
    }
    public void setEffectDate(Date effectDate)
    {
        this.effectDate = effectDate;
    }

    public Date getEffectDate()
    {
        return effectDate;
    }
    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getVersion()
    {
        return version;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("salaryProjectId", getSalaryProjectId())
            .append("comPro", getComPro())
            .append("comMon", getComMon())
            .append("perPro", getPerPro())
            .append("perMon", getPerMon())
            .append("baseUcl", getBaseUcl())
            .append("baseLl", getBaseLl())
            .append("deciBit", getDeciBit())
            .append("carryMode", getCarryMode())
            .append("payAreaId", getPayAreaId())
            .append("effectDate", getEffectDate())
            .append("version", getVersion())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
