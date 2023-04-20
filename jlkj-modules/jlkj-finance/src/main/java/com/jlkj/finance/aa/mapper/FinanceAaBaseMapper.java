package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaBase;

/**
 * 系统选单-基本设定Mapper接口
 * 
 * @author jlkj
 * @date 2023-04-19
 */
public interface FinanceAaBaseMapper 
{
    /**
     * 查询系统选单-基本设定
     * 
     * @param id 系统选单-基本设定主键
     * @return 系统选单-基本设定
     */
    public FinanceAaBase selectFinanceAaBaseById(String id);

    /**
     * 查询系统选单-基本设定列表
     * 
     * @param financeAaBase 系统选单-基本设定
     * @return 系统选单-基本设定集合
     */
    public List<FinanceAaBase> selectFinanceAaBaseList(FinanceAaBase financeAaBase);
    /**
     * 查询系统选单-基本设定列表
     *
     * @param financeAaBase 系统选单-基本设定
     * @return 系统选单-基本设定集合
     */
    public List<FinanceAaBase> selectFinanceAaBaseListTab(FinanceAaBase financeAaBase);

    /**
     * 新增系统选单-基本设定
     * 
     * @param financeAaBase 系统选单-基本设定
     * @return 结果
     */
    public int insertFinanceAaBase(FinanceAaBase financeAaBase);

    /**
     * 修改系统选单-基本设定
     * 
     * @param financeAaBase 系统选单-基本设定
     * @return 结果
     */
    public int updateFinanceAaBase(FinanceAaBase financeAaBase);

    /**
     * 删除系统选单-基本设定
     * 
     * @param id 系统选单-基本设定主键
     * @return 结果
     */
    public int deleteFinanceAaBaseById(String id);

    /**
     * 批量删除系统选单-基本设定
     * 
     * @param leafId 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaBaseByIds(String[] leafId);
}
