package com.jlkj.finance.ap.service;

import com.jlkj.finance.ap.domain.FinanceApBills;

import java.util.List;

/**
 * 发票信息Service接口
 * 
 * @author 265799
 * @date 2023-07-13
 */
public interface IFinanceApBillsService 
{
    /**
     * 查询发票信息
     * 
     * @param id 发票信息主键
     * @return 发票信息
     */
    public FinanceApBills selectFinanceApBillsById(String id);

    /**
     * 查询发票信息列表
     *
     * @author 265799
     * @date 2023-07-13
     * @param financeApBills 发票信息
     * @return 发票信息集合
     */
    public List<FinanceApBills> selectFinanceApBillsList(FinanceApBills financeApBills);

    /**
     * 新增发票信息
     *
     * @author 265799
     * @date 2023-07-13
     * @param financeApBills 发票信息
     * @return 结果
     */
    public int insertFinanceApBills(FinanceApBills financeApBills);

    /**
     * 修改发票信息
     *
     * @author 265799
     * @date 2023-07-13
     * @param financeApBills 发票信息
     * @return 结果
     */
    public int updateFinanceApBills(FinanceApBills financeApBills);

    /**
     * 批量删除发票信息
     *
     * @author 265799
     * @date 2023-07-13
     * @param ids 需要删除的发票信息主键集合
     * @return 结果
     */
    public int deleteFinanceApBillsByIds(String[] ids);

    /**
     * 删除发票信息信息
     *
     * @author 265799
     * @date 2023-07-13
     * @param id 发票信息主键
     * @return 结果
     */
    public int deleteFinanceApBillsById(String id);
}
