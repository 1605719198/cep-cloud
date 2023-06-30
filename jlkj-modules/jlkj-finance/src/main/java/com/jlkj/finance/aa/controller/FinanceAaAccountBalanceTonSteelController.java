package com.jlkj.finance.aa.controller;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.fastjson2.JSON;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.security.annotation.InnerAuth;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import com.jlkj.finance.aa.service.IFinanceAaAccountBalanceTonSteelService;
import com.jlkj.finance.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 科目余额报表Controller
 *
 * @author 265799
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/accountSteel")
@Slf4j
public class FinanceAaAccountBalanceTonSteelController extends BaseController{
    @Autowired
    private IFinanceAaAccountBalanceTonSteelService financeAaAccountBalanceTonSteelService;
    /**
     * 查询科目余额报表
     */
    @RequiresPermissions("aa:accountSteel:listDetailIfSteel")
    @GetMapping("/listDetailIfSteel")
    public List<FinanceAaLedgerAcctDTO>  listDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO)
    {

        List<FinanceAaLedgerAcctDTO> list = financeAaAccountBalanceTonSteelService.selectListDetailIfSteel(financeAaLedgerAcctDTO);
        return list;
    }


    /**
     * 导出科目余额报表
     */
    @InnerAuth
    @PostMapping("/reportDetailIfSteel")
    public  List<Map<String, String>>  reportDetailIfSteel(@RequestBody Map<String, Object> parameters)
    {
        FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO = new FinanceAaLedgerAcctDTO();
        financeAaLedgerAcctDTO.setCompanyId(AssertUtil.stringValue(parameters.get("companyId")));
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

        List<FinanceAaLedgerAcctDTO> list = financeAaAccountBalanceTonSteelService.selectListDetailIfSteel(financeAaLedgerAcctDTO);
        List<Map<String, String>> returnList = new ArrayList();
        for(FinanceAaLedgerAcctDTO vo :list){
         //  Map map=  JSON.parseObject(JSON.toJSONString(vo),Map.class);
            Map map=   BeanMapUtils.create(vo);
            returnList.add(map);
        }
        log.info("returnList=》{}",returnList);
        return returnList;
    }

}
