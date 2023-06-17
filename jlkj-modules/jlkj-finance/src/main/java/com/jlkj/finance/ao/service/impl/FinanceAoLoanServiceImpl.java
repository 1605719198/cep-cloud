package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.domain.FinanceAoLoan;
import com.jlkj.finance.ao.domain.FinanceAoLoanDetail;
import com.jlkj.finance.ao.mapper.FinanceAoLoanMapper;
import com.jlkj.finance.ao.service.IFinanceAoLoanService;
import com.jlkj.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 借支申请主档Service业务层处理
 *
 * @author 116514
 * @date 2023-06-02
 */
@Service
public class FinanceAoLoanServiceImpl implements IFinanceAoLoanService
{
    @Autowired
    private FinanceAoLoanMapper financeAoLoanMapper;
    private String seqNo;

    /**
     * 查询借支申请主档
     *
     * @param id 借支申请主档主键
     * @return 借支申请主档
     */
    @Override
    public FinanceAoLoan selectFinanceAoLoanById(String id)
    {
        return financeAoLoanMapper.selectFinanceAoLoanById(id);
    }

    /**
     * 查询借支申请主档列表
     *
     * @param financeAoLoan 借支申请主档
     * @return 借支申请主档
     */
    @Override
    public List<FinanceAoLoan> selectFinanceAoLoanList(FinanceAoLoan financeAoLoan)
    {
        return financeAoLoanMapper.selectFinanceAoLoanList(financeAoLoan);
    }

    /**
     * 新增借支申请主档
     *
     * @param financeAoLoan 借支申请主档
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertFinanceAoLoan(FinanceAoLoan financeAoLoan) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        financeAoLoan.setId(IdUtils.simpleUUID());
        // 申请单号编码规则年度后3码+月份2码+PB+5位流水号
        String dateStr = DateUtils.getDate();
        String billNo = dateStr.substring(1, 4) + dateStr.substring(5, 7) + "PB";
        String maxBillNo = financeAoLoanMapper.selectMaxBillNo(financeAoLoan.getCompanyId(), billNo);
        String seqNo = maxBillNo == null ? "00001" : String.format("%05d",(Integer.parseInt(maxBillNo.substring(7, 12)) + 1));
        financeAoLoan.setBillNo(billNo + seqNo);
        financeAoLoan.setStatus("00");
        financeAoLoan.setCreateBy(SecurityUtils.getUsername());
        financeAoLoan.setCreateName(SecurityUtils.getNickName());
        financeAoLoan.setCreateTime(DateUtils.getNowDate());
        financeAoLoan.setCreateDept(loginUser.getSysUser().getDeptId().toString());
        financeAoLoan.setCreateDeptName(loginUser.getSysUser().getDept().getDeptName());
        int rows = financeAoLoanMapper.insertFinanceAoLoan(financeAoLoan);
        insertFinanceAoLoanDetail(financeAoLoan);
        return rows;
    }

    /**
     * 修改借支申请主档
     *
     * @param financeAoLoan 借支申请主档
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateFinanceAoLoan(FinanceAoLoan financeAoLoan)
    {
        financeAoLoan.setUpdateBy(SecurityUtils.getUsername());
        financeAoLoan.setUpdateName(SecurityUtils.getNickName());
        financeAoLoan.setUpdateTime(DateUtils.getNowDate());
        financeAoLoanMapper.deleteFinanceAoLoanDetailByParentId(financeAoLoan.getId());
        insertFinanceAoLoanDetail(financeAoLoan);
        return financeAoLoanMapper.updateFinanceAoLoan(financeAoLoan);
    }

    /**
     * 批量删除借支申请主档
     *
     * @param ids 需要删除的借支申请主档主键
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteFinanceAoLoanByIds(String[] ids)
    {
        financeAoLoanMapper.deleteFinanceAoLoanDetailByParentIds(ids);
        return financeAoLoanMapper.deleteFinanceAoLoanByIds(ids);
    }

    /**
     * 删除借支申请主档信息
     *
     * @param id 借支申请主档主键
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteFinanceAoLoanById(String id)
    {
        financeAoLoanMapper.deleteFinanceAoLoanDetailByParentId(id);
        return financeAoLoanMapper.deleteFinanceAoLoanById(id);
    }

    /**
     * 新增报支管理-借支申请明细档信息
     *
     * @param financeAoLoan 借支申请主档对象
     */
    public void insertFinanceAoLoanDetail(FinanceAoLoan financeAoLoan)
    {
        List<FinanceAoLoanDetail> financeAoLoanDetailList = financeAoLoan.getFinanceAoLoanDetailList();
        String id = financeAoLoan.getId();
        if (StringUtils.isNotNull(financeAoLoanDetailList))
        {
            List<FinanceAoLoanDetail> list = new ArrayList<FinanceAoLoanDetail>();
            for (FinanceAoLoanDetail financeAoLoanDetail : financeAoLoanDetailList)
            {
                financeAoLoanDetail.setParentId(id);
                financeAoLoanDetail.setId(IdUtils.simpleUUID());
                financeAoLoanDetail.setItemNo(financeAoLoan.getItemNo());
                financeAoLoanDetail.setTotalAmt(financeAoLoanDetail.getNetAmt());
                financeAoLoanDetail.setCreateBy(SecurityUtils.getUsername());
                financeAoLoanDetail.setCreateName(SecurityUtils.getNickName());
                financeAoLoanDetail.setCreateTime(DateUtils.getNowDate());
                list.add(financeAoLoanDetail);
            }
            if (list.size() > 0)
            {
                financeAoLoanMapper.batchFinanceAoLoanDetail(list);
            }
        }
    }


    /**
     * 借支申请作业清单查询，含明细借支总额汇总
     * @param financeAoLoan
     * @return
     */
    @Override
    public List<FinanceAoLoan> selectAoLoanTotalList(FinanceAoLoan financeAoLoan){
        return financeAoLoanMapper.selectAoLoanTotalList(financeAoLoan);
    }
}
