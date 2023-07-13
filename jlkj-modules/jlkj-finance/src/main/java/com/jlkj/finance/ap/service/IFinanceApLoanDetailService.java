package com.jlkj.finance.ap.service;

import java.util.List;
import com.jlkj.finance.ap.domain.FinanceApLoanDetail;

/**
 * 预付申请明细档Service接口
 * 
 * @author 265799
 * @date 2023-07-10
 */
public interface IFinanceApLoanDetailService 
{
    /**
     * 查询预付申请明细档
     * 
     * @param id 预付申请明细档主键
     * @return 预付申请明细档
     */
    public FinanceApLoanDetail selectFinanceApLoanDetailById(String id);

    /**
     * 查询预付申请明细档列表
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoanDetail 预付申请明细档
     * @return 预付申请明细档集合
     */
    public List<FinanceApLoanDetail> selectFinanceApLoanDetailList(FinanceApLoanDetail financeApLoanDetail);

    /**
     * 新增预付申请明细档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoanDetail 预付申请明细档
     * @return 结果
     */
    public int insertFinanceApLoanDetail(FinanceApLoanDetail financeApLoanDetail);

    /**
     * 修改预付申请明细档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoanDetail 预付申请明细档
     * @return 结果
     */
    public int updateFinanceApLoanDetail(FinanceApLoanDetail financeApLoanDetail);

    /**
     * 批量删除预付申请明细档
     *
     * @author 265799
     * @date 2023-07-10
     * @param ids 需要删除的预付申请明细档主键集合
     * @return 结果
     */
    public int deleteFinanceApLoanDetailByIds(String[] ids);

    /**
     * 删除预付申请明细档信息
     *
     * @author 265799
     * @date 2023-07-10
     * @param id 预付申请明细档主键
     * @return 结果
     */
    public int deleteFinanceApLoanDetailById(String id);
}
