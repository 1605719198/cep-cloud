package com.jlkj.finance.ft.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 账务代码明细设定对象 finance_ft_acct_detail
 *
 * @author SunXuTong
 * @date 2023-06-30
 */
@Data
public class FinanceFtAcctDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId
    private String uuid;

    /** 公司 */
    private String companyId;

    /** 账务代码 */
    private String acctLogic;

    /** 序号 */
    private String seq;

    /** 借贷方 */
    @Excel(name = "借贷方")
    private String drCr;

    /** 会计科目 */
    @Excel(name = "会计科目")
    private String acctCode;

    /** 核算项目一 */
    @Excel(name = "核算项目一")
    private String calTypeCodea;

    /** 核算项目二 */
    @Excel(name = "核算项目二")
    private String calTypeCodeb;

    /** 核算项目三 */
    private String calTypeCodec;

    /** 核算项目四 */
    private String calTypeCoded;

    /** 金额类型 */
    @Excel(name = "金额类型")
    private String amtType;

    /** 到期日 */
    @Excel(name = "到期日")
    private String dueDate;

    /** 备注 */
    private String detailRemark;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;
}
