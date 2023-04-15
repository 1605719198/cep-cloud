package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaCrcy;

import java.util.List;

/**
 * 币别管理Mapper接口
 * 
 * @author SunXuTong
 * @date 2023-04-07
 */
public interface FinanceAaCrcyMapper 
{
    /**
     * 查询币别管理
     * 
     * @param id 币别管理主键
     * @return 币别管理
     */
    public FinanceAaCrcy selectFinanceAaCrcyById(String id);

    /**
     * 查询币别管理列表
     * 
     * @param financeAaCrcy 币别管理
     * @return 币别管理集合
     */
    public List<FinanceAaCrcy> selectFinanceAaCrcyList(FinanceAaCrcy financeAaCrcy);

    /**
     * 新增币别管理
     * 
     * @param financeAaCrcy 币别管理
     * @return 结果
     */
    public int insertFinanceAaCrcy(FinanceAaCrcy financeAaCrcy);

    /**
     * 修改币别管理
     * 
     * @param financeAaCrcy 币别管理
     * @return 结果
     */
    public int updateFinanceAaCrcy(FinanceAaCrcy financeAaCrcy);

    /**
     * 删除币别管理
     * 
     * @param id 币别管理主键
     * @return 结果
     */
    public int deleteFinanceAaCrcyById(String id);

    /**
     * 批量删除币别管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaCrcyByIds(String[] ids);
}
