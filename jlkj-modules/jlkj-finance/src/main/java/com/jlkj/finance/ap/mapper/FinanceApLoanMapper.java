package com.jlkj.finance.ap.mapper;

import com.jlkj.finance.ap.domain.FinanceApLoan;
import com.jlkj.finance.ap.domain.FinanceApLoanDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 预付申请主档Mapper接口
 * 
 * @author 265799
 * @date 2023-07-10
 */
@Repository
public interface FinanceApLoanMapper 
{
    /**
     * 查询预付申请主档
     * 
     * @param id 预付申请主档主键
     * @return 预付申请主档
     */
    public FinanceApLoan selectFinanceApLoanById(String id);

    /**
     * 查询预付申请主档列表
     * 
     * @param financeApLoan 预付申请主档
     * @return 预付申请主档集合
     */
    public List<FinanceApLoan> selectFinanceApLoanList(FinanceApLoan financeApLoan);

    /**
     * 新增预付申请主档
     * 
     * @param financeApLoan 预付申请主档
     * @return 结果
     */
    public int insertFinanceApLoan(FinanceApLoan financeApLoan);

    /**
     * 修改预付申请主档
     * 
     * @param financeApLoan 预付申请主档
     * @return 结果
     */
    public int updateFinanceApLoan(FinanceApLoan financeApLoan);

    /**
     * 删除预付申请主档
     * 
     * @param id 预付申请主档主键
     * @return 结果
     */
    public int deleteFinanceApLoanById(String id);

    /**
     * 批量删除预付申请主档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApLoanByIds(String[] ids);

    /**
     * 批量删除预付申请明细档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApLoanDetailByParentIds(String[] ids);
    
    /**
     * 批量新增预付申请明细档
     * 
     * @param financeApLoanDetailList 预付申请明细档列表
     * @return 结果
     */
    public int batchFinanceApLoanDetail(List<FinanceApLoanDetail> financeApLoanDetailList);
    

    /**
     * 通过预付申请主档主键删除预付申请明细档信息
     * 
     * @param id 预付申请主档ID
     * @return 结果
     */
    public int deleteFinanceApLoanDetailByParentId(String id);
    /**
     * 查询最大流水号
     * @param companyId
     * @param billNo
     * @return
     */
    public String selectMaxBillNo(@Param("companyId") String companyId, @Param("billNo")String billNo);
}
