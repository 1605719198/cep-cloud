package com.jlkj.finance.aa.service.impl;

import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import com.jlkj.finance.aa.mapper.FinanceAaLedgerAcctMapper;
import com.jlkj.finance.aa.mapper.FinanceAaLedgerlCalMapper;
import com.jlkj.finance.aa.mapper.FinanceAaVoucherDetailMapper;
import com.jlkj.finance.aa.service.IFinanceAaGeneralLedgerService;
import com.jlkj.finance.utils.ConstantsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 总账报表Service业务层处理
 *
 * @author 265799
 * @date 2023-06-02
 */
@Service
public class FinanceAaGeneralLedgerServiceImpl implements IFinanceAaGeneralLedgerService {
    @Autowired
    private FinanceAaLedgerAcctMapper financeAaLedgerAcctMapper;
    @Autowired
    private FinanceAaLedgerlCalMapper financeAaLedgerlCalMapper;
    @Autowired
    private FinanceAaVoucherDetailMapper financeAaVoucherDetailMapper;

    @Override
    public List<Map<String, Object>> selectListDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO) {
        List<Map<String, Object>> maps = new ArrayList<>();
        financeAaLedgerAcctDTO.setStartDate(financeAaLedgerAcctDTO.getStartDate().substring(0, 7));
        financeAaLedgerAcctDTO.setEndDate(financeAaLedgerAcctDTO.getEndDate().substring(0, 7));
        FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 = new FinanceAaLedgerAcctDTO();
        BeanUtils.copyProperties(financeAaLedgerAcctDTO, financeAaLedgerAcctDTO1);
        Map<String, String> dateMap = new HashMap<>(16);
        List<FinanceAaLedgerAcctDTO> acctCode = financeAaLedgerAcctMapper.selectFinanceAaGeneralLedger(financeAaLedgerAcctDTO);
        for (FinanceAaLedgerAcctDTO acctCode1 : acctCode) {
            List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS;
            financeAaLedgerAcctDTO1.setAcctCode(acctCode1.getAcctCode());
            financeAaLedgerAcctDTOS = financeAaLedgerAcctMapper.selectFinanceAaGeneralLedgerAcctCode1(financeAaLedgerAcctDTO1);
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getUnpostedVoucher())) {
                financeAaLedgerAcctDTOS = selectAccountLevel(financeAaLedgerAcctDTOS, financeAaLedgerAcctDTO);
            }
            List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS2 = new ArrayList<>();
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getAmountNotDisplayed())) {
                financeAaLedgerAcctDTOS = selectList(financeAaLedgerAcctDTOS);
            }
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getBalanceZero())) {
                financeAaLedgerAcctDTOS = selectListAdd(financeAaLedgerAcctDTOS);
            }
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getCurrentPeriod())) {
                BigDecimal bgnAmt;
                BigDecimal bgnQty;
                BigDecimal drQty;
                BigDecimal drAmt;
                BigDecimal crQty;
                BigDecimal crAmt;
                BigDecimal endOfPeriodQty;
                BigDecimal endOfPeriodAmt;
                BigDecimal bgnAmtAdd = BigDecimal.ZERO;
                BigDecimal bgnQtyAdd = BigDecimal.ZERO;
                BigDecimal drQtyAdd = BigDecimal.ZERO;
                BigDecimal drAmtAdd = BigDecimal.ZERO;
                BigDecimal crQtyAdd = BigDecimal.ZERO;
                BigDecimal crAmtAdd = BigDecimal.ZERO;
                BigDecimal endOfPeriodQtyAdd = BigDecimal.ZERO;
                BigDecimal endOfPeriodAmtAdd = BigDecimal.ZERO;
                for (int i = 0; i < financeAaLedgerAcctDTOS.size(); i++) {
                    FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2 = new FinanceAaLedgerAcctDTO();
                    if (financeAaLedgerAcctDTOS.size() > 1) {
                        if (!dateMap.containsKey(financeAaLedgerAcctDTOS.get(i).getAcctPeriod())) {
                            if (dateMap.size() > 0) {
                                financeAaLedgerAcctDTO2.setCalName("本期合计");
                                financeAaLedgerAcctDTO2.setBgnAmt(bgnAmtAdd);
                                financeAaLedgerAcctDTO2.setBgnQty(bgnQtyAdd);
                                financeAaLedgerAcctDTO2.setDrQty(drQtyAdd);
                                financeAaLedgerAcctDTO2.setDrAmt(drAmtAdd);
                                financeAaLedgerAcctDTO2.setCrQty(crQtyAdd);
                                financeAaLedgerAcctDTO2.setCrAmt(crAmtAdd);
                                financeAaLedgerAcctDTO2.setEndOfPeriodQty(endOfPeriodQtyAdd);
                                financeAaLedgerAcctDTO2.setEndOfPeriodAmt(endOfPeriodAmtAdd);
                                financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTO2);
                                dateMap.clear();
                                endOfPeriodQtyAdd = BigDecimal.ZERO;
                                endOfPeriodAmtAdd = BigDecimal.ZERO;
                                bgnAmtAdd = BigDecimal.ZERO;
                                bgnQtyAdd = BigDecimal.ZERO;
                                drQtyAdd = BigDecimal.ZERO;
                                drAmtAdd = BigDecimal.ZERO;
                                crQtyAdd = BigDecimal.ZERO;
                                crAmtAdd = BigDecimal.ZERO;
                            }
                            dateMap.put(financeAaLedgerAcctDTOS.get(i).getAcctPeriod(), financeAaLedgerAcctDTOS.get(i).getAcctPeriod());
                            financeAaLedgerAcctDTO2 = new FinanceAaLedgerAcctDTO();
                        }
                    }
                    bgnAmt = null == financeAaLedgerAcctDTOS.get(i).getBgnAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getBgnAmt();
                    bgnQty = null == financeAaLedgerAcctDTOS.get(i).getBgnQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getBgnQty();
                    drQty = null == financeAaLedgerAcctDTOS.get(i).getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getDrQty();
                    drAmt = null == financeAaLedgerAcctDTOS.get(i).getDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getDrAmt();
                    crQty = null == financeAaLedgerAcctDTOS.get(i).getCrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getCrQty();
                    crAmt = null == financeAaLedgerAcctDTOS.get(i).getCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getCrAmt();
                    endOfPeriodQty = (bgnQty.add(drQty)).subtract(crQty);
                    endOfPeriodAmt = (bgnAmt.add(drAmt)).subtract(crAmt);
                    financeAaLedgerAcctDTOS.get(i).setEndOfPeriodAmt(endOfPeriodAmt);
                    financeAaLedgerAcctDTOS.get(i).setEndOfPeriodQty(endOfPeriodQty);
                    financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTOS.get(i));
                    bgnAmtAdd = bgnAmtAdd.add(bgnAmt);
                    bgnQtyAdd = bgnQtyAdd.add(bgnQty);
                    drQtyAdd = drQtyAdd.add(drQty);
                    drAmtAdd = drAmtAdd.add(drAmt);
                    crQtyAdd = crQtyAdd.add(crQty);
                    crAmtAdd = crAmtAdd.add(crAmt);
                    endOfPeriodQtyAdd = endOfPeriodQtyAdd.add((bgnQty.add(drQty)).subtract(crQty));
                    endOfPeriodAmtAdd = endOfPeriodAmtAdd.add((bgnAmt.add(drAmt)).subtract(crAmt));
                }
                dateMap.clear();
                FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2 = new FinanceAaLedgerAcctDTO();
                financeAaLedgerAcctDTO2.setCalName("本期合计");
                financeAaLedgerAcctDTO2.setBgnAmt(bgnAmtAdd);
                financeAaLedgerAcctDTO2.setBgnQty(bgnQtyAdd);
                financeAaLedgerAcctDTO2.setDrQty(drQtyAdd);
                financeAaLedgerAcctDTO2.setDrAmt(drAmtAdd);
                financeAaLedgerAcctDTO2.setCrQty(crQtyAdd);
                financeAaLedgerAcctDTO2.setCrAmt(crAmtAdd);
                financeAaLedgerAcctDTO2.setEndOfPeriodQty(endOfPeriodQtyAdd);
                financeAaLedgerAcctDTO2.setEndOfPeriodAmt(endOfPeriodAmtAdd);
                financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTO2);

            } else {
                BigDecimal bgnAmt;
                BigDecimal bgnQty;
                BigDecimal drQty;
                BigDecimal drAmt;
                BigDecimal crQty;
                BigDecimal crAmt;
                BigDecimal endOfPeriodQty;
                BigDecimal endOfPeriodAmt;
                for (int i = 0; i < financeAaLedgerAcctDTOS.size(); i++) {
                    bgnAmt = null == financeAaLedgerAcctDTOS.get(i).getBgnAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getBgnAmt();
                    bgnQty = null == financeAaLedgerAcctDTOS.get(i).getBgnQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getBgnQty();
                    drQty = null == financeAaLedgerAcctDTOS.get(i).getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getDrQty();
                    drAmt = null == financeAaLedgerAcctDTOS.get(i).getDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getDrAmt();
                    crQty = null == financeAaLedgerAcctDTOS.get(i).getCrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getCrQty();
                    crAmt = null == financeAaLedgerAcctDTOS.get(i).getCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getCrAmt();
                    endOfPeriodQty = (bgnQty.add(drQty)).subtract(crQty);
                    endOfPeriodAmt = (bgnAmt.add(drAmt)).subtract(crAmt);
                    financeAaLedgerAcctDTOS.get(i).setEndOfPeriodAmt(endOfPeriodAmt);
                    financeAaLedgerAcctDTOS.get(i).setEndOfPeriodQty(endOfPeriodQty);
                }
                financeAaLedgerAcctDTOS2.addAll(financeAaLedgerAcctDTOS);
            }

            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getYearAdd())) {
                FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO3 = new FinanceAaLedgerAcctDTO();
                financeAaLedgerAcctDTO.setAcctCode(acctCode1.getAcctCode());
                FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2 = financeAaLedgerlCalMapper.selectFinanceAaLedgerlCalMaxMonth(financeAaLedgerAcctDTO);
                BigDecimal yearCrAmt = (null == financeAaLedgerAcctDTO2.getCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getCrAmt()).add(null == financeAaLedgerAcctDTO2.getYearCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getYearCrAmt());
                BigDecimal yearCrQty = (null == financeAaLedgerAcctDTO2.getYearCrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getYearCrQty()).add(null == financeAaLedgerAcctDTO2.getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getDrQty());
                BigDecimal yearDrAmt = (null == financeAaLedgerAcctDTO2.getYearDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getYearDrAmt()).add(null == financeAaLedgerAcctDTO2.getDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getDrAmt());
                BigDecimal yearDrQty = (null == financeAaLedgerAcctDTO2.getYearDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getYearDrQty()).add(null == financeAaLedgerAcctDTO2.getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getDrQty());
                if (!yearCrAmt.equals(BigDecimal.ZERO)) {
                    financeAaLedgerAcctDTO3.setCrAmt(yearCrAmt);
                }
                if (!yearCrQty.equals(BigDecimal.ZERO)) {
                    financeAaLedgerAcctDTO3.setCrQty(yearCrQty);
                }
                if (!yearDrAmt.equals(BigDecimal.ZERO)) {
                    financeAaLedgerAcctDTO3.setDrAmt(yearDrAmt);
                }
                if (!yearDrQty.equals(BigDecimal.ZERO)) {
                    financeAaLedgerAcctDTO3.setDrQty(yearDrQty);
                }
                financeAaLedgerAcctDTO3.setCalName("本年累计");
                financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTO3);
            }

            Map mapTemp = new HashMap(16);
            mapTemp.put("acctCode1", acctCode1.getAcctCode() + acctCode1.getAcctName());
            mapTemp.put("list", financeAaLedgerAcctDTOS2);
            maps.add(mapTemp);

        }

        return maps;
    }

    @Override
    public List<Map<String, Object>> selectListNumberDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO) {
        List<Map<String, Object>> maps = new ArrayList<>();
        financeAaLedgerAcctDTO.setStartDate(financeAaLedgerAcctDTO.getStartDate().substring(0, 7));
        financeAaLedgerAcctDTO.setEndDate(financeAaLedgerAcctDTO.getEndDate().substring(0, 7));
        FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 = new FinanceAaLedgerAcctDTO();
        BeanUtils.copyProperties(financeAaLedgerAcctDTO, financeAaLedgerAcctDTO1);
        Map<String, String> dateMap = new HashMap<>(16);
        List<FinanceAaLedgerAcctDTO> calName = financeAaLedgerlCalMapper.selectCalName(financeAaLedgerAcctDTO);
        for (FinanceAaLedgerAcctDTO acctCode1 : calName) {
            List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS;
            financeAaLedgerAcctDTO1.setCalName(acctCode1.getCalNamea());
            financeAaLedgerAcctDTOS = financeAaLedgerAcctMapper.selectFinanceAaGeneralLedgerCalName(financeAaLedgerAcctDTO1);
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getUnpostedVoucher())) {
                financeAaLedgerAcctDTOS = selectAccountLevel(financeAaLedgerAcctDTOS, financeAaLedgerAcctDTO);
            }
            List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS2 = new ArrayList<>();
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getCurrentPeriod())) {
                BigDecimal bgnAmt;
                BigDecimal bgnQty;
                BigDecimal drQty;
                BigDecimal drAmt;
                BigDecimal crQty;
                BigDecimal crAmt;
                BigDecimal endOfPeriodQty;
                BigDecimal endOfPeriodAmt;
                BigDecimal bgnAmtAdd = BigDecimal.ZERO;
                BigDecimal bgnQtyAdd = BigDecimal.ZERO;
                BigDecimal drQtyAdd = BigDecimal.ZERO;
                BigDecimal drAmtAdd = BigDecimal.ZERO;
                BigDecimal crQtyAdd = BigDecimal.ZERO;
                BigDecimal crAmtAdd = BigDecimal.ZERO;
                BigDecimal endOfPeriodQtyAdd = BigDecimal.ZERO;
                BigDecimal endOfPeriodAmtAdd = BigDecimal.ZERO;
                for (int i = 0; i < financeAaLedgerAcctDTOS.size(); i++) {
                    FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2 = new FinanceAaLedgerAcctDTO();
                    if (financeAaLedgerAcctDTOS.size() > 1) {
                        if (!dateMap.containsKey(financeAaLedgerAcctDTOS.get(i).getAcctPeriod())) {
                            if (dateMap.size() > 0) {
                                financeAaLedgerAcctDTO2.setAcctName("本期合计");
                                financeAaLedgerAcctDTO2.setBgnAmt(bgnAmtAdd);
                                financeAaLedgerAcctDTO2.setBgnQty(bgnQtyAdd);
                                financeAaLedgerAcctDTO2.setDrQty(drQtyAdd);
                                financeAaLedgerAcctDTO2.setDrAmt(drAmtAdd);
                                financeAaLedgerAcctDTO2.setCrQty(crQtyAdd);
                                financeAaLedgerAcctDTO2.setCrAmt(crAmtAdd);
                                financeAaLedgerAcctDTO2.setEndOfPeriodQty(endOfPeriodQtyAdd);
                                financeAaLedgerAcctDTO2.setEndOfPeriodAmt(endOfPeriodAmtAdd);
                                financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTO2);
                                dateMap.clear();
                                endOfPeriodQtyAdd = BigDecimal.ZERO;
                                endOfPeriodAmtAdd = BigDecimal.ZERO;
                                bgnAmtAdd = BigDecimal.ZERO;
                                bgnQtyAdd = BigDecimal.ZERO;
                                drQtyAdd = BigDecimal.ZERO;
                                drAmtAdd = BigDecimal.ZERO;
                                crQtyAdd = BigDecimal.ZERO;
                                crAmtAdd = BigDecimal.ZERO;
                            }
                            dateMap.put(financeAaLedgerAcctDTOS.get(i).getAcctPeriod(), financeAaLedgerAcctDTOS.get(i).getAcctPeriod());
                            financeAaLedgerAcctDTO2 = new FinanceAaLedgerAcctDTO();
                        }
                    }
                    bgnAmt = null == financeAaLedgerAcctDTOS.get(i).getBgnAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getBgnAmt();
                    bgnQty = null == financeAaLedgerAcctDTOS.get(i).getBgnQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getBgnQty();
                    drQty = null == financeAaLedgerAcctDTOS.get(i).getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getDrQty();
                    drAmt = null == financeAaLedgerAcctDTOS.get(i).getDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getDrAmt();
                    crQty = null == financeAaLedgerAcctDTOS.get(i).getCrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getCrQty();
                    crAmt = null == financeAaLedgerAcctDTOS.get(i).getCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getCrAmt();
                    endOfPeriodQty = (bgnQty.add(drQty)).subtract(crQty);
                    endOfPeriodAmt = (bgnAmt.add(drAmt)).subtract(crAmt);
                    financeAaLedgerAcctDTOS.get(i).setEndOfPeriodAmt(endOfPeriodAmt);
                    financeAaLedgerAcctDTOS.get(i).setEndOfPeriodQty(endOfPeriodQty);
                    financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTOS.get(i));
                    bgnAmtAdd = bgnAmtAdd.add(bgnAmt);
                    bgnQtyAdd = bgnQtyAdd.add(bgnQty);
                    drQtyAdd = drQtyAdd.add(drQty);
                    drAmtAdd = drAmtAdd.add(drAmt);
                    crQtyAdd = crQtyAdd.add(crQty);
                    crAmtAdd = crAmtAdd.add(crAmt);
                    endOfPeriodQtyAdd = endOfPeriodQtyAdd.add((bgnQty.add(drQty)).subtract(crQty));
                    endOfPeriodAmtAdd = endOfPeriodAmtAdd.add((bgnAmt.add(drAmt)).subtract(crAmt));
                }
                dateMap.clear();
                FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2 = new FinanceAaLedgerAcctDTO();
                financeAaLedgerAcctDTO2.setAcctName("本期合计");
                financeAaLedgerAcctDTO2.setBgnAmt(bgnAmtAdd);
                financeAaLedgerAcctDTO2.setBgnQty(bgnQtyAdd);
                financeAaLedgerAcctDTO2.setDrQty(drQtyAdd);
                financeAaLedgerAcctDTO2.setDrAmt(drAmtAdd);
                financeAaLedgerAcctDTO2.setCrQty(crQtyAdd);
                financeAaLedgerAcctDTO2.setCrAmt(crAmtAdd);
                financeAaLedgerAcctDTO2.setEndOfPeriodQty(endOfPeriodQtyAdd);
                financeAaLedgerAcctDTO2.setEndOfPeriodAmt(endOfPeriodAmtAdd);
                financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTO2);

            } else {
                BigDecimal bgnAmt;
                BigDecimal bgnQty;
                BigDecimal drQty;
                BigDecimal drAmt;
                BigDecimal crQty;
                BigDecimal crAmt;
                BigDecimal endOfPeriodQty;
                BigDecimal endOfPeriodAmt;
                for (int i = 0; i < financeAaLedgerAcctDTOS.size(); i++) {
                    bgnAmt = null == financeAaLedgerAcctDTOS.get(i).getBgnAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getBgnAmt();
                    bgnQty = null == financeAaLedgerAcctDTOS.get(i).getBgnQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getBgnQty();
                    drQty = null == financeAaLedgerAcctDTOS.get(i).getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getDrQty();
                    drAmt = null == financeAaLedgerAcctDTOS.get(i).getDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getDrAmt();
                    crQty = null == financeAaLedgerAcctDTOS.get(i).getCrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getCrQty();
                    crAmt = null == financeAaLedgerAcctDTOS.get(i).getCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTOS.get(i).getCrAmt();
                    endOfPeriodQty = (bgnQty.add(drQty)).subtract(crQty);
                    endOfPeriodAmt = (bgnAmt.add(drAmt)).subtract(crAmt);
                    financeAaLedgerAcctDTOS.get(i).setEndOfPeriodAmt(endOfPeriodAmt);
                    financeAaLedgerAcctDTOS.get(i).setEndOfPeriodQty(endOfPeriodQty);
                }
                financeAaLedgerAcctDTOS2.addAll(financeAaLedgerAcctDTOS);
            }
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getAmountNotDisplayed())) {
                financeAaLedgerAcctDTOS2 = selectList(financeAaLedgerAcctDTOS2);
            }
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getBalanceZero())) {
                financeAaLedgerAcctDTOS2 = selectListAdd(financeAaLedgerAcctDTOS2);
            }
            FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO3 = new FinanceAaLedgerAcctDTO();
            if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getYearAdd())) {

                financeAaLedgerAcctDTO.setCalCodea(acctCode1.getCalCodea());
                FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2 = financeAaLedgerlCalMapper.selecMaxMonth(financeAaLedgerAcctDTO);
                if (financeAaLedgerAcctDTO2!=null){
                    BigDecimal yearCrAmt = (null == financeAaLedgerAcctDTO2.getCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getCrAmt()).add(null == financeAaLedgerAcctDTO2.getYearCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getYearCrAmt());
                    BigDecimal yearCrQty = (null == financeAaLedgerAcctDTO2.getYearCrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getYearCrQty()).add(null == financeAaLedgerAcctDTO2.getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getDrQty());
                    BigDecimal yearDrAmt = (null == financeAaLedgerAcctDTO2.getYearDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getYearDrAmt()).add(null == financeAaLedgerAcctDTO2.getDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getDrAmt());
                    BigDecimal yearDrQty = (null == financeAaLedgerAcctDTO2.getYearDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getYearDrQty()).add(null == financeAaLedgerAcctDTO2.getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcctDTO2.getDrQty());
                    if (!yearCrAmt.equals(BigDecimal.ZERO)) {
                        financeAaLedgerAcctDTO3.setCrAmt(yearCrAmt);
                    }
                    if (!yearCrQty.equals(BigDecimal.ZERO)) {
                        financeAaLedgerAcctDTO3.setCrQty(yearCrQty);
                    }
                    if (!yearDrAmt.equals(BigDecimal.ZERO)) {
                        financeAaLedgerAcctDTO3.setDrAmt(yearDrAmt);
                    }
                    if (!yearDrQty.equals(BigDecimal.ZERO)) {
                        financeAaLedgerAcctDTO3.setDrQty(yearDrQty);
                    }

                }
                financeAaLedgerAcctDTO3.setAcctName("本年累计");
                financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTO3);
            }
            Map mapTemp = new HashMap(16);
            mapTemp.put("acctCode1", acctCode1.getCalCodea() + acctCode1.getCalNamea());
            mapTemp.put("list", financeAaLedgerAcctDTOS2);
            maps.add(mapTemp);

        }

        return maps;
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

    public List<FinanceAaLedgerAcctDTO> selectAccountLevel(List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTO, FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2) {
        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctList = new ArrayList<>();
        String substring = "";
        financeAaLedgerAcctList.addAll(financeAaLedgerAcctDTO);
        FinanceAaVoucherDetail financeAaVoucherDetail = new FinanceAaVoucherDetail();
        for (FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 : financeAaLedgerAcctDTO) {
            FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO3 = new FinanceAaLedgerAcctDTO();
            financeAaVoucherDetail.setCompanyId(financeAaLedgerAcctDTO2.getCompanyId());
            financeAaVoucherDetail.setAcctCode(financeAaLedgerAcctDTO1.getAcctCode());
            financeAaVoucherDetail.setStartDate(financeAaLedgerAcctDTO2.getStartDate() + "-01");
            financeAaVoucherDetail.setEndDate(financeAaLedgerAcctDTO2.getEndDate() + "-31");
            FinanceAaVoucherDetail financeAaVoucherDetail1 = financeAaVoucherDetailMapper.selectFinanceAaLedgerAcctList(financeAaVoucherDetail);
            if (financeAaVoucherDetail1 != null) {
                if (ConstantsUtil.DRCRC.equals(financeAaVoucherDetail1.getDrcr())) {
                    financeAaLedgerAcctDTO3.setDrAmt(financeAaVoucherDetail1.getNtamt());
                    financeAaLedgerAcctDTO3.setDrQty(financeAaVoucherDetail1.getQtyFrnamt());
                } else {
                    financeAaLedgerAcctDTO3.setCrAmt(financeAaVoucherDetail1.getNtamt());
                    financeAaLedgerAcctDTO3.setCrQty(financeAaVoucherDetail1.getQtyFrnamt());
                }
                financeAaLedgerAcctDTO3.setAcctPeriod(financeAaVoucherDetail1.getVoucherDate().substring(0, 7));
                financeAaLedgerAcctDTO3.setAcctCode(financeAaVoucherDetail1.getAcctCode());
                financeAaLedgerAcctDTO3.setAcctName(financeAaVoucherDetail1.getAcctName());

            }

            if (ConstantsUtil.CALRULE1.equals(financeAaLedgerAcctDTO2.getAccountLevel())) {
                if (financeAaLedgerAcctDTO1.getAcctCode().length() > 4) {
                    substring = financeAaLedgerAcctDTO1.getAcctCode().substring(0, 4);
                } else {
                    substring = financeAaLedgerAcctDTO1.getAcctCode();
                }
            }
            if (ConstantsUtil.CALRULE2.equals(financeAaLedgerAcctDTO2.getAccountLevel())) {
                if (financeAaLedgerAcctDTO1.getAcctCode().length() > 6) {
                    substring = financeAaLedgerAcctDTO1.getAcctCode().substring(0, 6);
                } else {
                    substring = financeAaLedgerAcctDTO1.getAcctCode();
                }
            }
            if (ConstantsUtil.CALRULE3.equals(financeAaLedgerAcctDTO2.getAccountLevel())) {
                if (financeAaLedgerAcctDTO1.getAcctCode().length() > 8) {
                    substring = financeAaLedgerAcctDTO1.getAcctCode().substring(0, 8);
                } else {
                    substring = financeAaLedgerAcctDTO1.getAcctCode();
                }
            }
            if (ConstantsUtil.CALRULE4.equals(financeAaLedgerAcctDTO2.getAccountLevel())) {
                if (financeAaLedgerAcctDTO1.getAcctCode().length() > 10) {
                    substring = financeAaLedgerAcctDTO1.getAcctCode().substring(0, 10);
                } else {
                    substring = financeAaLedgerAcctDTO1.getAcctCode();
                }
            }
            if (ConstantsUtil.CALRULE5.equals(financeAaLedgerAcctDTO2.getAccountLevel())) {
                if (financeAaLedgerAcctDTO1.getAcctCode().length() > 12) {
                    substring = financeAaLedgerAcctDTO1.getAcctCode().substring(0, 12);
                } else {
                    substring = financeAaLedgerAcctDTO1.getAcctCode();
                }
            }
            if (ConstantsUtil.ISEMPTY.equals(financeAaLedgerAcctDTO2.getAccountLevel())) {
                substring = financeAaLedgerAcctDTO1.getAcctCode();
            }
            financeAaLedgerAcctDTO3.setAcctCode(substring);
            if (ConstantsUtil.CALRULE2.equals(financeAaLedgerAcctDTO2.getMainItems())) {
                financeAaLedgerAcctDTO3.setCalName((financeAaVoucherDetail1.getCalNameb() + "_" + financeAaVoucherDetail1.getCalNamec() + "_" + financeAaVoucherDetail1.getCalNamed())); financeAaLedgerAcctDTO3.setCalName((financeAaVoucherDetail1.getCalNameb() + "_" + financeAaVoucherDetail1.getCalNamec() + "_" + financeAaVoucherDetail1.getCalNamed()));
            }
            financeAaLedgerAcctList.add(financeAaLedgerAcctDTO3);
        }
        financeAaLedgerAcctList.sort(Comparator.comparing(FinanceAaLedgerAcctDTO::getAcctPeriod));
        return financeAaLedgerAcctList;
    }


}
