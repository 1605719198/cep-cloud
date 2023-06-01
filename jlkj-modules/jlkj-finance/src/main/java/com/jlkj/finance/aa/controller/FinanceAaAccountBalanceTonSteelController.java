package com.jlkj.finance.aa.controller;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import com.jlkj.finance.aa.service.IFinanceAaAccountBalanceTonSteelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



/**
 * 科目余额报表Controller
 *
 * @author 265799
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/accountSteel")
public class FinanceAaAccountBalanceTonSteelController extends BaseController{
    @Autowired
    private IFinanceAaAccountBalanceTonSteelService financeAaAccountBalanceTonSteelService;
    /**
     * 查询科目余额报表
     */
    @RequiresPermissions("aa:accountSteel:listDetailIfSteel")
    @GetMapping("/listDetailIfSteel")
    public TableDataInfo listDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO)
    {
        startPage();
        List<FinanceAaLedgerAcctDTO> list = financeAaAccountBalanceTonSteelService.selectListDetailIfSteel(financeAaLedgerAcctDTO);
        return getDataTable(list);
    }
    /**
     * 查询科目余额报表
     */
    @RequiresPermissions("aa:accountSteel:listDetailIfSteel")
    @GetMapping("/listNumberDetailIfSteel")
    public TableDataInfo listNumberDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO)
    {
        startPage();
        List<FinanceAaLedgerAcctDTO> list = financeAaAccountBalanceTonSteelService.selectListNumberDetailIfSteel(financeAaLedgerAcctDTO);
        return getDataTable(list);
    }
    /**
     * 查询科目余额报表
     */
    @RequiresPermissions("aa:accountSteel:listDetailIfSteel")
    @GetMapping("/listCalNumberDetailIfSteel")
    public TableDataInfo listCalNumberDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO)
    {
        startPage();
        List<FinanceAaLedgerAcctDTO> list = financeAaAccountBalanceTonSteelService.selectListCalNumberDetailIfSteel(financeAaLedgerAcctDTO);
        return getDataTable(list);
    }
    /**
     * 查询科目余额报表
     */
    @RequiresPermissions("aa:accountSteel:listDetailIfSteel")
    @GetMapping("/listCalDetailIfSteel")
    public TableDataInfo listCalDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO)
    {
        startPage();
        List<FinanceAaLedgerAcctDTO> list = financeAaAccountBalanceTonSteelService.selectlistCalDetailIfSteel(financeAaLedgerAcctDTO);
        return getDataTable(list);
    }
}
