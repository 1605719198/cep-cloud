package com.jlkj.finance.ao.service;

import java.util.List;
import com.jlkj.finance.ao.domain.FinanceAoReimbursementDetail;

/**
 * 费用报销明细档Service接口
 * 
 * @author 265799
 * @date 2023-06-27
 */
public interface IFinanceAoReimbursementDetailService 
{
    /**
     * 查询费用报销明细档
     * 
     * @param id 费用报销明细档主键
     * @return 费用报销明细档
     */
    public FinanceAoReimbursementDetail selectFinanceAoReimbursementDetailById(String id);

    /**
     * 查询费用报销明细档列表
     *
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursementDetail 费用报销明细档
     * @return 费用报销明细档集合
     */
    public List<FinanceAoReimbursementDetail> selectFinanceAoReimbursementDetailList(FinanceAoReimbursementDetail financeAoReimbursementDetail);

    /**
     * 新增费用报销明细档
     *
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursementDetail 费用报销明细档
     * @return 结果
     */
    public int insertFinanceAoReimbursementDetail(FinanceAoReimbursementDetail financeAoReimbursementDetail);

    /**
     * 修改费用报销明细档
     *
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursementDetail 费用报销明细档
     * @return 结果
     */
    public int updateFinanceAoReimbursementDetail(FinanceAoReimbursementDetail financeAoReimbursementDetail);

    /**
     * 批量删除费用报销明细档
     *
     * @author 265799
     * @date 2023-06-27
     * @param ids 需要删除的费用报销明细档主键集合
     * @return 结果
     */
    public int deleteFinanceAoReimbursementDetailByIds(String[] ids);

    /**
     * 删除费用报销明细档信息
     *
     * @author 265799
     * @date 2023-06-27
     * @param id 费用报销明细档主键
     * @return 结果
     */
    public int deleteFinanceAoReimbursementDetailById(String id);
}
