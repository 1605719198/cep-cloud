package com.jlkj.finance.api.bean;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 凭证接品-明细对象 finance_aa_voucher_detail
 *
 * @author 114288
 * @date 2023-06-26
 */
@Data
public class FinanceVoucherDetailBean extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String voucherType;

    /** 核算项目1编号 */
    private String calTypeCodea;
    /** 核算项目2编号 */
    private String calTypeCodeb;
    /** 核算项目3编号 */
    private String calTypeCodec;
    /** 核算项目4编号 */
    private String calTypeCoded;
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
