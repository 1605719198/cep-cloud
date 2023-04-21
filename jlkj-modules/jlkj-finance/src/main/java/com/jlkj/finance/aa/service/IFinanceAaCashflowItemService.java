package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaCashflowItem;

/**
 * 现金流量代码-对应会计科目Service接口
 * 
 * @author 265799
 * @date 2023-04-17
 */
public interface IFinanceAaCashflowItemService 
{
    /**
     * 查询现金流量代码-对应会计科目
     * 
     * @param id 现金流量代码-对应会计科目主键
     * @return 现金流量代码-对应会计科目
     */
    public FinanceAaCashflowItem selectFinanceAaCashflowItemById(String id);

    /**
     * 查询现金流量代码-对应会计科目列表
     * 
     * @param financeAaCashflowItem 现金流量代码-对应会计科目
     * @return 现金流量代码-对应会计科目集合
     */
    public List<FinanceAaCashflowItem> selectFinanceAaCashflowItemList(FinanceAaCashflowItem financeAaCashflowItem);

    /**
     * 新增现金流量代码-对应会计科目
     * 
     * @param codeList 现金流量代码-对应会计科目
     * @return 结果
     */
    public int insertFinanceAaCashflowItem(  List<FinanceAaCashflowItem> codeList);

    /**
     * 修改现金流量代码-对应会计科目
     * 
     * @param financeAaCashflowItem 现金流量代码-对应会计科目
     * @return 结果
     */
    public int updateFinanceAaCashflowItem(FinanceAaCashflowItem financeAaCashflowItem);

    /**
     * 批量删除现金流量代码-对应会计科目
     * 
     * @param ids 需要删除的现金流量代码-对应会计科目主键集合
     * @return 结果
     */
    public int deleteFinanceAaCashflowItemByIds(String[] ids);

    /**
     * 删除现金流量代码-对应会计科目信息
     * 
     * @param id 现金流量代码-对应会计科目主键
     * @return 结果
     */
    public int deleteFinanceAaCashflowItemById(String id);
}
