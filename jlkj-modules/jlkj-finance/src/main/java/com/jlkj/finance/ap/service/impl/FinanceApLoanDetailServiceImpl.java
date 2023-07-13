package com.jlkj.finance.ap.service.impl;

import com.jlkj.finance.ap.domain.FinanceApLoanDetail;
import com.jlkj.finance.ap.mapper.FinanceApLoanDetailMapper;
import com.jlkj.finance.ap.service.IFinanceApLoanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预付申请明细档Service业务层处理
 *
 * @author 265799
 * @date 2023-07-10
 * @version 1.0
 */
@Service
public class FinanceApLoanDetailServiceImpl implements IFinanceApLoanDetailService
{
    @Autowired
    private FinanceApLoanDetailMapper financeApLoanDetailMapper;

    /**
     * 查询预付申请明细档
     *
     * @author 265799
     * @date 2023-07-10
     * @param id 预付申请明细档主键
     * @return 预付申请明细档
     */
    @Override
    public FinanceApLoanDetail selectFinanceApLoanDetailById(String id)
    {
        return financeApLoanDetailMapper.selectFinanceApLoanDetailById(id);
    }

    /**
     * 查询预付申请明细档列表
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoanDetail 预付申请明细档
     * @return 预付申请明细档
     */
    @Override
    public List<FinanceApLoanDetail> selectFinanceApLoanDetailList(FinanceApLoanDetail financeApLoanDetail)
    {
        return financeApLoanDetailMapper.selectFinanceApLoanDetailList(financeApLoanDetail);
    }

    /**
     * 新增预付申请明细档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoanDetail 预付申请明细档
     * @return 结果
     */
    @Override
    public int insertFinanceApLoanDetail(FinanceApLoanDetail financeApLoanDetail)
    {
        return financeApLoanDetailMapper.insertFinanceApLoanDetail(financeApLoanDetail);
    }

    /**
     * 修改预付申请明细档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoanDetail 预付申请明细档
     * @return 结果
     */
    @Override
    public int updateFinanceApLoanDetail(FinanceApLoanDetail financeApLoanDetail)
    {
        return financeApLoanDetailMapper.updateFinanceApLoanDetail(financeApLoanDetail);
    }

    /**
     * 批量删除预付申请明细档
     *
     * @author 265799
     * @date 2023-07-10
     * @param ids 需要删除的预付申请明细档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApLoanDetailByIds(String[] ids)
    {
        return financeApLoanDetailMapper.deleteFinanceApLoanDetailByIds(ids);
    }

    /**
     * 删除预付申请明细档信息
     *
     * @author 265799
     * @date 2023-07-10
     * @param id 预付申请明细档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceApLoanDetailById(String id)
    {
        return financeApLoanDetailMapper.deleteFinanceApLoanDetailById(id);
    }
}
