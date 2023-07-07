package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.finance.ao.domain.FinanceAoReimbursement;
import com.jlkj.finance.ao.domain.FinanceAoReimbursementDetail;
import com.jlkj.finance.ao.mapper.FinanceAoReimbursementMapper;
import com.jlkj.finance.ao.service.IFinanceAoReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 费用报销主档Service业务层处理
 *
 * @author 265799
 * @date 2023-07-03
 * @version 1.0
 */
@Service
public class FinanceAoReimbursementServiceImpl implements IFinanceAoReimbursementService
{
    @Autowired
    private FinanceAoReimbursementMapper financeAoReimbursementMapper;

    /**
     * 查询费用报销主档
     *
     * @author 265799
     * @date 2023-07-03
     * @param id 费用报销主档主键
     * @return 费用报销主档
     */
    @Override
    public FinanceAoReimbursement selectFinanceAoReimbursementById(String id)
    {
        return financeAoReimbursementMapper.selectFinanceAoReimbursementById(id);
    }

    /**
     * 查询费用报销主档列表
     *
     * @author 265799
     * @date 2023-07-03
     * @param financeAoReimbursement 费用报销主档
     * @return 费用报销主档
     */
    @Override
    public List<FinanceAoReimbursement> selectFinanceAoReimbursementList(FinanceAoReimbursement financeAoReimbursement)
    {
        return financeAoReimbursementMapper.selectFinanceAoReimbursementList(financeAoReimbursement);
    }

    /**
     * 新增费用报销主档
     *
     * @author 265799
     * @date 2023-07-03
     * @param financeAoReimbursement 费用报销主档
     * @return 结果
     */

    @Override
    public int insertFinanceAoReimbursement(FinanceAoReimbursement financeAoReimbursement)
    {
        financeAoReimbursement.setCreateTime(DateUtils.getNowDate());
        int rows = financeAoReimbursementMapper.insertFinanceAoReimbursement(financeAoReimbursement);
        insertFinanceAoReimbursementDetail(financeAoReimbursement);
        return rows;
    }

    /**
     * 修改费用报销主档
     *
     * @author 265799
     * @date 2023-07-03
     * @param financeAoReimbursement 费用报销主档
     * @return 结果
     */

    @Override
    public int updateFinanceAoReimbursement(FinanceAoReimbursement financeAoReimbursement)
    {
        financeAoReimbursementMapper.deleteFinanceAoReimbursementDetailByParentId(financeAoReimbursement.getId());
        insertFinanceAoReimbursementDetail(financeAoReimbursement);
        return financeAoReimbursementMapper.updateFinanceAoReimbursement(financeAoReimbursement);
    }

    /**
     * 批量删除费用报销主档
     *
     * @author 265799
     * @date 2023-07-03
     * @param ids 需要删除的费用报销主档主键
     * @return 结果
     */

    @Override
    public int deleteFinanceAoReimbursementByIds(String[] ids)
    {
        financeAoReimbursementMapper.deleteFinanceAoReimbursementDetailByParentIds(ids);
        return financeAoReimbursementMapper.deleteFinanceAoReimbursementByIds(ids);
    }

    /**
     * 删除费用报销主档信息
     *
     * @author 265799
     * @date 2023-07-03
     * @param id 费用报销主档主键
     * @return 结果
     */

    @Override
    public int deleteFinanceAoReimbursementById(String id)
    {
        financeAoReimbursementMapper.deleteFinanceAoReimbursementDetailByParentId(id);
        return financeAoReimbursementMapper.deleteFinanceAoReimbursementById(id);
    }

    /**
     * 新增费用报销明细档信息
     *
     * @author 265799
     * @date 2023-07-03
     * @param financeAoReimbursement 费用报销主档对象
     */
    public void insertFinanceAoReimbursementDetail(FinanceAoReimbursement financeAoReimbursement)
    {
        List<FinanceAoReimbursementDetail> financeAoReimbursementDetailList = financeAoReimbursement.getFinanceAoReimbursementDetailList();
        String id = financeAoReimbursement.getId();
        if (StringUtils.isNotNull(financeAoReimbursementDetailList))
        {
            List<FinanceAoReimbursementDetail> list = new ArrayList<FinanceAoReimbursementDetail>();
            for (FinanceAoReimbursementDetail financeAoReimbursementDetail : financeAoReimbursementDetailList)
            {
                financeAoReimbursementDetail.setParentId(id);
                list.add(financeAoReimbursementDetail);
            }
            if (list.size() > 0)
            {
                financeAoReimbursementMapper.batchFinanceAoReimbursementDetail(list);
            }
        }
    }
}
