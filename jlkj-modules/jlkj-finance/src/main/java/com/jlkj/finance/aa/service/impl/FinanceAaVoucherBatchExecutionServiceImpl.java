package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.finance.aa.domain.FinanceAaVoucher;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;
import com.jlkj.finance.aa.mapper.FinanceAaVoucherDetailMapper;
import com.jlkj.finance.aa.mapper.FinanceAaVoucherMapper;
import com.jlkj.finance.aa.service.IFinanceAaVoucherBatchExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 凭证批量执行Service业务层处理
 *
 * @author 265799
 * @date 2023-04-24
 */
@Service
public class FinanceAaVoucherBatchExecutionServiceImpl implements IFinanceAaVoucherBatchExecutionService {
    @Autowired
    private  FinanceAaVoucherServiceImpl financeAaVoucherService;
    @Autowired
    private FinanceAaVoucherDetailMapper financeAaVoucherDetailMapper;
    @Autowired
    private FinanceAaVoucherMapper financeAaVoucherMapper;

    @Override
    public List<FinanceAaVoucher> selectFinanceAaVoucherList(FinanceAaVoucher financeAaVoucher) {
        return financeAaVoucherMapper.selectFinanceAaVoucherBatchExecutionList(financeAaVoucher);

    }

    /**
     * 批量修改确认与取消确认
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    @Override
    public int updateFinanceAaVoucherStatus(FinanceAaVoucher financeAaVoucher) {
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();

        FinanceAaVoucher financeAaVoucherSelect = new FinanceAaVoucher();
        int i=0;
        for (FinanceAaVoucherDetail financeAaVoucherDetail: detailList){
            List<FinanceAaVoucherDetail> detailList1 = new ArrayList<>();
            financeAaVoucherSelect.setCompanyId(financeAaVoucherDetail.getCompanyId());
            financeAaVoucherSelect.setVoucherDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD,financeAaVoucherDetail.getVoucherDate()));
            financeAaVoucherSelect.setVoucherNo(financeAaVoucherDetail.getVoucherNo());
            financeAaVoucherSelect.setPrintCount(financeAaVoucher.getPrintCount());
            FinanceAaVoucher financeAaVoucher1 = financeAaVoucherService.selectFinanceAaVoucher(financeAaVoucherSelect);
            financeAaVoucher1.setStatus(financeAaVoucher.getStatus());
            financeAaVoucher1.setPostTime(financeAaVoucher.getPostTime());
            financeAaVoucher1.setPotstuserName(financeAaVoucher.getPotstuserName());
            detailList1.add(financeAaVoucherDetail);
            financeAaVoucher1.setDetailList(detailList1);
            financeAaVoucher1.setIsInspect("N");
            i = financeAaVoucherService.updateFinanceAaVoucherStatus(financeAaVoucher1);
        }
        return i ;
    }
    /**
     * 批量修改过账与反过账
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    @Override
    public int updateFinanceAaVoucherCross(FinanceAaVoucher financeAaVoucher) {
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        int i=0;
        for (FinanceAaVoucherDetail financeAaVoucherDetail: detailList){
            FinanceAaVoucher financeAaVoucher1 = publicFinanceAaVoucher(financeAaVoucherDetail);
            financeAaVoucher1.setStatus(financeAaVoucher.getStatus());
            i = financeAaVoucherService.updateFinanceAaVoucherCross(financeAaVoucher1);

        }
        return i;
    }
    /**
     * 批量删除
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    @Override
    public int deleteFinanceAaVoucherByIds(FinanceAaVoucher financeAaVoucher) {
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        int i=0;
        for (FinanceAaVoucherDetail financeAaVoucherDetail: detailList){
            FinanceAaVoucher financeAaVoucher1 = publicFinanceAaVoucher(financeAaVoucherDetail);

                  i = financeAaVoucherService.deleteFinanceAaVoucherByIds(financeAaVoucher1);
        }
        return i;
    }
    /**
     * 抽调公用
     *
     * @param financeAaVoucherDetail 凭证维护-明细
     * @return 结果
     */

    public FinanceAaVoucher publicFinanceAaVoucher(FinanceAaVoucherDetail financeAaVoucherDetail) {
        FinanceAaVoucher financeAaVoucherSelect = new FinanceAaVoucher();
        List<FinanceAaVoucherDetail> detailList1 = new ArrayList<>();
        financeAaVoucherSelect.setCompanyId(financeAaVoucherDetail.getCompanyId());
        financeAaVoucherSelect.setVoucherDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD,financeAaVoucherDetail.getVoucherDate()));
        financeAaVoucherSelect.setVoucherNo(financeAaVoucherDetail.getVoucherNo());
        FinanceAaVoucher financeAaVoucher = financeAaVoucherService.selectFinanceAaVoucher(financeAaVoucherSelect);
        List<FinanceAaVoucherDetail> financeAaVoucherDetails = financeAaVoucherDetailMapper.selectFinanceAaVoucherBatchExecutionList(financeAaVoucherDetail);
        detailList1.addAll(financeAaVoucherDetails);

        financeAaVoucher.setDetailList(detailList1);
        return financeAaVoucher;
    }
}
