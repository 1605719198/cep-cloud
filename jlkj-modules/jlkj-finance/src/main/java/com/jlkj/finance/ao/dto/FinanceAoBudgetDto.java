package com.jlkj.finance.ao.dto;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 预算设置对象 finance_ao_budget
 *
 * @author jlkj
 * @date 2023-06-16
 */
@Data
public class FinanceAoBudgetDto extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 年度 */
    @Excel(name = "年度")
    private String year;

    /** 部门编码 */
    @Excel(name = "部门编码")
    private String deptNo;

    /** 预算类别 */
    @Excel(name = "预算类别")
    private String budgetType;

    /** 年月 */
    private String yarMonth;

    /** 预算值 */
    @Excel(name = "预算值")
    private BigDecimal janBudget;

    /** 状态 */
    private String status;

    /** 新增人姓名 */
    private String createName;

    /** 异动人姓名 */
    private String updateName;


}
