package com.jlkj.finance.AA.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.finance.AA.domain.FinanceAccountTree;
import com.jlkj.finance.AA.dto.FinanceAccountingTreeDTO;

import java.util.List;

/**
* @author wang'mai
* @description 针对表【t_finance_account_tree(会计科目树状资料)】的数据库操作Service
*/
public interface FinanceAccountTreeService extends IService<FinanceAccountTree> {
    /**
     * 获取会计科目树结构
     * @return   List<FinanceAccountingTreeDTO> getEquipmentTypeTreeList()
     */
    List<FinanceAccountingTreeDTO> getEquipmentTypeTreeList();
    /**
     * 获取会计科目树结构
     * @return   List<FinanceAccountingTreeDTO> getEquipmentTypeAllTreeList()
     */
    List<FinanceAccountingTreeDTO> getEquipmentTypeAllTreeList();
}
