package com.jlkj.finance.aa.service.impl;

import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import com.jlkj.finance.aa.mapper.FinanceAaLedgerAcctMapper;
import com.jlkj.finance.aa.mapper.FinanceAaLedgerlCalMapper;
import com.jlkj.finance.aa.mapper.FinanceAaVoucherDetailMapper;
import com.jlkj.finance.aa.service.IFinanceAaAccountBalanceTonSteelService;
import com.jlkj.finance.utils.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 科目余额报表Service业务层处理
 *
 * @author 265799
 * @date 2023-05-30
 */
@Service
public class FinanceAaAccountBalanceTonSteelServiceImpl implements IFinanceAaAccountBalanceTonSteelService {
    @Autowired
    private FinanceAaLedgerAcctMapper financeAaLedgerAcctMapper;
    @Autowired
    private FinanceAaLedgerlCalMapper financeAaLedgerlCalMapper;
    @Autowired
    private FinanceAaVoucherDetailMapper financeAaVoucherDetailMapper;

    @Override
    public List<FinanceAaLedgerAcctDTO> selectListDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO) {
        financeAaLedgerAcctDTO.setStartDate(financeAaLedgerAcctDTO.getStartDate().substring(0,7));
        financeAaLedgerAcctDTO.setEndDate(financeAaLedgerAcctDTO.getEndDate().substring(0,7));
        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS1 = new ArrayList<>();
        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS  = new ArrayList<>();
        if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getAmountNotDisplayed())){
            financeAaLedgerAcctDTOS = financeAaLedgerAcctMapper.selectAmountNotDisplayed(financeAaLedgerAcctDTO);
            financeAaLedgerAcctDTOS1 = selectList(financeAaLedgerAcctDTOS);
        }
        if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getBalanceZero())){
            financeAaLedgerAcctDTOS = financeAaLedgerAcctMapper.selectAmountNotDisplayed(financeAaLedgerAcctDTO);
            financeAaLedgerAcctDTOS1 = selectListAdd(financeAaLedgerAcctDTOS);
        }
        if (ConstantsUtil.CODE_N.equals(financeAaLedgerAcctDTO.getBalanceZero())&& ConstantsUtil.CODE_N.equals(financeAaLedgerAcctDTO.getAmountNotDisplayed())){
            financeAaLedgerAcctDTOS = financeAaLedgerAcctMapper.selectAmountNotDisplayed(financeAaLedgerAcctDTO);
        }
        if (financeAaLedgerAcctDTOS.size()>0){
            financeAaLedgerAcctDTOS1 = selectAccountLevel(financeAaLedgerAcctDTOS, financeAaLedgerAcctDTO);
        }
        return financeAaLedgerAcctDTOS1;
    }

    @Override
    public List<FinanceAaLedgerAcctDTO> selectListNumberDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO) {
        return null;
    }


    @Override
    public List<FinanceAaLedgerAcctDTO> selectListCalNumberDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO) {
        financeAaLedgerAcctDTO.setStartDate(financeAaLedgerAcctDTO.getStartDate().substring(0,7));
        financeAaLedgerAcctDTO.setEndDate(financeAaLedgerAcctDTO.getEndDate().substring(0,7));
        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS1 = new ArrayList<>();
        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS ;
        if (ConstantsUtil.TYPE2.equals(financeAaLedgerAcctDTO.getReportType())){
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getAmountNotDisplayed())){
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedA(financeAaLedgerAcctDTO);
                financeAaLedgerAcctDTOS1 = selectList(financeAaLedgerAcctDTOS);
            }
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getBalanceZero())){
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedA(financeAaLedgerAcctDTO);
                financeAaLedgerAcctDTOS1 = selectListAdd(financeAaLedgerAcctDTOS);
            }
            {
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedA(financeAaLedgerAcctDTO);
            }
            if (financeAaLedgerAcctDTOS.size()>0){
                financeAaLedgerAcctDTOS1 = selectAccountLevel(financeAaLedgerAcctDTOS, financeAaLedgerAcctDTO);
            }
        }
        if (ConstantsUtil.TYPE3.equals(financeAaLedgerAcctDTO.getReportType())){
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getAmountNotDisplayed())){
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedB(financeAaLedgerAcctDTO);
                financeAaLedgerAcctDTOS1 = selectList(financeAaLedgerAcctDTOS);

            }
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getBalanceZero())){
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedB(financeAaLedgerAcctDTO);
                financeAaLedgerAcctDTOS1 = selectListAdd(financeAaLedgerAcctDTOS);
            }
            {
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedB(financeAaLedgerAcctDTO);
            }
            if (financeAaLedgerAcctDTOS.size()>0){
                financeAaLedgerAcctDTOS1 = selectAccountLevel(financeAaLedgerAcctDTOS, financeAaLedgerAcctDTO);
            }
        }
        if (ConstantsUtil.TYPE4.equals(financeAaLedgerAcctDTO.getReportType())){
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getAmountNotDisplayed())){
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedC(financeAaLedgerAcctDTO);
                financeAaLedgerAcctDTOS1 = selectList(financeAaLedgerAcctDTOS);

            }
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getBalanceZero())){
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedC(financeAaLedgerAcctDTO);
                financeAaLedgerAcctDTOS1 = selectListAdd(financeAaLedgerAcctDTOS);
            }
            {
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedC(financeAaLedgerAcctDTO);
            }
            if (financeAaLedgerAcctDTOS.size()>0){
                financeAaLedgerAcctDTOS1 = selectAccountLevel(financeAaLedgerAcctDTOS, financeAaLedgerAcctDTO);
            }
        }
        if (ConstantsUtil.TYPE5.equals(financeAaLedgerAcctDTO.getReportType())){
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getAmountNotDisplayed())){
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedD(financeAaLedgerAcctDTO);
                financeAaLedgerAcctDTOS1 = selectList(financeAaLedgerAcctDTOS);

            }
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getBalanceZero())){
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedD(financeAaLedgerAcctDTO);
                financeAaLedgerAcctDTOS1 = selectListAdd(financeAaLedgerAcctDTOS);
            }
            {
                financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedD(financeAaLedgerAcctDTO);
            }
            if (financeAaLedgerAcctDTOS.size()>0){
                financeAaLedgerAcctDTOS1 = selectAccountLevel(financeAaLedgerAcctDTOS, financeAaLedgerAcctDTO);
            }
        }
        return financeAaLedgerAcctDTOS1;
    }

    @Override
    public List<FinanceAaLedgerAcctDTO> selectlistCalDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO) {
        return null;
    }

    public List<FinanceAaLedgerAcctDTO> selectList(List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTO) {

        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS = new ArrayList<>();
        for (FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 :financeAaLedgerAcctDTO){
            if (financeAaLedgerAcctDTO1.getDrAmt() != null && financeAaLedgerAcctDTO1.getCrAmt() != null) {
                if (financeAaLedgerAcctDTO1.getDrAmt().compareTo(BigDecimal.ZERO) != 0 || financeAaLedgerAcctDTO1.getCrAmt().compareTo(BigDecimal.ZERO) != 0) {
                    financeAaLedgerAcctDTOS.add(financeAaLedgerAcctDTO1);
                }
            }
        }
        return financeAaLedgerAcctDTOS;
    }

    public List<FinanceAaLedgerAcctDTO> selectListAdd(List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTO) {
        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS = new ArrayList<>();
        for (FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 :financeAaLedgerAcctDTO){
            if (financeAaLedgerAcctDTO1.getDrAmt() != null && financeAaLedgerAcctDTO1.getCrAmt() != null && financeAaLedgerAcctDTO1.getBgnAmt() != null) {
                if (financeAaLedgerAcctDTO1.getDrAmt().compareTo(BigDecimal.ZERO) != 0 || financeAaLedgerAcctDTO1.getCrAmt().compareTo(BigDecimal.ZERO) != 0 || financeAaLedgerAcctDTO1.getBgnAmt().compareTo(BigDecimal.ZERO) != 0) {
                    financeAaLedgerAcctDTOS.add(financeAaLedgerAcctDTO1);
                }
            }
        }
        return financeAaLedgerAcctDTOS;

    }

    /**
     * 根据科目级截取会计科目编号
     *
     * @param financeAaLedgerAcctDTO 科目余额报表
     * @return 结果
     */

    public  List<FinanceAaLedgerAcctDTO>  selectAccountLevel(List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTO,FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2){
        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctList = new ArrayList<>();
        String substring="";
        FinanceAaVoucherDetail financeAaVoucherDetail = new FinanceAaVoucherDetail();
        for (FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 :financeAaLedgerAcctDTO){
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO2.getUnpostedVoucher())){
                financeAaVoucherDetail.setCompanyId(financeAaLedgerAcctDTO2.getCompanyId());
                financeAaVoucherDetail.setAcctCode(financeAaLedgerAcctDTO1.getAcctCode());
                financeAaVoucherDetail.setStartDate(financeAaLedgerAcctDTO2.getStartDate()+"-01");
                financeAaVoucherDetail.setEndDate(financeAaLedgerAcctDTO2.getEndDate()+"-31");
                FinanceAaVoucherDetail financeAaVoucherDetail1 = financeAaVoucherDetailMapper.selectFinanceAaLedgerAcctList(financeAaVoucherDetail);
                if (financeAaVoucherDetail1!=null){
                    if (ConstantsUtil.DRCRC.equals(financeAaVoucherDetail1.getDrcr())){
                        financeAaLedgerAcctDTO1.setDrAmt(financeAaVoucherDetail1.getNtamt());
                        financeAaLedgerAcctDTO1.setDrQty(financeAaVoucherDetail1.getQtyFrnamt());
                    }else {
                        financeAaLedgerAcctDTO1.setCrAmt(financeAaVoucherDetail1.getNtamt());
                        financeAaLedgerAcctDTO1.setCrQty(financeAaVoucherDetail1.getQtyFrnamt());
                    }

                }
            }
            if (ConstantsUtil.CALRULE1.equals(financeAaLedgerAcctDTO2.getAccountLevel())){
                if (financeAaLedgerAcctDTO1.getAcctCode().length()>4){
                    substring = financeAaLedgerAcctDTO1.getAcctCode().substring(0, 4);
                }else {
                    substring = financeAaLedgerAcctDTO1.getAcctCode();
                }
            }
            if (ConstantsUtil.CALRULE2.equals(financeAaLedgerAcctDTO2.getAccountLevel())){
                if (financeAaLedgerAcctDTO1.getAcctCode().length()>6){
                    substring = financeAaLedgerAcctDTO1.getAcctCode().substring(0, 6);
                }else {
                    substring = financeAaLedgerAcctDTO1.getAcctCode();
                }
            }
            if (ConstantsUtil.CALRULE3.equals(financeAaLedgerAcctDTO2.getAccountLevel())){
                if (financeAaLedgerAcctDTO1.getAcctCode().length()>8){
                    substring = financeAaLedgerAcctDTO1.getAcctCode().substring(0, 8);
                }else {
                    substring = financeAaLedgerAcctDTO1.getAcctCode();
                }
            }
            if (ConstantsUtil.CALRULE4.equals(financeAaLedgerAcctDTO2.getAccountLevel())){
                if (financeAaLedgerAcctDTO1.getAcctCode().length()>10){
                    substring = financeAaLedgerAcctDTO1.getAcctCode().substring(0, 10);
                }else {
                    substring = financeAaLedgerAcctDTO1.getAcctCode();
                }
            }
            if (ConstantsUtil.CALRULE5.equals(financeAaLedgerAcctDTO2.getAccountLevel())){
                if (financeAaLedgerAcctDTO1.getAcctCode().length()>12){
                    substring = financeAaLedgerAcctDTO1.getAcctCode().substring(0, 12);
                }else {
                    substring = financeAaLedgerAcctDTO1.getAcctCode();
                }
            }
            if(ConstantsUtil.ISEMPTY.equals(financeAaLedgerAcctDTO2.getAccountLevel())){
                substring = financeAaLedgerAcctDTO1.getAcctCode();
            }
            financeAaLedgerAcctDTO1.setAcctCode(substring);
            if ((null == financeAaLedgerAcctDTO1.getBgnAmt()?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getBgnAmt()).compareTo(BigDecimal.ZERO) >=0){
                financeAaLedgerAcctDTO1.setBgnAmtStraight(financeAaLedgerAcctDTO1.getBgnAmt());
            }else {
                financeAaLedgerAcctDTO1.setBgnAmtBurden(financeAaLedgerAcctDTO1.getBgnAmt().negate());
            }
            if ((null == financeAaLedgerAcctDTO1.getBgnQty()?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getBgnQty()).compareTo(BigDecimal.ZERO) >=0){
                financeAaLedgerAcctDTO1.setBgnQtyStraight(financeAaLedgerAcctDTO1.getBgnQty());
            }else {
                financeAaLedgerAcctDTO1.setBgnQtyBurden(financeAaLedgerAcctDTO1.getBgnAmt().negate());
            }
            BigDecimal yearCrAmt = (null == financeAaLedgerAcctDTO1.getCrAmt() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getCrAmt()).add(null == financeAaLedgerAcctDTO1.getYearCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTO1.getYearCrAmt());
            BigDecimal yearCrQty = (null == financeAaLedgerAcctDTO1.getYearCrQty() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getYearCrQty()).add(null == financeAaLedgerAcctDTO1.getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTO1.getDrQty());
            BigDecimal yearDrAmt = (null == financeAaLedgerAcctDTO1.getYearDrAmt() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getYearDrAmt()).add(null == financeAaLedgerAcctDTO1.getDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTO1.getDrAmt());
            BigDecimal yearDrQty = (null == financeAaLedgerAcctDTO1.getYearDrQty() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getYearDrQty()).add(null == financeAaLedgerAcctDTO1.getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTO1.getDrQty());
            if(!yearCrAmt.equals(BigDecimal.ZERO)){
                financeAaLedgerAcctDTO1.setYearCrAmt(yearCrAmt);
            }
            if(!yearCrQty.equals(BigDecimal.ZERO)){
                financeAaLedgerAcctDTO1.setYearCrQty(yearCrQty);

            }
            if(!yearDrAmt.equals(BigDecimal.ZERO)){
                financeAaLedgerAcctDTO1.setYearDrAmt(yearDrAmt);
            }
            if(!yearDrQty.equals(BigDecimal.ZERO)){
                financeAaLedgerAcctDTO1.setYearDrQty(yearDrQty);
            }

            BigDecimal subtract = ((null == financeAaLedgerAcctDTO1.getBgnQty() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getBgnQty()).add(null == financeAaLedgerAcctDTO1.getDrQty() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getDrQty())).subtract(null == financeAaLedgerAcctDTO1.getCrQty() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getCrQty());
            if (subtract.compareTo(BigDecimal.ZERO) >=0){
                financeAaLedgerAcctDTO1.setEndingBalanceQtyDr(subtract);
            }else {
                financeAaLedgerAcctDTO1.setEndingBalanceQtyCr(subtract.negate());
            }
            BigDecimal subtract1 = (( null == financeAaLedgerAcctDTO1.getBgnAmt() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getBgnAmt()).add(null == financeAaLedgerAcctDTO1.getDrAmt() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getDrAmt())).subtract(null == financeAaLedgerAcctDTO1.getCrAmt() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getCrAmt());
            if (subtract1.compareTo(BigDecimal.ZERO) >=0){
                financeAaLedgerAcctDTO1.setEndingBalanceDr(subtract1);
            }else {
                financeAaLedgerAcctDTO1.setEndingBalanceCr(subtract1.negate());
            }
            financeAaLedgerAcctList.add(financeAaLedgerAcctDTO1);
        }
        if (financeAaLedgerAcctList.size()>0){
            financeAaLedgerAcctList.sort(Comparator.comparing(FinanceAaLedgerAcctDTO::getAcctCode));
        }

        return financeAaLedgerAcctList;
    }

}
