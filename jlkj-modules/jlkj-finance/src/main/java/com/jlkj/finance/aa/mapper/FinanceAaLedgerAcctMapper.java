package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaLedgerAcct;

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
    public FinanceAaLedgerAcct selectFinanceAaLedgerAcct(FinanceAaLedgerAcct financeAaLedgerAcct);

    /**
     * 新增账薄-会计科目余额
     * 
     * @param financeAaLedgerAcct 账薄-会计科目余额
     * @return 结果
     */
    public int insertFinanceAaLedgerAcct(FinanceAaLedgerAcct financeAaLedgerAcct);

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
