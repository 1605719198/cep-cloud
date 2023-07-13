package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoExpenseBills;

import java.util.List;

/**
 * 我的发票Service接口
 * 
 * @author 116514
 * @date 2023-05-18
 */
public interface IFinanceAoExpenseBillsService 
{
    /**
     * 查询我的发票
     * 
     * @param id 我的发票主键
     * @return 我的发票
     */
    public FinanceAoExpenseBills selectFinanceAoExpenseBillsById(String id);

    /**
     * 查询我的发票列表
     *
     * @author 116514
     * @date 2023-05-18
     * @param financeAoExpenseBills 我的发票
     * @return 我的发票集合
     */
    public List<FinanceAoExpenseBills> selectFinanceAoExpenseBillsList(FinanceAoExpenseBills financeAoExpenseBills);

    /**
     * 新增我的发票
     *
     * @author 116514
     * @date 2023-05-18
     * @param financeAoExpenseBills 我的发票
     * @return 结果
     */
    public int insertFinanceAoExpenseBills(FinanceAoExpenseBills financeAoExpenseBills);

    /**
     * 修改我的发票
     *
     * @author 116514
     * @date 2023-05-18
     * @param financeAoExpenseBills 我的发票
     * @return 结果
     */
    public int updateFinanceAoExpenseBills(FinanceAoExpenseBills financeAoExpenseBills);

    /**
     * 批量删除我的发票
     *
     * @author 116514
     * @date 2023-05-18
     * @param ids 需要删除的我的发票主键集合
     * @return 结果
     */
    public int deleteFinanceAoExpenseBillsByIds(String[] ids);

    /**
     * 删除我的发票信息
     *
     * @author 116514
     * @date 2023-05-18
     * @param id 我的发票主键
     * @return 结果
     */
    public int deleteFinanceAoExpenseBillsById(String id);
}
