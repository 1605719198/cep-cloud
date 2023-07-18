package com.jlkj.finance.ao.service;

import com.jlkj.finance.ao.domain.FinanceAoRepayment;

import java.util.List;
import java.util.Map;

/**
 * 还款单资料档Service接口
 * 
 * @author 116514
 * @date 2023-06-15
 */
public interface IFinanceAoRepaymentService 
{
    /**
     * 查询还款单资料档
     * 
     * @param id 还款单资料档主键
     * @return 还款单资料档
     */
    public FinanceAoRepayment selectFinanceAoRepaymentById(String id);

    /**
     * 查询还款单资料档列表
     * 
     * @param financeAoRepayment 还款单资料档
     * @return 还款单资料档集合
     */
    public List<FinanceAoRepayment> selectFinanceAoRepaymentList(FinanceAoRepayment financeAoRepayment);

    /**
     * 新增还款单资料档
     * 
     * @param financeAoRepayment 还款单资料档
     * @return 结果
     */
    public int insertFinanceAoRepayment(FinanceAoRepayment financeAoRepayment);


    /**
     * 修改还款单资料档
     * 
     * @param financeAoRepayment 还款单资料档
     * @return 结果
     */
    public int updateFinanceAoRepayment(FinanceAoRepayment financeAoRepayment);

    /**
     * 批量删除还款单资料档
     * 
     * @param ids 需要删除的还款单资料档主键集合
     * @return 结果
     */
    public int deleteFinanceAoRepaymentByIds(String[] ids);

    /**
     * 删除还款单资料档信息
     * 
     * @param id 还款单资料档主键
     * @return 结果
     */
    public int deleteFinanceAoRepaymentById(String id);


    /**
     * 查询借支申请单
     * @param companyId
     * @return
     */
    public List<Map<String,String>> selectLoanApply(String companyId);


    /**
     * 查询还款单资料档（连表查询）
     * @param id
     * @return
     */
    public Map<String,Object> selectRepaymentById(String id);


    /**
     * 还款单清单查询
     * @param financeAoRepayment
     * @return
     */
    public List<Map<String,Object>> selectRepaymentList(FinanceAoRepayment financeAoRepayment);


    /**
     * 批量新增还款单资料档
     * @param financeAoRepayments
     * @return
     */
    public int insertBatchFinanceAoRepayment(List<FinanceAoRepayment> financeAoRepayments);


    /**
     * 成本中心清单查询
     * @param paramInfo
     * @return
     */
    public List<Map<String,Object>> selectcostCenterList(Map paramInfo);
}
