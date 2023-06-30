package com.jlkj.finance.aa.ureport;


import com.jlkj.common.core.utils.SpringUtils;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import com.jlkj.finance.aa.service.impl.FinanceAaAccountBalanceTonSteelServiceImpl;
import com.jlkj.finance.utils.AssertUtil;
import com.jlkj.finance.utils.ConstantsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author 114288
 * @version 1.0.0
 * @ClassName 科目余额表
 * @createTime 2023年06月27日 17:10
 */
@Component("accountBalanceTonSteelReport")
@Slf4j
public class AccountBalanceTonSteelReport {
        /**
         * 方法必须包含三个参数：String，String，Map
         * @return 集合类型
         */
        public List<Map<String, Object>> loadReportData(String dsName, String datasetName, Map<String, Object> parameters) {
            FinanceAaAccountBalanceTonSteelServiceImpl financeAaAccountBalanceTonSteelServiceImpl =
                    (FinanceAaAccountBalanceTonSteelServiceImpl) SpringUtils.getBean("proYearPlanYieldServiceImpl");
            FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO = new FinanceAaLedgerAcctDTO();
            financeAaLedgerAcctDTO.setAcctCode(AssertUtil.stringValue(parameters.get("acctCode")));
            financeAaLedgerAcctDTO.setReportType(AssertUtil.stringValue(parameters.get("reportType")));
            financeAaLedgerAcctDTO.setStartDate(AssertUtil.stringValue(parameters.get("acctPeriodStart")));
            financeAaLedgerAcctDTO.setEndDate(AssertUtil.stringValue(parameters.get("acctPeriodEnd")));
            financeAaLedgerAcctDTO.setAccountLevel(AssertUtil.stringValue(parameters.get("accountLevel")));
            financeAaLedgerAcctDTO.setAcctNameStart(AssertUtil.stringValue(parameters.get("acctNameStart")));
            financeAaLedgerAcctDTO.setAcctNameEnd(AssertUtil.stringValue(parameters.get("acctNameEnd")));
            financeAaLedgerAcctDTO.setCalTypeCode(AssertUtil.stringValue(parameters.get("calTypeCode")));
            financeAaLedgerAcctDTO.setCalNameaStart(AssertUtil.stringValue(parameters.get("calNameaStart")));
            financeAaLedgerAcctDTO.setCalNameaEnd(AssertUtil.stringValue(parameters.get("calNameaEnd")));
            financeAaLedgerAcctDTO.setUnpostedVoucher(AssertUtil.stringValue(parameters.get("unpostedVoucher")));
            financeAaLedgerAcctDTO.setAmountNotDisplayed(AssertUtil.stringValue(parameters.get("amountNotDisplayed")));
            financeAaLedgerAcctDTO.setBalanceZero(AssertUtil.stringValue(parameters.get("balanceZero")));
            financeAaLedgerAcctDTO.setIsNoNumber(AssertUtil.stringValue(parameters.get("isNoNumber")));
            log.info("接收到参数->parameters{}",parameters);
            log.info("接收到参数->financeAaLedgerAcctDTO{}",financeAaLedgerAcctDTO);

            List list = financeAaAccountBalanceTonSteelServiceImpl.selectListDetailIfSteel(financeAaLedgerAcctDTO);
            return list;
        }

    /**
     * 方法必须包含三个参数：String，String，Map
     * @return 集合类型 ，包含字段：id,name,salary
     */
    public List<Map<String, Object>> loadReportTitleData(String dsName, String datasetName, Map<String, Object> parameters) {
        log.info("接收到参数->parameters{}",parameters);
       String amountNotDisplayed =  AssertUtil.stringValue(parameters.get("amountNotDisplayed"));
        List<Map<String, Object>> list = new ArrayList<>();

       if(ConstantsUtil.DISABLEDCODE.equals(amountNotDisplayed)){
           Map<String, Object> dataMap = new HashMap<>(4);
           dataMap.put("title11","借方金额");
           dataMap.put("title12","贷方金额");
           dataMap.put("title13","借方数量");
           dataMap.put("title14","贷方数量");
           List<Map<String, Object>> list1 = new ArrayList<>();
           list1.add(dataMap);


           Map<String, Object> dataMap1 = new HashMap<>(4);
           dataMap1.put("title1",list1);
           dataMap1.put("title1",list1);
           dataMap1.put("title1",list1);
           dataMap1.put("title1",list1);



           list.add(dataMap1);
       }else{
           Map<String, Object> dataMap = new HashMap<>(2);
           dataMap.put("title11","借方");
           dataMap.put("title12","贷方");

           List<Map<String, Object>> list1 = new ArrayList<>();
           list1.add(dataMap);


           Map<String, Object> dataMap1 = new HashMap<>(4);
           dataMap1.put("title1",list1);
           dataMap1.put("title1",list1);
           dataMap1.put("title1",list1);
           dataMap1.put("title1",list1);



           list.add(dataMap1);
       }


        return list;
    }
}
