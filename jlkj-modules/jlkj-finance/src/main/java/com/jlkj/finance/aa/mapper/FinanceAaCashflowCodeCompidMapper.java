package com.jlkj.finance.aa.mapper;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaCashflowCodeCompid;

/**
 * 现金流量代码公司级Mapper接口
 * 
 * @author 265799
 * @date 2023-05-04
 */
public interface FinanceAaCashflowCodeCompidMapper 
{
    /**
     * 查询现金流量代码公司级
     * 
     * @param id 现金流量代码公司级主键
     * @return 现金流量代码公司级
     */
    public FinanceAaCashflowCodeCompid selectFinanceAaCashflowCodeCompidById(String id);

    /**
     * 查询现金流量代码公司级列表
     * 
     * @param financeAaCashflowCodeCompid 现金流量代码公司级
     * @return 现金流量代码公司级集合
     */
    public List<FinanceAaCashflowCodeCompid> selectFinanceAaCashflowCodeCompidList(FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid);

    /**
     * 新增现金流量代码公司级
     * 
     * @param financeAaCashflowCodeCompid 现金流量代码公司级
     * @return 结果
     */
    public int insertFinanceAaCashflowCodeCompid(FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid);

    /**
     * 修改现金流量代码公司级
     * 
     * @param financeAaCashflowCodeCompid 现金流量代码公司级
     * @return 结果
     */
    public int updateFinanceAaCashflowCodeCompid(FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid);

    /**
     * 删除现金流量代码公司级
     * 
     * @param id 现金流量代码公司级主键
     * @return 结果
     */
    public int deleteFinanceAaCashflowCodeCompidById(String id);

    /**
     * 批量删除现金流量代码公司级
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaCashflowCodeCompidByIds(String[] ids);
}
