package com.jlkj.finance.api.domain;

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
public class FinanceAaLedgerAcctVo {
    private static final long serialVersionUID = 1L;
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
     * 核算项目
     */
    private String calName;



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
     * 贷方发生额
     */

    private BigDecimal crAmt;



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


    /** 抛帐系统代号 */

    private String apid;

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
