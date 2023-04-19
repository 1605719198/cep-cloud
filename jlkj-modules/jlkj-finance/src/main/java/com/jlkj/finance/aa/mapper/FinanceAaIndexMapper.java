package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaIndex;

/**
 * 系统选单-索引设定Mapper接口
 * 
 * @author jlkj
 * @date 2023-04-19
 */
public interface FinanceAaIndexMapper 
{
    /**
     * 查询系统选单-索引设定
     * 
     * @param id 系统选单-索引设定主键
     * @return 系统选单-索引设定
     */
    public FinanceAaIndex selectFinanceAaIndexById(String id);

    /**
     * 查询系统选单-索引设定列表
     * 
     * @param financeAaIndex 系统选单-索引设定
     * @return 系统选单-索引设定集合
     */
    public List<FinanceAaIndex> selectFinanceAaIndexList(FinanceAaIndex financeAaIndex);

    /**
     * 新增系统选单-索引设定
     * 
     * @param financeAaIndex 系统选单-索引设定
     * @return 结果
     */
    public int insertFinanceAaIndex(FinanceAaIndex financeAaIndex);

    /**
     * 修改系统选单-索引设定
     * 
     * @param financeAaIndex 系统选单-索引设定
     * @return 结果
     */
    public int updateFinanceAaIndex(FinanceAaIndex financeAaIndex);

    /**
     * 删除系统选单-索引设定
     * 
     * @param id 系统选单-索引设定主键
     * @return 结果
     */
    public int deleteFinanceAaIndexById(String id);

    /**
     * 批量删除系统选单-索引设定
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaIndexByIds(String[] ids);
}
