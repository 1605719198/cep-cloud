package com.jlkj.finance.ao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.finance.ao.domain.FinanceAoReimbursementLoan;

import java.util.List;

/**
 * 报支管理-费用报销冲借支单档Service接口
 * 
 * @author 265799
 * @date 2023-06-28
 */
public interface IFinanceAoReimbursementLoanService  extends IService<FinanceAoReimbursementLoan>
{
    /**
     * 查询报支管理-费用报销冲借支单档
     * 
     * @param id 报支管理-费用报销冲借支单档主键
     * @return 报支管理-费用报销冲借支单档
     */
    public FinanceAoReimbursementLoan selectFinanceAoReimbursementLoanById(String id);

    /**
     * 查询报支管理-费用报销冲借支单档列表
     *
     * @author 265799
     * @date 2023-06-28
     * @param financeAoReimbursementLoan 报支管理-费用报销冲借支单档
     * @return 报支管理-费用报销冲借支单档集合
     */
    public List<FinanceAoReimbursementLoan> selectFinanceAoReimbursementLoanList(FinanceAoReimbursementLoan financeAoReimbursementLoan);
    /**
     * 查询报支管理-费用报销冲借支单档列表
     *
     * @author 265799
     * @date 2023-06-28
     * @param financeAoReimbursementLoan 报支管理-费用报销冲借支单档
     * @return 报支管理-费用报销冲借支单档集合
     */
    public List<FinanceAoReimbursementLoan> selectFinanceAoReimbursementLoanListLoan(FinanceAoReimbursementLoan financeAoReimbursementLoan);

    /**
     * 新增报支管理-费用报销冲借支单档
     *
     * @author 265799
     * @date 2023-06-28
     * @param financeAoReimbursementLoan 报支管理-费用报销冲借支单档
     * @return 结果
     */
    public int insertFinanceAoReimbursementLoan(FinanceAoReimbursementLoan financeAoReimbursementLoan);

    /**
     * 修改报支管理-费用报销冲借支单档
     *
     * @author 265799
     * @date 2023-06-28
     * @param financeAoReimbursementLoan 报支管理-费用报销冲借支单档
     * @return 结果
     */
    public int updateFinanceAoReimbursementLoan(FinanceAoReimbursementLoan financeAoReimbursementLoan);

    /**
     * 批量删除报支管理-费用报销冲借支单档
     *
     * @author 265799
     * @date 2023-06-28
     * @param ids 需要删除的报支管理-费用报销冲借支单档主键集合
     * @return 结果
     */
    public int deleteFinanceAoReimbursementLoanByIds(String[] ids);

    /**
     * 删除报支管理-费用报销冲借支单档信息
     *
     * @author 265799
     * @date 2023-06-28
     * @param id 报支管理-费用报销冲借支单档主键
     * @return 结果
     */
    public int deleteFinanceAoReimbursementLoanById(String id);
}
