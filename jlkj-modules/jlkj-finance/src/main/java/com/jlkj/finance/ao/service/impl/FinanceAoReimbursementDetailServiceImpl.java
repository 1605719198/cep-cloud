package com.jlkj.finance.ao.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.ao.mapper.FinanceAoReimbursementDetailMapper;
import com.jlkj.finance.ao.domain.FinanceAoReimbursementDetail;
import com.jlkj.finance.ao.service.IFinanceAoReimbursementDetailService;

/**
 * 费用报销明细档Service业务层处理
 *
 * @author 265799
 * @date 2023-06-27
 */
@Service
public class FinanceAoReimbursementDetailServiceImpl implements IFinanceAoReimbursementDetailService
{
    @Autowired
    private FinanceAoReimbursementDetailMapper financeAoReimbursementDetailMapper;

    /**
     * 查询费用报销明细档
     *
     * @author 265799
     * @date 2023-06-27
     * @param id 费用报销明细档主键
     * @return 费用报销明细档
     */
    @Override
    public FinanceAoReimbursementDetail selectFinanceAoReimbursementDetailById(String id)
    {
        return financeAoReimbursementDetailMapper.selectFinanceAoReimbursementDetailById(id);
    }

    /**
     * 查询费用报销明细档列表
     *
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursementDetail 费用报销明细档
     * @return 费用报销明细档
     */
    @Override
    public List<FinanceAoReimbursementDetail> selectFinanceAoReimbursementDetailList(FinanceAoReimbursementDetail financeAoReimbursementDetail)
    {
        return financeAoReimbursementDetailMapper.selectFinanceAoReimbursementDetailList(financeAoReimbursementDetail);
    }

    /**
     * 新增费用报销明细档
     *
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursementDetail 费用报销明细档
     * @return 结果
     */
    @Override
    public int insertFinanceAoReimbursementDetail(FinanceAoReimbursementDetail financeAoReimbursementDetail)
    {
        financeAoReimbursementDetail.setCreateTime(DateUtils.getNowDate());
        return financeAoReimbursementDetailMapper.insertFinanceAoReimbursementDetail(financeAoReimbursementDetail);
    }

    /**
     * 修改费用报销明细档
     *
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursementDetail 费用报销明细档
     * @return 结果
     */
    @Override
    public int updateFinanceAoReimbursementDetail(FinanceAoReimbursementDetail financeAoReimbursementDetail)
    {
        financeAoReimbursementDetail.setUpdateTime(DateUtils.getNowDate());
        return financeAoReimbursementDetailMapper.updateFinanceAoReimbursementDetail(financeAoReimbursementDetail);
    }

    /**
     * 批量删除费用报销明细档
     *
     * @author 265799
     * @date 2023-06-27
     * @param ids 需要删除的费用报销明细档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoReimbursementDetailByIds(String[] ids)
    {
        return financeAoReimbursementDetailMapper.deleteFinanceAoReimbursementDetailByIds(ids);
    }

    /**
     * 删除费用报销明细档信息
     *
     * @author 265799
     * @date 2023-06-27
     * @param id 费用报销明细档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoReimbursementDetailById(String id)
    {
        return financeAoReimbursementDetailMapper.deleteFinanceAoReimbursementDetailById(id);
    }
}
