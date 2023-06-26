package com.jlkj.finance.api.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 凭证接口-主对象 finance_aa_voucher
 *
 * @author 114288
 * @date 2023-06-26
 */
@Data
public class FinanceVoucherBean extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /** 凭证维护明细对象 */
    private List<FinanceVoucherDetailBean> detailList;
    /**凭证类别  T-机器 M-转账 P-付款 R-收款 */
    private String voucherType;

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
    /** 经办人姓名 */
    @Excel(name = "经办人姓名")
    private String pastuserName;
    /** 凭单张数 */
    @Excel(name = "附件数")
    private Long srlnoCount;
    /** 主键 */
    private String id;
    /** 会计公司id */
    private String companyId;
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
    /** 状态 */
    @Excel(name = "状态")
    private String status;
    /** 新增日期 */
    @Excel(name = "新增日期")
    private String createDate;
    /** 新增人姓名 */
    @Excel(name = "新增人姓名")
    private String createName;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("voucherDate", getVoucherDate())
            .append("apid", getApid())
            .append("pgrmid", getPgrmid())
            .append("voucherDesc", getVoucherDesc())
            .append("billNo", getBillNo())
            .append("billUrl", getBillUrl())
            .append("srlnoCount", getSrlnoCount())
            .append("pastuserId", getPastuserId())
            .append("pastuserName", getPastuserName())
            .append("moneyuserId", getMoneyuserId())
            .append("moneyuserName", getMoneyuserName())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createDate", getCreateDate())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createName", getCreateName())

            .toString();
    }
}
