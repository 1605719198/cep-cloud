package com.jlkj.finance.aa.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 凭证维护-主对象 finance_aa_voucher
 *
 * @author 265799
 * @date 2023-04-24
 */
@Data
public class FinanceAaVoucher extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /** 凭证维护明细对象 */
    private List<FinanceAaVoucherDetail> detailList;
    private String voucherNoStart;
    private String voucherType;
    private String voucherNoEnd;
    private String startDate;
    private String endDate;
    private String acctNameStart;
    private String acctNameEnd;
    private String calNameaStart;
    private String calNameaEnd;
    private String calNamebStart;
    private String calNamebEnd;
    private String qtyFrnamtStart;
    private String qtyFrnamtEnd;
    /** 凭证号码 */
    @Excel(name = "凭证号码")
    private String voucherNo;
    /** 凭证日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "凭证日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date voucherDate;

    /** 抛帐系统代号 */
    @Excel(name = "抛帐系统代号")
    private String apid;
    /**
     * 凭证分录摘要
     */
    @Excel(name = "摘要")
    private String srlDesc;
    /**
     * 会计科目编号
     */
    @Excel(name = "科目代码")
    private String acctCode;

    /**
     * 会计科目中文名称
     */
    @Excel(name = "科目名称")
    private String acctName;
    /**
     * 数量单位/币别
     */
    @Excel(name = "单位/币别")
    private String crcyUnit;

    /**
     * 数量/外币金额
     */
    @Excel(name = "原币金额")
    private BigDecimal qtyFrnamt;


    /** 借方金额 */
    @Excel(name = "借方金额")
    private BigDecimal drAmt;
    /** 贷方金额 */
    @Excel(name = "贷方金额")
    private BigDecimal crAmt;
    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal ntamt;

    /** 经办人姓名 */
    @Excel(name = "经办人姓名")
    private String pastuserName;
    /** 凭单张数 */
    @Excel(name = "附件数")
    private Long srlnoCount;
    /** 打印次数 */
    @Excel(name = "打印次数")
    private Long printCount;




    /** 主键 */
    private String id;

    /** 会计公司id */
    private String companyId;




    /** 过帐凭证号码 */

    private String tradeNo;


    /** 抛帐程序名称 */
    @Excel(name = "抛帐程序名称")
    private String pgrmid;

    /** 凭证摘要说明 */

    private String voucherDesc;

    /** 业务单据编号 */

    private String billNo;

    /** 业务单据查询地址 */

    private String billUrl;


    /** 经办人工号 */

    private String pastuserId;



    /** 出纳工号 */

    private String moneyuserId;

    /** 出纳姓名 */

    private String moneyuserName;

    /** 过账人工号 */

    private String potstuserId;

    /** 过账人名称 */

    private String potstuserName;

    /** 过账日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date postDate;

    /** 过账时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date postTime;



    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 新增日期 */
    @Excel(name = "新增日期")
    private String createDate;

    /** 异动日期 */
    @Excel(name = "异动日期")
    private String updateDate;

    /** 新增人姓名 */
    @Excel(name = "新增人姓名")
    private String createName;

    /** 异动人姓名 */
    @Excel(name = "异动人姓名")
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
    public void setVoucherDate(Date voucherDate)
    {
        this.voucherDate = voucherDate;
    }

    public Date getVoucherDate()
    {
        return voucherDate;
    }
    public void setVoucherNo(String voucherNo)
    {
        this.voucherNo = voucherNo;
    }

    public String getVoucherNo()
    {
        return voucherNo;
    }
    public void setTradeNo(String tradeNo)
    {
        this.tradeNo = tradeNo;
    }

    public String getTradeNo()
    {
        return tradeNo;
    }
    public void setApid(String apid)
    {
        this.apid = apid;
    }

    public String getApid()
    {
        return apid;
    }
    public void setPgrmid(String pgrmid)
    {
        this.pgrmid = pgrmid;
    }

    public String getPgrmid()
    {
        return pgrmid;
    }
    public void setVoucherDesc(String voucherDesc)
    {
        this.voucherDesc = voucherDesc;
    }

    public String getVoucherDesc()
    {
        return voucherDesc;
    }
    public void setBillNo(String billNo)
    {
        this.billNo = billNo;
    }

    public String getBillNo()
    {
        return billNo;
    }
    public void setBillUrl(String billUrl)
    {
        this.billUrl = billUrl;
    }

    public String getBillUrl()
    {
        return billUrl;
    }
    public void setPrintCount(Long printCount)
    {
        this.printCount = printCount;
    }

    public Long getPrintCount()
    {
        return printCount;
    }
    public void setSrlnoCount(Long srlnoCount)
    {
        this.srlnoCount = srlnoCount;
    }

    public Long getSrlnoCount()
    {
        return srlnoCount;
    }
    public void setPastuserId(String pastuserId)
    {
        this.pastuserId = pastuserId;
    }

    public String getPastuserId()
    {
        return pastuserId;
    }
    public void setPastuserName(String pastuserName)
    {
        this.pastuserName = pastuserName;
    }

    public String getPastuserName()
    {
        return pastuserName;
    }
    public void setMoneyuserId(String moneyuserId)
    {
        this.moneyuserId = moneyuserId;
    }

    public String getMoneyuserId()
    {
        return moneyuserId;
    }
    public void setMoneyuserName(String moneyuserName)
    {
        this.moneyuserName = moneyuserName;
    }

    public String getMoneyuserName()
    {
        return moneyuserName;
    }
    public void setPotstuserId(String potstuserId)
    {
        this.potstuserId = potstuserId;
    }

    public String getPotstuserId()
    {
        return potstuserId;
    }
    public void setPotstuserName(String potstuserName)
    {
        this.potstuserName = potstuserName;
    }

    public String getPotstuserName()
    {
        return potstuserName;
    }
    public void setPostDate(Date postDate)
    {
        this.postDate = postDate;
    }

    public Date getPostDate()
    {
        return postDate;
    }
    public void setPostTime(Date postTime)
    {
        this.postTime = postTime;
    }

    public Date getPostTime()
    {
        return postTime;
    }
    public void setDrAmt(BigDecimal drAmt)
    {
        this.drAmt = drAmt;
    }

    public BigDecimal getDrAmt()
    {
        return drAmt;
    }
    public void setCrAmt(BigDecimal crAmt)
    {
        this.crAmt = crAmt;
    }

    public BigDecimal getCrAmt()
    {
        return crAmt;
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
            .append("voucherDate", getVoucherDate())
            .append("voucherNo", getVoucherNo())
            .append("tradeNo", getTradeNo())
            .append("apid", getApid())
            .append("pgrmid", getPgrmid())
            .append("voucherDesc", getVoucherDesc())
            .append("billNo", getBillNo())
            .append("billUrl", getBillUrl())
            .append("printCount", getPrintCount())
            .append("srlnoCount", getSrlnoCount())
            .append("pastuserId", getPastuserId())
            .append("pastuserName", getPastuserName())
            .append("moneyuserId", getMoneyuserId())
            .append("moneyuserName", getMoneyuserName())
            .append("potstuserId", getPotstuserId())
            .append("potstuserName", getPotstuserName())
            .append("postDate", getPostDate())
            .append("postTime", getPostTime())
            .append("drAmt", getDrAmt())
            .append("crAmt", getCrAmt())
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
