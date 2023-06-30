package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaCashflowItem;
import org.springframework.stereotype.Repository;

/**
 * 现金流量代码-对应会计科目Mapper接口
 * 
 * @author 265799
 * @date 2023-04-17
 */
@Repository
public interface FinanceAaCashflowItemMapper 
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
     * @param financeAaCashflowItem 现金流量代码-对应会计科目
     * @return 结果
     */
    public int insertFinanceAaCashflowItem(FinanceAaCashflowItem financeAaCashflowItem);

    /**
     * 修改现金流量代码-对应会计科目
     * 
     * @param financeAaCashflowItem 现金流量代码-对应会计科目
     * @return 结果
     */
    public int updateFinanceAaCashflowItem(FinanceAaCashflowItem financeAaCashflowItem);

    /**
     * 删除现金流量代码-对应会计科目
     * 
     * @param id 现金流量代码-对应会计科目主键
     * @return 结果
     */
    public int deleteFinanceAaCashflowItemById(String id);

    /**
     * 批量删除现金流量代码-对应会计科目
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaCashflowItemByIds(String[] ids);
}
