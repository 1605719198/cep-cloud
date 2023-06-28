package com.jlkj.finance.aa.service;

import java.util.List;
import com.jlkj.finance.aa.domain.FinanceAaProjectResult;

/**
 * 印项目计算结果Service接口
 * 
 * @author jlkj
 * @date 2023-06-28
 */
public interface IFinanceAaProjectResultService 
{
    /**
     * 查询印项目计算结果
     * 
     * @param id 印项目计算结果主键
     * @return 印项目计算结果
     */
    public FinanceAaProjectResult selectFinanceAaProjectResultById(Long id);

    /**
     * 查询印项目计算结果列表
     * 
     * @param financeAaProjectResult 印项目计算结果
     * @return 印项目计算结果集合
     */
    public List<FinanceAaProjectResult> selectFinanceAaProjectResultList(FinanceAaProjectResult financeAaProjectResult);

    /**
     * 新增印项目计算结果
     * 
     * @param financeAaProjectResult 印项目计算结果
     * @return 结果
     */
    public int insertFinanceAaProjectResult(FinanceAaProjectResult financeAaProjectResult);

    /**
     * 修改印项目计算结果
     * 
     * @param financeAaProjectResult 印项目计算结果
     * @return 结果
     */
    public int updateFinanceAaProjectResult(FinanceAaProjectResult financeAaProjectResult);

    /**
     * 批量删除印项目计算结果
     * 
     * @param ids 需要删除的印项目计算结果主键集合
     * @return 结果
     */
    public int deleteFinanceAaProjectResultByIds(Long[] ids);

    /**
     * 删除印项目计算结果信息
     * 
     * @param id 印项目计算结果主键
     * @return 结果
     */
    public int deleteFinanceAaProjectResultById(Long id);
}
