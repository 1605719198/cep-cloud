package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoItemComp;

import java.util.List;

/**
 * 报支类别-公司级-设定主档Service接口
 * 
 * @author 116514
 * @date 2023-05-23
 */
public interface IFinanceAoItemCompService 
{
    /**
     * 查询报支类别-公司级-设定主档
     * 
     * @param id 报支类别-公司级-设定主档主键
     * @return 报支类别-公司级-设定主档
     */
    public FinanceAoItemComp selectFinanceAoItemCompById(String id);

    /**
     * 查询报支类别-公司级-设定主档列表
     * 
     * @param financeAoItemComp 报支类别-公司级-设定主档
     * @return 报支类别-公司级-设定主档集合
     */
    public List<FinanceAoItemComp> selectFinanceAoItemCompList(FinanceAoItemComp financeAoItemComp);

    /**
     * 新增报支类别-公司级-设定主档
     * 
     * @param financeAoItemComp 报支类别-公司级-设定主档
     * @return 结果
     */
    public int insertFinanceAoItemComp(FinanceAoItemComp financeAoItemComp);

    /**
     * 修改报支类别-公司级-设定主档
     * 
     * @param financeAoItemComp 报支类别-公司级-设定主档
     * @return 结果
     */
    public int updateFinanceAoItemComp(FinanceAoItemComp financeAoItemComp);

    /**
     * 批量删除报支类别-公司级-设定主档
     * 
     * @param ids 需要删除的报支类别-公司级-设定主档主键集合
     * @return 结果
     */
    public int deleteFinanceAoItemCompByIds(String[] ids);

    /**
     * 删除报支类别-公司级-设定主档信息
     * 
     * @param id 报支类别-公司级-设定主档主键
     * @return 结果
     */
    public int deleteFinanceAoItemCompById(String id);


    /**
     * 批量新增公司级报支类别
     * @param financeAoItemComps
     * @return
     */
    public int insertBatch(List<FinanceAoItemComp> financeAoItemComps);
}
