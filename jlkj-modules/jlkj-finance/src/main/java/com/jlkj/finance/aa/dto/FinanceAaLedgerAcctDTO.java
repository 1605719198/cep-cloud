package com.jlkj.finance.aa.dto;

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
    /** 期初贷余额 */

    private  BigDecimal bgnAmtBurden;
    /** 期初借余额 */

    private  BigDecimal bgnAmtStraight;

    /** 期初借数量 */

    private  BigDecimal  bgnQtyStraight;
    /** 期初贷数量 */

    private  BigDecimal  bgnQtyBurden;
    /** 期末余额借余额 */

    private  BigDecimal endingBalanceDr;
    /** 期末余额贷余额 */

    private  BigDecimal endingBalanceCr;
    /** 期末余额借数量 */

    private  BigDecimal endingBalanceQtyDr;
    /** 期末余额贷数量 */

    private  BigDecimal endingBalanceQtyCr;
    /** 起始编码 */
    private String  acctCodeStart;
    /** 截止编码 */
    private String  acctCodeEnd;
    /** 起始日期 */
    private String  startDate;
    /** 截止日期 */
    private String  endDate;
    /** 科目级别 */
    private String  accountLevel;
    /** 核算项目起始 */
    private String calNameaStart;
    /** 核算项目终止 */
    private String calNameaEnd;
    /** 是否含未过账凭证 */
    private String  unpostedVoucher;
    /** 无发生额不显示 */
    private String  amountNotDisplayed;
    /** 科目类型 */
    private String  reportType;
    /** 余额为零且无发生额不显示 */
    private String   balanceZero;
    /** 核算项目一 */
    private String   calNamea;
    /** 核算项目二 */
    private String   calNameb;
    /** 核算项目三 */
    private String   calNamec;
    /** 核算项目四 */
    private String   calNamed;


    /** 主键 */
    private String id;

    /** 会计公司id */

    private String companyId;

    /** 会计周期 */

    private String acctPeriod;
    /** 会计周期 */

    private String acctCode;

    /** 会计科目id */

    private String acctId;

    /** 会计科目中文名称 */

    private String acctName;

    /** 期初余额 */

    private BigDecimal bgnAmt;

    /** 借方发生额 */

    private BigDecimal drAmt;

    /** 贷方发生额 */

    private BigDecimal crAmt;

    /** 期初余数量 */
  private BigDecimal bgnQty;

    /** 借方发生数量 */

    private BigDecimal drQty;

    /** 贷方发生数量 */

    private BigDecimal crQty;

    /** 本年累计借方发生额 */

    private BigDecimal yearDrAmt;

    /** 本年累计贷方发生额 */

    private BigDecimal yearCrAmt;

    /** 本年累计借方发生数量 */

    private BigDecimal yearDrQty;

    /** 本年累计贷方发生数量 */
    private BigDecimal yearCrQty;
}
