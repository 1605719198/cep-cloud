package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaAcctcodeCorp;
import com.jlkj.finance.aa.mapper.FinanceAaAcctcodeCorpMapper;
import com.jlkj.finance.ao.domain.*;
import com.jlkj.finance.ao.dto.FinanceAoAccountDto;
import com.jlkj.finance.ao.mapper.FinanceAoAccountCashierMapper;
import com.jlkj.finance.ao.mapper.FinanceAoCashierMapper;
import com.jlkj.finance.ao.mapper.FinanceAoItemCompDetailMapper;
import com.jlkj.finance.ao.service.IFinanceAoAccountCashierService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出纳确认明细档Service业务层处理
 *
 * @author 265799
 * @date 2023-07-05
 * @version 1.0
 */
@Service
public class FinanceAoAccountCashierServiceImpl implements IFinanceAoAccountCashierService
{
    @Autowired
    private FinanceAoAccountCashierMapper financeAoAccountCashierMapper;
    @Autowired
    private FinanceAoItemCompDetailMapper financeAoItemCompDetailMapper;
    @Autowired
    private FinanceAaAcctcodeCorpMapper financeAaAcctcodeCorpMapper;
    @Autowired
    private  FinanceAoCashierServiceImpl financeAoCashierService;
    @Autowired
    private FinanceAoCashierMapper financeAoCashierMapper;
    @Autowired
    private FinanceAoLoanServiceImpl financeAoLoanService;

    /**
     * 查询出纳确认明细档
     *
     * @author 265799
     * @date 2023-07-05
     * @param id 出纳确认明细档主键
     * @return 出纳确认明细档
     */
    @Override
    public FinanceAoAccountCashier selectFinanceAoAccountCashierById(String id)
    {
        return financeAoAccountCashierMapper.selectFinanceAoAccountCashierById(id);
    }

    /**
     * 查询出纳确认明细档列表
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoAccountCashier 出纳确认明细档
     * @return 出纳确认明细档
     */
    @Override
    public List<FinanceAoAccountCashier> selectFinanceAoAccountCashierList(FinanceAoAccountCashier financeAoAccountCashier)
    {
        financeAoAccountCashier.setBillId(financeAoAccountCashier.getId());
        return financeAoAccountCashierMapper.selectFinanceAoAccountCashierList(financeAoAccountCashier);
    }

    /**
     * 查询会计处理明细列表
     * @author 265799
     * @date 2023-07-055
     * @param financeAoAccountCashier 出纳确认明细档
     * @return 会计处理明细
     */
    @Override
    public FinanceAoAccountCashier selectListLoanADetailCashier(FinanceAoAccountCashier financeAoAccountCashier)
    {
        FinanceAoAccountCashier financeAoAccountCashier1 = new FinanceAoAccountCashier();
        FinanceAoItemCompDetail financeAoItemCompDetail= new FinanceAoItemCompDetail();
        BeanUtils.copyProperties(financeAoAccountCashier,financeAoItemCompDetail);
        FinanceAoItemCompDetail financeAoItemCompDetails = financeAoItemCompDetailMapper.selectFinanceAoItemComp(financeAoItemCompDetail);
        financeAoAccountCashier1.setAcctCode(financeAoItemCompDetails.getDrAcctCode());
        FinanceAaAcctcodeCorp financeAaAcctcodeCorp1 = financeAaAcctcodeCorpMapper.selectAcctId(financeAoItemCompDetails.getDrAcctCode(), financeAoAccountCashier.getCompanyId());
        financeAoAccountCashier1.setAcctId(financeAaAcctcodeCorp1.getAcctId());
        financeAoAccountCashier1.setCalTypeCodea(financeAaAcctcodeCorp1.getCalTypeCodea());
        financeAoAccountCashier1.setCalTypeCodeb(financeAaAcctcodeCorp1.getCalTypeCodeb());
        return financeAoAccountCashier1;
    }
    /**
     * 新增出纳确认明细档
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoAccountDto 出纳确认明细档
     * @return 结果
     */

