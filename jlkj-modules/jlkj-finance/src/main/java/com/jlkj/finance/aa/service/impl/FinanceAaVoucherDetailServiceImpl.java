package com.jlkj.finance.aa.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaVoucherDetailMapper;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;
import com.jlkj.finance.aa.service.IFinanceAaVoucherDetailService;

/**
 * 凭证维护-明细Service业务层处理
 *
 * @author 265799
 * @date 2023-04-24
 */
@Service
public class FinanceAaVoucherDetailServiceImpl implements IFinanceAaVoucherDetailService
{
    @Autowired
    private FinanceAaVoucherDetailMapper financeAaVoucherDetailMapper;

    /**
     * 查询凭证维护-明细
     *
     * @param id 凭证维护-明细主键
     * @return 凭证维护-明细
     */
    @Override
    public FinanceAaVoucherDetail selectFinanceAaVoucherDetailById(String id)
    {

        return financeAaVoucherDetailMapper.selectFinanceAaVoucherDetailById(id);
    }

    /**
     * 查询凭证维护-明细列表
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 凭证维护-明细
     */
    @Override
    public List<FinanceAaVoucherDetail> selectFinanceAaVoucherDetailList(FinanceAaVoucherDetail financeAaVoucherDetail)
    {
        return financeAaVoucherDetailMapper.selectFinanceAaVoucherDetailList(financeAaVoucherDetail);
    }
    /**
     * 更多-明细列表
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 凭证维护-明细
     */
    @Override
    public List<FinanceAaVoucherDetail> selectFinanceVoucherList(FinanceAaVoucherDetail financeAaVoucherDetail)
    {
        return financeAaVoucherDetailMapper.selectFinanceAaVoucherList(financeAaVoucherDetail);
    }

    /**
     * 新增凭证维护-明细
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 结果
     */
    @Override
    public int insertFinanceAaVoucherDetail(FinanceAaVoucherDetail financeAaVoucherDetail)
    {
        return financeAaVoucherDetailMapper.insertFinanceAaVoucherDetail(financeAaVoucherDetail);
    }

    /**
     * 修改凭证维护-明细
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 结果
     */
    @Override
    public int updateFinanceAaVoucherDetail(FinanceAaVoucherDetail financeAaVoucherDetail)
    {
        return financeAaVoucherDetailMapper.updateFinanceAaVoucherDetail(financeAaVoucherDetail);
    }

    /**
     * 批量删除凭证维护-明细
     *
     * @param ids 需要删除的凭证维护-明细主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaVoucherDetailByIds(String[] ids)
    {
        return financeAaVoucherDetailMapper.deleteFinanceAaVoucherDetailByIds(ids);
    }

    /**
     * 删除凭证维护-明细信息
     *
     * @param id 凭证维护-明细主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaVoucherDetailById(String id)
    {
        return financeAaVoucherDetailMapper.deleteFinanceAaVoucherDetailById(id);
    }
}
