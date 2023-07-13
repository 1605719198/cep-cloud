package com.jlkj.finance.ap.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ap.domain.FinanceApItemComp;
import com.jlkj.finance.ap.domain.FinanceApLoan;
import com.jlkj.finance.ap.domain.FinanceApLoanDetail;
import com.jlkj.finance.ap.mapper.FinanceApItemCompMapper;
import com.jlkj.finance.ap.mapper.FinanceApLoanMapper;
import com.jlkj.finance.ap.service.IFinanceApLoanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 预付申请主档Service业务层处理
 *
 * @author 265799
 * @date 2023-07-10
 * @version 1.0
 */
@Service
public class FinanceApLoanServiceImpl implements IFinanceApLoanService
{
    @Autowired
    private FinanceApLoanMapper financeApLoanMapper;
    @Autowired
    private FinanceApItemCompMapper financeApItemCompMapper;
    /**
     * 查询预付申请主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param id 预付申请主档主键
     * @return 预付申请主档
     */
    @Override
    public FinanceApLoan selectFinanceApLoanById(String id)
    {
        return financeApLoanMapper.selectFinanceApLoanById(id);
    }

    /**
     * 查询预付申请主档列表
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoan 预付申请主档
     * @return 预付申请主档
     */
    @Override
    public List<FinanceApLoan> selectFinanceApLoanList(FinanceApLoan financeApLoan)
    {
        return financeApLoanMapper.selectFinanceApLoanList(financeApLoan);
    }

    /**
     * 新增预付申请主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoan 预付申请主档
     * @return 结果
     */

    @Override
    public int insertFinanceApLoan(FinanceApLoan financeApLoan)
    {
        String maxBillNo = null;
        financeApLoan.setCreateTime(DateUtils.getNowDate());
        //新增时自动生成，生成后不可更改，12码，规则为：年度后3码+月份2码+PB+5位流水号
        String dateStr = DateUtils.getDate();
        String billNo = dateStr.substring(1, 4) + dateStr.substring(5, 7) + "PB";
        maxBillNo = maxBillNo == null ? financeApLoanMapper.selectMaxBillNo(financeApLoan.getCompanyId(), billNo) : maxBillNo;
        String seqNo = maxBillNo == null ? "00001" : String.format("%05d",(Integer.parseInt(maxBillNo.substring(7, 12)) + 1));
        financeApLoan.setId(IdUtils.fastSimpleUUID());
        financeApLoan.setBillNo(billNo + seqNo);
        financeApLoan.setStatus("00");
        FinanceApItemComp financeApItemComp = new FinanceApItemComp();
        BeanUtils.copyProperties(financeApLoan,financeApItemComp);
        //获取到期天数
        FinanceApItemComp financeApItemComp1 = financeApItemCompMapper.selectFinanceApItemComp(financeApItemComp);
       if (financeApItemComp1!=null && financeApItemComp1.getDueDay()!=null){
           String dueDates = DateUtils.addCurDay(financeApItemComp1.getDueDay().intValue());
           financeApLoan.setDueDate(DateUtils.dateTime(DateUtils.YYYY_MM,dueDates));
       }else {
           financeApLoan.setDueDate(DateUtils.getNowDate());
       }
        financeApLoan.setCreateBy(SecurityUtils.getUsername());
        financeApLoan.setCreateTime(DateUtils.getNowDate());
        financeApLoan.setCreateName(SecurityUtils.getNickName());
        financeApLoan.setCreateDeptName(SecurityUtils.getUser().getDept().getDeptName());
        int rows = financeApLoanMapper.insertFinanceApLoan(financeApLoan);
        insertFinanceApLoanDetail(financeApLoan);
        return rows;
    }

    /**
     * 修改预付申请主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoan 预付申请主档
     * @return 结果
     */

    @Override
    public int updateFinanceApLoan(FinanceApLoan financeApLoan)
    {
        financeApLoan.setUpdateTime(DateUtils.getNowDate());
        financeApLoanMapper.deleteFinanceApLoanDetailByParentId(financeApLoan.getId());
        insertFinanceApLoanDetail(financeApLoan);
        return financeApLoanMapper.updateFinanceApLoan(financeApLoan);
    }

    /**
     * 批量删除预付申请主档
     *
     * @author 265799
     * @date 2023-07-10
     * @param ids 需要删除的预付申请主档主键
     * @return 结果
     */

    @Override
    public int deleteFinanceApLoanByIds(String[] ids)
    {
        financeApLoanMapper.deleteFinanceApLoanDetailByParentIds(ids);
        return financeApLoanMapper.deleteFinanceApLoanByIds(ids);
    }

    /**
     * 删除预付申请主档信息
     *
     * @author 265799
     * @date 2023-07-10
     * @param id 预付申请主档主键
     * @return 结果
     */

    @Override
    public int deleteFinanceApLoanById(String id)
    {
        financeApLoanMapper.deleteFinanceApLoanDetailByParentId(id);
        return financeApLoanMapper.deleteFinanceApLoanById(id);
    }

    /**
     * 新增预付申请明细档信息
     *
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoan 预付申请主档对象
     */
    public void insertFinanceApLoanDetail(FinanceApLoan financeApLoan)
    {
        List<FinanceApLoanDetail> financeApLoanDetailList = financeApLoan.getFinanceApLoanDetailList();
        String id = financeApLoan.getId();
        if (StringUtils.isNotNull(financeApLoanDetailList))
        {
            List<FinanceApLoanDetail> list = new ArrayList<FinanceApLoanDetail>();
            for (FinanceApLoanDetail financeApLoanDetail : financeApLoanDetailList)
            {financeApLoanDetail.setId(IdUtils.fastSimpleUUID());
                financeApLoanDetail.setItemNo(financeApLoan.getItemNo());
                financeApLoanDetail.setExchangeRate(financeApLoan.getExchangeRate());
                financeApLoanDetail.setParentId(id);
                list.add(financeApLoanDetail);
            }
            if (list.size() > 0)
            {
                financeApLoanMapper.batchFinanceApLoanDetail(list);
            }
        }
    }
}
