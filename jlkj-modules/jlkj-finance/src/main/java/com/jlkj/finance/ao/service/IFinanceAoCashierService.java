package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoCashier;

import java.util.List;

/**
 * 出纳确认主档Service接口
 * 
 * @author 265799
 * @date 2023-07-05
 */
public interface IFinanceAoCashierService 
{
    /**
     * 查询出纳确认主档
     * 
     * @param id 出纳确认主档主键
     * @return 出纳确认主档
     */
    public FinanceAoCashier selectFinanceAoCashierById(String id);

    /**
     * 查询出纳确认主档列表
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier 出纳确认主档
     * @return 出纳确认主档集合
     */
    public List<FinanceAoCashier> selectFinanceAoCashierList(FinanceAoCashier financeAoCashier);

    /**
     * 新增出纳确认主档
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier 出纳确认主档
     * @return 结果
     */
    public int insertFinanceAoCashier(FinanceAoCashier financeAoCashier);

    /**
     * 修改出纳确认主档
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier 出纳确认主档
     * @return 结果
     */
    public int updateFinanceAoCashier(FinanceAoCashier financeAoCashier);
    /**
     * 还款修改出纳确认主档
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier 出纳确认主档
     * @return 结果
     */
    public int updateFinanceAoCashierRepayment(FinanceAoCashier financeAoCashier);

    /**
     * 批量删除出纳确认主档
     *
     * @author 265799
     * @date 2023-07-05
     * @param ids 需要删除的出纳确认主档主键集合
     * @return 结果
     */
    public int deleteFinanceAoCashierByIds(String[] ids);

    /**
     * 删除出纳确认主档信息
     *
     * @author 265799
     * @date 2023-07-05
     * @param id 出纳确认主档主键
     * @return 结果
     */
    public int deleteFinanceAoCashierById(String id);
}
