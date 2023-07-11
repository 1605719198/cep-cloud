package com.jlkj.finance.aa.report;

import com.jlkj.finance.aa.domain.FinanceAaProjectFormula;
import com.jlkj.finance.aa.domain.FinanceAaProjectResult;
import com.jlkj.finance.aa.domain.FinanceAaReportInfo;
import com.jlkj.finance.aa.dto.FinanceAaReportBalanceDTO;
import com.jlkj.finance.aa.mapper.FinanceAaProjectResultMapper;
import com.jlkj.finance.utils.ConstantsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @Author: 114288
 * @date: 2023/7/8 11:27
 */
@Service
@Slf4j
public class FinanceAaReportBalance {

    @Autowired
    private FinanceAaProjectResultMapper financeAaProjectResultMapper;

    public List query(FinanceAaReportInfo financeAaReportInfo) {
        FinanceAaProjectResult financeAaProjectResult = new FinanceAaProjectResult();
        financeAaProjectResult.setCompanyId(financeAaReportInfo.getCompanyId());
        financeAaProjectResult.setReportNo(financeAaReportInfo.getReportNo());
        financeAaProjectResult.setAcctPeriod(financeAaReportInfo.getAcctPeriod());
        List<FinanceAaProjectResult> financeAaProjectResults =   financeAaProjectResultMapper.selectFinanceAaProjectResults(financeAaProjectResult);
        FinanceAaReportBalanceDTO financeAaReportBalanceDTO =  new FinanceAaReportBalanceDTO();
        List<FinanceAaReportBalanceDTO> returnList = new ArrayList<FinanceAaReportBalanceDTO>();
        for(int i=0;i<financeAaProjectResults.size();i++){
            financeAaProjectResult = financeAaProjectResults.get(i);

            financeAaReportBalanceDTO = new FinanceAaReportBalanceDTO();
            if((ConstantsUtil.STR_1).equals(financeAaProjectResult.getItemType())){

                financeAaReportBalanceDTO.setReportId(financeAaProjectResult.getReportId());
                financeAaReportBalanceDTO.setItemCode(financeAaProjectResult.getItemCode());
                financeAaReportBalanceDTO.setItemRow(financeAaProjectResult.getItemRow());
                financeAaReportBalanceDTO.setItemName(financeAaProjectResult.getItemName());

                financeAaReportBalanceDTO.setEndAmt(financeAaProjectResult.getAmt());
                financeAaReportBalanceDTO.setYearBegAmt(financeAaProjectResult.getAmt());
                returnList.add(financeAaReportBalanceDTO);
            }

       }

        log.info("returnList.size()==========="+returnList.size());
        int count = 0;
        for(int i=0;i<financeAaProjectResults.size();i++){
            financeAaProjectResult = financeAaProjectResults.get(i);

            financeAaReportBalanceDTO = new FinanceAaReportBalanceDTO();
            if((ConstantsUtil.STR_2).equals(financeAaProjectResult.getItemType())){
                if(returnList.size()> count){
                    financeAaReportBalanceDTO = returnList.get(count);
                    financeAaReportBalanceDTO.setTwoItemCode(financeAaProjectResult.getItemCode());
                    financeAaReportBalanceDTO.setTwoItemRow(financeAaProjectResult.getItemRow());
                    financeAaReportBalanceDTO.setTwoItemName(financeAaProjectResult.getItemName());
                    financeAaReportBalanceDTO.setTwoEndAmt(financeAaProjectResult.getAmt());
                    financeAaReportBalanceDTO.setTwoYearBegAmt(financeAaProjectResult.getAmt());
                }else{
                    financeAaReportBalanceDTO.setReportId(financeAaProjectResult.getReportId());
                    financeAaReportBalanceDTO.setTwoItemCode(financeAaProjectResult.getItemCode());
                    financeAaReportBalanceDTO.setTwoItemRow(financeAaProjectResult.getItemRow());
                    financeAaReportBalanceDTO.setTwoItemName(financeAaProjectResult.getItemName());
                    financeAaReportBalanceDTO.setTwoEndAmt(financeAaProjectResult.getAmt());
                    financeAaReportBalanceDTO.setTwoEndAmt(financeAaProjectResult.getAmt());
                    returnList.add(financeAaReportBalanceDTO);
                }
                count++;
            }



        }


        log.info("returnList==========="+returnList);
        return  returnList;
    }
}
