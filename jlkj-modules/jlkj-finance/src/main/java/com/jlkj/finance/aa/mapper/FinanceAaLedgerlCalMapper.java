package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaLedgerlCal;

import java.util.List;

/**
 * 账薄-核算项目余额Mapper接口
 * 
 * @author jlkj
 * @date 2023-04-29
 */
public interface FinanceAaLedgerlCalMapper 
{
    /**
     * 查询账薄-核算项目余额
     * 
     * @param id 账薄-核算项目余额主键
     * @return 账薄-核算项目余额
     */
    public FinanceAaLedgerlCal selectFinanceAaLedgerlCalById(String id);

    /**
     * 查询账薄-核算项目余额列表
     * 
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public List<FinanceAaLedgerlCal> selectFinanceAaLedgerlCalList(FinanceAaLedgerlCal financeAaLedgerlCal);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public List<FinanceAaLedgerlCal> selectAcctPeriodList(FinanceAaLedgerlCal financeAaLedgerlCal);
    /**
     * 查询账薄-核算项目余额列表
     *
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 账薄-核算项目余额集合
     */
    public FinanceAaLedgerlCal selectFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal);

    /**
     * 新增账薄-核算项目余额
     * 
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 结果
     */
    public int insertFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal);
    /**
     * 批量新增账薄-核算项目余额
     *
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 结果
     */
    public int batchFinanceAaLedgerlCal(List<FinanceAaLedgerlCal> financeAaLedgerlCal);

    /**
     * 修改账薄-核算项目余额
     * 
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 结果
     */
    public int updateFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal);

    /**
     * 删除账薄-核算项目余额
     * 
     * @param id 账薄-核算项目余额主键
     * @return 结果
     */
    public int deleteFinanceAaLedgerlCalById(String id);

    /**
     * 批量删除账薄-核算项目余额
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaLedgerlCalByIds(String[] ids);
}
