package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 各公司薪资发放说明对象 human_hs_salary_explain
 *
 * @author 266861
 * @date 2023-05-25
 */
@Data
public class SalaryExplain extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司 */
    private String compId;

    /** 年月 */
    private String yearMonth;

    /** 月 */
    @Excel(name = "月")
    private String month;

    /** 年 */
    private Long year;

    /** 拟定工资到账日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拟定工资到账日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date salaryDate;

    /** 工资备注 */
    @Excel(name = "工资备注")
    private String salaryRemarks;

    /** 拟定奖金到账日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拟定奖金到账日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date bounsDate;

    /** 奖金备注 */
    @Excel(name = "奖金备注")
    private String bounsRemarks;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入人工号 */
    private String creatorNo;

    /** 输入日期 */
    private Date createDate;


}
