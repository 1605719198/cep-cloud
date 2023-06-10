package com.jlkj.finance.ap.service;

import com.jlkj.finance.ap.domain.FinanceApItemComp;

import java.util.List;

/**
 * 报支类别公司设置Service接口
 * 
 * @author SunXuTong
 * @date 2023-06-06
 */
public interface IFinanceApItemCompService 
{
    /**
     * 查询报支类别公司设置
     * 
     * @param id 报支类别公司设置主键
     * @return 报支类别公司设置
     */
    public FinanceApItemComp selectFinanceApItemCompById(String id);

    /**
     * 查询报支类别公司设置列表
     * 
     * @param financeApItemComp 报支类别公司设置
     * @return 报支类别公司设置集合
     */
    public List<FinanceApItemComp> selectFinanceApItemCompList(FinanceApItemComp financeApItemComp);

    /**
     * 新增报支类别公司设置
     * 
     * @param financeApItemComp 报支类别公司设置
     * @return 结果
     */
    public int insertFinanceApItemComp(FinanceApItemComp financeApItemComp);

    /**
     * 修改报支类别公司设置
     * 
     * @param financeApItemComp 报支类别公司设置
     * @return 结果
     */
    public int updateFinanceApItemComp(FinanceApItemComp financeApItemComp);

    /**
     * 批量删除报支类别公司设置
     * 
     * @param ids 需要删除的报支类别公司设置主键集合
     * @return 结果
     */
    public int deleteFinanceApItemCompByIds(String[] ids);

    /**
     * 删除报支类别公司设置信息
     * 
     * @param id 报支类别公司设置主键
     * @return 结果
     */
    public int deleteFinanceApItemCompById(String id);

    /**
     * 批量新增公司级报支类别
     * @param financeApItemComps
     * @return
     */
    public int insertBatch(List<FinanceApItemComp> financeApItemComps);
}
