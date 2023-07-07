package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoAccountCashier;
import com.jlkj.finance.ao.dto.FinanceAoAccountDto;

import java.util.List;

/**
 * 出纳确认明细档Service接口
 * 
 * @author 265799
 * @date 2023-07-05
 */
public interface IFinanceAoAccountCashierService 
{
    /**
     * 查询出纳确认明细档
     * 
     * @param id 出纳确认明细档主键
     * @return 出纳确认明细档
     */
    public FinanceAoAccountCashier selectFinanceAoAccountCashierById(String id);

    /**
     * 查询出纳确认明细档列表
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoAccountCashier 出纳确认明细档
     * @return 出纳确认明细档集合
     */
    public List<FinanceAoAccountCashier> selectFinanceAoAccountCashierList(FinanceAoAccountCashier financeAoAccountCashier);
    /**
     * 查询出纳确认明细档
     *
     * @param financeAoAccountCashier 出纳确认明细档
     * @return 出纳确认明细档
     */
    public FinanceAoAccountCashier selectListLoanADetailCashier(FinanceAoAccountCashier financeAoAccountCashier);
    /**
     * 新增出纳确认明细档
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoAccountDto 出纳确认明细档
     * @return 结果
     */
    public int insertFinanceAoAccountCashier(FinanceAoAccountDto financeAoAccountDto);

    /**
     * 修改出纳确认明细档
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoAccountDto 出纳确认明细档
     * @return 结果
     */

    public int updateFinanceAoAccountCashier(FinanceAoAccountDto financeAoAccountDto);
 /**
     * 批量删除出纳确认明细档
     *
     * @author 265799
     * @date 2023-07-05
     * @param ids 需要删除的出纳确认明细档主键集合
     * @return 结果
     */
    public int deleteFinanceAoAccountCashierByIds(String[] ids);

    /**
     * 删除出纳确认明细档信息
     *
     * @author 265799
     * @date 2023-07-05
     * @param id 出纳确认明细档主键
     * @return 结果
     */
    public int deleteFinanceAoAccountCashierById(String id);
}
