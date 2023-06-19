package com.jlkj.human.hs.dto;

import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

/**
 * 银行账号导入DTO
 *
 * @author 266861
 * @date 2023-06-12
 */
@Data
public class PersonalSalaryBankDTO {

    /** 员工工号 */
    @Excel(name = "工号*")
    private String empNo;

    /** 员工姓名 */
    @Excel(name = "姓名")
    private String empName;

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



}
