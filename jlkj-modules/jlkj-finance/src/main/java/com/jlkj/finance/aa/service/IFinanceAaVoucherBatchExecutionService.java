package com.jlkj.finance.aa.service;

import com.jlkj.finance.aa.domain.FinanceAaVoucher;

import java.util.List;

/**
 * 凭证批量执行Service接口
 *
 * @author 265799
 * @date 2023-04-24
 */
public interface IFinanceAaVoucherBatchExecutionService {
    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主集合
     */
    public List<FinanceAaVoucher> selectFinanceAaVoucherList(FinanceAaVoucher financeAaVoucher);

    /**
     * 确认和取消确认凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    public int updateFinanceAaVoucherStatus(FinanceAaVoucher financeAaVoucher);
    /**
     * 过账凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    public int updateFinanceAaVoucherCross(FinanceAaVoucher financeAaVoucher);


    /**
     * 批量删除凭证维护-主
     *
     * @param financeAaVoucher 需要删除的凭证维护-主主键集合
     * @return 结果
     */
    public int deleteFinanceAaVoucherByIds(FinanceAaVoucher financeAaVoucher);

}
