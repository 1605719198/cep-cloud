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
     * 查询凭=科目余额报表
     *
     * @param financeAaLedgerAcctDTO 科目余额报表
     * @return 科目余额报表
     */
    public List<FinanceAaLedgerAcctDTO> selectListDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询凭=科目余额报表
     *
     * @param financeAaLedgerAcctDTO 科目余额报表
     * @return 科目余额报表
     */
    public List<FinanceAaLedgerAcctDTO> selectListNumberDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);

    /**
     * 查询凭=科目余额报表
     *
     * @param financeAaLedgerAcctDTO 科目余额报表
     * @return 科目余额报表
     */
    public List<FinanceAaLedgerAcctDTO> selectListCalNumberDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询凭=科目余额报表
     *
     * @param financeAaLedgerAcctDTO 科目余额报表
     * @return 科目余额报表
     */
    public List<FinanceAaLedgerAcctDTO> selectlistCalDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);

}
