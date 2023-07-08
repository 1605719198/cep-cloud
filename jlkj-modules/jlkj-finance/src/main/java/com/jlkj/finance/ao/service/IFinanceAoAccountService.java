package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoAccount;

import java.util.List;

/**
 * 会计确认主档Service接口
 * 
 * @author 265799
 * @date 2023-07-04
 */
public interface IFinanceAoAccountService 
{
    /**
     * 查询会计确认主档
     * 
     * @param id 会计确认主档主键
     * @return 会计确认主档
     */
    public FinanceAoAccount selectFinanceAoAccountById(String id);

    /**
     * 查询会计确认主档列表
     *
     * @author 265799
     * @date 2023-07-04
     * @param financeAoAccount 会计确认主档
     * @return 会计确认主档集合
     */
    public List<FinanceAoAccount> selectFinanceAoAccountList(FinanceAoAccount financeAoAccount);

    /**
     * 查询会计确认明细主档列表
     *
     * @param financeAoAccount 会计确认主档
     * @return 会计确认主档集合
     */
    public List<FinanceAoAccount> selectDetailList(FinanceAoAccount financeAoAccount);

    /**
     * 新增会计确认主档
     *
     * @author 265799
     * @date 2023-07-04
     * @param financeAoAccount 会计确认主档
     * @return 结果
     */
    public int insertFinanceAoAccount(FinanceAoAccount financeAoAccount);

    /**
     * 报销修改会计确认主档
     *
     * @author 265799
     * @date 2023-07-04
     * @param financeAoAccount 会计确认主档
     * @return 结果
     */
    public int updateFinanceAoAccount(FinanceAoAccount financeAoAccount);
    /**
     * 还款修改会计确认主档
     *
     * @author 265799
     * @date 2023-07-04
     * @param financeAoAccount 会计确认主档
     * @return 结果
     */
    public int updateFinanceAoAccountRepayment(FinanceAoAccount financeAoAccount);
    /**
     * 批量删除会计确认主档
     *
     * @author 265799
     * @date 2023-07-04
     * @param ids 需要删除的会计确认主档主键集合
     * @return 结果
     */
    public int deleteFinanceAoAccountByIds(String[] ids);

    /**
     * 删除会计确认主档信息
     *
     * @author 265799
     * @date 2023-07-04
     * @param id 会计确认主档主键
     * @return 结果
     */
    public int deleteFinanceAoAccountById(String id);
}
