package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaLedgerlCal;

/**
 * 账薄-核算项目余额Service接口
 * 
 * @author jlkj
 * @date 2023-04-29
 */
public interface IFinanceAaLedgerlCalService 
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
    public FinanceAaLedgerlCal selectFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal);

    /**
     * 新增账薄-核算项目余额
     * 
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 结果
     */
    public int insertFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal);

    /**
     * 修改账薄-核算项目余额
     * 
     * @param financeAaLedgerlCal 账薄-核算项目余额
     * @return 结果
     */
    public int updateFinanceAaLedgerlCal(FinanceAaLedgerlCal financeAaLedgerlCal);

    /**
     * 批量删除账薄-核算项目余额
     * 
     * @param ids 需要删除的账薄-核算项目余额主键集合
     * @return 结果
     */
    public int deleteFinanceAaLedgerlCalByIds(String[] ids);

    /**
     * 删除账薄-核算项目余额信息
     * 
     * @param id 账薄-核算项目余额主键
     * @return 结果
     */
    public int deleteFinanceAaLedgerlCalById(String id);
}
