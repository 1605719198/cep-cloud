package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import com.jlkj.finance.aa.service.IFinanceAaGeneralLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 总账报表Controller
 *
 * @author 265799
 * @date 2023-06-02
 */
@RestController
@RequestMapping("/generalLedger")
public class FinanceAaGeneralLedgerController  extends BaseController {
    @Autowired
    private IFinanceAaGeneralLedgerService financeAaGeneralLedgerService;
    /**
     * 查询科目余额报表
     */
    @RequiresPermissions("aa:accountSteel:listDetailIfSteel")
    @GetMapping("/listDetailIfSteel")
    public List<Map<String, Object>>  listDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO)
    {
        List<Map<String, Object>> list = financeAaGeneralLedgerService.selectListDetailIfSteel(financeAaLedgerAcctDTO);
        return list;
    }
    /**
     * 查询核算项目余额报表
     */
    @RequiresPermissions("aa:accountSteel:listDetailIfSteel")
    @GetMapping("/listNumberDetailIfSteel")
    public List<Map<String, Object>>  listNumberDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO)
    {
        List<Map<String, Object>> list = financeAaGeneralLedgerService.selectListNumberDetailIfSteel(financeAaLedgerAcctDTO);
        return list;
    }
}
