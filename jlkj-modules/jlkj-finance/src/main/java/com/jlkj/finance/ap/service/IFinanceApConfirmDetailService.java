package com.jlkj.finance.ap.service;

import java.util.List;
import com.jlkj.finance.ap.domain.FinanceApConfirmDetail;

/**
 * 会计确认明细Service接口
 * 
 * @author jlkj
 * @date 2023-07-10
 */
public interface IFinanceApConfirmDetailService 
{
    /**
     * 查询会计确认明细
     * 
     * @param billId 会计确认明细主键
     * @return 会计确认明细
     */
    public FinanceApConfirmDetail selectFinanceApConfirmDetailByBillId(String billId);

    /**
     * 查询会计确认明细列表
     *
     * @author jlkj
     * @date 2023-07-10
     * @param financeApConfirmDetail 会计确认明细
     * @return 会计确认明细集合
     */
    public List<FinanceApConfirmDetail> selectFinanceApConfirmDetailList(FinanceApConfirmDetail financeApConfirmDetail);

    /**
     * 新增会计确认明细
     *
     * @author jlkj
     * @date 2023-07-10
     * @param financeApConfirmDetail 会计确认明细
     * @return 结果
     */
    public int insertFinanceApConfirmDetail(FinanceApConfirmDetail financeApConfirmDetail);

    /**
     * 修改会计确认明细
     *
     * @author jlkj
     * @date 2023-07-10
     * @param financeApConfirmDetail 会计确认明细
     * @return 结果
     */
    public int updateFinanceApConfirmDetail(FinanceApConfirmDetail financeApConfirmDetail);

    /**
     * 批量删除会计确认明细
     *
     * @author jlkj
     * @date 2023-07-10
     * @param billIds 需要删除的会计确认明细主键集合
     * @return 结果
     */
    public int deleteFinanceApConfirmDetailByBillIds(String[] billIds);

    /**
     * 删除会计确认明细信息
     *
     * @author jlkj
     * @date 2023-07-10
     * @param billId 会计确认明细主键
     * @return 结果
     */
    public int deleteFinanceApConfirmDetailByBillId(String billId);
}
