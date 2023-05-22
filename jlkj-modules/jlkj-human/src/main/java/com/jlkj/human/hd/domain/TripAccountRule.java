package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 出差会计科目对象 human_hd_trip_account_rule
 *
 * @author 266861
 * @date 2023-04-23
 */
public class TripAccountRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 会计类别 */
    @Excel(name = "会计类别")
    private String acctType;

    /** 费用类别 */
    @Excel(name = "费用类别")
    private String apprGroup;

    /** 类别说明 */
    @Excel(name = "类别说明")
    private String acctDesc;

    /** 类别分类 */
    @Excel(name = "类别分类")
    private String acctClassify;

    /** 借方会计科目 */
    @Excel(name = "借方会计科目")
    private String acctCoded;

    /** 借方户号 */
    @Excel(name = "借方户号")
    private String idCoded;

    /** 借方参号 */
    @Excel(name = "借方参号")
    private String refNod;

    /** 借方到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借方到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDated;

    /** 贷方会计科目 */
    @Excel(name = "贷方会计科目")
    private String acctCodec;

    /** 贷方户号 */
    @Excel(name = "贷方户号")
    private String idCodec;

    /** 贷方参号 */
    @Excel(name = "贷方参号")
    private String refNoc;

    /** 贷方到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "贷方到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDatec;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
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
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setAcctType(String acctType)
    {
        this.acctType = acctType;
    }

    public String getAcctType()
    {
        return acctType;
    }
    public void setApprGroup(String apprGroup)
    {
        this.apprGroup = apprGroup;
    }

    public String getApprGroup()
    {
        return apprGroup;
    }
    public void setAcctDesc(String acctDesc)
    {
        this.acctDesc = acctDesc;
    }

    public String getAcctDesc()
    {
        return acctDesc;
    }
    public void setAcctClassify(String acctClassify)
    {
        this.acctClassify = acctClassify;
    }

    public String getAcctClassify()
    {
        return acctClassify;
    }
    public void setAcctCoded(String acctCoded)
    {
        this.acctCoded = acctCoded;
    }

    public String getAcctCoded()
    {
        return acctCoded;
    }
    public void setIdCoded(String idCoded)
    {
        this.idCoded = idCoded;
    }

    public String getIdCoded()
    {
        return idCoded;
    }
    public void setRefNod(String refNod)
    {
        this.refNod = refNod;
    }

    public String getRefNod()
    {
        return refNod;
    }
    public void setDueDated(Date dueDated)
    {
        this.dueDated = dueDated;
    }

    public Date getDueDated()
    {
        return dueDated;
    }
    public void setAcctCodec(String acctCodec)
    {
        this.acctCodec = acctCodec;
    }

    public String getAcctCodec()
    {
        return acctCodec;
    }
    public void setIdCodec(String idCodec)
    {
        this.idCodec = idCodec;
    }

    public String getIdCodec()
    {
        return idCodec;
    }
    public void setRefNoc(String refNoc)
    {
        this.refNoc = refNoc;
    }

    public String getRefNoc()
    {
        return refNoc;
    }
    public void setDueDatec(Date dueDatec)
    {
        this.dueDatec = dueDatec;
    }

    public Date getDueDatec()
    {
        return dueDatec;
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
            .append("compId", getCompId())
            .append("acctType", getAcctType())
            .append("apprGroup", getApprGroup())
            .append("acctDesc", getAcctDesc())
            .append("acctClassify", getAcctClassify())
            .append("acctCoded", getAcctCoded())
            .append("idCoded", getIdCoded())
            .append("refNod", getRefNod())
            .append("dueDated", getDueDated())
            .append("acctCodec", getAcctCodec())
            .append("idCodec", getIdCodec())
            .append("refNoc", getRefNoc())
            .append("dueDatec", getDueDatec())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
