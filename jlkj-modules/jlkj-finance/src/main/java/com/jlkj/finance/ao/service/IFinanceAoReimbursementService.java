package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoReimbursement;

import java.util.List;

/**
 * 费用报销主档Service接口
 * 
 * @author 265799
 * @date 2023-07-03
 */
public interface IFinanceAoReimbursementService 
{
    /**
     * 查询费用报销主档
     * 
     * @param id 费用报销主档主键
     * @return 费用报销主档
     */
    public FinanceAoReimbursement selectFinanceAoReimbursementById(String id);

    /**
     * 查询费用报销主档列表
     *
     * @author 265799
     * @date 2023-07-03
     * @param financeAoReimbursement 费用报销主档
     * @return 费用报销主档集合
     */
    public List<FinanceAoReimbursement> selectFinanceAoReimbursementList(FinanceAoReimbursement financeAoReimbursement);

    /**
     * 新增费用报销主档
     *
     * @author 265799
     * @date 2023-07-03
     * @param financeAoReimbursement 费用报销主档
     * @return 结果
     */
    public int insertFinanceAoReimbursement(FinanceAoReimbursement financeAoReimbursement);

    /**
     * 修改费用报销主档
     *
     * @author 265799
     * @date 2023-07-03
     * @param financeAoReimbursement 费用报销主档
     * @return 结果
     */
    public int updateFinanceAoReimbursement(FinanceAoReimbursement financeAoReimbursement);

    /**
     * 批量删除费用报销主档
     *
     * @author 265799
     * @date 2023-07-03
     * @param ids 需要删除的费用报销主档主键集合
     * @return 结果
     */
    public int deleteFinanceAoReimbursementByIds(String[] ids);

    /**
     * 删除费用报销主档信息
     *
     * @author 265799
     * @date 2023-07-03
     * @param id 费用报销主档主键
     * @return 结果
     */
    public int deleteFinanceAoReimbursementById(String id);
}
