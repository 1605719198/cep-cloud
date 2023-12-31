package com.jlkj.finance.aa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wang'mai
 */
@Data
public class FinanceAaVoucherDTO {
    private static final long serialVersionUID = 1L;
    /** 凭证维护明细对象 */
    private List<FinanceAaVoucherDetail> detailList;
    private String voucherNoStart;

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
    /** 错误原因 */
    private String errorReason;
    /** 凭证号码 */
    private String voucherNo;
    /** 凭证日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "凭证日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date voucherDate;
    /** 凭证类型 */
    @Excel(name = "凭证类型")
    private String voucherType;

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
    @Excel(name = "单位/币别")
    private String crcyUnit;

    /**
     * 数量/外币金额
     */
    @Excel(name = "原币金额")
    private BigDecimal qtyFrnamt;
    /**
     * 借贷方向
     */
    @Excel(name = "借贷方向")
    private String drcr;
    /**
     * 记帐币金额
     */
    @Excel(name = "金额")
    private BigDecimal ntamt;
    /**
     * 凭证分录摘要
     */
    @Excel(name = "凭证分录摘要")
    private String srlDesc;
    /** 抛帐系统代号 */
    private String apid;





    /** 借方金额 */
    private BigDecimal drAmt;
    /** 贷方金额 */

    private BigDecimal crAmt;

    /** 经办人姓名 */

    private String pastuserName;
    /** 凭单张数 */

    private Long srlnoCount;
    /** 打印次数 */

    private Long printCount;
    /** 主键 */
    private String id;
    /** 会计公司id */
    private String companyId;
    /** 过帐凭证号码 */
    private String tradeNo;
    /** 抛帐程序名称 */

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

    private String status;

    /** 新增日期 */

    private String createDate;

    /** 异动日期 */

    private String updateDate;

    /** 新增人姓名 */

    private String createName;

    /** 异动人姓名 */

    private String updateName;






    private BigDecimal ntamtC;
    private BigDecimal  ntamtD;
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

    private String voucherId;



    /**
     * 分录序号
     */

    private Long srlno;


    /**
     * 集团级会计科目主键
     */
    private String groupAcctId;

    /**
     * 会计科目id
     */
    @Excel(name = "会计科目id")
    private String acctId;



    /**
     * 核算项目一id
     */

    private String calIda;



    /**
     * 核算项目二id
     */

    private String calIdb;



    /**
     * 核算项目三id
     */

    private String calIdc;

    /**
     * 核算项目四id
     */
    @Excel(name = "核算项目四id")
    private String calIdd;

}
