package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.finance.aa.domain.FinanceAaAcctcodeCorp;
import com.jlkj.finance.aa.mapper.FinanceAaAcctcodeCorpMapper;
import com.jlkj.finance.ao.domain.*;
import com.jlkj.finance.ao.dto.FinanceAoAccountDto;
import com.jlkj.finance.ao.mapper.FinanceAoAccountDetailMapper;
import com.jlkj.finance.ao.mapper.FinanceAoAccountMapper;
import com.jlkj.finance.ao.mapper.FinanceAoItemCompDetailMapper;
import com.jlkj.finance.ao.service.IFinanceAoAccountDetailService;
import com.jlkj.finance.utils.ConstantsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 会计处理明细Service业务层处理
 *
 * @author 265799
 * @date 2023-06-25
 */
@Service
public class FinanceAoAccountDetailServiceImpl implements IFinanceAoAccountDetailService
{
    @Autowired
    private FinanceAoAccountDetailMapper financeAoAccountDetailMapper;
    @Autowired
    private FinanceAoItemCompDetailMapper financeAoItemCompDetailMapper;
    @Autowired
    private FinanceAoLoanServiceImpl financeAoLoanService;
    @Autowired
    private FinanceAaAcctcodeCorpMapper financeAaAcctcodeCorpMapper;
    @Autowired
    private FinanceAoAccountMapper financeAoAccountMapper;
    @Autowired
    private  FinanceAoCashierServiceImpl financeAoCashierService;
    /**
     * 查询会计处理明细
     *
     * @author 265799
     * @date 2023-06-25
     * @param id 会计处理明细主键
     * @return 会计处理明细
     */
    @Override
    public FinanceAoAccountDetail selectFinanceAoAccountDetailById(String id)
    {
        return financeAoAccountDetailMapper.selectFinanceAoAccountDetailById(id);
    }
    /**
     * 查询会计处理明细列表
     *
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDetail 会计处理明细
     * @return 会计处理明细
     */
    @Override
    public FinanceAoAccountDetail selectListLoanADetail(FinanceAoAccountDetail financeAoAccountDetail)
    {
       FinanceAoAccountDetail financeAoAccountDetails = new FinanceAoAccountDetail();
        FinanceAoItemCompDetail financeAoItemCompDetail= new FinanceAoItemCompDetail();
        BeanUtils.copyProperties(financeAoAccountDetail,financeAoItemCompDetail);
        FinanceAoItemCompDetail financeAoItemCompDetails = financeAoItemCompDetailMapper.selectFinanceAoItemComp(financeAoItemCompDetail);
        financeAoAccountDetails.setAcctCode(financeAoItemCompDetails.getDrAcctCode());
        FinanceAaAcctcodeCorp financeAaAcctcodeCorp1 = financeAaAcctcodeCorpMapper.selectAcctId(financeAoItemCompDetails.getDrAcctCode(), financeAoAccountDetail.getCompanyId());
        financeAoAccountDetails.setAcctId(financeAaAcctcodeCorp1.getAcctId());
        financeAoAccountDetails.setCalTypeCodea(financeAaAcctcodeCorp1.getCalTypeCodea());
        financeAoAccountDetails.setCalTypeCodeb(financeAaAcctcodeCorp1.getCalTypeCodeb());
        return financeAoAccountDetails;
    }
    /**
     * 查询会计处理明细列表
     *
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDetail 会计处理明细
     * @return 会计处理明细
     */
    @Override
    public List<FinanceAoAccountDetail> selectFinanceAoAccountDetailList(FinanceAoAccountDetail financeAoAccountDetail)
    {
        financeAoAccountDetail.setBillId(financeAoAccountDetail.getId());
        return financeAoAccountDetailMapper.selectFinanceAoAccountDetailList(financeAoAccountDetail);
    }

