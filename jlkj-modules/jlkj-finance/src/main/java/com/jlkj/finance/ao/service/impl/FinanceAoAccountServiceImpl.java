package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.domain.*;
import com.jlkj.finance.ao.mapper.FinanceAoAccountMapper;

import com.jlkj.finance.ao.service.IFinanceAoAccountService;
import com.jlkj.finance.utils.ConstantsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 会计确认主档Service业务层处理
 *
 * @author 265799
 * @date 2023-07-04
 * @version 1.0
 */
@Service
public class FinanceAoAccountServiceImpl implements IFinanceAoAccountService
{
    @Autowired
    private FinanceAoAccountMapper financeAoAccountMapper;
    @Autowired
    private FinanceAoReimbursementServiceImpl financeAoReimbursementService;
    @Autowired
    private FinanceAoRepaymentServiceImpl financeAoRepaymentService;
    @Autowired
    private  FinanceAoCashierServiceImpl financeAoCashierService;
    /**
     * 查询会计确认主档
     *
     * @author 265799
     * @date 2023-07-04
     * @param id 会计确认主档主键
     * @return 会计确认主档
     */
    @Override
    public FinanceAoAccount selectFinanceAoAccountById(String id)
    {
        return financeAoAccountMapper.selectFinanceAoAccountById(id);
    }

    /**
     * 查询会计确认主档列表
     *
     * @author 265799
     * @date 2023-07-04
     * @param financeAoAccount 会计确认主档
     * @return 会计确认主档
     */
    @Override
    public List<FinanceAoAccount> selectFinanceAoAccountList(FinanceAoAccount financeAoAccount)
    {

        financeAoAccount.setAccountBy(SecurityUtils.getUsername());
        if (StringUtils.isEmpty(financeAoAccount.getCompanyId())){
            financeAoAccount.setCompanyId("J00");
        }
        return financeAoAccountMapper.selectFinanceAoAccountList(financeAoAccount);
    }
    /**
     * 查询会计明细确认主档列表
     *
     * @param financeAoAccount 会计确认主档
     * @return 会计确认主档
     */
    @Override
    public List<FinanceAoAccount> selectDetailList(FinanceAoAccount financeAoAccount)
    {
        financeAoAccount.setAccountBy(SecurityUtils.getUsername());
        return financeAoAccountMapper.selectDetailList(financeAoAccount);
    }
    /**
     * 新增会计确认主档
     *
     * @author 265799
     * @date 2023-07-04
     * @param financeAoAccount 会计确认主档
     * @return 结果
     */

    @Override
    public int insertFinanceAoAccount(FinanceAoAccount financeAoAccount)
    {
        int rows ;
        financeAoAccount.setBillId(financeAoAccount.getId());
        financeAoAccount.setId(IdUtils.fastSimpleUUID());
        financeAoAccount.setCreateTime(DateUtils.getNowDate());
         rows = financeAoAccountMapper.insertFinanceAoAccount(financeAoAccount);
        insertFinanceAoAccountDetail(financeAoAccount);
        return rows;
    }

    /**
     * 修改报销会计确认主档
     *
     * @author 265799
     * @date 2023-07-04
     * @param financeAoAccount 会计确认主档
     * @return 结果
     */

    @Override
    public int updateFinanceAoAccount(FinanceAoAccount financeAoAccount)
    {
        FinanceAoReimbursement financeAoReimbursement = new FinanceAoReimbursement();
        BeanUtils.copyProperties( financeAoAccount,financeAoReimbursement);
        int rows ;
        List<FinanceAoAccountDetail> detailAccountList = financeAoAccount.getDetailAccountList();
        financeAoAccount.setId(detailAccountList.get(0).getAccountId());
        financeAoAccount.setUpdateTime(DateUtils.getNowDate());
        financeAoAccountMapper.deleteFinanceAoAccountDetailByAccountId(financeAoAccount.getId());
        financeAoAccount.setBillId(financeAoReimbursement.getId());
        insertFinanceAoAccountDetail(financeAoAccount);
        financeAoAccount.setId(financeAoReimbursement.getId());
        rows = financeAoAccountMapper.updateFinanceAoAccount(financeAoAccount);
        rows = financeAoReimbursementService.updateFinanceAoReimbursement(financeAoReimbursement);
        //报销出纳新增
        if(financeAoAccount.getStatus().equals(ConstantsUtil.STR_TEN)){
            FinanceAoCashier financeAoCashier =new FinanceAoCashier();
            BeanUtils.copyProperties( financeAoAccount,financeAoCashier);
             financeAoCashier.setCashierBy( financeAoAccount.getAccountBy());
            financeAoCashier.setCashierName(financeAoAccount.getAccountName());
            List<FinanceAoAccountCashier> detailAccountLoanList = new ArrayList<>();
            List<FinanceAoAccountDetail> detailAccountList1 = financeAoAccount.getDetailAccountList();
            for (FinanceAoAccountDetail financeAoAccountDetail :detailAccountList1){
                FinanceAoAccountCashier financeAoAccountCashier = new FinanceAoAccountCashier();
                BeanUtils.copyProperties( financeAoAccountDetail,financeAoAccountCashier);
                detailAccountLoanList.add(financeAoAccountCashier);
            }
            financeAoCashier.setFinanceAoAccountCashierList(detailAccountLoanList);
            rows = financeAoCashierService.insertFinanceAoCashier(financeAoCashier);
        }
        return rows;
    }
    /**
     * 还款报销会计确认主档
     *
     * @author 265799
     * @date 2023-07-04
     * @param financeAoAccount 会计确认主档
     * @return 结果
     */

