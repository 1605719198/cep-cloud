package com.jlkj.finance.ap.service;

import com.jlkj.finance.ap.domain.FinanceApLoan;

import java.util.List;

/**
 * 预付申请主档Service接口
 * 
 * @author 265799
 * @date 2023-07-10
 */
public interface IFinanceApLoanService 
{
    /**
     * 查询预付申请主档
     * 
     * @param id 预付申请主档主键
     * @return 预付申请主档
     */
    public FinanceApLoan selectFinanceApLoanById(String id);

    /**
     * 查询预付申请主档列表
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoan 预付申请主档
     * @return 预付申请主档集合
     */
    public List<FinanceApLoan> selectFinanceApLoanList(FinanceApLoan financeApLoan);

    /**
     * 新增预付申请主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoan 预付申请主档
     * @return 结果
     */
    public int insertFinanceApLoan(FinanceApLoan financeApLoan);

    /**
     * 修改预付申请主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoan 预付申请主档
     * @return 结果
     */
    public int updateFinanceApLoan(FinanceApLoan financeApLoan);

    /**
     * 批量删除预付申请主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param ids 需要删除的预付申请主档主键集合
     * @return 结果
     */
    public int deleteFinanceApLoanByIds(String[] ids);

    /**
     * 删除预付申请主档信息
     *
     * @author 265799
     * @date 2023-07-10
     * @param id 预付申请主档主键
     * @return 结果
     */
    public int deleteFinanceApLoanById(String id);
}
