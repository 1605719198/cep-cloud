package com.jlkj.finance.aa.service;

import com.jlkj.finance.aa.domain.FinanceAaInit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 初始化记录Service接口
 * 
 * @author 265799
 * @date 2023-05-26
 */
public interface IFinanceAaInitService 
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
     * 批量删除初始化记录
     * 
     * @param ids 需要删除的初始化记录主键集合
     * @return 结果
     */
    public int deleteFinanceAaInitByIds(String[] ids);

    /**
     * 删除初始化记录信息
     * 
     * @param id 初始化记录主键
     * @return 结果
     */
    public int deleteFinanceAaInitById(String id);
    /**
     * 导入凭证
     *
     * @param financeAaInits 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     *  @param acctPeriod 会计周期
     *  @param companyId 公司别
     * @return 结果
     */
    public List<FinanceAaInit> importFinanceAaInits(List<FinanceAaInit> financeAaInits, Boolean isUpdateSupport, @Param("operName") String operName ,@Param("companyId") String companyId,@Param("acctPeriod")String acctPeriod);
}


