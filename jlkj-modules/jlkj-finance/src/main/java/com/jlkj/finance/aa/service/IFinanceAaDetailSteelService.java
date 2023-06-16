package com.jlkj.finance.aa.service;

import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;

import java.util.List;
import java.util.Map;

/**
 * 明细报表Service接口
 *
 * @author 265799
 * @date 2023-06-02
 */
public interface IFinanceAaDetailSteelService {
    /**
     * 总账报表
     *
     * @param financeAaLedgerAcctDTO 科目余额报表
     * @return 科目余额报表
     */
    public List<Map<String, Object>> selectListDetailSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO);
}
