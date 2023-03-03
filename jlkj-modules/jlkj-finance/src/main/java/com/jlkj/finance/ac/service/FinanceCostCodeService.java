package com.jlkj.finance.ac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.finance.ac.domain.FinanceCostCode;
import com.jlkj.finance.ip.dto.FinanceCostDownTypeTreeDTO;

import java.util.List;

/**
* @author wang'mai
* @description 针对表【t_finance_cost_code(成本科目基本资料)】的数据库操作Service
*/
public interface FinanceCostCodeService extends IService<FinanceCostCode> {
    /**
     * 根据成本科目基本资料查询会计科目
     * @param  financeCostDownTypeTreeDTO
     * @return
     */
    List<FinanceCostDownTypeTreeDTO> getFinanceCostDownTypeTree(FinanceCostDownTypeTreeDTO financeCostDownTypeTreeDTO);
}
