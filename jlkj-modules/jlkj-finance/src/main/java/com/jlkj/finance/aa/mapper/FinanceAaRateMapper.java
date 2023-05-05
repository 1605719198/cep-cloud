package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaRate;

import java.util.List;

/**
 * 利率汇率管理Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-04-17
 */
public interface FinanceAaRateMapper 
{
    /**
     * 查询利率汇率管理
     * 
     * @param id 利率汇率管理主键
     * @return 利率汇率管理
     */
    public FinanceAaRate selectFinanceAaRateById(String id);

    /**
     * 查询利率汇率管理列表
     * 
     * @param financeAaRate 利率汇率管理
     * @return 利率汇率管理集合
     */
    public List<FinanceAaRate> selectFinanceAaRateList(FinanceAaRate financeAaRate);

    /**
     * 新增利率汇率管理
     * 
     * @param financeAaRate 利率汇率管理
     * @return 结果
     */
    public int insertFinanceAaRate(FinanceAaRate financeAaRate);

    /**
     * 修改利率汇率管理
     * 
     * @param financeAaRate 利率汇率管理
     * @return 结果
     */
    public int updateFinanceAaRate(FinanceAaRate financeAaRate);

    /**
     * 删除利率汇率管理
     * 
     * @param id 利率汇率管理主键
     * @return 结果
     */
    public int deleteFinanceAaRateById(String id);

    /**
     * 批量删除利率汇率管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaRateByIds(String[] ids);
}
