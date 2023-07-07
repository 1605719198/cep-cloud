package com.jlkj.finance.ao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.finance.ao.domain.FinanceAoReimbursementLoan;
import com.jlkj.finance.ao.domain.FinanceAoRepayment;
import com.jlkj.finance.ao.mapper.FinanceAoReimbursementLoanMapper;
import com.jlkj.finance.ao.mapper.FinanceAoRepaymentMapper;
import com.jlkj.finance.ao.service.IFinanceAoReimbursementLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 报支管理-费用报销冲借支单档Service业务层处理
 *
 * @author 265799
 * @date 2023-06-28
 */
@Service
public class FinanceAoReimbursementLoanServiceImpl extends
        ServiceImpl<FinanceAoReimbursementLoanMapper,FinanceAoReimbursementLoan> implements IFinanceAoReimbursementLoanService
{
    @Autowired
    private FinanceAoReimbursementLoanMapper financeAoReimbursementLoanMapper;
    @Autowired
    private FinanceAoRepaymentMapper financeAoRepaymentMapper;
    /**
     * 查询报支管理-费用报销冲借支单档
     *
     * @author 265799
     * @date 2023-06-28
     * @param id 报支管理-费用报销冲借支单档主键
     * @return 报支管理-费用报销冲借支单档
     */
    @Override
    public FinanceAoReimbursementLoan selectFinanceAoReimbursementLoanById(String id)
    {

        return financeAoReimbursementLoanMapper.selectFinanceAoReimbursementLoanById(id);
    }

    /**
     * 查询报支管理-费用报销冲借支单档列表
     *
     * @author 265799
     * @date 2023-06-28
     * @param financeAoReimbursementLoan 报支管理-费用报销冲借支单档
     * @return 报支管理-费用报销冲借支单档
     */
    @Override
    public List<FinanceAoReimbursementLoan> selectFinanceAoReimbursementLoanList(FinanceAoReimbursementLoan financeAoReimbursementLoan)
    {


        return financeAoReimbursementLoanMapper.selectFinanceAoReimbursementLoanList(financeAoReimbursementLoan);
    }
    /**
     * 查询报支管理-费用报销冲借支单档列表
     *
     * @author 265799
     * @date 2023-06-28
     * @param financeAoReimbursementLoan 报支管理-费用报销冲借支单档
     * @return 报支管理-费用报销冲借支单档
     */
    @Override
    public List<FinanceAoReimbursementLoan> selectFinanceAoReimbursementLoanListLoan(FinanceAoReimbursementLoan financeAoReimbursementLoan)
    {
        List<FinanceAoReimbursementLoan> financeAoReimbursementLoans = financeAoReimbursementLoanMapper.selectFinanceAoReimbursementLoanListLoan(financeAoReimbursementLoan);
        for (FinanceAoReimbursementLoan financeAoReimbursementLoan1 :financeAoReimbursementLoans){
            FinanceAoReimbursementLoan financeAoReimbursementLoan2 = financeAoReimbursementLoanMapper.selectCancelledAmt(financeAoReimbursementLoan1);
            if (financeAoReimbursementLoan2!=null){
                FinanceAoRepayment financeAoRepayment = financeAoRepaymentMapper.selectLoanId(financeAoReimbursementLoan1.getLoanId());
                if (financeAoRepayment!=null){
                    financeAoReimbursementLoan1.setCancelledAmt(financeAoReimbursementLoan2.getCancelledAmt().add(financeAoRepayment.getAmt()));


                }else {
                    financeAoReimbursementLoan1.setCancelledAmt(financeAoReimbursementLoan2.getCancelledAmt());
                }

            }else {
                financeAoReimbursementLoan1.setCancelledAmt(BigDecimal.ZERO);
            }
            //借支金额
            BigDecimal cancelledAmt = financeAoReimbursementLoan1.getCancelledAmt();
            //已销账金额
            BigDecimal cancelledAmt1 = financeAoReimbursementLoan1.getTotalAmt();
            //未销账金额
            BigDecimal subtract = cancelledAmt.subtract(cancelledAmt1);
            financeAoReimbursementLoan1.setUncollectedAccountsAmt(subtract);
        }
        return financeAoReimbursementLoans;
    }

    /**
     * 新增报支管理-费用报销冲借支单档
     *
     * @author 265799
     * @date 2023-06-28
     * @param financeAoReimbursementLoan 报支管理-费用报销冲借支单档
     * @return 结果
     */
    @Override
    public int insertFinanceAoReimbursementLoan(FinanceAoReimbursementLoan financeAoReimbursementLoan)
    {
        financeAoReimbursementLoan.setCreateTime(DateUtils.getNowDate());
        return financeAoReimbursementLoanMapper.insertFinanceAoReimbursementLoan(financeAoReimbursementLoan);
    }

    /**
     * 修改报支管理-费用报销冲借支单档
     *
     * @author 265799
     * @date 2023-06-28
     * @param financeAoReimbursementLoan 报支管理-费用报销冲借支单档
     * @return 结果
     */
    @Override
    public int updateFinanceAoReimbursementLoan(FinanceAoReimbursementLoan financeAoReimbursementLoan)
    {
        financeAoReimbursementLoan.setUpdateTime(DateUtils.getNowDate());
        return financeAoReimbursementLoanMapper.updateFinanceAoReimbursementLoan(financeAoReimbursementLoan);
    }

    /**
     * 批量删除报支管理-费用报销冲借支单档
     *
     * @author 265799
     * @date 2023-06-28
     * @param ids 需要删除的报支管理-费用报销冲借支单档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoReimbursementLoanByIds(String[] ids)
    {
        return financeAoReimbursementLoanMapper.deleteFinanceAoReimbursementLoanByIds(ids);
    }

    /**
     * 删除报支管理-费用报销冲借支单档信息
     *
     * @author 265799
     * @date 2023-06-28
     * @param id 报支管理-费用报销冲借支单档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoReimbursementLoanById(String id)
    {
        return financeAoReimbursementLoanMapper.deleteFinanceAoReimbursementLoanById(id);
    }


}
