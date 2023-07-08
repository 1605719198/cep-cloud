package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoAccountCashier;
import com.jlkj.finance.ao.domain.FinanceAoCashier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 出纳确认主档Mapper接口
 * 
 * @author 265799
 * @date 2023-07-05
 */
@Repository
public interface FinanceAoCashierMapper 
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
     * @param financeAoCashier 出纳确认主档
     * @return 出纳确认主档集合
     */
    public List<FinanceAoCashier> selectFinanceAoCashierList(FinanceAoCashier financeAoCashier);

    /**
     * 新增出纳确认主档
     * 
     * @param financeAoCashier 出纳确认主档
     * @return 结果
     */
    public int insertFinanceAoCashier(FinanceAoCashier financeAoCashier);

    /**
     * 修改出纳确认主档
     * 
     * @param financeAoCashier 出纳确认主档
     * @return 结果
     */
    public int updateFinanceAoCashier(FinanceAoCashier financeAoCashier);

    /**
     * 删除出纳确认主档
     * 
     * @param id 出纳确认主档主键
     * @return 结果
     */
    public int deleteFinanceAoCashierById(String id);

    /**
     * 批量删除出纳确认主档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoCashierByIds(String[] ids);

    /**
     * 批量删除出纳确认明细档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoAccountCashierByAccountIds(String[] ids);
    
    /**
     * 批量新增出纳确认明细档
     * 
     * @param financeAoAccountCashierList 出纳确认明细档列表
     * @return 结果
     */
    public int batchFinanceAoAccountCashier(List<FinanceAoAccountCashier> financeAoAccountCashierList);
    

    /**
     * 通过出纳确认主档主键删除出纳确认明细档信息
     * 
     * @param id 出纳确认主档ID
     * @return 结果
     */
    public int deleteFinanceAoAccountCashierByAccountId(String id);
}
