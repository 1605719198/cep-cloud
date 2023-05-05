package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaCashflowItemCompid;

/**
 * 现金流量代码公司级-对应会计科目Service接口
 * 
 * @author 265799
 * @date 2023-05-04
 */
public interface IFinanceAaCashflowItemCompidService 
{
    /**
     * 查询现金流量代码公司级-对应会计科目
     * 
     * @param id 现金流量代码公司级-对应会计科目主键
     * @return 现金流量代码公司级-对应会计科目
     */
    public FinanceAaCashflowItemCompid selectFinanceAaCashflowItemCompidById(String id);

    /**
     * 查询现金流量代码公司级-对应会计科目列表
     * 
     * @param financeAaCashflowItemCompid 现金流量代码公司级-对应会计科目
     * @return 现金流量代码公司级-对应会计科目集合
     */
    public List<FinanceAaCashflowItemCompid> selectFinanceAaCashflowItemCompidList(FinanceAaCashflowItemCompid financeAaCashflowItemCompid);

    /**
     * 新增现金流量代码公司级-对应会计科目
     * 
     * @param financeAaCashflowItemCompid 现金流量代码公司级-对应会计科目
     * @return 结果
     */
    public int insertFinanceAaCashflowItemCompid(FinanceAaCashflowItemCompid financeAaCashflowItemCompid);

    /**
     * 修改现金流量代码公司级-对应会计科目
     * 
     * @param financeAaCashflowItemCompid 现金流量代码公司级-对应会计科目
     * @return 结果
     */
    public int updateFinanceAaCashflowItemCompid(FinanceAaCashflowItemCompid financeAaCashflowItemCompid);

    /**
     * 批量删除现金流量代码公司级-对应会计科目
     * 
     * @param ids 需要删除的现金流量代码公司级-对应会计科目主键集合
     * @return 结果
     */
    public int deleteFinanceAaCashflowItemCompidByIds(String[] ids);

    /**
     * 删除现金流量代码公司级-对应会计科目信息
     * 
     * @param id 现金流量代码公司级-对应会计科目主键
     * @return 结果
     */
    public int deleteFinanceAaCashflowItemCompidById(String id);
}
