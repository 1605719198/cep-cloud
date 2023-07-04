package com.jlkj.finance.ft.dto;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import com.jlkj.finance.ft.domain.FinanceFtAcctDetail;
import lombok.Data;

import java.util.List;

/**
 * 账务代码对象 finance_ft_acct_main
 *
 * @author SunXuTong
 * @date 2023-06-27
 */
@Data
public class FinanceFtAcctMainDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司 */
    private String companyId;

    /** 账务分类代码 */
    private String acctType;

    /** 账务代码 */
    @Excel(name = "账务代码")
    private String acctLogic;

    /** 账务代码明细 */
    private String acctDetail;

    /** 账务代码名称 */
    @Excel(name = "账务代码名称")
    private String logicName;

    /** 是否抛AA */
    @Excel(name = "是否抛AA")
    private String isAa;

    /** 备注 */
    @Excel(name = "备注")
    private String logicRemark;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;

    /** 账务资料表单数据 */
    private List<FinanceFtAcctDetail> acctMainListA;

}