    @Override
    public int updateFinanceAoAccountRepayment(FinanceAoAccount financeAoAccount)
    {
        FinanceAoRepayment financeAoRepayment = new FinanceAoRepayment();
        BeanUtils.copyProperties( financeAoAccount,financeAoRepayment);
        int rows ;
        List<FinanceAoAccountDetail> detailAccountList = financeAoAccount.getDetailAccountList();
        financeAoAccount.setId(detailAccountList.get(0).getAccountId());
        financeAoAccount.setUpdateTime(DateUtils.getNowDate());
        financeAoAccountMapper.deleteFinanceAoAccountDetailByAccountId(financeAoAccount.getId());
        financeAoAccount.setBillId(financeAoRepayment.getId());
        insertFinanceAoAccountDetail(financeAoAccount);
        financeAoAccount.setId(financeAoRepayment.getId());
        rows = financeAoAccountMapper.updateFinanceAoAccount(financeAoAccount);
        rows = financeAoRepaymentService.updateFinanceAoRepayment(financeAoRepayment);
        return rows;
    }
    /**
     * 批量删除会计确认主档
     *
     * @author 265799
     * @date 2023-07-04
     * @param ids 需要删除的会计确认主档主键
     * @return 结果
     */

    @Override
    public int deleteFinanceAoAccountByIds(String[] ids)
    {
        financeAoAccountMapper.deleteFinanceAoAccountDetailByAccountIds(ids);
        return financeAoAccountMapper.deleteFinanceAoAccountByIds(ids);
    }

    /**
     * 删除会计确认主档信息
     *
     * @author 265799
     * @date 2023-07-04
     * @param id 会计确认主档主键
     * @return 结果
     */

    @Override
    public int deleteFinanceAoAccountById(String id)
    {
        financeAoAccountMapper.deleteFinanceAoAccountDetailByAccountId(id);
        return financeAoAccountMapper.deleteFinanceAoAccountById(id);
    }

    /**
     * 新增会计处理明细信息
     *
     * @author 265799
     * @date 2023-07-04
     * @param financeAoAccount 会计确认主档对象
     */
    public void insertFinanceAoAccountDetail(FinanceAoAccount financeAoAccount)
    {
        List<FinanceAoAccountDetail> financeAoAccountDetailList = financeAoAccount.getDetailAccountList();
        String id = financeAoAccount.getId();
        if (StringUtils.isNotNull(financeAoAccountDetailList))
        {
            List<FinanceAoAccountDetail> list = new ArrayList<FinanceAoAccountDetail>();
            for (FinanceAoAccountDetail financeAoAccountDetail : financeAoAccountDetailList)
            {
                financeAoAccountDetail.setCreateTime(financeAoAccount.getCreateTime());
                financeAoAccountDetail.setUpdateTime(financeAoAccount.getUpdateTime());
                financeAoAccountDetail.setBillNo(financeAoAccount.getBillNo());
                financeAoAccountDetail.setBillId(financeAoAccount.getBillId());
                financeAoAccountDetail.setCompanyId(financeAoAccount.getCompanyId());
                financeAoAccountDetail.setId(IdUtils.fastSimpleUUID());
                financeAoAccountDetail.setAccountId(id);
                list.add(financeAoAccountDetail);
            }
            if (list.size() > 0)
            {
                financeAoAccountMapper.batchFinanceAoAccountDetail(list);
            }
        }
    }
}