    /**
     * 新增会计处理明细
     *
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDto 会计处理明细
     * @return 结果
     */
    @Override
    public int insertFinanceAoAccountDetail(FinanceAoAccountDto financeAoAccountDto)
    {
        FinanceAoAccountDetail financeAoAccountDetail= new FinanceAoAccountDetail();
        financeAoAccountDetail.setCreateTime(DateUtils.getNowDate());
        int i;
        //会计确认主表新增
        FinanceAoAccount financeAoAccount = new FinanceAoAccount();
        BeanUtils.copyProperties(financeAoAccountDto,financeAoAccount);
        financeAoAccount.setId(IdUtils.fastSimpleUUID());
        financeAoAccount.setBillId(financeAoAccountDto.getId());
        financeAoAccount.setCreateTime(DateUtils.getNowDate());
        i=   financeAoAccountMapper.insertFinanceAoAccount(financeAoAccount);
        //会计确认明细表新增或更新
        BeanUtils.copyProperties( financeAoAccountDto.getDetailAccountLoanList().get(0),financeAoAccountDetail);
        financeAoAccountDetail.setId(IdUtils.fastSimpleUUID());
        financeAoAccountDetail.setBillNo(financeAoAccountDto.getBillNo());
        financeAoAccountDetail.setAccountId(financeAoAccount.getId());
        financeAoAccountDetail.setBillId(financeAoAccountDto.getId());
        financeAoAccountDetail.setCompanyId(financeAoAccountDto.getCompanyId());
        i=  financeAoAccountDetailMapper.insertFinanceAoAccountDetail(financeAoAccountDetail);
        //报支修改
        FinanceAoLoan financeAoLoan =new FinanceAoLoan();
        BeanUtils.copyProperties( financeAoAccountDto,financeAoLoan);
        i = financeAoLoanService.updateFinanceAoLoan(financeAoLoan);
        return i;
    }

    /**
     * 修改会计处理明细
     *
     * @author 265799
     * @date 2023-06-25
     * @param financeAoAccountDto 会计处理明细
     * @return 结果
     */
    @Override
    public int updateFinanceAoAccountDetail(FinanceAoAccountDto financeAoAccountDto)
    {
        int i;
        financeAoAccountDto.setCreateTime(DateUtils.getNowDate());
        FinanceAoAccountDetail financeAoAccountDetail= new FinanceAoAccountDetail();
        //会计确认主表修改
        FinanceAoAccount financeAoAccount = new FinanceAoAccount();
        BeanUtils.copyProperties(financeAoAccountDto,financeAoAccount);
        financeAoAccount.setBillId(financeAoAccountDto.getId());
        financeAoAccount.setCreateTime(DateUtils.getNowDate());
        i=   financeAoAccountMapper.updateFinanceAoAccount(financeAoAccount);
        //会计确认明细表修改
        BeanUtils.copyProperties( financeAoAccountDto.getDetailAccountLoanList().get(0),financeAoAccountDetail);
        financeAoAccountDetail.setBillNo(financeAoAccountDto.getBillNo());
        financeAoAccountDetail.setAccountId(financeAoAccount.getId());
        financeAoAccountDetail.setBillId(financeAoAccountDto.getId());
        financeAoAccountDetail.setCompanyId(financeAoAccountDto.getCompanyId());
        i=  financeAoAccountDetailMapper.updateFinanceAoAccountDetail(financeAoAccountDetail);
        //报支修改
        FinanceAoLoan financeAoLoan =new FinanceAoLoan();
        BeanUtils.copyProperties( financeAoAccountDto,financeAoLoan);
        i = financeAoLoanService.updateFinanceAoLoan(financeAoLoan);
        //借支出纳新增
        if(financeAoAccountDto.getStatus().equals(ConstantsUtil.STR_TEN)){
            FinanceAoCashier financeAoCashier =new FinanceAoCashier();
            BeanUtils.copyProperties( financeAoAccountDto,financeAoCashier);
            financeAoCashier.setCashierBy( financeAoAccountDto.getAccountBy());
            financeAoCashier.setCashierName(financeAoAccountDto.getAccountName());
            List<FinanceAoAccountCashier> detailAccountLoanList = new ArrayList<>();
            List<FinanceAoAccountDetail> detailAccountLoanList1 = financeAoAccountDto.getDetailAccountLoanList();
            for (FinanceAoAccountDetail financeAoAccountDetail1 :detailAccountLoanList1){
                FinanceAoAccountCashier financeAoAccountCashier = new FinanceAoAccountCashier();
                BeanUtils.copyProperties( financeAoAccountDetail1,financeAoAccountCashier);
                detailAccountLoanList.add(financeAoAccountCashier);
            }
            financeAoCashier.setFinanceAoAccountCashierList(detailAccountLoanList);
            i = financeAoCashierService.insertFinanceAoCashier(financeAoCashier);
        }
        return i;

    }

    /**
     * 批量删除会计处理明细
     *
     * @author 265799
     * @date 2023-06-25
     * @param ids 需要删除的会计处理明细主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoAccountDetailByIds(String[] ids)
    {
        return financeAoAccountDetailMapper.deleteFinanceAoAccountDetailByIds(ids);
    }

    /**
     * 删除会计处理明细信息
     *
     * @author 265799
     * @date 2023-06-25
     * @param id 会计处理明细主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoAccountDetailById(String id)
    {
        return financeAoAccountDetailMapper.deleteFinanceAoAccountDetailById(id);
    }
}
