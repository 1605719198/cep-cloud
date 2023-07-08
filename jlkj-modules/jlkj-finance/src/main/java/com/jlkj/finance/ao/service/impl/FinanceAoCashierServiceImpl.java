package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.domain.FinanceAoAccountCashier;
import com.jlkj.finance.ao.domain.FinanceAoCashier;
import com.jlkj.finance.ao.domain.FinanceAoReimbursement;
import com.jlkj.finance.ao.domain.FinanceAoRepayment;
import com.jlkj.finance.ao.mapper.FinanceAoCashierMapper;
import com.jlkj.finance.ao.service.IFinanceAoCashierService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 出纳确认主档Service业务层处理
 *
 * @author 265799
 * @date 2023-07-05
 * @version 1.0
 */
@Service
public class FinanceAoCashierServiceImpl implements IFinanceAoCashierService
{
    @Autowired
    private FinanceAoCashierMapper financeAoCashierMapper;
    @Autowired
    private FinanceAoReimbursementServiceImpl financeAoReimbursementService;
    @Autowired
    private FinanceAoRepaymentServiceImpl financeAoRepaymentService;


    /**
     * 查询出纳确认主档
     *
     * @author 265799
     * @date 2023-07-05
     * @param id 出纳确认主档主键
     * @return 出纳确认主档
     */
    @Override
    public FinanceAoCashier selectFinanceAoCashierById(String id)
    {
        return financeAoCashierMapper.selectFinanceAoCashierById(id);
    }

    /**
     * 查询出纳确认主档列表
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier 出纳确认主档
     * @return 出纳确认主档
     */
    @Override
    public List<FinanceAoCashier> selectFinanceAoCashierList(FinanceAoCashier financeAoCashier)
    {
        financeAoCashier.setCashierBy(SecurityUtils.getUsername());
        if (StringUtils.isEmpty(financeAoCashier.getCompanyId())){
            financeAoCashier.setCompanyId("J00");
        }
        return financeAoCashierMapper.selectFinanceAoCashierList(financeAoCashier);
    }

    /**
     * 新增出纳确认主档
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier 出纳确认主档
     * @return 结果
     */

    @Override
    public int insertFinanceAoCashier(FinanceAoCashier financeAoCashier)
    {
        int rows ;
        financeAoCashier.setBillId(financeAoCashier.getId());
        financeAoCashier.setId(IdUtils.fastSimpleUUID());
        financeAoCashier.setCreateTime(DateUtils.getNowDate());
         rows = financeAoCashierMapper.insertFinanceAoCashier(financeAoCashier);
        insertFinanceAoAccountCashier(financeAoCashier);
        return rows;
    }

    /**
     * 报销修改出纳确认主档
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier 出纳确认主档
     * @return 结果
     */

    @Override
    public int updateFinanceAoCashier(FinanceAoCashier financeAoCashier)
    {
        FinanceAoReimbursement financeAoReimbursement = new FinanceAoReimbursement();
        BeanUtils.copyProperties( financeAoCashier,financeAoReimbursement);
        int rows ;
        List<FinanceAoAccountCashier> detailAccountLoanList = financeAoCashier.getDetailAccountLoanList();
        financeAoCashier.setId(detailAccountLoanList.get(0).getAccountId());
        financeAoCashier.setUpdateTime(DateUtils.getNowDate());
        financeAoCashierMapper.deleteFinanceAoAccountCashierByAccountId(financeAoCashier.getId());
        financeAoCashier.setBillId(financeAoReimbursement.getId());
        insertFinanceAoAccountCashier(financeAoCashier);
        financeAoCashier.setId(financeAoReimbursement.getId());
        rows = financeAoCashierMapper.updateFinanceAoCashier(financeAoCashier);
        rows = financeAoReimbursementService.updateFinanceAoReimbursement(financeAoReimbursement);
        return rows;
    }
    /**
     * 还款修改出纳确认主档
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier 出纳确认主档
     * @return 结果
     */

    @Override
    public int updateFinanceAoCashierRepayment(FinanceAoCashier financeAoCashier)
    {
        FinanceAoRepayment financeAoRepayment = new FinanceAoRepayment();
        BeanUtils.copyProperties( financeAoCashier,financeAoRepayment);
        int rows ;
        List<FinanceAoAccountCashier> detailAccountLoanList = financeAoCashier.getDetailAccountLoanList();
        financeAoCashier.setId(detailAccountLoanList.get(0).getAccountId());
        financeAoCashier.setUpdateTime(DateUtils.getNowDate());
        financeAoCashierMapper.deleteFinanceAoAccountCashierByAccountId(financeAoCashier.getId());
        financeAoCashier.setBillId(financeAoRepayment.getId());
        insertFinanceAoAccountCashier(financeAoCashier);
        financeAoCashier.setId(financeAoRepayment.getId());
        rows = financeAoCashierMapper.updateFinanceAoCashier(financeAoCashier);
        rows = financeAoRepaymentService.updateFinanceAoRepayment(financeAoRepayment);
        return rows;
    }
    /**
     * 批量删除出纳确认主档
     *
     * @author 265799
     * @date 2023-07-05
     * @param ids 需要删除的出纳确认主档主键
     * @return 结果
     */

    @Override
    public int deleteFinanceAoCashierByIds(String[] ids)
    {
        financeAoCashierMapper.deleteFinanceAoAccountCashierByAccountIds(ids);
        return financeAoCashierMapper.deleteFinanceAoCashierByIds(ids);
    }

    /**
     * 删除出纳确认主档信息
     *
     * @author 265799
     * @date 2023-07-05
     * @param id 出纳确认主档主键
     * @return 结果
     */

    @Override
    public int deleteFinanceAoCashierById(String id)
    {
        financeAoCashierMapper.deleteFinanceAoAccountCashierByAccountId(id);
        return financeAoCashierMapper.deleteFinanceAoCashierById(id);
    }

    /**
     * 新增出纳确认明细档信息
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier 出纳确认主档对象
     */
    public void insertFinanceAoAccountCashier(FinanceAoCashier financeAoCashier)
    {
        List<FinanceAoAccountCashier> financeAoAccountCashierList = financeAoCashier.getDetailAccountLoanList();
        String id = financeAoCashier.getId();
        if (StringUtils.isNotNull(financeAoAccountCashierList))
        {
            List<FinanceAoAccountCashier> list = new ArrayList<FinanceAoAccountCashier>();
            for (FinanceAoAccountCashier financeAoAccountCashier : financeAoAccountCashierList)
            {
                BeanUtils.copyProperties( financeAoCashier,financeAoAccountCashier);
                financeAoAccountCashier.setId(IdUtils.fastSimpleUUID());
                financeAoAccountCashier.setAccountId(id);
                list.add(financeAoAccountCashier);
            }
            if (list.size() > 0)
            {
                financeAoCashierMapper.batchFinanceAoAccountCashier(list);
            }
        }
    }
}
