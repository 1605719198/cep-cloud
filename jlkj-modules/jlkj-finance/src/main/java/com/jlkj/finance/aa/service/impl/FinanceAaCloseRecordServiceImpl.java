package com.jlkj.finance.aa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceAaCloseRecord;
import com.jlkj.finance.aa.domain.FinanceAaLedgerAcct;
import com.jlkj.finance.aa.domain.FinanceAaLedgerlCal;
import com.jlkj.finance.aa.domain.FinanceAccountYear;
import com.jlkj.finance.aa.mapper.FinanceAaCloseRecordMapper;
import com.jlkj.finance.aa.mapper.FinanceAaLedgerAcctMapper;
import com.jlkj.finance.aa.mapper.FinanceAaLedgerlCalMapper;
import com.jlkj.finance.aa.service.FinanceAccountYearService;
import com.jlkj.finance.aa.service.IFinanceAaCloseRecordService;
import com.jlkj.finance.utils.ConstantsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 关账执行记录Service业务层处理
 *
 * @author 265799
 * @date 2023-05-23
 */
@Service
public class FinanceAaCloseRecordServiceImpl implements IFinanceAaCloseRecordService {
    @Autowired
    private FinanceAaCloseRecordMapper financeAaCloseRecordMapper;
    @Autowired
    private FinanceAaLedgerlCalMapper financeAaLedgerlCalMapper;
    @Autowired
    private FinanceAaLedgerAcctMapper financeAaLedgerAcctMapper;
    @Autowired
    private FinanceAccountYearService financeAccountYearService;

    /**
     * 查询关账执行记录
     *
     * @param id 关账执行记录主键
     * @return 关账执行记录
     */
    @Override
    public FinanceAaCloseRecord selectFinanceAaCloseRecordById(String id) {
        return financeAaCloseRecordMapper.selectFinanceAaCloseRecordById(id);
    }

    /**
     * 查询关账执行记录列表
     *
     * @param financeAaCloseRecord 关账执行记录
     * @return 关账执行记录
     */
    @Override
    public List<FinanceAaCloseRecord> selectFinanceAaCloseRecordList(FinanceAaCloseRecord financeAaCloseRecord) {
        financeAaCloseRecord.setAcctPeriod(financeAaCloseRecord.getAcctPeriod().substring(0, 7));
        return financeAaCloseRecordMapper.selectFinanceAaCloseRecordList(financeAaCloseRecord);
    }

