package com.jlkj.finance.aa.controller;

import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import com.jlkj.finance.aa.service.IFinanceAaDetailSteelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 明细.报表Controller
 *
 * @author 265799
 * @date 2023-06-02
 */
@RestController
@RequestMapping("/detailSteel")
public class FinanceAaDetailSteelController {
    @Autowired
    private IFinanceAaDetailSteelService financeAaDetailSteelService;
    /**
     * 查询科目余额报表
     */
    @RequiresPermissions("aa:detailSteel:listDetailSteel")
    @GetMapping("/listDetailIfSteel")
    public List<Map<String, Object>> listDetailIfSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO)
    {
        List<Map<String, Object>> list = financeAaDetailSteelService.selectListDetailSteel(financeAaLedgerAcctDTO);
        return list;
    }
}
