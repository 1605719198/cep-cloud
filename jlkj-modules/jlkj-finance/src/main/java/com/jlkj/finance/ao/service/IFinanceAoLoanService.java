package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoLoan;

import java.util.List;

/**
 * 借支申请主档Service接口
 * 
 * @author 116514
 * @date 2023-06-02
 */
public interface IFinanceAoLoanService 
{
    /**
     * 查询借支申请主档
     * 
     * @param id 借支申请主档主键
     * @return 借支申请主档
     */
    public FinanceAoLoan selectFinanceAoLoanById(String id);

    /**
     * 查询借支申请主档列表
     * 
     * @param financeAoLoan 借支申请主档
     * @return 借支申请主档集合
     */
    public List<FinanceAoLoan> selectFinanceAoLoanList(FinanceAoLoan financeAoLoan);

    /**
     * 新增借支申请主档
     * 
     * @param financeAoLoan 借支申请主档
     * @return 结果
     */
    public int insertFinanceAoLoan(FinanceAoLoan financeAoLoan);

    /**
     * 修改借支申请主档
     * 
     * @param financeAoLoan 借支申请主档
     * @return 结果
     */
    public int updateFinanceAoLoan(FinanceAoLoan financeAoLoan);

    /**
     * 批量删除借支申请主档
     * 
     * @param ids 需要删除的借支申请主档主键集合
     * @return 结果
     */
    public int deleteFinanceAoLoanByIds(String[] ids);

    /**
     * 删除借支申请主档信息
     * 
     * @param id 借支申请主档主键
     * @return 结果
     */
    public int deleteFinanceAoLoanById(String id);


    /**
     * 借支申请作业清单查询，含明细借支总额汇总
     * @param financeAoLoan
     * @return
     */
    public List<FinanceAoLoan> selectAoLoanTotalList(FinanceAoLoan financeAoLoan);
}