    /**
     * 新增关账执行记录
     *
     * @param financeAaCloseRecord 关账执行记录
     * @return 结果
     */
    @Override
    public List<FinanceAaCloseRecord> insertFinanceAaCloseRecord(FinanceAaCloseRecord financeAaCloseRecord) {
        List<FinanceAaCloseRecord> financeAaCloseRecords = new ArrayList<>();
        StringBuilder successMsg = new StringBuilder();
        String acctPeriod = financeAaCloseRecord.getAcctPeriod();
        financeAaCloseRecord.setCreateTime(DateUtils.getNowDate());
        financeAaCloseRecord.setId(IdUtils.simpleUUID());
        financeAaCloseRecord.setCreateBy(SecurityUtils.getUsername());
        financeAaCloseRecord.setCreateName(SecurityUtils.getNickName());
        financeAaCloseRecord.setAcctPeriod(financeAaCloseRecord.getAcctPeriod().substring(0, 7));
        FinanceAaLedgerAcct financeAaLedgerAcct = new FinanceAaLedgerAcct();
        financeAaLedgerAcct.setCompanyId(financeAaCloseRecord.getCompanyId());
        financeAaLedgerAcct.setAcctPeriod(financeAaCloseRecord.getAcctPeriod().substring(0, 7));
        FinanceAaLedgerlCal financeAaLedgerlCal = new FinanceAaLedgerlCal();
        financeAaLedgerlCal.setCompanyId(financeAaCloseRecord.getCompanyId());
        financeAaLedgerlCal.setAcctPeriod(financeAaCloseRecord.getAcctPeriod().substring(0, 7));
        QueryWrapper<FinanceAccountYear> wrapper = new QueryWrapper<>();
        int i = 5;
        int b = 7;
        if (ConstantsUtil.CALRULE1.equals(financeAaCloseRecord.getAcctPeriod().substring(i, b))) {
            wrapper.eq("account_period", financeAaCloseRecord.getAcctPeriod().substring(0, 4) + financeAaCloseRecord.getAcctPeriod().substring(5, 7))
                    .eq("comp_id", financeAaCloseRecord.getCompanyId())
                    .orderByDesc("account_period");
        } else {
            wrapper.lt("account_period", financeAaCloseRecord.getAcctPeriod().substring(0, 4) + financeAaCloseRecord.getAcctPeriod().substring(5, 7))
                    .ge("account_period", financeAaCloseRecord.getAcctPeriod().substring(0, 4) + "01")
                    .eq("comp_id", financeAaCloseRecord.getCompanyId())
                    .orderByDesc("account_period");
        }

        List<FinanceAccountYear> list = financeAccountYearService.list(wrapper);
        for (FinanceAccountYear financeAccountYear : list) {
            if (!ConstantsUtil.DISABLEDCODE.equals(financeAccountYear.getIsClosed())) {
                successMsg.append(financeAccountYear.getAccountPeriod() + "状态为未关账！");
            }
        }
        if (!StringUtils.isEmpty(successMsg)) {
            financeAaCloseRecord.setDesc(successMsg.toString());
            financeAaCloseRecord.setStatus(ConstantsUtil.CODE_N);
            financeAaCloseRecords.add(financeAaCloseRecord);
            financeAaCloseRecordMapper.insertFinanceAaCloseRecord(financeAaCloseRecord);
            return financeAaCloseRecords;
        }
        try {
            FinanceAccountYear financeAccountYear;
            QueryWrapper<FinanceAccountYear> wrapperYearPost = new QueryWrapper<>();
            wrapperYearPost.eq("account_period", financeAaCloseRecord.getAcctPeriod().substring(0, 4) + financeAaCloseRecord.getAcctPeriod().substring(5, 7))
                    .eq("comp_id", financeAaCloseRecord.getCompanyId());
            financeAccountYear = financeAccountYearService.getOne(wrapperYearPost);
            //检查如果状态是己关账状态 ，不可重复关账
            if (financeAccountYear.getIsClosed().equals(ConstantsUtil.CODE_Y)) {
                throw new Exception(financeAaCloseRecord.getAcctPeriod().substring(0, 7) + "己关账，不能重复关账！");
            }
            financeAccountYear.setIsClosed(ConstantsUtil.CODE_Y);
            financeAccountYearService.updateById(financeAccountYear);
            List<FinanceAaLedgerlCal> financeAaLedger = new ArrayList<>();
            List<FinanceAaLedgerlCal> financeAaLedgerlCals = financeAaLedgerlCalMapper.selectAcctPeriodList(financeAaLedgerlCal);
            BigDecimal bgnAmt;
            BigDecimal drAmt;
            BigDecimal crAmt;
            BigDecimal bgnQty;
            BigDecimal drQty;
            BigDecimal crQty;
            BigDecimal yearDrAmt;
            BigDecimal yearCrAmt;
            BigDecimal yearDrQty;
            BigDecimal yearCrQty;
            for (FinanceAaLedgerlCal financeAaLedgerlCal1 : financeAaLedgerlCals) {
                BeanUtils.copyProperties(financeAaLedgerlCal1, financeAaLedgerlCal);
                financeAaLedgerlCal.setDrAmt(BigDecimal.ZERO);
                financeAaLedgerlCal.setCrAmt(BigDecimal.ZERO);
                financeAaLedgerlCal.setDrQty(BigDecimal.ZERO);
                financeAaLedgerlCal.setCrQty(BigDecimal.ZERO);
                financeAaLedgerlCal.setAcctId(financeAaLedgerlCal1.getAcctId());
                financeAaLedgerlCal.setAcctName(financeAaLedgerlCal1.getAcctName());
                bgnAmt = null == financeAaLedgerlCal1.getBgnAmt() ? BigDecimal.ZERO : financeAaLedgerlCal1.getBgnAmt();
                drAmt = null == financeAaLedgerlCal1.getDrAmt() ? BigDecimal.ZERO : financeAaLedgerlCal1.getDrAmt();
                crAmt = null == financeAaLedgerlCal1.getCrAmt() ? BigDecimal.ZERO : financeAaLedgerlCal1.getCrAmt();
                financeAaLedgerlCal.setBgnAmt(bgnAmt.add(drAmt).subtract(crAmt));
                bgnQty = null == financeAaLedgerlCal1.getBgnQty() ? BigDecimal.ZERO : financeAaLedgerlCal1.getBgnQty();
                drQty = null == financeAaLedgerlCal1.getDrQty() ? BigDecimal.ZERO : financeAaLedgerlCal1.getDrQty();
                crQty = null == financeAaLedgerlCal1.getCrQty() ? BigDecimal.ZERO : financeAaLedgerlCal1.getCrQty();
                financeAaLedgerlCal.setBgnQty(bgnQty.add(drQty).subtract(crQty));
                yearDrAmt = null == financeAaLedgerlCal1.getYearDrAmt() ? BigDecimal.ZERO : financeAaLedgerlCal1.getYearDrAmt();
                financeAaLedgerlCal.setYearDrAmt(yearDrAmt.add(drAmt));
                yearCrAmt = null == financeAaLedgerlCal1.getYearCrAmt() ? BigDecimal.ZERO : financeAaLedgerlCal1.getYearCrAmt();
                financeAaLedgerlCal.setYearCrAmt(yearCrAmt.add(crAmt));
                yearDrQty = null == financeAaLedgerlCal1.getYearDrQty() ? BigDecimal.ZERO : financeAaLedgerlCal1.getYearDrQty();
                financeAaLedgerlCal.setYearDrQty(yearDrQty.add(drQty));
                yearCrQty = null == financeAaLedgerlCal1.getYearCrQty() ? BigDecimal.ZERO : financeAaLedgerlCal1.getYearCrQty();
                financeAaLedgerlCal.setYearCrQty(yearCrQty.add(crQty));
                financeAaLedgerlCal.setAcctPeriod(DateUtils.addMonth(acctPeriod, 1).substring(0, 7));
                if (BigDecimal.ZERO.equals(bgnAmt) && BigDecimal.ZERO.equals(drAmt) && BigDecimal.ZERO.equals(crAmt)
                        && BigDecimal.ZERO.equals(bgnQty) && BigDecimal.ZERO.equals(drQty) && BigDecimal.ZERO.equals(crQty) &&
                        BigDecimal.ZERO.equals(yearDrAmt) && BigDecimal.ZERO.equals(yearCrAmt) && BigDecimal.ZERO.equals(yearDrQty) && BigDecimal.ZERO.equals(yearCrQty)) {
                    successMsg.append(financeAaLedgerlCal1.getAcctName() + "金额都为空不能结转！");
                }
                financeAaLedgerlCal.setId(IdUtils.simpleUUID());
                financeAaLedger.add(financeAaLedgerlCal);
                financeAaLedgerlCal = new FinanceAaLedgerlCal();
            }
            if (!StringUtils.isEmpty(successMsg)) {
                financeAaCloseRecord.setDesc(successMsg.toString());
                financeAaCloseRecord.setStatus("N");
                financeAaCloseRecords.add(financeAaCloseRecord);
                financeAaCloseRecordMapper.insertFinanceAaCloseRecord(financeAaCloseRecord);
                return financeAaCloseRecords;
            }
            if (financeAaLedger.size() > 0) {
                financeAaLedgerlCalMapper.batchFinanceAaLedgerlCal(financeAaLedger);
            }

            List<FinanceAaLedgerAcct> financeAaAccts = new ArrayList<>();
            List<FinanceAaLedgerAcct> financeAaLedgerAccts = financeAaLedgerAcctMapper.selectLedgerAcctPeriodList(financeAaLedgerAcct);
            for (FinanceAaLedgerAcct financeAaLedgerAcct1 : financeAaLedgerAccts) {
                financeAaLedgerAcct.setAcctCode(financeAaLedgerAcct1.getAcctCode());
                financeAaLedgerAcct.setAcctId(financeAaLedgerAcct1.getAcctId());
                financeAaLedgerAcct.setAcctName(financeAaLedgerAcct1.getAcctName());
                bgnAmt = null == financeAaLedgerAcct1.getBgnAmt() ? BigDecimal.ZERO : financeAaLedgerAcct1.getBgnAmt();
                drAmt = null == financeAaLedgerAcct1.getDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcct1.getDrAmt();
                crAmt = null == financeAaLedgerAcct1.getCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcct1.getCrAmt();
                financeAaLedgerAcct.setBgnAmt(bgnAmt.add(drAmt).subtract(crAmt));
                bgnQty = null == financeAaLedgerAcct1.getBgnQty() ? BigDecimal.ZERO : financeAaLedgerAcct1.getBgnQty();
                drQty = null == financeAaLedgerAcct1.getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcct1.getDrQty();
                crQty = null == financeAaLedgerAcct1.getCrQty() ? BigDecimal.ZERO : financeAaLedgerAcct1.getCrQty();
                financeAaLedgerAcct.setBgnQty(bgnQty.add(drQty).subtract(crQty));
                yearDrAmt = null == financeAaLedgerAcct1.getYearDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcct1.getYearDrAmt();
                financeAaLedgerAcct.setYearDrAmt(yearDrAmt.add(drAmt));
                yearCrAmt = null == financeAaLedgerAcct1.getYearCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcct1.getYearCrAmt();
                financeAaLedgerAcct.setYearCrAmt(yearCrAmt.add(crAmt));
                yearDrQty = null == financeAaLedgerAcct1.getYearDrQty() ? BigDecimal.ZERO : financeAaLedgerAcct1.getYearDrQty();
                financeAaLedgerAcct.setYearDrQty(yearDrQty.add(drQty));
                yearCrQty = null == financeAaLedgerAcct1.getYearCrQty() ? BigDecimal.ZERO : financeAaLedgerAcct1.getYearCrQty();
                financeAaLedgerAcct.setYearCrQty(yearCrQty.add(crQty));
                financeAaLedgerAcct.setAcctPeriod(DateUtils.addMonth(acctPeriod, 1).substring(0, 7));
                if (BigDecimal.ZERO.equals(bgnAmt) && BigDecimal.ZERO.equals(drAmt) && BigDecimal.ZERO.equals(crAmt)
                        && BigDecimal.ZERO.equals(bgnQty) && BigDecimal.ZERO.equals(drQty) && BigDecimal.ZERO.equals(crQty) &&
                        BigDecimal.ZERO.equals(yearDrAmt) && BigDecimal.ZERO.equals(yearCrAmt) && BigDecimal.ZERO.equals(yearDrQty) && BigDecimal.ZERO.equals(yearCrQty)) {
                    successMsg.append(financeAaLedgerAcct1.getAcctName() + "数量都为空不能结转！");

                }
                financeAaLedgerAcct.setId(IdUtils.simpleUUID());
                financeAaAccts.add(financeAaLedgerAcct);
                financeAaLedgerAcct = new FinanceAaLedgerAcct();

            }
            if (!StringUtils.isEmpty(successMsg)) {
                financeAaCloseRecord.setDesc(successMsg.toString());
                financeAaCloseRecord.setStatus(ConstantsUtil.CODE_N);
                financeAaCloseRecords.add(financeAaCloseRecord);
                financeAaCloseRecordMapper.insertFinanceAaCloseRecord(financeAaCloseRecord);
                return financeAaCloseRecords;
            }
            if (financeAaAccts.size() > 0) {
                financeAaLedgerAcctMapper.batchFinanceAcct(financeAaAccts);
            }

        } catch (Exception e) {
            successMsg.append(e.getMessage());
            financeAaCloseRecord.setDesc(successMsg.toString());
            financeAaCloseRecord.setStatus(ConstantsUtil.CODE_N);
            financeAaCloseRecords.add(financeAaCloseRecord);
            financeAaCloseRecordMapper.insertFinanceAaCloseRecord(financeAaCloseRecord);
            return financeAaCloseRecords;
        }
        financeAaCloseRecord.setStatus(ConstantsUtil.CODE_Y);
        financeAaCloseRecordMapper.insertFinanceAaCloseRecord(financeAaCloseRecord);
        financeAaCloseRecords.add(financeAaCloseRecord);
        return financeAaCloseRecords;
    }


    /**
     * 修改关账执行记录
     *
     * @param financeAaCloseRecord 关账执行记录
     * @return 结果
     */
    @Override
    public int updateFinanceAaCloseRecord(FinanceAaCloseRecord financeAaCloseRecord) {
        return financeAaCloseRecordMapper.updateFinanceAaCloseRecord(financeAaCloseRecord);
    }

    /**
     * 批量删除关账执行记录
     *
     * @param ids 需要删除的关账执行记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCloseRecordByIds(String[] ids) {
        return financeAaCloseRecordMapper.deleteFinanceAaCloseRecordByIds(ids);
    }

    /**
     * 删除关账执行记录信息
     *
     * @param id 关账执行记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCloseRecordById(String id) {
        return financeAaCloseRecordMapper.deleteFinanceAaCloseRecordById(id);
    }
}
