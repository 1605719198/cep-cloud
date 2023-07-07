package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoAccountCashier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 出纳确认明细档Mapper接口
 * 
 * @author 265799
 * @date 2023-07-05
 */
@Repository
public interface FinanceAoAccountCashierMapper 
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
     * @param financeAoAccountCashier 出纳确认明细档
     * @return 出纳确认明细档集合
     */
    public List<FinanceAoAccountCashier> selectFinanceAoAccountCashierList(FinanceAoAccountCashier financeAoAccountCashier);

    /**
     * 新增出纳确认明细档
     * 
     * @param financeAoAccountCashier 出纳确认明细档
     * @return 结果
     */
    public int insertFinanceAoAccountCashier(FinanceAoAccountCashier financeAoAccountCashier);

    /**
     * 修改出纳确认明细档
     * 
     * @param financeAoAccountCashier 出纳确认明细档
     * @return 结果
     */
    public int updateFinanceAoAccountCashier(FinanceAoAccountCashier financeAoAccountCashier);

    /**
     * 删除出纳确认明细档
     * 
     * @param id 出纳确认明细档主键
     * @return 结果
     */
    public int deleteFinanceAoAccountCashierById(String id);

    /**
     * 批量删除出纳确认明细档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoAccountCashierByIds(String[] ids);
}
