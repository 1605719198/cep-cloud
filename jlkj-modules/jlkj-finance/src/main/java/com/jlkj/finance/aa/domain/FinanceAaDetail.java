package com.jlkj.finance.aa.domain;

import com.jlkj.finance.aa.dto.FinanceAaCashflowCodeDTO;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

import java.util.List;

/**
 * 系统选单-明细设定对象 finance_aa_detail
 *
 * @author 265799
 * @date 2023-04-19
 */
@Data
public class FinanceAaDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /** 现金流量代码 */
    private List<FinanceAaDetail> detailList;
    /** id */
    private String id;

    /** 会计公司id */
    @Excel(name = "会计公司id")
    private String companyId;

    /** 节点指示码 */
    @Excel(name = "节点指示码")
    private String leafId;

    /** 项次一名称 */
    @Excel(name = "项次一名称")
    private String itema;

    /** 项次二名称 */
    @Excel(name = "项次二名称")
    private String itemb;

    /** 项次三名称 */
    @Excel(name = "项次三名称")
    private String itemc;

    /** 项次四名称 */
    @Excel(name = "项次四名称")
    private String itemd;

    /** 项次五名称 */
    @Excel(name = "项次五名称")
    private String iteme;

    /** 资料一名称 */
    @Excel(name = "资料一名称")
    private String dataa;

    /** 资料二名称 */
    @Excel(name = "资料二名称")
    private String datab;

    /** 资料三名称 */
    @Excel(name = "资料三名称")
    private String datac;

    /** 资料四名称 */
    @Excel(name = "资料四名称")
    private String datad;

    /** 资料五名称 */
    @Excel(name = "资料五名称")
    private String datae;

    /** 资料六名称 */
    @Excel(name = "资料六名称")
    private String dataf;

    /** 资料七名称 */
    @Excel(name = "资料七名称")
    private String datag;

    /** 排列指示 */
    private Long dataSeq;

    /** 是否启用 */
    private String status;

    /** 新增日期 */
    private String createDate;

    /** 异动日期 */
    private String updateDate;

    /** 新增人姓名 */
    private String createName;

    /** 异动人姓名 */
    private String updateName;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setLeafId(String leafId)
    {
        this.leafId = leafId;
    }

    public String getLeafId()
    {
        return leafId;
    }
    public void setItema(String itema)
    {
        this.itema = itema;
    }

    public String getItema()
    {
        return itema;
    }
    public void setItemb(String itemb)
    {
        this.itemb = itemb;
    }

    public String getItemb()
    {
        return itemb;
    }
    public void setItemc(String itemc)
    {
        this.itemc = itemc;
    }

    public String getItemc()
    {
        return itemc;
    }
    public void setItemd(String itemd)
    {
        this.itemd = itemd;
    }

    public String getItemd()
    {
        return itemd;
    }
    public void setIteme(String iteme)
    {
        this.iteme = iteme;
    }

    public String getIteme()
    {
        return iteme;
    }
    public void setDataa(String dataa)
    {
        this.dataa = dataa;
    }

    public String getDataa()
    {
        return dataa;
    }
    public void setDatab(String datab)
    {
        this.datab = datab;
    }

    public String getDatab()
    {
        return datab;
    }
    public void setDatac(String datac)
    {
        this.datac = datac;
    }

    public String getDatac()
    {
        return datac;
    }
    public void setDatad(String datad)
    {
        this.datad = datad;
    }

    public String getDatad()
    {
        return datad;
    }
    public void setDatae(String datae)
    {
        this.datae = datae;
    }

    public String getDatae()
    {
        return datae;
    }
    public void setDataf(String dataf)
    {
        this.dataf = dataf;
    }

    public String getDataf()
    {
        return dataf;
    }
    public void setDatag(String datag)
    {
        this.datag = datag;
    }

    public String getDatag()
    {
        return datag;
    }
    public void setDataSeq(Long dataSeq)
    {
        this.dataSeq = dataSeq;
    }

    public Long getDataSeq()
    {
        return dataSeq;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    public String getCreateDate()
    {
        return createDate;
    }
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getUpdateDate()
    {
        return updateDate;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("leafId", getLeafId())
            .append("itema", getItema())
            .append("itemb", getItemb())
            .append("itemc", getItemc())
            .append("itemd", getItemd())
            .append("iteme", getIteme())
            .append("dataa", getDataa())
            .append("datab", getDatab())
            .append("datac", getDatac())
            .append("datad", getDatad())
            .append("datae", getDatae())
            .append("dataf", getDataf())
            .append("datag", getDatag())
            .append("dataSeq", getDataSeq())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createDate", getCreateDate())
            .append("updateBy", getUpdateBy())
            .append("updateDate", getUpdateDate())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createName", getCreateName())
            .append("updateName", getUpdateName())
            .toString();
    }
}
