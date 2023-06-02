package com.jlkj.finance.aa.mapper;

import com.jlkj.finance.aa.domain.FinanceAaInit;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 初始化记录Mapper接口
 * 
 * @author 265799
 * @date 2023-05-26
 */
public interface FinanceAaInitMapper 
{
    /**
     * 查询初始化记录
     * 
     * @param id 初始化记录主键
     * @return 初始化记录
     */
    public FinanceAaInit selectFinanceAaInitById(String id);

    /**
     * 查询初始化记录列表
     * 
     * @param financeAaInit 初始化记录
     * @return 初始化记录集合
     */
    public List<FinanceAaInit> selectFinanceAaInitList(FinanceAaInit financeAaInit);


    /**
     * 新增初始化记录
     * 
     * @param financeAaInit 初始化记录
     * @return 结果
     */
    public int insertFinanceAaInit(FinanceAaInit financeAaInit);

    /**
     * 修改初始化记录
     * 
     * @param financeAaInit 初始化记录
     * @return 结果
     */
    public int updateFinanceAaInit(FinanceAaInit financeAaInit);

    /**
     * 删除初始化记录
     * 
     * @param id 初始化记录主键
     * @return 结果
     */
    public int deleteFinanceAaInitById(String id);

    /**
     * 批量删除初始化记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAaInitByIds(String[] ids);

    /**
     * 批量删除初始化记录
     *
     * @param companyId
     * @param acctPeriod
     * @return 结果
     */
    public int deleteFinanceAaInit( @Param("companyId") String companyId, @Param("acctPeriod") String acctPeriod);


    /**
     * 批量新增初始化记录
     *
     * @param financeAaInit 初始化记录
     * @return 结果
     */
    public int batchFinanceAaInit(List<FinanceAaInit> financeAaInit);
}
