package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaLedgerAcct;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;

import java.util.List;

/**
 * 账薄-会计科目余额Mapper接口
 * 
 * @author jlkj
 * @date 2023-04-29
 */
public interface FinanceAaLedgerAcctMapper 
{
    /**
     * 查询账薄-会计科目余额
     * 
     * @param id 账薄-会计科目余额主键
     * @return 账薄-会计科目余额
     */
    public FinanceAaLedgerAcct selectFinanceAaLedgerAcctById(String id);
    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-会计科目余额
     * @return 账薄-会计科目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectListDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-会计科目余额
     * @return 账薄-会计科目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectAmountNotDisplayed(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-会计科目余额
     * @return 账薄-会计科目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectFinanceAaGeneralLedger(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-会计科目余额
     * @return 账薄-会计科目余额集合
     */
    public String[] selectAcctCode(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-会计科目余额
     * @return 账薄-会计科目余额集合
     */
    public FinanceAaLedgerAcctDTO selectBgnAmtAndBgnQty(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);

    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-会计科目余额
     * @return 账薄-会计科目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectFinanceAaGeneralLedgerAcctCode1(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-会计科目余额
     * @return 账薄-会计科目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectFinanceAaGeneralLedgerCalName(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcctDTO 账薄-会计科目余额
     * @return 账薄-会计科目余额集合
     */
    public List<FinanceAaLedgerAcctDTO> selectBalanceZero(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
    /**
     * 查询账薄-会计科目余额列表
     * 
     * @param financeAaLedgerAcct 账薄-会计科目余额
     * @return 账薄-会计科目余额集合
     */
    public List<FinanceAaLedgerAcct> selectFinanceAaLedgerAcctList(FinanceAaLedgerAcct financeAaLedgerAcct);
    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcct 账薄-会计科目余额
     * @return 账薄-会计科目余额集合
     */
    public List<FinanceAaLedgerAcct> selectLedgerAcctPeriodList(FinanceAaLedgerAcct financeAaLedgerAcct);
    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcct 账薄-会计科目余额
     * @return 账薄-会计科目余额集合
     */
    public FinanceAaLedgerAcct selectFinanceAaLedgerAcct(FinanceAaLedgerAcct financeAaLedgerAcct);

    /**
     * 新增账薄-会计科目余额
     * 
     * @param financeAaLedgerAcct 账薄-会计科目余额
     * @return 结果
     */
    public int insertFinanceAaLedgerAcct(FinanceAaLedgerAcct financeAaLedgerAcct);
    /**
     * 新增账薄-会计科目余额
     *
     * @param financeAaLedgerAcct 账薄-会计科目余额
     * @return 结果
     */
    public int batchFinanceAcct(List<FinanceAaLedgerAcct> financeAaLedgerAcct);

    /**
     * 修改账薄-会计科目余额
     * 
     * @param financeAaLedgerAcct 账薄-会计科目余额
     * @return 结果
     */
    public int updateFinanceAaLedgerAcct(FinanceAaLedgerAcct financeAaLedgerAcct);

    /**
     * 删除账薄-会计科目余额
     * 
     * @param id 账薄-会计科目余额主键
     * @return 结果
     */
    public int deleteFinanceAaLedgerAcctById(String id);

    /**
     * 批量删除账薄-会计科目余额
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaLedgerAcctByIds(String[] ids);
}
