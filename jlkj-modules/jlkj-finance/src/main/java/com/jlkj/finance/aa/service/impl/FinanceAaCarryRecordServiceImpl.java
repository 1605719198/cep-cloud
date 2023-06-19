package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.*;
import com.jlkj.finance.aa.mapper.*;
import com.jlkj.finance.aa.service.IFinanceAaCarryRecordService;
import com.jlkj.finance.utils.ConstantsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自动结转规则-执行记录Service业务层处理
 *
 * @author 265799
 * @date 2023-05-19
 */
@Service
public class FinanceAaCarryRecordServiceImpl implements IFinanceAaCarryRecordService {

    /*** 自动结转规则-执行记录Mapper接*/
    @Autowired
    private FinanceAaCarryRecordMapper financeAaCarryRecordMapper;
    /*** 集团管理-自动结转规则-规则设置Mapper接口*/
    @Autowired
    private FinanceAaCarryRulesMapper financeAaCarryRulesMapper;

    /*** 公司級集团管理-自动结转规则-规则设置Mapper接口*/
    @Autowired
    private FinanceAaCarryRulesCorpMapper financeAaCarryRulesCorpMapper;
    /*** 凭证维护-明细Mapper接口*/
    @Autowired
    private FinanceAaVoucherDetailMapper financeAaVoucherDetailMapper;
    /*** 凭证维护-主Service业务层处理*/
    @Autowired
    private FinanceAaVoucherServiceImpl financeAaVoucherService;

    /***  会计科目-集团级*/
    @Autowired
    private FinanceAcctcodeGroupMapper financeAcctcodeGroupMapper;
    /*** 会计科目-公司级Mapper接口*/
    @Autowired
    private FinanceAaAcctcodeCorpMapper financeAaAcctcodeCorpMapper;

    /**
     * 查询自动结转规则-执行记录
     *
     * @param id 自动结转规则-执行记录主键
     * @return 自动结转规则-执行记录
     */
    @Override
    public FinanceAaCarryRecord selectFinanceAaCarryRecordById(String id) {
        return financeAaCarryRecordMapper.selectFinanceAaCarryRecordById(id);
    }

    /**
     * 查询自动结转规则-执行记录列表(集团)
     *
     * @param financeAaCarryRecord 自动结转规则-执行记录
     * @return 自动结转规则-执行记录
     */
    @Override
    public List<FinanceAaCarryRecord> selectFinanceAaCarryRecordList(FinanceAaCarryRecord financeAaCarryRecord) {
        financeAaCarryRecord.setAcctPeriod(financeAaCarryRecord.getAcctPeriod().substring(0,7));
        return financeAaCarryRecordMapper.selectFinanceAaCarryRecordList(financeAaCarryRecord);
    }

    /**
     * 查询自动结转规则-执行记录列表(公司)
     *
     * @param financeAaCarryRecord 自动结转规则-执行记录
     * @return 自动结转规则-执行记录
     */
    @Override
    public List<FinanceAaCarryRecord> selectFinanceAaCarryRecordCompanyIdList(FinanceAaCarryRecord financeAaCarryRecord) {
        financeAaCarryRecord.setAcctPeriod(financeAaCarryRecord.getAcctPeriod().substring(0,7));
        return financeAaCarryRecordMapper.selectFinanceAaCarryRecordCompanyIdList(financeAaCarryRecord);
    }

