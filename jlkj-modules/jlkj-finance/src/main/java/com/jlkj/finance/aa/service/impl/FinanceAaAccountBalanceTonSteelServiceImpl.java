package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import com.jlkj.finance.aa.mapper.FinanceAaLedgerlCalMapper;
import com.jlkj.finance.aa.service.IFinanceAaAccountBalanceTonSteelService;
import com.jlkj.finance.utils.AssertUtil;
import com.jlkj.finance.utils.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
    private FinanceAaLedgerlCalMapper financeAaLedgerlCalMapper;

    /**
     * 会计科目总账 查询
     * @param financeAaLedgerAcctDTO 科目余额报表
     * @return
     */
    @Override
    public List<FinanceAaLedgerAcctDTO> selectListDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO) {

        String startDate =financeAaLedgerAcctDTO.getStartDate().substring(0,7)+"-01";
        Date dateEnd = DateUtils.dateTime(DateUtils.YYYY_MM,financeAaLedgerAcctDTO.getEndDate());
        String endDate = DateUtils.getMaxMonthDate(DateUtils.dateTime(dateEnd));
        financeAaLedgerAcctDTO.setStartDetailDate(startDate);
        financeAaLedgerAcctDTO.setEndDetailDate(endDate);
        financeAaLedgerAcctDTO.setStartDate(financeAaLedgerAcctDTO.getStartDate().substring(0,7));
        financeAaLedgerAcctDTO.setEndDate(financeAaLedgerAcctDTO.getEndDate().substring(0,7));
        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS1 = new ArrayList<>();
        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS  ;
        financeAaLedgerAcctDTO.getUnpostedVoucher();
        financeAaLedgerAcctDTOS = financeAaLedgerlCalMapper.selectAmountNotDisplayedCal(financeAaLedgerAcctDTO);
       //无发生额不显示 Y
        if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getAmountNotDisplayed())){
            financeAaLedgerAcctDTOS = selectList(financeAaLedgerAcctDTOS);
        }
        // 余额为零且无发生额不显示 Y
        if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getBalanceZero())){
            financeAaLedgerAcctDTOS=  selectListAdd(financeAaLedgerAcctDTOS);
        }

        //合并层级
        if (financeAaLedgerAcctDTOS.size()>0){
            financeAaLedgerAcctDTOS1 = selectAccountLevel(financeAaLedgerAcctDTOS, financeAaLedgerAcctDTO);
        }
        return financeAaLedgerAcctDTOS1;
    }
    public List<FinanceAaLedgerAcctDTO> selectList(List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTO) {

        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS = new ArrayList<>();
        for (FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 :financeAaLedgerAcctDTO){
            if( AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getDrAmt()) .compareTo(BigDecimal.ZERO) == 0
                    || AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getCrAmt()) .compareTo(BigDecimal.ZERO)  ==0
                    || AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getDrQty()) .compareTo(BigDecimal.ZERO) == 0
                    || AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getCrQty()) .compareTo(BigDecimal.ZERO)  ==0
            ){
                financeAaLedgerAcctDTOS.add(financeAaLedgerAcctDTO1);
        }

    }
        return financeAaLedgerAcctDTOS;
    }

    public List<FinanceAaLedgerAcctDTO> selectListAdd(List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTO) {
       List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS = new ArrayList<>();
        for (FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 :financeAaLedgerAcctDTO){

            if( AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getDrAmt()) .compareTo(BigDecimal.ZERO) == 0
                    || AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getCrAmt()) .compareTo(BigDecimal.ZERO)  ==0
                    || AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getBgnAmt()) .compareTo(BigDecimal.ZERO) == 0
                    || AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getDrQty()) .compareTo(BigDecimal.ZERO) == 0
                    || AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getCrQty()) .compareTo(BigDecimal.ZERO)  ==0
                    || AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getBgnQty()) .compareTo(BigDecimal.ZERO)  == 0
        ){
                financeAaLedgerAcctDTOS.add(financeAaLedgerAcctDTO1);
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

        FinanceAaVoucherDetail financeAaVoucherDetail = new FinanceAaVoucherDetail();
        for (FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 :financeAaLedgerAcctDTO){
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
            BigDecimal yearCrAmt = AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getYearCrAmt() );
            BigDecimal yearCrQty = AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getYearCrQty() );
            BigDecimal yearDrAmt = AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getYearDrAmt() );;
            BigDecimal yearDrQty =AssertUtil.bigDecimalValue(financeAaLedgerAcctDTO1.getYearDrQty() );;
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

            BigDecimal subtract = ((null == financeAaLedgerAcctDTO1.getBgnQty() ?
                    BigDecimal.ZERO :financeAaLedgerAcctDTO1.getBgnQty())
                    .add(null == financeAaLedgerAcctDTO1.getDrQty() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getDrQty()))
                    .subtract(null == financeAaLedgerAcctDTO1.getCrQty() ?BigDecimal.ZERO :financeAaLedgerAcctDTO1.getCrQty());
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
