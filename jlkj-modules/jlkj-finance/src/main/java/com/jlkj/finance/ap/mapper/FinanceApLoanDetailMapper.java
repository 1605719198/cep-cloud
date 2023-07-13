package com.jlkj.finance.ap.mapper;

import com.jlkj.finance.ap.domain.FinanceApLoanDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 预付申请明细档Mapper接口
 * 
 * @author 265799
 * @date 2023-07-10
 */
@Repository
public interface FinanceApLoanDetailMapper 
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
     * @param financeApLoanDetail 预付申请明细档
     * @return 预付申请明细档集合
     */
    public List<FinanceApLoanDetail> selectFinanceApLoanDetailList(FinanceApLoanDetail financeApLoanDetail);

    /**
     * 新增预付申请明细档
     * 
     * @param financeApLoanDetail 预付申请明细档
     * @return 结果
     */
    public int insertFinanceApLoanDetail(FinanceApLoanDetail financeApLoanDetail);

    /**
     * 修改预付申请明细档
     * 
     * @param financeApLoanDetail 预付申请明细档
     * @return 结果
     */
    public int updateFinanceApLoanDetail(FinanceApLoanDetail financeApLoanDetail);

    /**
     * 删除预付申请明细档
     * 
     * @param id 预付申请明细档主键
     * @return 结果
     */
    public int deleteFinanceApLoanDetailById(String id);

    /**
     * 批量删除预付申请明细档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceApLoanDetailByIds(String[] ids);
}
