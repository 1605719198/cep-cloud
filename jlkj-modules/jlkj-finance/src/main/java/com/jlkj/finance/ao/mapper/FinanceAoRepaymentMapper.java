package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoRepayment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 还款单资料档Mapper接口
 * 
 * @author 116514
 * @date 2023-06-15
 */
public interface FinanceAoRepaymentMapper 
{
    /**
     * 查询还款单资料档
     * 
     * @param id 还款单资料档主键
     * @return 还款单资料档
     */
    public FinanceAoRepayment selectFinanceAoRepaymentById(String id);
    /**
     * 查询还款单资料档
     *
     * @param loanId 借支id
     * @return 还款单资料档
     */
    public FinanceAoRepayment selectLoanId(String loanId);

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
     * 删除还款单资料档
     * 
     * @param id 还款单资料档主键
     * @return 结果
     */
    public int deleteFinanceAoRepaymentById(String id);

    /**
     * 批量删除还款单资料档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoRepaymentByIds(String[] ids);


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
     * 查询最大流水号
     * @param companyId
     * @param billNo
     * @return
     */
    public String selectMaxBillNo(@Param("companyId") String companyId, @Param("billNo")String billNo);


    /**
     * 批量新增还款单资料档
     * @param financeAoRepayments
     * @return
     */
    public int insertBatch(List<FinanceAoRepayment> financeAoRepayments);
}
