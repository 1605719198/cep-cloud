package com.jlkj.finance.ao.mapper;

import com.jlkj.finance.ao.domain.FinanceAoLoan;
import com.jlkj.finance.ao.domain.FinanceAoLoanDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 借支申请主档Mapper接口
 * 
 * @author 116514
 * @date 2023-06-02
 */
@Repository
public interface FinanceAoLoanMapper 
{
    /**
     * 查询借支申请主档
     * 
     * @param id 借支申请主档主键
     * @return 借支申请主档
     */
    public FinanceAoLoan selectFinanceAoLoanById(String id);

    /**
     * 查询借支申请主档列表
     * 
     * @param financeAoLoan 借支申请主档
     * @return 借支申请主档集合
     */
    public List<FinanceAoLoan> selectFinanceAoLoanList(FinanceAoLoan financeAoLoan);


    /**
     * 新增借支申请主档
     * 
     * @param financeAoLoan 借支申请主档
     * @return 结果
     */
    public int insertFinanceAoLoan(FinanceAoLoan financeAoLoan);

    /**
     * 修改借支申请主档
     * 
     * @param financeAoLoan 借支申请主档
     * @return 结果
     */
    public int updateFinanceAoLoan(FinanceAoLoan financeAoLoan);

    /**
     * 删除借支申请主档
     * 
     * @param id 借支申请主档主键
     * @return 结果
     */
    public int deleteFinanceAoLoanById(String id);

    /**
     * 批量删除借支申请主档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoLoanByIds(String[] ids);

    /**
     * 批量删除报支管理-借支申请明细档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceAoLoanDetailByParentIds(String[] ids);
    
    /**
     * 批量新增报支管理-借支申请明细档
     * 
     * @param financeAoLoanDetailList 报支管理-借支申请明细档列表
     * @return 结果
     */
    public int batchFinanceAoLoanDetail(List<FinanceAoLoanDetail> financeAoLoanDetailList);
    

    /**
     * 通过借支申请主档主键删除报支管理-借支申请明细档信息
     * 
     * @param id 借支申请主档ID
     * @return 结果
     */
    public int deleteFinanceAoLoanDetailByParentId(String id);

    /**
     * 查询最大流水号
     * @param companyId
     * @param billNo
     * @return
     */
    public String selectMaxBillNo(@Param("companyId") String companyId, @Param("billNo")String billNo);


    /**
     * 借支申请作业清单查询，含明细借支总额汇总
     * @param financeAoLoan
     * @return
     */
    public List<FinanceAoLoan> selectAoLoanTotalList(FinanceAoLoan financeAoLoan);
}
