package com.jlkj.human.hs.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 薪资计算结果对象 human_hs_salary_result
 *
 * @author 266861
 * @date 2023-06-20
 */
@Data
public class SalaryResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司ID */
    private String compId;

    /** 员工工号 */
    @Excel(name = "员工工号")
    private String empNo;

    /** 员工ID */
    private String empId;

    /** 机构ID */
    @Excel(name = "机构ID")
    private String orgId;

    /** 各公司薪资表项目ID */
    private String payTableId;

    /** 薪资表项目编码 */
    private String payTableCode;

    /** 薪资表项目名称 */
    private String payTableName;

    /** 计算结果 */
    private BigDecimal result;

    /** 月 */
    @Excel(name = "月")
    private Long month;

    /** 年 */
    @Excel(name = "年")
    private Long year;

    /** 薪资类别ID */
    private String payTypeId;

    /** 岗位全称 */
    private String postFullName;

    /** 职位等级 */
    private String postLevel;

    /** 工资条显示位置 */
    private String salarySheetLocation;

    /** 预留属性1 */
    private String resvAttr1;

    /** 预留属性2 */
    private String resvAttr2;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入人工号 */
    private String creatorNo;

    /** 输入日期 */
    private Date createDate;

}