    /**
     * 执行自动结转规则-执行记录列表
     *
     * @param financeAaCarryRecord 自动结转规则-执行记录
     * @return 自动结转规则-执行记录
     */
    @Override
    public String insertFinanceAaCarryRecordExecute(FinanceAaCarryRecord financeAaCarryRecord) {
        FinanceAaCarryRecord financeAaCarryRecord2 = new FinanceAaCarryRecord();
        financeAaCarryRecord2.setStatus("Y");
        financeAaCarryRecord.setStatus("Y");
        BigDecimal ntamt = BigDecimal.ZERO;
        String voucherNo;
        FinanceAaVoucher financeAaVoucher = new FinanceAaVoucher();
        financeAaVoucher.setId(IdUtils.simpleUUID());
        financeAaVoucher.setStatus(financeAaCarryRecord.getStatusA());
        FinanceAaVoucherDetail financeAaVoucherDetail = new FinanceAaVoucherDetail();
        financeAaVoucherDetail.setCompanyId(financeAaCarryRecord.getCompanyId());
        Date date = DateUtils.dateTime(DateUtils.YYYY_MM, financeAaCarryRecord.getAcctPeriod());
        String ruleAdd ="";
        StringBuilder successMsg = new StringBuilder();
        if (ConstantsUtil.CARRYA.equals(financeAaCarryRecord.getCarry())) {
            FinanceAaCarryRules financeAaCarryRules = financeAaCarryRulesMapper.selectFinanceAaCarryRulesExecuteList(financeAaCarryRecord.getRulesNo());
            financeAaVoucherDetail.setAcctCode(financeAaCarryRules.getAccountCodeOut());
            financeAaVoucherDetail.setDrcr(financeAaCarryRules.getCarryType());
            financeAaVoucherDetail.setVoucherDate(financeAaCarryRecord.getAcctPeriod().substring(0,7));
            FinanceAcctcodeGroup financeAcctcodeGroup = financeAcctcodeGroupMapper.selectFinanceAcctcodeGroupByGroupAcctId(financeAaCarryRules.getAccountIdIn());
            if (!ConstantsUtil.CARRYA.equals(financeAaCarryRules.getCarryType())) {
                List<FinanceAaVoucherDetail> financeAaVoucherDetails = financeAaVoucherDetailMapper.selectFinanceAaVoucherExecuteList(financeAaVoucherDetail);
                List<FinanceAaVoucherDetail> financeAaVoucherDetailsList = new ArrayList<>();
                voucherNo = inspectionCollection(financeAaCarryRecord.getCompanyId(), "M", financeAaCarryRecord.getAcctPeriod());
                BeanUtils.copyProperties(financeAaCarryRecord, financeAaCarryRecord2);
                for (FinanceAaVoucherDetail financeAaVoucherDetail1 : financeAaVoucherDetails) {
                    financeAaVoucherDetail1.setVoucherId(financeAaVoucher.getId());
                    financeAaVoucherDetail1.setVoucherNo(voucherNo);
                    financeAaVoucherDetail1.setVoucherDate(DateUtils.getMaxMonthDate(DateUtils.dateTime(date)));
                    ntamt = ntamt.add(null == financeAaVoucherDetail1.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getNtamt());
                    BeanUtils.copyProperties(financeAaCarryRecord, financeAaVoucherDetail1);
                    financeAaVoucherDetail1.setSrlDesc(financeAaCarryRules.getRemark());
                    financeAaVoucherDetail1.setDrcr(financeAaCarryRules.getDrcr());
                    financeAaVoucherDetailsList.add(financeAaVoucherDetail1);
                }

                financeAaVoucherDetail.setDrcr(financeAaCarryRules.getCarryType());
                financeAaVoucherDetail.setNtamt(ntamt);
                financeAaVoucherDetail.setVoucherNo(voucherNo);
                financeAaVoucherDetail.setAcctId(financeAaVoucher.getId());
                financeAaVoucherDetail.setVoucherDate(DateUtils.getMaxMonthDate(DateUtils.dateTime(date)));
                financeAaVoucherDetail.setGroupAcctId(financeAaCarryRules.getAccountIdIn());
                financeAaVoucherDetail.setAcctCode(financeAaCarryRules.getAccountCodeIn());
                financeAaVoucherDetail.setAcctName(financeAcctcodeGroup.getGroupAcctName());
                financeAaVoucherDetail.setSrlDesc(financeAaCarryRules.getRemark());
                financeAaVoucherDetail.setVoucherId(financeAaVoucher.getId());
                financeAaVoucherDetailsList.add(financeAaVoucherDetail);
                financeAaVoucher.setCompanyId(financeAaCarryRecord.getCompanyId());
                financeAaVoucher.setVoucherDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD, DateUtils.getMaxMonthDate(DateUtils.dateTime(date))));
                financeAaVoucher.setVoucherNo(voucherNo);
                financeAaVoucher.setDrAmt(ntamt);
                financeAaVoucher.setCrAmt(ntamt);
                financeAaVoucher.setDetailList(financeAaVoucherDetailsList);
                financeAaVoucher.setVoucherDesc(financeAaCarryRecord.getRemark());
                financeAaVoucher.setCreateBy(SecurityUtils.getUsername());
                financeAaVoucher.setCreateTime(DateUtils.getNowDate());
                financeAaVoucher.setCreateDate(DateUtils.dateTime());
                financeAaVoucher.setCreateDate(DateUtils.dateTime());
                financeAaVoucher.setPotstuserName(SecurityUtils.getUsername());
                BeanUtils.copyProperties(financeAaCarryRecord, financeAaCarryRecord2);
                financeAaCarryRecord2.setExecuteVoucherNo(voucherNo);
                financeAaCarryRecord2.setCreateTime(DateUtils.getNowDate());
                financeAaCarryRecord2.setCreateBy(SecurityUtils.getUsername());
                financeAaCarryRecord2.setAcctPeriod(financeAaCarryRecord.getAcctPeriod().substring(0, 7));
                 ruleAdd = financeAaVoucherService.insertFinanceAaVoucher(financeAaVoucher);
                if (StringUtils.isEmpty(ruleAdd)) {
                    financeAaCarryRecord.setAcctPeriod(DateUtils.getMaxMonthDate(DateUtils.dateTime(date)));
                    financeAaCarryRecord.setAcctPeriod( financeAaCarryRecord.getAcctPeriod().substring(0, 7));
                    List<FinanceAaCarryRecord> financeAaCarryRecords = financeAaCarryRecordMapper.selectList(financeAaCarryRecord);
                    if(financeAaCarryRecords.size()>0){
                        financeAaCarryRecordMapper.updateFinanceAaCarryRecord(financeAaCarryRecord2);
                    }else {
                        financeAaCarryRecord2.setId(IdUtils.simpleUUID());
                        financeAaCarryRecordMapper.insertFinanceAaCarryRecord(financeAaCarryRecord2);
                    }

                }else {
                    successMsg.append(ruleAdd);
                }
            } else {
                financeAaVoucherDetail.setDrcr("");
                BigDecimal ntamtD = BigDecimal.ZERO;
                BigDecimal ntamtC = BigDecimal.ZERO;
                List<FinanceAaVoucherDetail> financeAaVoucherDetailsList = new ArrayList<>();
                List<FinanceAaVoucherDetail> financeAaVoucherDetails1 = financeAaVoucherDetailMapper.selectFinanceAaVoucherExecuteAList(financeAaVoucherDetail);
                voucherNo = inspectionCollection(financeAaCarryRecord.getCompanyId(), financeAaVoucherDetails1.get(0).getVoucherNo().substring(0, 1), financeAaCarryRecord.getAcctPeriod());
                for (FinanceAaVoucherDetail financeAaVoucherDetails2 : financeAaVoucherDetails1) {
                    financeAaVoucherDetails2.setVoucherId(financeAaVoucher.getId());
                    if ("D".equals(financeAaVoucherDetail.getDrcr())) {
                        ntamtD = ntamtD.add(null == financeAaVoucherDetail.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail.getNtamt());
                    }
                    if ("C".equals(financeAaVoucherDetail.getDrcr())) {
                        ntamtC = ntamtC.add(null == financeAaVoucherDetail.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail.getNtamt());
                    }
                    financeAaVoucherDetails2.setId(IdUtils.simpleUUID());
                    financeAaVoucherDetails2.setVoucherNo(voucherNo);
                    financeAaVoucherDetailsList.add(financeAaVoucherDetails2);
                }
                financeAaVoucherDetail.setNtamt(ntamtD.subtract(ntamtC));
                financeAaVoucherDetail.setVoucherNo(voucherNo);
                financeAaVoucherDetail.setAcctId(financeAaVoucher.getId());
                financeAaVoucherDetail.setGroupAcctId(financeAaCarryRules.getAccountIdIn());
                financeAaVoucherDetail.setAcctCode(financeAaCarryRules.getAccountCodeIn());
                financeAaVoucherDetail.setAcctName(financeAcctcodeGroup.getGroupAcctName());
                financeAaVoucherDetail.setSrlDesc(financeAaCarryRules.getRemark());
                financeAaVoucherDetail.setVoucherId(financeAaVoucher.getId());
                financeAaVoucherDetailsList.add(financeAaVoucherDetail);
                financeAaVoucher.setCompanyId(financeAaCarryRecord.getCompanyId());
                financeAaVoucher.setVoucherDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD, DateUtils.getMaxMonthDate(DateUtils.dateTime(date))));
                financeAaVoucher.setVoucherNo(voucherNo);
                financeAaVoucher.setDrAmt(ntamtD);
                financeAaVoucher.setCrAmt(ntamtC);
                financeAaVoucher.setCreateTime(DateUtils.getNowDate());
                financeAaVoucher.setCreateDate(DateUtils.dateTime());
                financeAaVoucher.setCreateBy(SecurityUtils.getUsername());
                financeAaVoucher.setPotstuserName(SecurityUtils.getUsername());
                financeAaVoucher.setDetailList(financeAaVoucherDetailsList);
                financeAaVoucher.setVoucherDesc(financeAaCarryRecord.getRemark());
                BeanUtils.copyProperties(financeAaCarryRecord, financeAaCarryRecord2);
                financeAaCarryRecord2.setId(IdUtils.simpleUUID());
                financeAaCarryRecord2.setExecuteVoucherNo(voucherNo);
                financeAaCarryRecord2.setCreateTime(DateUtils.getNowDate());
                financeAaCarryRecord2.setCreateBy(SecurityUtils.getUsername());
                financeAaCarryRecord2.setAcctPeriod(financeAaCarryRecord.getAcctPeriod().substring(0, 7));
                BeanUtils.copyProperties(financeAaCarryRecord2, financeAaVoucher);
                financeAaVoucher.setDetailList(financeAaVoucherDetailsList);
                System.out.println(financeAaVoucher.getDetailList());
                 ruleAdd = financeAaVoucherService.insertFinanceAaVoucher(financeAaVoucher);
                if (StringUtils.isEmpty(ruleAdd)) {
                    BeanUtils.copyProperties(financeAaCarryRecord, financeAaCarryRecord2);

                    financeAaCarryRecord2.setExecuteVoucherNo(voucherNo);
                    financeAaCarryRecord2.setCreateTime(DateUtils.getNowDate());
                    financeAaCarryRecord2.setCreateBy(SecurityUtils.getUsername());
                    financeAaCarryRecord.setAcctPeriod(DateUtils.getMaxMonthDate(DateUtils.dateTime(date)));
                    financeAaCarryRecord.setAcctPeriod( financeAaCarryRecord.getAcctPeriod().substring(0, 7));
                    List<FinanceAaCarryRecord> financeAaCarryRecords = financeAaCarryRecordMapper.selectList(financeAaCarryRecord);
                    if(financeAaCarryRecords.size()>0){
                        financeAaCarryRecordMapper.updateFinanceAaCarryRecord(financeAaCarryRecord2);
                    }else {
                        financeAaCarryRecord2.setId(IdUtils.simpleUUID());
                        financeAaCarryRecordMapper.insertFinanceAaCarryRecord(financeAaCarryRecord2);
                    }

                }else {
                    successMsg.append(ruleAdd);
                }
            }
        } else if (ConstantsUtil.CARRYB.equals(financeAaCarryRecord.getCarry())) {
            FinanceAaCarryRulesCorp financeAaCarryRulesCorp = financeAaCarryRulesCorpMapper.selectFinanceAaCarryRulesExecuteList(financeAaCarryRecord.getRulesNo(), financeAaCarryRecord.getCompanyId());
            financeAaVoucherDetail.setAcctCode(financeAaCarryRulesCorp.getAccountCodeOut());
            financeAaVoucherDetail.setDrcr(financeAaCarryRulesCorp.getCarryType());
            financeAaVoucherDetail.setVoucherDate(financeAaCarryRecord.getAcctPeriod().substring(0,7));
            FinanceAaAcctcodeCorp financeAaAcctcodeCorp = financeAaAcctcodeCorpMapper.selectFinanceAaAcctcodeCorpByGroupAcct(financeAaCarryRulesCorp.getAccountIdIn(), financeAaCarryRecord.getCompanyId());
            if (!ConstantsUtil.CARRYA.equals(financeAaCarryRulesCorp.getCarryType())) {
                List<FinanceAaVoucherDetail> financeAaVoucherDetails = financeAaVoucherDetailMapper.selectFinanceAaVoucherExecuteList(financeAaVoucherDetail);
                List<FinanceAaVoucherDetail> financeAaVoucherDetailsList = new ArrayList<>();
                voucherNo = inspectionCollection(financeAaCarryRecord.getCompanyId(), "M", financeAaCarryRecord.getAcctPeriod());
                BeanUtils.copyProperties(financeAaCarryRecord, financeAaCarryRecord2);
                for (FinanceAaVoucherDetail financeAaVoucherDetail1 : financeAaVoucherDetails) {
                    financeAaVoucherDetail1.setVoucherId(financeAaVoucher.getId());
                    financeAaVoucherDetail1.setVoucherNo(voucherNo);
                    financeAaVoucherDetail.setVoucherDate(DateUtils.getMaxMonthDate(DateUtils.dateTime(date)));
                    ntamt = ntamt.add(null == financeAaVoucherDetail1.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getNtamt());
                    BeanUtils.copyProperties(financeAaCarryRecord, financeAaVoucherDetail1);
                    financeAaVoucherDetail1.setDrcr(financeAaCarryRulesCorp.getDrcr());
                    financeAaVoucherDetail1.setSrlDesc(financeAaCarryRecord.getRemark());
                    financeAaVoucherDetailsList.add(financeAaVoucherDetail1);
                }
                financeAaVoucherDetail.setDrcr(financeAaCarryRulesCorp.getCarryType());
                financeAaVoucherDetail.setNtamt(ntamt);
                financeAaVoucherDetail.setVoucherNo(voucherNo);
                financeAaVoucherDetail.setAcctId(financeAaVoucher.getId());
                financeAaVoucherDetail.setGroupAcctId(financeAaCarryRulesCorp.getAccountIdIn());
                financeAaVoucherDetail.setAcctCode(financeAaCarryRulesCorp.getAccountCodeIn());
                financeAaVoucherDetail.setAcctName(financeAaAcctcodeCorp.getAcctName());
                financeAaVoucherDetail.setSrlDesc(financeAaCarryRulesCorp.getRemark());
                financeAaVoucherDetail.setVoucherId(financeAaVoucher.getId());
                financeAaVoucherDetail.setVoucherDate(DateUtils.getMaxMonthDate(DateUtils.dateTime(date)));
                financeAaVoucherDetailsList.add(financeAaVoucherDetail);
                financeAaVoucher.setCompanyId(financeAaCarryRecord.getCompanyId());
                financeAaVoucher.setVoucherDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD, DateUtils.getMaxMonthDate(DateUtils.dateTime(date))));
                financeAaVoucher.setVoucherNo(voucherNo);
                financeAaVoucher.setDrAmt(ntamt);
                financeAaVoucher.setCrAmt(ntamt);
                financeAaVoucher.setCreateTime(DateUtils.getNowDate());
                financeAaVoucher.setCreateDate(DateUtils.dateTime());
                financeAaVoucher.setCreateBy(SecurityUtils.getUsername());
                financeAaVoucher.setPotstuserName(SecurityUtils.getUsername());
                financeAaVoucher.setDetailList(financeAaVoucherDetailsList);
                financeAaVoucher.setVoucherDesc(financeAaCarryRecord.getRemark());
                BeanUtils.copyProperties(financeAaCarryRecord, financeAaCarryRecord2);

                financeAaCarryRecord2.setExecuteVoucherNo(voucherNo);
                financeAaCarryRecord2.setCreateTime(DateUtils.getNowDate());
                financeAaCarryRecord2.setCreateBy(SecurityUtils.getUsername());
                financeAaCarryRecord2.setAcctPeriod(financeAaCarryRecord.getAcctPeriod().substring(0, 7));
                BeanUtils.copyProperties(financeAaCarryRecord2, financeAaVoucher);
                 ruleAdd = financeAaVoucherService.insertFinanceAaVoucher(financeAaVoucher);
                if (StringUtils.isEmpty(ruleAdd)) {
                    financeAaCarryRecord.setAcctPeriod(DateUtils.getMaxMonthDate(DateUtils.dateTime(date)));
                    financeAaCarryRecord.setAcctPeriod( financeAaCarryRecord.getAcctPeriod().substring(0, 7));
                    List<FinanceAaCarryRecord> financeAaCarryRecords = financeAaCarryRecordMapper.selectList(financeAaCarryRecord);
                    if(financeAaCarryRecords.size()>0){
                        financeAaCarryRecordMapper.updateFinanceAaCarryRecord(financeAaCarryRecord2);
                    }else {
                        financeAaCarryRecord2.setId(IdUtils.simpleUUID());
                        financeAaCarryRecordMapper.insertFinanceAaCarryRecord(financeAaCarryRecord2);
                    }
                }else {
                    successMsg.append(ruleAdd);
                }
            } else {
                financeAaVoucherDetail.setDrcr("");
                BigDecimal ntamtD = BigDecimal.ZERO;
                BigDecimal ntamtC = BigDecimal.ZERO;
                List<FinanceAaVoucherDetail> financeAaVoucherDetailsList = new ArrayList<>();
                List<FinanceAaVoucherDetail> financeAaVoucherDetails1 = financeAaVoucherDetailMapper.selectFinanceAaVoucherExecuteAList(financeAaVoucherDetail);
                voucherNo = inspectionCollection(financeAaCarryRecord.getCompanyId(), financeAaVoucherDetails1.get(0).getVoucherNo().substring(0, 1), financeAaCarryRecord.getAcctPeriod());
                for (FinanceAaVoucherDetail financeAaVoucherDetails2 : financeAaVoucherDetails1) {
                    financeAaVoucherDetails2.setVoucherId(financeAaVoucher.getId());
                    if ("D".equals(financeAaVoucherDetail.getDrcr())) {
                        ntamtD = ntamtD.add(null == financeAaVoucherDetail.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail.getNtamt());
                    }
                    if ("C".equals(financeAaVoucherDetail.getDrcr())) {
                        ntamtC = ntamtC.add(null == financeAaVoucherDetail.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail.getNtamt());
                    }
                    financeAaVoucherDetails2.setVoucherDate(DateUtils.getMaxMonthDate(DateUtils.dateTime(date)));
                    financeAaVoucherDetails2.setId(IdUtils.simpleUUID());
                    financeAaVoucherDetails2.setVoucherNo(voucherNo);
                    financeAaVoucherDetailsList.add(financeAaVoucherDetails2);
                }
                financeAaVoucherDetail.setNtamt(ntamtD.subtract(ntamtC));
                financeAaVoucherDetail.setVoucherNo(voucherNo);
                financeAaVoucherDetail.setAcctId(financeAaVoucher.getId());
                financeAaVoucherDetail.setGroupAcctId(financeAaCarryRulesCorp.getAccountIdIn());
                financeAaVoucherDetail.setAcctCode(financeAaCarryRulesCorp.getAccountCodeIn());
                financeAaVoucherDetail.setAcctName(financeAaAcctcodeCorp.getAcctName());
                financeAaVoucherDetail.setSrlDesc(financeAaCarryRulesCorp.getRemark());
                financeAaVoucherDetail.setVoucherId(financeAaVoucher.getId());
                financeAaVoucherDetail.setVoucherDate(DateUtils.getMaxMonthDate(DateUtils.dateTime(date)));
                financeAaVoucherDetailsList.add(financeAaVoucherDetail);
                financeAaVoucher.setCompanyId(financeAaCarryRecord.getCompanyId());
                financeAaVoucher.setVoucherDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD, DateUtils.getMaxMonthDate(DateUtils.dateTime(date))));
                financeAaVoucher.setVoucherNo(voucherNo);
                financeAaVoucher.setDrAmt(ntamtD);
                financeAaVoucher.setCrAmt(ntamtC);
                financeAaVoucher.setCreateTime(DateUtils.getNowDate());
                financeAaVoucher.setCreateDate(DateUtils.dateTime());
                financeAaVoucher.setCreateBy(SecurityUtils.getUsername());
                financeAaVoucher.setPotstuserName(SecurityUtils.getUsername());
                financeAaVoucher.setDetailList(financeAaVoucherDetailsList);
                financeAaVoucher.setVoucherDesc(financeAaCarryRecord.getRemark());
                BeanUtils.copyProperties(financeAaCarryRecord, financeAaCarryRecord2);
                financeAaCarryRecord2.setId(IdUtils.simpleUUID());
                financeAaCarryRecord2.setExecuteVoucherNo(voucherNo);
                financeAaCarryRecord2.setCreateTime(DateUtils.getNowDate());
                financeAaCarryRecord2.setCreateBy(SecurityUtils.getUsername());
                financeAaCarryRecord2.setAcctPeriod(financeAaCarryRecord.getAcctPeriod().substring(0, 7));
                BeanUtils.copyProperties(financeAaCarryRecord2, financeAaVoucher);
                financeAaVoucher.setDetailList(financeAaVoucherDetailsList);
                 ruleAdd = financeAaVoucherService.insertFinanceAaVoucher(financeAaVoucher);
                if (StringUtils.isEmpty(ruleAdd)) {
                    BeanUtils.copyProperties(financeAaCarryRecord, financeAaCarryRecord2);

                    financeAaCarryRecord2.setExecuteVoucherNo(voucherNo);
                    financeAaCarryRecord2.setCreateTime(DateUtils.getNowDate());
                    financeAaCarryRecord2.setCreateBy(SecurityUtils.getUsername());
                    financeAaCarryRecord.setAcctPeriod(DateUtils.getMaxMonthDate(DateUtils.dateTime(date)));
                    financeAaCarryRecord.setAcctPeriod( financeAaCarryRecord.getAcctPeriod().substring(0, 7));
                    List<FinanceAaCarryRecord> financeAaCarryRecords = financeAaCarryRecordMapper.selectList(financeAaCarryRecord);
                    if(financeAaCarryRecords.size()>0){
                        financeAaCarryRecordMapper.updateFinanceAaCarryRecord(financeAaCarryRecord2);
                    }else {
                        financeAaCarryRecord2.setId(IdUtils.simpleUUID());
                        financeAaCarryRecordMapper.insertFinanceAaCarryRecord(financeAaCarryRecord2);
                    }
                }else {
                    successMsg.append(ruleAdd);
                }
            }
        }
        return successMsg.toString();
    }

    /**
     * 取消自动结转规则-执行记录列表
     *
     * @param financeAaCarryRecord 自动结转规则-执行记录
     * @return 自动结转规则-执行记录
     */
    @Override
    public int updateCancelFinanceAaCarryRecord(FinanceAaCarryRecord financeAaCarryRecord) {
        int i = 0;
        FinanceAaVoucher financeAaVoucher = new FinanceAaVoucher();
        financeAaVoucher.setVoucherNo(financeAaCarryRecord.getExecuteVoucherNo());
        FinanceAaVoucher financeAaVoucher1 = financeAaVoucherService.selectFinanceAaVoucher(financeAaVoucher);
        FinanceAaVoucherDetail financeAaVoucherDetail = new FinanceAaVoucherDetail();
        financeAaVoucherDetail.setCompanyId(financeAaVoucher1.getCompanyId());
        financeAaVoucherDetail.setVoucherNo(financeAaVoucher1.getVoucherNo());
        financeAaVoucher1.setVoucherType(financeAaVoucher1.getVoucherNo().substring(0,1));
        List<FinanceAaVoucherDetail> financeAaVoucherDetails = financeAaVoucherDetailMapper.selectFinanceAaVoucherBatchExecutionList(financeAaVoucherDetail);
        financeAaVoucher1.setDetailList(financeAaVoucherDetails);
        financeAaVoucher1.setVoucherNo("");
        String ruleAdd = financeAaVoucherService.insertHongChong(financeAaVoucher1);
        if (!StringUtils.isEmpty(ruleAdd)) {
            financeAaCarryRecord.setStatus("N");
            financeAaCarryRecord.setExecuteVoucherNo("");
            financeAaCarryRecord.setCancelVoucherNo(ruleAdd);
            financeAaCarryRecord.setCreateTime(DateUtils.getNowDate());
            financeAaCarryRecord.setCreateBy(SecurityUtils.getUsername());
            financeAaCarryRecord.setAcctPeriod(financeAaCarryRecord.getAcctPeriod().substring(0, 7));
             i = financeAaCarryRecordMapper.updateFinanceAaCarryRecord(financeAaCarryRecord);
        }
        return i;
    }

    /**
     * 获取凭证号
     */
    public String inspectionCollection(String companyId, String voucherType, String voucherDate) {
        String voucherNo;
        FinanceAaVoucher financeAaVoucher = new FinanceAaVoucher();
        financeAaVoucher.setCompanyId(companyId);
        financeAaVoucher.setVoucherType(voucherType);
        financeAaVoucher.setVoucherDate(DateUtils.dateTime(DateUtils.YYYY_MM, voucherDate));
        voucherNo = financeAaVoucherService.insertFinanceAaVoucherVoucherNo(financeAaVoucher);
        return voucherNo;
    }

    /**
     * 新增自动结转规则-执行记录
     *
     * @param financeAaCarryRecord 自动结转规则-执行记录
     * @return 结果
     */
    @Override
    public int insertFinanceAaCarryRecord(FinanceAaCarryRecord financeAaCarryRecord) {
        financeAaCarryRecord.setCreateTime(DateUtils.getNowDate());
        return financeAaCarryRecordMapper.insertFinanceAaCarryRecord(financeAaCarryRecord);
    }

    /**
     * 修改自动结转规则-执行记录
     *
     * @param financeAaCarryRecord 自动结转规则-执行记录
     * @return 结果
     */
    @Override
    public int updateFinanceAaCarryRecord(FinanceAaCarryRecord financeAaCarryRecord) {
        return financeAaCarryRecordMapper.updateFinanceAaCarryRecord(financeAaCarryRecord);
    }

    /**
     * 批量删除自动结转规则-执行记录
     *
     * @param ids 需要删除的自动结转规则-执行记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCarryRecordByIds(String[] ids) {
        return financeAaCarryRecordMapper.deleteFinanceAaCarryRecordByIds(ids);
    }

    /**
     * 删除自动结转规则-执行记录信息
     *
     * @param id 自动结转规则-执行记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaCarryRecordById(String id) {
        return financeAaCarryRecordMapper.deleteFinanceAaCarryRecordById(id);
    }
}
