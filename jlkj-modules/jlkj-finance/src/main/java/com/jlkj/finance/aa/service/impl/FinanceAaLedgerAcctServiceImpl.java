package com.jlkj.finance.aa.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaLedgerAcctMapper;
import com.jlkj.finance.aa.domain.FinanceAaLedgerAcct;
import com.jlkj.finance.aa.service.IFinanceAaLedgerAcctService;

/**
 * 账薄-会计科目余额Service业务层处理
 *
 * @author jlkj
 * @date 2023-04-29
 */
@Service
public class FinanceAaLedgerAcctServiceImpl implements IFinanceAaLedgerAcctService
{
    @Autowired
    private FinanceAaLedgerAcctMapper financeAaLedgerAcctMapper;

    /**
     * 查询账薄-会计科目余额
     *
     * @param id 账薄-会计科目余额主键
     * @return 账薄-会计科目余额
     */
    @Override
    public FinanceAaLedgerAcct selectFinanceAaLedgerAcctById(String id)
    {
        return financeAaLedgerAcctMapper.selectFinanceAaLedgerAcctById(id);
    }

    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcct 账薄-会计科目余额
     * @return 账薄-会计科目余额
     */
    @Override
    public List<FinanceAaLedgerAcct> selectFinanceAaLedgerAcctList(FinanceAaLedgerAcct financeAaLedgerAcct)
    {
        return financeAaLedgerAcctMapper.selectFinanceAaLedgerAcctList(financeAaLedgerAcct);
    }
    /**
     * 查询账薄-会计科目余额列表
     *
     * @param financeAaLedgerAcct 账薄-会计科目余额
     * @return 账薄-会计科目余额
     */
    @Override
    public FinanceAaLedgerAcct selectFinanceAaLedgerAcct(FinanceAaLedgerAcct financeAaLedgerAcct)
    {
        return financeAaLedgerAcctMapper.selectFinanceAaLedgerAcct(financeAaLedgerAcct);
    }
    /**
     * 新增账薄-会计科目余额
     *
     * @param financeAaLedgerAcct 账薄-会计科目余额
     * @return 结果
     */
    @Override
    public int insertFinanceAaLedgerAcct(FinanceAaLedgerAcct financeAaLedgerAcct)
    {
        return financeAaLedgerAcctMapper.insertFinanceAaLedgerAcct(financeAaLedgerAcct);
    }

    /**
     * 修改账薄-会计科目余额
     *
     * @param financeAaLedgerAcct 账薄-会计科目余额
     * @return 结果
     */
    @Override
    public int updateFinanceAaLedgerAcct(FinanceAaLedgerAcct financeAaLedgerAcct)
    {
        return financeAaLedgerAcctMapper.updateFinanceAaLedgerAcct(financeAaLedgerAcct);
    }

    /**
     * 批量删除账薄-会计科目余额
     *
     * @param ids 需要删除的账薄-会计科目余额主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaLedgerAcctByIds(String[] ids)
    {
        return financeAaLedgerAcctMapper.deleteFinanceAaLedgerAcctByIds(ids);
    }

    /**
     * 删除账薄-会计科目余额信息
     *
     * @param id 账薄-会计科目余额主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaLedgerAcctById(String id)
    {
        return financeAaLedgerAcctMapper.deleteFinanceAaLedgerAcctById(id);
    }
}