    @Override
    public int insertFinanceAoAccountCashier(FinanceAoAccountDto financeAoAccountDto)
    {
        FinanceAoAccountCashier financeAoAccountCashier = new FinanceAoAccountCashier();
        int i;
        //出纳主表新增
        FinanceAoCashier financeAoCashier = new FinanceAoCashier();
        BeanUtils.copyProperties(financeAoAccountDto,financeAoCashier);
        financeAoCashier.setId(IdUtils.fastSimpleUUID());
        financeAoCashier.setBillId(financeAoAccountDto.getId());
        financeAoCashier.setCreateTime(DateUtils.getNowDate());
        financeAoCashierMapper.insertFinanceAoCashier(financeAoCashier);
        //出纳明细表表新增
        List<FinanceAoAccountDetail> detailAccountLoanList = financeAoAccountDto.getDetailAccountLoanList();
        for (FinanceAoAccountDetail financeAoAccountDetail:detailAccountLoanList){
            BeanUtils.copyProperties( financeAoAccountDetail,financeAoAccountCashier);
            financeAoAccountCashier.setId(IdUtils.fastSimpleUUID());
            financeAoAccountCashier.setBillNo(financeAoAccountDto.getBillNo());
            financeAoAccountCashier.setAccountId(financeAoCashier.getId());
            financeAoAccountCashier.setBillId(financeAoAccountDto.getId());
            financeAoAccountCashier.setCreateTime(DateUtils.getNowDate());
            financeAoAccountCashier.setCreateName(SecurityUtils.getUsername());
            financeAoAccountCashier.setCreateBy(SecurityUtils.getNickName());
            financeAoAccountCashier.setCompanyId(financeAoAccountDto.getCompanyId());
            i=  financeAoAccountCashierMapper.insertFinanceAoAccountCashier(financeAoAccountCashier);
        }
        //报支修改
        FinanceAoLoan financeAoLoan =new FinanceAoLoan();
        BeanUtils.copyProperties( financeAoAccountDto,financeAoLoan);
        i = financeAoLoanService.updateFinanceAoLoan(financeAoLoan);
        return  i ;
    }

    /**
     * 修改出纳确认明细档
     *
     * @author 265799
     * @date 2023-07-05
     * @param financeAoAccountDto 出纳确认明细档
     * @return 结果
     */
    @Override
    public int updateFinanceAoAccountCashier(FinanceAoAccountDto financeAoAccountDto)
    {
        int i;
        FinanceAoAccountCashier financeAoAccountCashier = new FinanceAoAccountCashier ();

        //出纳确认主表修改
        FinanceAoCashier financeAoCashier = new FinanceAoCashier();
        BeanUtils.copyProperties(financeAoAccountDto,financeAoCashier);
        financeAoCashier.setBillId(financeAoAccountDto.getId());
        financeAoCashier.setCreateTime(DateUtils.getNowDate());
        i=   financeAoCashierMapper.updateFinanceAoCashier(financeAoCashier);
        //会计确认明细表修改
        List<FinanceAoAccountDetail> detailAccountLoanList = financeAoAccountDto.getDetailAccountLoanList();
        for (FinanceAoAccountDetail financeAoAccountDetail:detailAccountLoanList){
            BeanUtils.copyProperties( financeAoAccountDetail,financeAoAccountCashier);
            financeAoAccountCashier.setBillNo(financeAoAccountDto.getBillNo());
            financeAoAccountCashier.setAccountId(financeAoCashier.getId());
            financeAoAccountCashier.setBillId(financeAoAccountDto.getId());
            financeAoAccountCashier.setCompanyId(financeAoAccountDto.getCompanyId());
            financeAoAccountCashier.setUpdateTime(DateUtils.getNowDate());
            financeAoAccountCashier.setUpdateBy(SecurityUtils.getUsername());
            financeAoAccountCashier.setUpdateName(SecurityUtils.getNickName());
            i=  financeAoAccountCashierMapper.updateFinanceAoAccountCashier(financeAoAccountCashier);
        }


        //报支修改
        FinanceAoLoan financeAoLoan =new FinanceAoLoan();
        BeanUtils.copyProperties( financeAoAccountDto,financeAoLoan);
        i = financeAoLoanService.updateFinanceAoLoan(financeAoLoan);
        return i;
    }

    /**
     * 批量删除出纳确认明细档
     *
     * @author 265799
     * @date 2023-07-05
     * @param ids 需要删除的出纳确认明细档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoAccountCashierByIds(String[] ids)
    {
        return financeAoAccountCashierMapper.deleteFinanceAoAccountCashierByIds(ids);
    }

    /**
     * 删除出纳确认明细档信息
     *
     * @author 265799
     * @date 2023-07-05
     * @param id 出纳确认明细档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoAccountCashierById(String id)
    {
        return financeAoAccountCashierMapper.deleteFinanceAoAccountCashierById(id);
    }
}
