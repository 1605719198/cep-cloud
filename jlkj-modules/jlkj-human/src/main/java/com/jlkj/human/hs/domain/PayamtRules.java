package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 应付抛帐设定对象 human_hs_payamt_rules
 *
 * @author 116519
 * @date 2023-06-21
 */
public class PayamtRules extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 薪资项目ID */
    @Excel(name = "薪资项目ID")
    private String code;

    /** 费用类别ID */
    @Excel(name = "费用类别ID")
    private String apprgroup;

    /** 费用类别编码 */
    @Excel(name = "费用类别编码")
    private String infocode;

    /** 费用类别名称 */
    @Excel(name = "费用类别名称")
    private String infoname;

    /** 借方会计科目 */
    @Excel(name = "借方会计科目")
    private String acctcoded;

    /** 借方参考号码 */
    @Excel(name = "借方参考号码")
    private String efnod;

    /** 借方户号  */
    @Excel(name = "借方户号 ")
    private String idcoded;

    /** 借方到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借方到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date duedated;

    /** 贷方会计科目 */
    @Excel(name = "贷方会计科目")
    private String acctcodec;

    /** 贷方户号 */
    @Excel(name = "贷方户号")
    private String idcodec;

    /** 贷方参考号码 */
    @Excel(name = "贷方参考号码")
    private String refnoc;

    /** 贷方到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "贷方到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date duedatec;

    /** 公司 */
    @Excel(name = "公司")
    private String compId;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 输入人姓名 */
    @Excel(name = "输入人姓名")
    private String creatBy;

    /** 输入人工号 */
    @Excel(name = "输入人工号")
    private String creatNo;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatId;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setApprgroup(String apprgroup)
    {
        this.apprgroup = apprgroup;
    }

    public String getApprgroup()
    {
        return apprgroup;
    }
    public void setInfocode(String infocode)
    {
        this.infocode = infocode;
    }

    public String getInfocode()
    {
        return infocode;
    }
    public void setInfoname(String infoname)
    {
        this.infoname = infoname;
    }

    public String getInfoname()
    {
        return infoname;
    }
    public void setAcctcoded(String acctcoded)
    {
        this.acctcoded = acctcoded;
    }

    public String getAcctcoded()
    {
        return acctcoded;
    }
    public void setEfnod(String efnod)
    {
        this.efnod = efnod;
    }

    public String getEfnod()
    {
        return efnod;
    }
    public void setIdcoded(String idcoded)
    {
        this.idcoded = idcoded;
    }

    public String getIdcoded()
    {
        return idcoded;
    }
    public void setDuedated(Date duedated)
    {
        this.duedated = duedated;
    }

    public Date getDuedated()
    {
        return duedated;
    }
    public void setAcctcodec(String acctcodec)
    {
        this.acctcodec = acctcodec;
    }

    public String getAcctcodec()
    {
        return acctcodec;
    }
    public void setIdcodec(String idcodec)
    {
        this.idcodec = idcodec;
    }

    public String getIdcodec()
    {
        return idcodec;
    }
    public void setRefnoc(String refnoc)
    {
        this.refnoc = refnoc;
    }

    public String getRefnoc()
    {
        return refnoc;
    }
    public void setDuedatec(Date duedatec)
    {
        this.duedatec = duedatec;
    }

    public Date getDuedatec()
    {
        return duedatec;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setCreatBy(String creatBy)
    {
        this.creatBy = creatBy;
    }

    public String getCreatBy()
    {
        return creatBy;
    }
    public void setCreatNo(String creatNo)
    {
        this.creatNo = creatNo;
    }

    public String getCreatNo()
    {
        return creatNo;
    }
    public void setCreatId(String creatId)
    {
        this.creatId = creatId;
    }

    public String getCreatId()
    {
        return creatId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("apprgroup", getApprgroup())
            .append("infocode", getInfocode())
            .append("infoname", getInfoname())
            .append("acctcoded", getAcctcoded())
            .append("efnod", getEfnod())
            .append("idcoded", getIdcoded())
            .append("duedated", getDuedated())
            .append("acctcodec", getAcctcodec())
            .append("idcodec", getIdcodec())
            .append("refnoc", getRefnoc())
            .append("duedatec", getDuedatec())
            .append("compId", getCompId())
            .append("status", getStatus())
            .append("creatBy", getCreatBy())
            .append("creatNo", getCreatNo())
            .append("creatId", getCreatId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
