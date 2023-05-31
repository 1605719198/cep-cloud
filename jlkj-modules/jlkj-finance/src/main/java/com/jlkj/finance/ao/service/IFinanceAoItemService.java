package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoItem;

import java.util.List;
import java.util.Map;

/**
 * 报支管理-报支类别集团设定主档Service接口
 * 
 * @author 116514
 * @date 2023-05-20
 */
public interface IFinanceAoItemService 
{
    /**
     * 查询报支管理-报支类别集团设定主档
     * 
     * @param id 报支管理-报支类别集团设定主档主键
     * @return 报支管理-报支类别集团设定主档
     */
    public FinanceAoItem selectFinanceAoItemById(String id);

    /**
     * 查询报支管理-报支类别集团设定主档列表
     * 
     * @param financeAoItem 报支管理-报支类别集团设定主档
     * @return 报支管理-报支类别集团设定主档集合
     */
    public List<FinanceAoItem> selectFinanceAoItemList(FinanceAoItem financeAoItem);

    /**
     * 新增报支管理-报支类别集团设定主档
     * 
     * @param financeAoItem 报支管理-报支类别集团设定主档
     * @return 结果
     */
    public int insertFinanceAoItem(FinanceAoItem financeAoItem);

    /**
     * 修改报支管理-报支类别集团设定主档
     * 
     * @param financeAoItem 报支管理-报支类别集团设定主档
     * @return 结果
     */
    public int updateFinanceAoItem(FinanceAoItem financeAoItem);

    /**
     * 批量删除报支管理-报支类别集团设定主档
     * 
     * @param ids 需要删除的报支管理-报支类别集团设定主档主键集合
     * @return 结果
     */
    public int deleteFinanceAoItemByIds(String[] ids);

    /**
     * 删除报支管理-报支类别集团设定主档信息
     * 
     * @param id 报支管理-报支类别集团设定主档主键
     * @return 结果
     */
    public int deleteFinanceAoItemById(String id);

    /**
     *  选取集团报支类别(公司级报支类别用)
     * @param companyId
     * @return
     */
    public List<Map<String,Object>> selectItemAndDetailList(String companyId);
}
