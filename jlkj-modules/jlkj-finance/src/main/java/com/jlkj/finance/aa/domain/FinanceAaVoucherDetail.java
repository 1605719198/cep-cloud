package com.jlkj.finance.aa.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 凭证维护-明细对象 finance_aa_voucher_detail
 *
 * @author 265799
 * @date 2023-04-24
 */
@Data
public class FinanceAaVoucherDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;
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
    private BigDecimal ntamtC;
    private BigDecimal  ntamtD;
    /**
     * 主键
     */
    private String id;

    /**
     * 会计公司id
     */
    @Excel(name = "会计公司id")
    private String companyId;

    /**
     * 凭证id
     */
    @Excel(name = "凭证id")
    private String voucherId;

    /**
     * 凭证日期
     */
    @Excel(name = "凭证日期")
    private String voucherDate;

    /**
     * 凭证号码
     */
    @Excel(name = "凭证号码")
    private String voucherNo;

    /**
     * 分录序号
     */
    @Excel(name = "分录序号")
    private Long srlno;

    /**
     * 借贷方向
     */
    @Excel(name = "借贷方向")
    private String drcr;

    /**
     * 集团级会计科目主键
     */
    @Excel(name = "集团级会计科目主键")
    private String groupAcctId;

    /**
     * 会计科目id
     */
    @Excel(name = "会计科目id")
    private String acctId;

    /**
     * 会计科目编号
     */
    @Excel(name = "会计科目编号")
    private String acctCode;

    /**
     * 会计科目中文名称
     */
    @Excel(name = "会计科目中文名称")
    private String acctName;

    /**
     * 核算项目一id
     */
    @Excel(name = "核算项目一id")
    private String calIda;

    /**
     * 核算项目一代码
     */
    @Excel(name = "核算项目一代码")
    private String calCodea;

    /**
     * 核算项目一名称
     */
    @Excel(name = "核算项目一名称")
    private String calNamea;

    /**
     * 核算项目二id
     */
    @Excel(name = "核算项目二id")
    private String calIdb;

    /**
     * 核算项目二代码
     */
    @Excel(name = "核算项目二代码")
    private String calCodeb;

    /**
     * 核算项目二名称
     */
    @Excel(name = "核算项目二名称")
    private String calNameb;

    /**
     * 核算项目三id
     */
    @Excel(name = "核算项目三id")
    private String calIdc;

    /**
     * 核算项目三代码
     */
    @Excel(name = "核算项目三代码")
    private String calCodec;

    /**
     * 核算项目三名称
     */
    @Excel(name = "核算项目三名称")
    private String calNamec;

    /**
     * 核算项目四id
     */
    @Excel(name = "核算项目四id")
    private String calIdd;

    /**
     * 核算项目四代码
     */
    @Excel(name = "核算项目四代码")
    private String calCoded;

    /**
     * 核算项目四名称
     */
    @Excel(name = "核算项目四名称")
    private String calNamed;

    /**
     * 数量单位/币别
     */
    @Excel(name = "数量单位/币别")
    private String crcyUnit;

    /**
     * 数量/外币金额
     */
    @Excel(name = "数量/外币金额")
    private BigDecimal qtyFrnamt;

    /**
     * 记帐币金额
     */
    @Excel(name = "记帐币金额")
    private BigDecimal ntamt;

    /**
     * 凭证分录摘要
     */
    @Excel(name = "凭证分录摘要")
    private String srlDesc;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setSrlno(Long srlno) {
        this.srlno = srlno;
    }

    public Long getSrlno() {
        return srlno;
    }

    public void setDrcr(String drcr) {
        this.drcr = drcr;
    }

    public String getDrcr() {
        return drcr;
    }

    public void setGroupAcctId(String groupAcctId) {
        this.groupAcctId = groupAcctId;
    }

    public String getGroupAcctId() {
        return groupAcctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setCalIda(String calIda) {
        this.calIda = calIda;
    }

    public String getCalIda() {
        return calIda;
    }

    public void setCalCodea(String calCodea) {
        this.calCodea = calCodea;
    }

    public String getCalCodea() {
        return calCodea;
    }

    public void setCalNamea(String calNamea) {
        this.calNamea = calNamea;
    }

    public String getCalNamea() {
        return calNamea;
    }

    public void setCalIdb(String calIdb) {
        this.calIdb = calIdb;
    }

    public String getCalIdb() {
        return calIdb;
    }

    public void setCalCodeb(String calCodeb) {
        this.calCodeb = calCodeb;
    }

    public String getCalCodeb() {
        return calCodeb;
    }

    public void setCalNameb(String calNameb) {
        this.calNameb = calNameb;
    }

    public String getCalNameb() {
        return calNameb;
    }

    public void setCalIdc(String calIdc) {
        this.calIdc = calIdc;
    }

    public String getCalIdc() {
        return calIdc;
    }

    public void setCalCodec(String calCodec) {
        this.calCodec = calCodec;
    }

    public String getCalCodec() {
        return calCodec;
    }

    public void setCalNamec(String calNamec) {
        this.calNamec = calNamec;
    }

    public String getCalNamec() {
        return calNamec;
    }

    public void setCalIdd(String calIdd) {
        this.calIdd = calIdd;
    }

    public String getCalIdd() {
        return calIdd;
    }

    public void setCalCoded(String calCoded) {
        this.calCoded = calCoded;
    }

    public String getCalCoded() {
        return calCoded;
    }

    public void setCalNamed(String calNamed) {
        this.calNamed = calNamed;
    }

    public String getCalNamed() {
        return calNamed;
    }

    public void setCrcyUnit(String crcyUnit) {
        this.crcyUnit = crcyUnit;
    }

    public String getCrcyUnit() {
        return crcyUnit;
    }

    public void setQtyFrnamt(BigDecimal qtyFrnamt) {
        this.qtyFrnamt = qtyFrnamt;
    }

    public BigDecimal getQtyFrnamt() {
        return qtyFrnamt;
    }

    public void setNtamt(BigDecimal ntamt) {
        this.ntamt = ntamt;
    }

    public BigDecimal getNtamt() {
        return ntamt;
    }

    public void setSrlDesc(String srlDesc) {
        this.srlDesc = srlDesc;
    }

    public String getSrlDesc() {
        return srlDesc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("companyId", getCompanyId())
                .append("voucherId", getVoucherId())
                .append("voucherDate", getVoucherDate())
                .append("voucherNo", getVoucherNo())
                .append("srlno", getSrlno())
                .append("drcr", getDrcr())
                .append("groupAcctId", getGroupAcctId())
                .append("acctId", getAcctId())
                .append("acctCode", getAcctCode())
                .append("acctName", getAcctName())
                .append("calIda", getCalIda())
                .append("calCodea", getCalCodea())
                .append("calNamea", getCalNamea())
                .append("calIdb", getCalIdb())
                .append("calCodeb", getCalCodeb())
                .append("calNameb", getCalNameb())
                .append("calIdc", getCalIdc())
                .append("calCodec", getCalCodec())
                .append("calNamec", getCalNamec())
                .append("calIdd", getCalIdd())
                .append("calCoded", getCalCoded())
                .append("calNamed", getCalNamed())
                .append("crcyUnit", getCrcyUnit())
                .append("qtyFrnamt", getQtyFrnamt())
                .append("ntamt", getNtamt())
                .append("srlDesc", getSrlDesc())
                .toString();
    }
}
