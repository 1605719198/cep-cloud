package com.jlkj.finance.aa.dto;

import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账薄-会计科目余额对象 finance_aa_ledger_acct
 *
 * @author jlkj
 * @date 2023-04-29
 */
@Data
public class FinanceAaLedgerAcctDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 记帐币金额
     */

    private BigDecimal ntAmt;
    /**
     * 期初贷余额
     */

    private BigDecimal bgnAmtBurden;
    /**
     * 期初借余额
     */

    private BigDecimal bgnAmtStraight;

    /**
     * 期初借数量
     */

    private BigDecimal bgnQtyStraight;
    /**
     * 期初贷数量
     */

    private BigDecimal bgnQtyBurden;
    /**
     * 期末余额借余额
     */

    private BigDecimal endingBalanceDr;
    /**
     * 期末余额贷余额
     */

    private BigDecimal endingBalanceCr;
    /**
     * 期末余额借数量
     */

    private BigDecimal endingBalanceQtyDr;
    /**
     * 期末余额贷数量
     */

    private BigDecimal endingBalanceQtyCr;
    /**
     * 起始编码
     */
    private String acctCodeStart;
    /**
     * 截止编码
     */
    private String acctCodeEnd;
    /**
     * 起始日期
     */
    private String startDate;
    /**
     * 截止日期
     */
    private String endDate;

    /**
     * 起始日期
     */
    private String startDetailDate;
    /**
     * 截止日期
     */
    private String endDetailDate;
    /**
     * 科目级别
     */
    private String accountLevel;
    /**
     * 核算项目起始
     */
    private String calNameaStart;
    /**
     * 核算项目终止
     */
    private String calNameaEnd;
    /**
     * 是否含未过账凭证
     */
    private String unpostedVoucher;
    /**
     * 无发生额不显示
     */
    private String amountNotDisplayed;
    /**
     * 科目类型
     */
    private String reportType;
    /**
     * 余额为零且无发生额不显示
     */
    private String balanceZero;
    /**
     * 核算项目一
     */
    private String calCodea;
    /**
     * 核算项目一
     */
    private String calNamea;
    /**
     * 核算项目二
     */
    private String calNameb;
    /**
     * 核算项目三
     */
    private String calNamec;
    /**
     * 核算项目四
     */
    private String calNamed;
    /**
     * 是否显示本期合计
     */
    private String currentPeriod;
    /**
     * 是否显示本年累计
     */
    private String yearAdd;
    /**
     * 核算项目
     */
    private String calName;
    /**
     * 数量
     */
    private BigDecimal endOfPeriodQty;
    /**
     * 金额
     */
    private BigDecimal endOfPeriodAmt;
    /**
     * 是否显示主项
     */
    private String MainItems;
    /**
     * 主键
     */
    private String id;

    /**
     * 会计公司id
     */

    private String companyId;

    /**
     * 会计周期
     */

    private String acctPeriod;
    /**
     * 会计科目
     */

    private String acctCode;

    /**
     * 会计科目id
     */

    private String acctId;

    /**
     * 会计科目中文名称
     */

    private String acctName;

    /**
     * 期初余额
     */

    private BigDecimal bgnAmt;

    /**
     * 借方发生额
     */

    private BigDecimal drAmt;

    /**
     * 贷方发生额
     */

    private BigDecimal crAmt;

    /**
     * 期初余数量
     */
    private BigDecimal bgnQty;

    /**
     * 借方发生数量
     */

    private BigDecimal drQty;

    /**
     * 贷方发生数量
     */

    private BigDecimal crQty;

    /**
     * 本年累计借方发生额
     */

    private BigDecimal yearDrAmt;

    /**
     * 本年累计贷方发生额
     */

    private BigDecimal yearCrAmt;

    /**
     * 本年累计借方发生数量
     */

    private BigDecimal yearDrQty;

    /**
     * 本年累计贷方发生数量
     */
    private BigDecimal yearCrQty;

    /**
     * 只印有交易资料
     */

    private String transactionalData;

    /**
     * 凭证日期
     */
    private String voucherDate;

    /**
     * 会计科目
     */
    private String acctNameStart;
    /**
     * 会计科目
     */
    private String acctNameEnd;
    /**
     * 核算项目类别
     */
    private String calTypeCode;

    /**
     * 凭证号码
     */
    private String voucherNoStart;
    /**
     * 凭证号码
     */
    private String voucherNoEnd;
    /**
     * 借贷方
     */
    private String debitOrCredit;
    /**
     * 显示核算项目
     */
    private String accountingItems;
    /**
     * 金额
     */
    private BigDecimal amountStart;
    /**
     * 金额
     */
    private BigDecimal amountEnd;
    /**
     * 数量或外币
     */
    private BigDecimal qtyFrnamtStart;
    /**
     * 数量或外币
     */
    private BigDecimal qtyFrnamtEnd;
    /**
     * 是否显示数量
     */
    private String isNoNumber;
    /**
     * 贷方金额
     */
    private BigDecimal ntamtC;
    /**
     * 借方金额
     */
    private BigDecimal  ntamtD;
    /**
     *   借方数量/外币金额
     */

    private BigDecimal qtyFrnamtD;
    /**
     *   贷方数量/外币金额
     */
    private BigDecimal qtyFrnamtC;

    /**
     * 借贷方向
     */

    private String drcr;
    /**
     *   余额数量/外币金额
     */
    private BigDecimal  qtyFrnamtBalance;
    /**
     *   余额
     */
    private BigDecimal  balance;


    private String calNamebStart;
    private String calNamebEnd;

    /** 核算项目1编号 */

    private String calTypeCodea;
    /** 核算项目2编号 */

    private String calTypeCodeb;
    /** 核算项目3编号 */

    private String calTypeCodec;
    /** 核算项目4编号 */

    private String calTypeCoded;


    /**
     * 凭证id
     */
    @Excel(name = "凭证id")
    private String voucherId;


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
     * 集团级会计科目主键
     */
    @Excel(name = "集团级会计科目主键")
    private String groupAcctId;




    /**
     * 核算项目一id
     */
    @Excel(name = "核算项目一id")
    private String calIda;



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
     * 数量单位/币别
     */

    private String crcyUnit;

    /**
     * 数量/外币金额
     */

    private BigDecimal qtyFrnamt;



    /**
     * 凭证分录摘要
     */

    private String srlDesc;
}
