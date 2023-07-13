package com.jlkj.finance.ap.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.ap.mapper.FinanceApConfirmDetailMapper;
import com.jlkj.finance.ap.domain.FinanceApConfirmDetail;
import com.jlkj.finance.ap.service.IFinanceApConfirmDetailService;

/**
 * 会计确认明细Service业务层处理
 *
 * @author jlkj
 * @date 2023-07-10
 * @version 1.0
 */
@Service
public class FinanceApConfirmDetailServiceImpl implements IFinanceApConfirmDetailService
{
    @Autowired
    private FinanceApConfirmDetailMapper financeApConfirmDetailMapper;

    /**
     * 查询会计确认明细
     *
     * @author jlkj
     * @date 2023-07-10
     * @param billId 会计确认明细主键
     * @return 会计确认明细
     */
    @Override
    public FinanceApConfirmDetail selectFinanceApConfirmDetailByBillId(String billId)
    {
        return financeApConfirmDetailMapper.selectFinanceApConfirmDetailByBillId(billId);
    }

    /**
     * 查询会计确认明细列表
     *
     * @author jlkj
     * @date 2023-07-10
     * @param financeApConfirmDetail 会计确认明细
     * @return 会计确认明细
     */
    @Override
    public List<FinanceApConfirmDetail> selectFinanceApConfirmDetailList(FinanceApConfirmDetail financeApConfirmDetail)
    {
        return financeApConfirmDetailMapper.selectFinanceApConfirmDetailList(financeApConfirmDetail);
    }

    /**
     * 新增会计确认明细
     *
     * @author jlkj
     * @date 2023-07-10
     * @param financeApConfirmDetail 会计确认明细
     * @return 结果
     */
    @Override
    public int insertFinanceApConfirmDetail(FinanceApConfirmDetail financeApConfirmDetail)
    {
        return financeApConfirmDetailMapper.insertFinanceApConfirmDetail(financeApConfirmDetail);
    }

    /**
     * 修改会计确认明细
     *
     * @author jlkj
     * @date 2023-07-10
     * @param financeApConfirmDetail 会计确认明细
     * @return 结果
     */
    @Override
    public int updateFinanceApConfirmDetail(FinanceApConfirmDetail financeApConfirmDetail)
    {
        return financeApConfirmDetailMapper.updateFinanceApConfirmDetail(financeApConfirmDetail);
    }

    /**
     * 批量删除会计确认明细
     *
     * @author jlkj
     * @date 2023-07-10
     * @param billIds 需要删除的会计确认明细主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApConfirmDetailByBillIds(String[] billIds)
    {
        return financeApConfirmDetailMapper.deleteFinanceApConfirmDetailByBillIds(billIds);
    }

    /**
     * 删除会计确认明细信息
     *
     * @author jlkj
     * @date 2023-07-10
     * @param billId 会计确认明细主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApConfirmDetailByBillId(String billId)
    {
        return financeApConfirmDetailMapper.deleteFinanceApConfirmDetailByBillId(billId);
    }
}
