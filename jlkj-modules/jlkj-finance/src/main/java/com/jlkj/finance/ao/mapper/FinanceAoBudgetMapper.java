package com.jlkj.finance.ao.mapper;

import java.util.List;
import com.jlkj.finance.ao.domain.FinanceAoBudget;

/**
 * 预算设置Mapper接口
 * 
 * @author jlkj
 * @date 2023-06-16
 */
public interface FinanceAoBudgetMapper 
{
    /**
     * 查询预算设置
     * 
     * @param id 预算设置主键
     * @return 预算设置
     */
    public FinanceAoBudget selectFinanceAoBudgetById(String id);

    /**
     * 查询预算设置列表
     * 
     * @param financeAoBudget 预算设置
     * @return 预算设置集合
     */
    public List<FinanceAoBudget> selectFinanceAoBudgetList(FinanceAoBudget financeAoBudget);

    /**
     * 新增预算设置
     * 
     * @param financeAoBudget 预算设置
     * @return 结果
     */
    public int insertFinanceAoBudget(FinanceAoBudget financeAoBudget);

    /**
     * 修改预算设置
     * 
     * @param financeAoBudget 预算设置
     * @return 结果
     */
    public int updateFinanceAoBudget(FinanceAoBudget financeAoBudget);

    /**
     * 删除预算设置
     * 
     * @param id 预算设置主键
     * @return 结果
     */
    public int deleteFinanceAoBudgetById(String id);

    /**
     * 批量删除预算设置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoBudgetByIds(String[] ids);
}
