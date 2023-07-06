package com.jlkj.finance.ao.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.domain.FinanceAoRepayment;
import com.jlkj.finance.ao.mapper.FinanceAoRepaymentMapper;
import com.jlkj.finance.ao.service.IFinanceAoRepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 还款单资料档Service业务层处理
 *
 * @author 116514
 * @date 2023-06-15
 */
@Service
public class FinanceAoRepaymentServiceImpl implements IFinanceAoRepaymentService
{
    public static ReentrantLock lock = new ReentrantLock(true);
    @Autowired
    private FinanceAoRepaymentMapper financeAoRepaymentMapper;

    /**
     * 查询还款单资料档
     *
     * @param id 还款单资料档主键
     * @return 还款单资料档
     */
    @Override
    public FinanceAoRepayment selectFinanceAoRepaymentById(String id)
    {
        return financeAoRepaymentMapper.selectFinanceAoRepaymentById(id);
    }

    /**
     * 查询还款单资料档列表
     *
     * @param financeAoRepayment 还款单资料档
     * @return 还款单资料档
     */
    @Override
    public List<FinanceAoRepayment> selectFinanceAoRepaymentList(FinanceAoRepayment financeAoRepayment)
    {
        return financeAoRepaymentMapper.selectFinanceAoRepaymentList(financeAoRepayment);
    }

    /**
     * 新增还款单资料档
     *
     * @param financeAoRepayment 还款单资料档
     * @return 结果
     */
    @Override
    public int insertFinanceAoRepayment(FinanceAoRepayment financeAoRepayment)
    {
        financeAoRepayment.setCreateTime(DateUtils.getNowDate());
        return financeAoRepaymentMapper.insertFinanceAoRepayment(financeAoRepayment);
    }

    /**
     * 修改还款单资料档
     *
     * @param financeAoRepayment 还款单资料档
     * @return 结果
     */
    @Override
    public int updateFinanceAoRepayment(FinanceAoRepayment financeAoRepayment)
    {
        financeAoRepayment.setUpdateTime(DateUtils.getNowDate());
        return financeAoRepaymentMapper.updateFinanceAoRepayment(financeAoRepayment);
    }

    /**
     * 批量删除还款单资料档
     *
     * @param ids 需要删除的还款单资料档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoRepaymentByIds(String[] ids)
    {
        return financeAoRepaymentMapper.deleteFinanceAoRepaymentByIds(ids);
    }

    /**
     * 删除还款单资料档信息
     *
     * @param id 还款单资料档主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoRepaymentById(String id)
    {
        return financeAoRepaymentMapper.deleteFinanceAoRepaymentById(id);
    }

    /**
     * 查询借支申请单
     * @param companyId
     * @return
     */
    @Override
    public List<Map<String,String>> selectLoanApply(String companyId){
        return financeAoRepaymentMapper.selectLoanApply(companyId);
    }


    /**
     * 批量新增还款单资料档
     * @param financeAoRepayments
     * @return
     */
    @Override
    public int insertBatchFinanceAoRepayment(List<FinanceAoRepayment> financeAoRepayments){
        // 加锁放并发新增导致流水号重复
        lock.lock();
        try{
            String maxBillNo = null;
            for (FinanceAoRepayment financeAoRepayment : financeAoRepayments) {
                financeAoRepayment.setId(IdUtils.simpleUUID());
                // 申请单号编码规则年度后3码+月份2码+PB+5位流水号
                String dateStr = DateUtils.getDate();
                String billNo = dateStr.substring(1, 4) + dateStr.substring(5, 7) + "PD";
                maxBillNo = maxBillNo == null ? financeAoRepaymentMapper.selectMaxBillNo(financeAoRepayment.getCompanyId(), billNo) : maxBillNo;
                String seqNo = maxBillNo == null ? "00001" : String.format("%05d",(Integer.parseInt(maxBillNo.substring(7, 12)) + 1));
                financeAoRepayment.setBillNo(billNo + seqNo);
                financeAoRepayment.setPayDate(DateUtils.getNowDate());
                // 00-新增 10-会计已确认 20-出纳已确认
                financeAoRepayment.setStatus("00");
                financeAoRepayment.setCreateBy(SecurityUtils.getUsername());
                financeAoRepayment.setCreateName(SecurityUtils.getNickName());
                financeAoRepayment.setCreateTime(DateUtils.getNowDate());
                maxBillNo = financeAoRepayment.getBillNo();
            }
            return financeAoRepaymentMapper.insertBatch(financeAoRepayments);
        }finally {
            lock.unlock();
        }
    }
}
