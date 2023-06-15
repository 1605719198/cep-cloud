package com.jlkj.finance.ap.dto;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 报支类别集团细项对象 finance_ap_item_detail
 *
 * @author SunXuTong
 * @date 2023-05-31
 */
@Data
public class FinanceApItemDetailDTO extends BaseEntity
{

    /** 主键 */
    private String id;

    /** 报支类别编码 */
    private String itemNo;

    /** 细项编码 */
    @Excel(name = "细项编码")
    private String detailNo;

    /** 细项名称 */
    @Excel(name = "细项名称")
    private String detailName;

    /** 费用类别 */
    @Excel(name = "费用类别")
    private String expenseType;

    /** 借方科目 */
    @Excel(name = "借方科目")
    private String drAcctCode;

    /** 贷方科目 */
    @Excel(name = "贷方科目")
    private String crAcctCode;

    /** 预算类别 */
    private String budgetType;

    /** 成本科目 */
    private String costCode;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;

    /** 异动名称 */
    private String companyId;
}
