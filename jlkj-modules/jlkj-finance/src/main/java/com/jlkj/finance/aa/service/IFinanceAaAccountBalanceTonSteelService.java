package com.jlkj.finance.aa.service;

import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;

import java.util.List;

/**
 * 科目余额报表Service接口
 *
 * @author 265799
 * @date 2023-05-30
 */
public interface IFinanceAaAccountBalanceTonSteelService {
    /**
     * 查询科目余额报表
     *
     * @param financeAaLedgerAcctDTO 科目余额报表
     * @return 科目余额报表
     */
    public List<FinanceAaLedgerAcctDTO> selectListDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);

/**
* @description  查询报表
* @param financeAaLedgerAcctDTO financeAaLedgerAcctDTO
* @return java.util.List<com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO>
* @Author 114288
* @Date 2023/7/7 10:35
**/
    List<FinanceAaLedgerAcctDTO> reportListDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
}
