package com.jlkj.human.hs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 薪资核定导入DTO
 *
 * @author 266861
 * @date 2023-06-02
 */
@Data
public class PersonalSalaryDTO {

    /** 员工工号 */
    @Excel(name = "工号*")
    private String empNo;

    /** 员工姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 薪资核定原因编码* */
    @Excel(name = "薪资核定原因编码*")
    private String payCheckReaCode;

    /** 生效日期* */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期*", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /** 定薪单编号 */
    @Excel(name = "定薪单编号")
    private String payCheckCode;

    /** 定薪原因说明 */
    @Excel(name = "定薪原因说明")
    private String payCheckDesc;

    /** 工资支付银行编码* */
    @Excel(name = "工资支付银行编码*")
    private String salBankCode;

    /** 工资支付银行账号 */
    @Excel(name = "工资支付银行账号*")
    private String salBankNo;

    /** 奖金支付银行编码* */
    @Excel(name = "奖金支付银行编码*")
    private String bonBankCode;

    /** 奖金支付银行账号* */
    @Excel(name = "奖金支付银行账号*")
    private String bonBankNo;

    /** 列支薪等* */
    @Excel(name = "列支薪等*")
    private String salaryLevel;

    /** 基薪* */
    @Excel(name = "基薪*")
    private String basisSalary;

    /** 是否扣税* */
    @Excel(name = "是否扣税*")
    private String istax;

}
